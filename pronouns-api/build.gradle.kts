plugins {
    `java-library`
}

repositories {
    mavenCentral()
}
dependencies {
    compileOnly("org.jetbrains:annotations:20.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}