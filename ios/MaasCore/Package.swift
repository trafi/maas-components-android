// swift-tools-version:5.3
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "MaasCore",
    platforms: [
        .iOS(.v13),
    ],
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "MaasCore",
            targets: ["MaasCore", "MaasComponents", "MaasTheme"]),
        .library(
            name: "CoreBinary",
            targets: ["CoreBinary"]),
        .library(
            name: "MaasTheme",
            targets: ["MaasTheme"]),
        .library(
            name: "MaasComponents",
            targets: ["MaasComponents"]),
    ],
    dependencies: [
        .package(
            name: "Swappable",
            url: "https://github.com/trafi/swappable.git", .upToNextMinor(from: "0.0.1")),
        .package(
            name: "swift-composable-architecture",
            url: "https://github.com/pointfreeco/swift-composable-architecture", from: "0.10.0"),
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
         .binaryTarget(
             name: "CoreBinary",
             path: "Sources/CoreBinary/CoreBinary.xcframework"),
        .target(
            name: "MaasCore",
            dependencies: [
              "CoreBinary",
              .product(name: "ComposableArchitecture", package: "swift-composable-architecture"),
            ],
            path: "Sources/MaasCore"),
        .target(
            name: "MaasTheme",
            dependencies: ["MaasCore"],
            path: "Sources/MaasTheme"),
        .target(
            name: "MaasComponents",
            dependencies: ["MaasTheme", "Swappable"],
            path: "Sources/MaasComponents"),
    ]
)
