

plugins {
    java
    application
}

dependencies {
    compile(files("/usr/share/java/gtk.jar"))
    compile(project(":tlg"))
}

application {
    mainClass.set("ch.bailu.tlg_gnome.Main")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ch.bailu.tlg_gnome.Main"
    }
}
