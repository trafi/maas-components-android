import SwiftUI
import MaasCore
import MaasComponents

struct ProfileView: View {

    @EnvironmentObject var appState: AppState

    var body: some View {
        profilePropertiesList()
            .overlay(buttonsStack, alignment: .bottom)
            .navigationTitle("Profile")
    }

    private var buttonsStack: some View {
        VStack(spacing: 16) {
            Button(
                "Save",
                isLoading: appState.isLoading,
                action: appState.updateProfile
            )
            Button("Corrupt Token 🧨", action: appState.corruptToken)
            Button("Logout", action: appState.logout)
        }
        .padding()
    }
}

private extension ProfileView {

    func profilePropertiesList() -> some View {
        List {
            Section(header: Text("Profile")) {
                row(title: "First Name", text: $appState.firstName)
                row(title: "Last Name", text: $appState.lastName)
            }
            Section {
                SwiftUI.Button(action: appState.requirements) {
                    Text("Check Provider Requirements")
                }
            }
        }
        .listStyle(InsetGroupedListStyle())
    }

    func row(title: String, text: Binding<String>) -> some View {
        HStack {
            Text(title)
            TextField(title, text: text)
        }
        .multilineTextAlignment(.trailing)
    }
}
