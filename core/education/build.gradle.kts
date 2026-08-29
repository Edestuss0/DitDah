plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ditdah.core.education"
    compileSdk = 37
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.ktor.client.mock)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotation)
    implementation(libs.ktor.client.serialization)

    implementation(project(":core:di"))
    implementation(project(":core:settings"))
    implementation(project(":core:config"))
    implementation(project(":core:exception"))
    implementation(project(":core:morse"))
}