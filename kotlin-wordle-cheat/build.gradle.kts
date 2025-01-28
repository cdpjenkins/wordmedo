plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")

    // Kotest Assertions
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
