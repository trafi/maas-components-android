import MaasCore
import Combine

class MaasConfiguration: ApiConfig {

    private init() {}

    static let shared = MaasConfiguration()
    static var config: CoreBinary.ApiConfiguration { shared.config }

    @UserDefault(key: "accessToken", defaultValue: nil)
    static var accessToken: String?

    @UserDefault(key: "temporaryAccessToken", defaultValue: nil)
    static var temporaryAccessToken: String?

    var baseUrl: String { "https://api-trafi.bedrock.dev.trafi.com/" }
    var apiKey: String { "OwJwMJr8x11WfgqJlTmZJ3cpXHBPIdob3TNvTgmk" }

    var logger: Logger? { ExampleLogger.shared }

    func getIdToken() -> String? { MaasConfiguration.accessToken }

    func refreshIdToken(completion: @escaping (String?) -> ()) {
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            MaasConfiguration.accessToken = MaasConfiguration.temporaryAccessToken
            completion(MaasConfiguration.accessToken)
        }
    }
}

class ExampleLogger: Logger {
    
    private init() {}
    static let shared = ExampleLogger()
    
    func log(message: String) {
        print("SDK example log: \(message)")
    }
}

extension UsersApi {
    static var shared = UsersApi(config: MaasConfiguration.config)
}
