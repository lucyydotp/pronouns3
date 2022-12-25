rootProject.name = "pronouns"

include("pronouns-api", "pronouns-common", "pronouns-fabric")

pluginManagement {
    repositories {
        maven( "https://maven.fabricmc.net/")
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://libraries.minecraft.net")
    }
    versionCatalogs.create("libs") {
        version("adventure", "4.12.0")
        version("cloud", "1.8.0")
        library("adventure", "net.kyori", "adventure-api").versionRef("adventure")
        listOf("core", "annotations", "fabric").forEach {
            library("cloud.$it", "cloud.commandframework", "cloud-$it").versionRef("cloud")
        }
    }
}
