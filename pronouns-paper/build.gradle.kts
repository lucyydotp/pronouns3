plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("pronouns.conventions")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}


dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    implementation(project(":pronouns-common"))
    implementation(libs.cloud.paper)
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        relocate("cloud.commandframework", "net.lucypoulton.pronouns.shadow.cloud")
        relocate("io.leangen.geantyref", "net.lucypoulton.pronouns.shadow.geantyref")
        minimize()
    }

    build {
        dependsOn(shadowJar)
    }
}
