plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.kwai.koom.base"
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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add("stl_mode")
    productFlavors {
        create("StaticCpp") {
            dimension = "stl_mode"
            extra["artifactIdSuffix"] = "static"
            externalNativeBuild {
                cmake {
                    arguments.addAll(listOf("-DANDROID_STL=c++_static"))
                }
            }
        }
        create("SharedCpp") {
            dimension = "stl_mode"
            extra["artifactIdSuffix"] = ""
            externalNativeBuild {
                cmake {
                    arguments.addAll(listOf("-DANDROID_STL=c++_shared"))
                }
            }
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.material)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

