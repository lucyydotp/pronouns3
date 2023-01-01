plugins {
    id("fabric-loom") version "1.0-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("pronouns.conventions")
    id("pronouns.publishable")
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://maven.nucleoid.xyz/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.19.3")
    mappings("net.fabricmc:yarn:1.19.3+build.3:v2")
    modImplementation("net.fabricmc:fabric-loader:0.14.11")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.69.1+1.19.3")
    shadow(project(":pronouns-common"))
    modImplementation(include("net.kyori:adventure-platform-fabric:5.6.0")!!)
    // included in cloud fabric so no jij needed
    modImplementation("me.lucko:fabric-permissions-api:0.2-SNAPSHOT")
    modImplementation(libs.cloud.fabric)
    include(libs.cloud.fabric)
    modImplementation(include("eu.pb4:placeholder-api:2.0.0-pre.2+1.19.3")!!)
}

tasks {
    shadowJar {
        archiveClassifier.set("shadow")
        configurations = listOf(project.configurations.shadow.get())
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
    }

    processResources {
        expand("version" to project.version)
    }
}

modrinth {
    versionName.set("$version for Fabric")
    uploadFile.set(tasks.remapJar.get())
}
