plugins {
    `java-library`
    id("pronouns.conventions")
}

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
}

dependencies {
    api(project(":pronouns-api"))
    compileOnlyApi(libs.cloud.core)
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.adventure.text.minimessage)
    compileOnly(libs.gson)
    compileOnly(libs.hikari)
}
