plugins {
    `java-library`
    id("pronouns.conventions")
    id("pronouns.unitTest")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:23.1.0")
}
