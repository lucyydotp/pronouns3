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
    compileOnlyApi(libs.slf4j)
    compileOnly(libs.gson)
    compileOnly("org.yaml:snakeyaml:1.33")
    compileOnly(libs.hikari)

    testImplementation("org.yaml:snakeyaml:1.33")

}
