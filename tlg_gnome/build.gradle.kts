

plugins {
    java
    application
}

dependencies {

   /**
     *    local dependency for gtk bindings
     *    Debian package: "libjava-gnome-java"
     *    Java-gnome language bindings project
     *    https://en.wikipedia.org/wiki/Java-gnome
     *    http://java-gnome.sourceforge.net/
     *    https://github.com/istathar/java-gnome
     *
     */
    implementation(files("/usr/share/java/gtk.jar"))
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
