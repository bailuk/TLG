

plugins {
    java
    application
}

dependencies {
    implementation(project(":tlg"))
}

application {
    mainClass.set("ch.bailu.tlg_awt.Main")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ch.bailu.tlg_awt.Main"
    }
}
