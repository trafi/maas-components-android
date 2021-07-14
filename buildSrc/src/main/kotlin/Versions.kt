import java.io.FileNotFoundException
import java.util.Properties

object Versions {
    private val versions = loadProperties("build_src_versions.properties")

    val kotlin: String = versions.getProperty("version.kotlin")
    val androidGradlePlugin: String = versions.getProperty("version.androidGradlePlugin")

    const val androidBuildToolsVersion = "31.0.0"
    const val androidCompileSdk = 31
    const val androidTargetSdk = 30
    const val androidMinSdk = 23

    const val compose = "1.0.0-rc02"
    const val composeNavigation = "2.4.0-alpha04"
    const val composeConstraintLayout = "1.0.0-alpha08"

    const val ktor = "1.6.1"
    const val coroutines = "1.5.0-native-mt"
    const val kotlinxSerialization = "1.2.1"

    const val ktlintGradlePlugin = "10.1.0"
    const val ktlint = "0.40.0"
    val shot: String = versions.getProperty("version.shot")
}

private fun Any.loadProperties(propFileName: String): Properties {
    val props = Properties()
    val inputStream = javaClass.classLoader!!.getResourceAsStream(propFileName)
        ?: throw FileNotFoundException("property file '$propFileName' not found in the classpath")
    inputStream.use { props.load(it) }
    return props
}
