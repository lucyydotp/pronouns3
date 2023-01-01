plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("pronouns.conventions")
    id("pronouns.publishable")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")
}

val minecraftVersion = "1.19.3"

dependencies {
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")
    shadow(project(":pronouns-common"))
    shadow(libs.cloud.paper)
    compileOnly("me.clip:placeholderapi:2.11.2")
}

tasks {
    shadowJar {
        configurations = listOf(project.configurations.shadow.get())
        archiveClassifier.set("")
        relocate("cloud.commandframework", "net.lucypoulton.pronouns.shadow.cloud")
        relocate("io.leangen.geantyref", "net.lucypoulton.pronouns.shadow.geantyref")
    }

    build {
        dependsOn(shadowJar)
    }
    processResources {
        expand("version" to project.version)
    }
}

modrinth {
    gameVersions.add(minecraftVersion)
    loaders.add("paper")
    versionName.set("$versionName for Paper")
}
