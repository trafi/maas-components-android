import SwiftUI
import MaasComponents

public struct RequirementListItem: View, Swappable {

    // TODO: Constants
    @Environment(\.colorPrimary) var primaryColor
    @Environment(\.textStyleTextM) var textStyle

    public struct InputType {
        public let checked: Binding<Bool>
        public let title: String

        public struct Hyperlink {
            public let title: String
            public let action: () -> ()
        }
        public let hyperlink: Hyperlink
    }
    public var input: InputType

    // MARK: - Constructors

    public init(input: InputType) {
        self.input = input
    }

    public init(
        checked: Binding<Bool>,
        title: String,
        hyperlink: InputType.Hyperlink
    ) {
        self.init(input: .init(checked: checked, title: title, hyperlink: hyperlink))
    }

    // MARK: - Content

    public var defaultBody: some View {
        Cell(
            content: content,
            suffix: suffix
        )
    }

    private func content() -> some View {
        [
            Text(input.title),
            Text(input.hyperlink.title)
                .foregroundColor(primaryColor)
                .underline()
        ]
        .joined(with: .space())
        .textStyle(textStyle)
        .onTapGesture { input.hyperlink.action() }
    }

    private func suffix() -> some View { Checkbox(isOn: input.checked) }
}

#if DEBUG

struct RequirementListItem_Previews: PreviewProvider {

    static var previews: some View {
        RequirementListItem(
            checked: .constant(true),
            title: "I agree to all App mobility service provider’s Terms of service",
            hyperlink: .init(
                title: "Terms of service",
                action: { /* empty */ }
            )
        )
    }
}

#endif