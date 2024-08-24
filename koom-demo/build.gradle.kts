plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.kwai.koom.demo"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
                arguments.addAll(listOf("-DANDROID_TOOLCHAIN=clang", "-DANDROID_STL=c++_shared", "-DCMAKE_VERBOSE_MAKEFILE=ON"))
                cppFlags.addAll(listOf("-Wl,--gc-sections", "-fno-exceptions", "-fno-rtti", "-fvisibility=hidden", "-flto"))
                cFlags.addAll(listOf("-Wl,--gc-sections", "-fvisibility=hidden"))
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/jni/CMakeLists.txt")
            version = "3.18.1"
        }
    }

    flavorDimensions.add("stl_mode")
    productFlavors {
        create("StaticCpp") {
            dimension = "stl_mode"
        }
        create("SharedCpp") {
            dimension = "stl_mode"
        }
    }

    packaging {
        jniLibs {
            pickFirsts.add("lib/*/libc++_shared.so")
        }
    }
}

dependencies {
//    implementation "com.kuaishou.koom:koom-native-leak:${VERSION_NAME}"
//    implementation "com.kuaishou.koom:koom-monitor-base:${VERSION_NAME}"
//    implementation "com.kuaishou.koom:koom-java-leak:${VERSION_NAME}"
//    implementation "com.kuaishou.koom:koom-thread-leak:${VERSION_NAME}"
    implementation(project(":koom-monitor-base"))
    implementation(project(":koom-native-leak"))
    implementation(project(":koom-java-leak"))
    implementation(project(":koom-thread-leak"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}