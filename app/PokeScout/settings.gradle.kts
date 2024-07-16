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
include(":common:themelibrary")
include(":common:nfc")
include(":common:pokemonlibrary")
include(":common:result")
