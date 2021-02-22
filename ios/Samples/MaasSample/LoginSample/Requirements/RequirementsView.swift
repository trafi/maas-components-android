import SwiftUI
import MaasCore
import MaasComponents

class RequirementsState: ObservableObject {

    @Published var requirementStatus: VerifyProviderRequirementsResponse

    init(requirementStatus: VerifyProviderRequirementsResponse) {
        self.requirementStatus = requirementStatus
    }

    @Published var showMPSRequirements: Bool = false
}

struct RequirementsView: View {

    @ObservedObject var requiremenetsState: RequirementsState
    @Environment(\.presentationMode) private var presentationMode

    var body: some View {
        NavigationView {
            VStack(spacing: 44) {
                requirementImage()
                requirementOptions()
                requirementButtons()
            }
            .navigationTitle("Terms of service")
            .listStyle(InsetGroupedListStyle())
            .background(
                NavigationLink(
                    destination: MSPRequirementsView(),
                    isActive: $requiremenetsState.showMPSRequirements,
                    label: { EmptyView() }
                )
            )
        }
        .environmentObject(requiremenetsState)
    }
}

// MARK: - Content

private extension RequirementsView {

    func requirementImage() -> some View {
        VStack {
            Spacer()
            Image(uiImage: #imageLiteral(resourceName: "requirements"))
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: 150, maxHeight: 150)
        }
    }

    func requirementOptions() -> some View {

        let displayName = String(describing: Bundle.main.infoDictionary?["CFBundleName"] ?? "")

        return VStack {
            RequirementListItem(
                checked: .constant(true),
                title: "I agree to \(displayName)’s general",
                link: ("Terms of service", { /*  */ })
            )
            RequirementListItem(
                checked: $requiremenetsState.showMPSRequirements,
                title: "I agree to all \(displayName) mobility service provider’s Terms of service",
                link: ("Terms of service", { requiremenetsState.showMPSRequirements.toggle() })
            )
        }
    }

    func requirementButtons() -> some View {
        VStack(spacing: 16) {
            Button("Let's go!") { presentationMode.wrappedValue.dismiss() }
            Button("Cancel") { presentationMode.wrappedValue.dismiss() }
                .environment(\.uiColorPrimary, .systemGray5)
                .environment(\.uiColorOnPrimary, .label)
            Spacer()
        }
        .padding()
    }
}

struct RequirementsView_Previews: PreviewProvider {

    static var previews: some View {
        RequirementsView(requiremenetsState: .init(requirementStatus: .init(requirements: [], user: .init(id: "", identity: .init(verificationStatus: .failed, verificationMessageKey: nil, verificationFallbackMessage: nil), profile: .default, phoneNumber: .init(verificationStatus: .failed, number: nil, verificationMessageKey: nil, verificationFallbackMessage: nil), providerAccounts: [], drivingLicence: .init(categories: [], verificationStatus: .failed, licenceNumber: nil, issuedAt: nil, expiresAt: nil, verificationMessageKey: nil, verificationFallbackMessage: nil), terms: .init(platformTerms: [], providerTerms: [], declinedPlatformTerms: [], declinedProviderTerms: []), paymentMethods: nil, memberships: nil), requirementInterdependence: [:])))
            .preferredColorScheme(.dark)
    }
}
