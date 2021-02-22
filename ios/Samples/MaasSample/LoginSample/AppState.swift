import MaasCore
import Combine
import SwiftUI

extension VerifyProviderRequirementsResponse: Identifiable { }

class AppState: ObservableObject {

    @Published var user: User? = nil
    @Published var error: AuthenticationError? = nil
    @Published var providersRequirementStatus: VerifyProviderRequirementsResponse? = nil


    var cancelableStore = Set<AnyCancellable>()

    // MARK: - Authentication

    @Published var authenticationProvider: AuthenticationProvider? = nil {
        didSet {

            isLoading = authenticationProvider != nil

            guard
                authenticationProvider != oldValue,
                let authentication = authenticationProvider
            else { return }

            authenticate(authentication)
        }
    }

    func authenticate(_ provider: AuthenticationProvider) {
        FacebookAuthentication().authenticate
            .flatMap { FirebaseAuthentication.init(authCredential: $0).signIn() }
            .sink(
                receiveCompletion: { [unowned self] in
                    if case .failure(let error) = $0 {
                        self.error = error
                    } else {
                        self.error = nil
                    }

                    self.authenticationProvider = nil
                },
                receiveValue: { [unowned self] in
                    MaasConfiguration.accessToken = $0
                    MaasConfiguration.temporaryAccessToken = $0
                    self.getOrCreateUser()
                }
            )
            .store(in: &cancelableStore)
    }

    // MARK: - Requests

    func getOrCreateUser() {
        UsersApi.shared.createOrGetUser(profile: .default).publisher
            .sink(receiveCompletion: onError, receiveValue: onValue)
            .store(in: &cancelableStore)
    }

    func updateProfile() {
        UsersApi.shared.updateProfile(profile: user?.profile)
            .publisher
            .sink(receiveCompletion: onError, receiveValue: onValue)
            .store(in: &cancelableStore)
    }

    func requirements() {
        UsersApi.shared.providerRequirements()
            .publisher
            .sink(receiveCompletion: onError, receiveValue: onValue)
            .store(in: &cancelableStore)
    }

    private func onError(_ completion: Subscribers.Completion<ApiError>) {
        switch completion {
        case .finished:
            error = nil
        case let .failure(apiError):
            error = .apiError(apiError)
        }

        isLoading = false
    }

    private func onValue(_ completion: User) {
        user = completion
    }

    private func onValue(_ completion: VerifyProviderRequirementsResponse) {

        let hasUnfulfilledRequirements = completion.requirements
            .compactMap { $0.requirementStatuses.flatMap { $0[""]?.fulfilled?.boolValue } }
            .contains(false)

        //providersRequirementStatus = hasUnfulfilledRequirements ? completion : nil
        providersRequirementStatus = completion 
    }

    // MARK: - Bindings

    var firstName: String {
        get { user?.profile.firstName ?? "" }
        set { user?.profile.firstName = newValue }
    }

    var lastName: String {
        get { user?.profile.lastName ?? "" }
        set { user?.profile.lastName = newValue }
    }

    // MARK: - Queries

    var isLoading: Bool = false

    func logout() {
        user = nil
        corruptToken()
    }

    func corruptToken() {
        MaasConfiguration.accessToken = ""
    }
}

extension Profile {

    static var `default`: Self {
        .init(
            gender: .male,
            ext: [:],
            firstName: "John",
            lastName: "Doe",
            displayName: "John",
            email: "john.doe@maas.com",
            address: .init(lineOne: nil, lineTwo: nil, postCode: nil, city: "Springfield", country: "USA"),
            birthDate: nil
        )
    }
}
