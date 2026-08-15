import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
}

val configFile = rootProject.file("local.properties")
val configProps = Properties()

if (configFile.exists()) {
    configFile.inputStream().use {
        configProps.load(it)
    }
} else {
    throw GradleException(
        "local.properties not found: ${configFile.absolutePath}"
    )
}

android {
    namespace = "com.ditdah.core.config"
    compileSdk = 37

    defaultConfig {
        minSdk = 25
        buildConfigField(
            "String", "API_URL", "\"${configProps.getProperty("api.url")}\""
        )
    }

    buildFeatures{
        buildConfig = true
    }

}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}