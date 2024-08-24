pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":koom-demo")
include(":koom-java-leak")
include(":koom-native-leak")
include(":koom-thread-leak")
include(":koom-fast-dump")
include(":koom-monitor-base")
include(":koom-common:third-party:xhook")
include(":koom-common:third-party:shark")
include(":koom-common:kwai-unwind")
include(":koom-common:kwai-android-base")

rootProject.name = "KOOM"