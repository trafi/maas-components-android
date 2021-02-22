import Combine
import SwiftUI
import MaasCore

import FirebaseAuth
import FacebookLogin

// MARK: - Types

enum AuthenticationProvider: String, CaseIterable {
    // case google = "Google"
    // case apple = "Apple"
    case facebook = "Facebook"
}

extension AuthenticationProvider {

    var title: String { "Continue with \(self.rawValue)" }

    var icon: Image? {
        UIImage(named: rawValue.lowercased()).flatMap { Image(uiImage: $0) }
    }

    var primaryColor: UIColor { UIColor(named: rawValue.lowercased() + "-color") ?? .black }
}

enum AuthenticationError: Swift.Error, Identifiable {

    internal var id: UUID { UUID() }

    case cancelled
    case declined
    case apiError(ApiError?)
    case error(String)
    case unknown

    var message: String? {
        switch self {
        case .cancelled: return "Authentication has been canceled"
        case .declined: return "Authentication has been declined"
        case let .apiError(error): return error?.developerMessage
        default: return "Received unknown error"
        }
    }
}

// MARK: - Facebook

struct FacebookAuthentication: Equatable {

    var authenticate: AnyPublisher<AuthCredential, AuthenticationError> {
        Future { promise in
            LoginManager().logIn(
                permissions: [.email],
                viewController: UIApplication.shared.windows.first?.rootViewController
            ) {
                switch $0 {
                case .cancelled:
                    promise(.failure(.cancelled))
                case let .failed(error):
                    promise(.failure(.error(error.localizedDescription)))
                case let .success(_, _, token):
                    guard let accessToken = token else {
                        promise(.failure(.declined))
                        return
                    }
                    promise(.success(FacebookAuthProvider.credential(withAccessToken: accessToken.tokenString)))
                }
            }
        }
        .eraseToAnyPublisher()
    }
}

// MARK: - Firebase

struct FirebaseAuthentication {

    var authCredential: AuthCredential

    func signIn() -> AnyPublisher<String, AuthenticationError> {
        Future { promise in
            Auth.auth().signIn(with: authCredential) { (result, error) in
                if let error = error {
                    promise(.failure(.error(error.localizedDescription)))
                } else if let result = result {
                    self.getIDToken(result, promise: promise)
                } else {
                    promise(.failure(.unknown))
                }
            }
        }
        .eraseToAnyPublisher()
    }

    private func getIDToken(_ result: AuthDataResult, promise: @escaping (Result<String, AuthenticationError>) -> ()) {
        result.user.getIDToken { token, error in
            if let error = error {
                promise(.failure(.error(error.localizedDescription)))
            } else if let token = token {
                promise(.success(token))
            } else {
                promise(.failure(.unknown))
            }
        }
    }
}
