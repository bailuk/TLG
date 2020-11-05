

plugins {
    java
    application

    // see: https://plugins.gradle.org/plugin/com.diffplug.gradle.swt.nativedeps
    id("com.diffplug.swt.nativedeps") version "3.25.0"
}

buildscript {
  repositories {
    maven {
      url = uri("https://plugins.gradle.org/m2/")
    }
  }
  dependencies {
    classpath("com.diffplug.gradle:goomph:3.25.0")
  }
}

apply(plugin = "com.diffplug.gradle.swt.nativedeps")



dependencies {
    compile(project(":tlg"))
}




application {
    mainClass.set("ch.bailu.tlg_swt.Main")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ch.bailu.tlg_swt.Main"
    }
}




