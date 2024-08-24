plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.kwai.koom.fastdump"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        externalNativeBuild {
            cmake {
                abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
                cppFlags.addAll(listOf("-std=c++17", "-fno-exceptions", "-fno-rtti"))
            }
        }
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

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
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

    packagingOptions {
        exclude("lib/*/libc++_shared.so")
    }
}

dependencies {
    implementation(project(":koom-monitor-base"))
    implementation(project(":koom-common:kwai-android-base"))
}