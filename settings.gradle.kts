rootProject.name = "TokoOnline"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")
include(":products")
include(":apis:product")
include(":features:home")
include(":libraries:core")
include(":features:productdetail")
include(":libraries:component")
include(":features:productlist")
include(":features:favorite")
include(":apis:authentication")
include(":features:cart")
include(":features:login")
