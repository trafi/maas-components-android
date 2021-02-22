/**
 - Tag: ApiError
 */
public enum ApiError: Swift.Error, Equatable, Hashable {
    /**
     The server returned 401 Unauthorized.
     The Authorization header Bearer token retrieved using [ApiConfig.getIdToken](x-source-tag://ApiConfig.getIdToken) may be invalid or expired.
     - Parameter error: optional error message.
     */
    case unauthorized(error: CoreBinary.Error?)
    /**
     The server returned 403 Forbidden.
     The x-api-key header retrieved using [ApiConfig.apiKey](x-source-tag://ApiConfig.apiKey) may be invalid or not subscribed to the API.
     - Parameter error: optional error message.
     */
    case forbidden(error: CoreBinary.Error?)
    /**
     The server returned an error not covered by the more specific [ApiError](x-source-tag://ApiError) types..
     - Parameter error: optional error message.
     */
    case error(error: CoreBinary.Error?)
    /**
     A generic failure not covered by the more specific [ApiError](x-source-tag://ApiError) types.
     - Parameter developerMessage: optional message to help identify the error.
     */
    case failure(developerMessage: String?)

    public var developerMessage: String? {
        switch self {
        case .unauthorized(let error): return error?.developerMessage
        case .forbidden(error: let error): return error?.developerMessage
        case .error(let error): return error?.developerMessage
        case .failure(let error): return error
        }
    }
}
