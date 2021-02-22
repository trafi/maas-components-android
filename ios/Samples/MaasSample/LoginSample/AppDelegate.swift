import Foundation
import UIKit

import FBSDKCoreKit
import Firebase
import MaasCore

// @UIApplicationMain
class AppDelegate: NSObject, UIApplicationDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {

        // Setup facebook
        ApplicationDelegate.shared.application(
            application,
            didFinishLaunchingWithOptions: launchOptions
        )

        // Setup Firebase
        FirebaseApp.configure()

        // setup SDK
        Maas.configure(api: MaasConfiguration.shared)
        
        return true
    }
}
