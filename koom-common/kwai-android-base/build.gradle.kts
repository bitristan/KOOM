plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.kwai.android.base"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
                cppFlags.addAll(listOf("-std=c++17", "-fno-exceptions", "-fno-rtti"))
            }
        }
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
            externalNativeBuild {
                cmake {
                    cppFlags.addAll(listOf("-Wl,--gc-sections", "-fvisibility=hidden", "-flto"))
                }
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}