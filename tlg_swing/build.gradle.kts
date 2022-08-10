plugins {
    application
    // https://imperceptiblethoughts.com/shadow/getting-started
    id("com.github.johnrengelman.shadow") version "7.1.2"

    // https://kotlinlang.org/docs/gradle.html#targeting-the-jvm
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":tlg"))
    implementation(project(":tlg_awt"))
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

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
