pluginManagement {
    repositories {
        google()
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

rootProject.name = "PokeScout"
include(":pokescout:developer")
include(":pokescout:leader")
include(":pokescout:trainer")
include(":common:compose")
include(":common:nfc")
include(":common:pokemon")
include(":common:result")
include(":common:option")
