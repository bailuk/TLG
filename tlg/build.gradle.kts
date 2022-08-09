plugins {
    id ("java-library")

    // https://kotlinlang.org/docs/gradle.html#targeting-the-jvm
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}
