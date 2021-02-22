extension CoreBinary.Error {
    
    public static func just(devMessage: String) -> Self {
        .init(
            uiError: nil,
            developerMessage: devMessage,
            errorCode: nil,
            providerId: nil,
            ext: nil)
    }
}
