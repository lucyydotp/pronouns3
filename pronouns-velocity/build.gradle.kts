plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.kyori.blossom") version "1.3.1"
    id("pronouns.conventions")
    id("pronouns.publishable")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.0")
    annotationProcessor("com.velocitypowered:velocity-annotation-processor:4.0.0-SNAPSHOT")

    shadow(project(":pronouns-common"))
    shadow(libs.cloud.velocity)
    shadow(libs.hikari) {
        exclude("org.slf4j")
    }
}

blossom {
    val file = "src/main/java/net/lucypoulton/pronouns/velocity/ProNounsVelocity.java"
    replaceToken("\${version}", version, file)
}

tasks {
    shadowJar {
        configurations = listOf(project.configurations.shadow.get())
        archiveClassifier.set("")
        relocate("cloud.commandframework", "net.lucypoulton.pronouns.shadow.cloud")
        relocate("io.leangen.geantyref", "net.lucypoulton.pronouns.shadow.geantyref")
        relocate("com.zaxxer.hikari", "net.lucypoulton.pronouns.shadow.hikari")

        minimize {
            exclude(project(":pronouns-core"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
