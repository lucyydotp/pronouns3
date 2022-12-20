plugins {
    id("fabric-loom") version "1.0-SNAPSHOT"
}

dependencies {
    minecraft("com.mojang:minecraft:1.19.3")
    mappings("net.fabricmc:yarn:1.19.3+build.3:v2")
    modImplementation("net.fabricmc:fabric-loader:0.14.11")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.69.1+1.19.3")
    implementation(project(":pronouns-api"))
}

