plugins {
    id("org.jlleitschuh.gradle.ktlint") version Versions.ktlintGradlePlugin apply false
}

allprojects {
    group = "com.trafi.maas"
    version = "0.1.0-dev07-SNAPSHOT"

    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        if (project.hasProperty("android")) {
            apply(plugin = "org.jlleitschuh.gradle.ktlint")
            extensions.getByType<org.jlleitschuh.gradle.ktlint.KtlintExtension>().apply {
                version.set(Versions.ktlint)
                android.set(true)
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
