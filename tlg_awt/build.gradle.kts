plugins {
    application
    // https://imperceptiblethoughts.com/shadow/getting-started
    id("com.github.johnrengelman.shadow") version "7.1.2"

    // https://kotlinlang.org/docs/gradle.html#targeting-the-jvm
    kotlin("jvm")
}

dependencies {
    implementation(project(":tlg"))
}

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

val appMainClass = "AppKt"

application {
    mainClass.set(appMainClass)
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to appMainClass))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
