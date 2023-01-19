plugins {
    `java-library`
    id("pronouns.conventions")
    id("pronouns.unitTest")
}

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
}

dependencies {
    api(project(":pronouns-core"))
    compileOnlyApi(libs.cloud.core)
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.adventure.text.minimessage)
    compileOnly(libs.gson)
    compileOnly(libs.hikari)
}
