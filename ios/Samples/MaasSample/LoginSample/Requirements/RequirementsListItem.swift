import SwiftUI
import MaasComponents

struct RequirementListItem: View {

    @Environment(\.colorPrimary) var primaryColor
    @Environment(\.textStyleTextM) var textStyle

    @Binding var checked: Bool
    var title: String
    var link: (String, () -> ())

    var body: some View {
        Cell(
            content: content,
            suffix: suffix
        )
    }

    private func content() -> some View {
        [
            Text(title),
            Text(link.0)
                .foregroundColor(primaryColor)
                .underline()
        ]
        .joined(with: .space())
        .textStyle(textStyle)
        .onTapGesture { link.1() }
    }

    private func suffix() -> some View { CheckboxView(checked: _checked) }
}

struct CheckboxView: View {

    @Binding var checked: Bool

    var body: some View {
        Image(uiImage: UIImage(named: checked ? "checked" : "unchecked")!)
            .frame(width: 24, height: 24)
            .onTapGesture { checked.toggle() }
    }
}
