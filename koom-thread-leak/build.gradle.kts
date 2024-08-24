plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.kwai.performance.overhead.thread.monitor"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        externalNativeBuild {
            cmake {
                abiFilters.addAll(listOf("arm64-v8a"))
                cppFlags.addAll(listOf("-std=c++17", "-fexceptions", "-fno-rtti"))
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

    packagingOptions {
        exclude("lib/*/libc++_shared.so")
    }
}

dependencies {
    implementation(project(":koom-monitor-base"))
    implementation(project(":koom-common:kwai-android-base"))
    implementation(project(":koom-common:kwai-unwind"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

