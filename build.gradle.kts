buildscript {

    repositories {
        // needed by android edition
        google()
    }

    val androidBuildToolVersion: String by project

    dependencies {
        // needed by android edition
        classpath ("com.android.tools.build:gradle:$androidBuildToolVersion")
    }

}
