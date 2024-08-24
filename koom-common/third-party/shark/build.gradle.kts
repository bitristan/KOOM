plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.square.shark"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Okio is a required dependency, but we're making it required on the "shark" artifact which is the main artifact that
    // should generally be used. The shark artifact depends on Okio 2.x (ensure compatibility with modern Okio). Depending on 1.x here
    // enables us to ensure binary compatibility with Okio 1.x and allow us to use the deprecated (error level) Okio APIs to keep that
    // compatibility.
    // See https://github.com/square/leakcanary/issues/1624
    implementation("com.squareup.okio:okio:1.14.0")
}
