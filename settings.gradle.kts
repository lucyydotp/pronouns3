rootProject.name = "pronouns"

include("pronouns-api", "pronouns-fabric")

pluginManagement {
    repositories {
        maven( "https://maven.fabricmc.net/")
        gradlePluginPortal()
    }
}
