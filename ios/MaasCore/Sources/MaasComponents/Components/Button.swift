import struct SwiftUI.State

public struct Button: View, Swappable {
    
    public struct InputType {
        public let text: String
        public let icon: Image?
        public let foregroundColor: Color?
        public let backgroundColor: Color?
        public let isSmall: Bool
        public let isHugging: Bool
        public let isLoading: Bool
        public let action: () -> Void
    }
    
    public let input: InputType
    
    public init(
        _ text: String,
        icon: Image? = nil,
        foregroundColor: Color? = nil,
        backgroundColor: Color? = nil,
        isSmall: Bool = false,
        isHugging: Bool = false,
        isLoading: Bool = false,
        action: @escaping () -> Void) {
        
        input = InputType(
            text: text,
            icon: icon,
            foregroundColor: foregroundColor,
            backgroundColor: backgroundColor,
            isSmall: isSmall,
            isHugging: isHugging,
            isLoading: isLoading,
            action: action
        )
    }
    
    @Environment(\.isEnabled) var isEnabled
    @Themeable(ButtonConstants.init) var buttonConstants
    
    private var animation = Animation.linear(duration: 0.2)
        
    public var defaultBody: some View {
        SwiftUI.Button(action: input.action) {
            HStack(spacing: buttonConstants.spaceBetween) {
                
                if input.isLoading || input.icon != nil {
                    ZStack {
                        
                        if let icon = input.icon {
                            image(icon)
                        }
                        
                        spinner
                    }
                }
                
                if !input.text.isEmpty {
                    text
                }
            }
            .padding(.horizontal, buttonConstants.paddingHorizontal)
            .frame(
                maxWidth: input.isHugging ? nil : .infinity,
                minHeight: input.isSmall ? buttonConstants.heightSmall : buttonConstants.height
            )
            .animation(animation)
        }
        .foregroundColor(foregroundColor)
        .background(backgroundColor)
        .cornerRadius(buttonConstants.cornerRadius)
    }
    
    private var text: some View {
        Text(input.text)
            .lineLimit(1)
            .minimumScaleFactor(0.75)
            .textStyle(input.isSmall ? buttonConstants.textStyleSmall : buttonConstants.textStyle)
            .transition(.opacity)
    }
    
    private var spinner: some View {
        Spinner(color: foregroundColor, isAnimating: input.isLoading)
            .padding(input.isSmall ? 2 : 3)
            .frame(width: iconSize, height: iconSize)
    }
    
    private func image(_ icon: Image) -> some View {
        icon.resizable()
            .frame(width: iconSize, height: iconSize)
            .opacity(input.isLoading ? 0 : 1)
    }
    
    private var iconSize: CGFloat {
        input.isSmall
            ? buttonConstants.iconSizeSmall
            : buttonConstants.iconSize
    }
    
    private var foregroundColor: Color {
        input.foregroundColor ?? (isEnabled
                                    ? buttonConstants.defaultContentColor
                                    : buttonConstants.disabledContentColor)
    }
    
    private var backgroundColor: Color {
        input.backgroundColor ?? (isEnabled
                                    ? buttonConstants.defaultBackgroundColor
                                    : buttonConstants.disabledBackgroundColor)
    }
}

#if DEBUG
public struct Button_Previews: PreviewProvider, Snapped {
    
    public static var snapped: [String: AnyView] {
        [
            "Button": AnyView(
                Button("Some title", action: {})
            ),
            
            "Button Disabled": AnyView(
                Button("Some title", action: {})
                    .disabled(true)
            ),
            
            "Button Hugging": AnyView(
                Button("Some title", icon: Image(systemName: "command"), isHugging: true, action: {})
            ),
            
            "Button Hugging Small": AnyView(
                Button("Small title", isSmall: true, isHugging: true, action: {})
            ),
            
            "Button Small": AnyView(
                Button("Some title", isSmall: true, action: {})
            ),
            
            "Button Very Long title": AnyView(
                Button("Some very very very very very very very long title", action: {})
            ),
            
            "Button icon": AnyView(
                Button("Some title", icon: Image(systemName: "command"), isLoading: false, action: {})
            ),
            
            "Button icon loading": AnyView(
                Button("Some title", icon: Image(systemName: "command"), isLoading: true, action: {})
            ),
            
            "Colors": AnyView(
                Button("Some title", foregroundColor: Color(.label), backgroundColor: Color(.systemFill), action: {})
            ),
            
            "Themed": AnyView(
                Button("Some title", action: {})
                    .environment(\.uiColorPrimary, .systemBlue)
                    .environment(\.uiColorOnPrimary, .systemYellow)
                    .environment(\.cornerRadiusButton, 20)
                    .environment(\.textStyleTextL, TextStyle(fontWeight: .normal, fontStyle: .normal, fontSize: 25, lineSpacing: 0, color: nil))
                    .environment(\.textFont, { Font(UIFont(name: "Papyrus", size: $0)!) })
            ),
        ]
    }
    
    public static var detailed: Bool { true }
    public static var elementWidth: CGFloat? { 320 }
}

struct ExampleButton: View {
    
    @State private var isLoadingButton = false
    @State var isIcon = false
    
    var title: String {
        isIcon
            ? isLoadingButton ? "Iconning..." : "ICON"
            : isLoadingButton ? "" : "Button"
    }
    
    var icon: Image? {
        isIcon
            ? Image(systemName: "command")
            : nil
    }
    
    var body: some View {
        VStack(spacing: 20) {
            HStack {
                SwiftUI.Button("start") { isLoadingButton = true }
                SwiftUI.Button("stop") { isLoadingButton = false }
                SwiftUI.Button("icon") { isIcon.toggle() }
            }
            
            Button(
                title,
                icon: icon,
                isHugging: false,
                isLoading: isLoadingButton,
                action: tappedButton
            ).padding()
            
            Spinner(color: .black, lineWidth: 20, isAnimating: isLoadingButton)
                .frame(width: 100, height: 100, alignment: .center)
        }
    }
    
    
    func tappedButton() {
        isLoadingButton = true
        Timer.scheduledTimer(withTimeInterval: 2, repeats: false) { _ in
            isLoadingButton = false
        }
    }
}

struct ExampleButton_Preview: PreviewProvider {
    
    static var previews: some View {
        ExampleButton()
    }
}
#endif
