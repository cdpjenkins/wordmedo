plugins {
    kotlin("jvm") version "2.0.20"
    id("com.diffplug.spotless") version "7.0.2"
    application
}

spotless {
    kotlin {
        ktfmt()
        ktlint()
    }
}

application {
    mainClass.set("com.cdpjenkins.wordlecheater.MainKt")
}

tasks.register("wordMeDo", JavaExec::class.java) {
    description = "my task does something awesome"
    classpath = sourceSets["main"].runtimeClasspath

    mainClass.set("com.cdpjenkins.wordlecheater.WordMeDoKt")

    standardInput = System.`in`

}

tasks.register("cheater", JavaExec::class.java) {
    description = "my task does something awesome"
    classpath = sourceSets["main"].runtimeClasspath

    mainClass.set("com.cdpjenkins.wordlecheater.MainKt")
}

group = "com.cdpjenkins"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testImplementation("io.kotest:kotest-assertions-core:5.7.2")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
