rootProject.name = "pronouns"

include("pronouns-core", "pronouns-common", "pronouns-fabric", "pronouns-paper")

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
        listOf("api", "text-minimessage").forEach {
            library("adventure.$it", "net.kyori", "adventure-$it").versionRef("adventure")
        }
        listOf("core", "fabric", "paper").forEach {
            library("cloud.$it", "cloud.commandframework", "cloud-$it").versionRef("cloud")
        }
        library("hikari", "com.zaxxer:HikariCP:5.0.1")

        library("gson", "com.google.code.gson:gson:2.10")
    }
}
