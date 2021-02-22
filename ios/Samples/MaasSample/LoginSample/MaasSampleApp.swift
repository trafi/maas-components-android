//
//  MaasSampleApp.swift
//  MaasSample
//
//  Created by Ignas Jasiunas on 2021-01-28.
//

import SwiftUI
import MaasComponents

struct LaunchView: View {

    @ObservedObject var appState: AppState = .init()

    var body: some View {
        Group {
            if appState.user == nil {
                LoginView()
            } else {
                NavigationView { MenuView() }
            }
        }
        .alert(item: $appState.error) {
            Alert(title: Text($0.message ?? ""))
        }
        .animation(.default)
        .environmentObject(appState)
        .sheet(item: $appState.providersRequirementStatus) {
            RequirementsView(requiremenetsState: .init(requirementStatus: $0))
        }
    }
}

@main
struct MaasSampleApp: App {

    // Helps to call app delegate. At the moment our base config setup goes there
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    let persistenceController = PersistenceController.shared

    var body: some Scene {
        WindowGroup {
            LaunchView()
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
