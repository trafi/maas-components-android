import SwiftUI

enum MenuCase: String, CaseIterable {
    case profile = "My Profile"
    case ondemand = "Micromoblity"
}

struct MenuView: View {

    @EnvironmentObject var appState: AppState

    var body: some View {
        List {
            Section {
                ForEach(MenuCase.allCases, id: \.self) {
                    NavigationLink($0.rawValue, destination: $0.destination)
                }
            }
        }
        .navigationTitle("Menu")
        .navigationBarItems(
            trailing: SwiftUI.Button(action: appState.logout) { Image(systemName: "power") }
        )
        .listStyle(InsetGroupedListStyle())
    }
}

private extension MenuCase {

    var destination: AnyView {
        switch self {
        case .profile:
            return ProfileView()
                .erased
        case .ondemand:
            return Text("Ondemand")
                .erased
        }
    }
}
