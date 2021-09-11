

plugins {
    java
    application
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://maven.pkg.github.com/bailuk/java-gtk")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }

}

dependencies {

    implementation("ch.bailu.java-gtk:library:0.1.0-SNAPSHOT")
    implementation(project(":tlg"))
}

application {
    mainClass.set("ch.bailu.tlg_gtk.Main")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ch.bailu.tlg_gnome.Main"
    }
}
