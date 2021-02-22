import SwiftUI
import Combine
import MaasComponents
import struct SwiftUI.State

// MARK: -

struct LoginView: View {

    @EnvironmentObject var appState: AppState

    var body: some View {
        NavigationView {
            VStack {
                Spacer()
                logoView()
                Spacer()
                loginProviderButtonsView()
            }
        }
        .onAppear {
            if MaasConfiguration.accessToken?.isEmpty == false {
                appState.getOrCreateUser()
            }
        }
    }
}


// MARK: - View

private extension LoginView {

    func logoView() -> some View {
        Image(uiImage: #imageLiteral(resourceName: "wave"))
            .resizable()
            .aspectRatio(contentMode: .fit)
            .padding(100)
    }

    func loginProviderButtonsView() -> some View {
        VStack(spacing: 16) {

            ForEach(AuthenticationProvider.allCases, id: \.self) { provider in
                Button(
                    provider.title,
                    icon: provider.icon,
                    isLoading: appState.isLoading,
                    action: { appState.authenticationProvider = provider }
                )
                .environment(\.uiColorPrimary, provider.primaryColor)
                .environment(\.isEnabled, !appState.isLoading)
            }

            Button(
                "Other ways to sign-up",
                action: {  }
            )
            .environment(\.uiColorPrimary, .systemGray2)
        }
        .padding(24)
    }
}
