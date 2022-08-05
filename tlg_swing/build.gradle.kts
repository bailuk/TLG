

plugins {
    java
    application
}

dependencies {
    implementation(project(":tlg"))
    implementation(project(":tlg_awt"))
}

application {
    mainClass.set("ch.bailu.tlg_swing.Main")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ch.bailu.tlg_swing.Main"
    }
}
