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
    compileOnlyApi(libs.adventure)
    implementation("com.google.code.gson:gson:2.10")
}
