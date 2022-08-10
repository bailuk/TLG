![Java CI with Gradle](https://github.com/bailuk/TLG/workflows/Java%20CI%20with%20Gradle/badge.svg)
# About
*TLG - Tetris Like Game* Kotlin and Typescript variant for multiple GUI-platforms.
    
# Screenshot
![TLG running on Android](screenshot.png)  
TLG running on Android.


# Dependencies
- Java 11
- GTK 4
- Android SDK
- Node.js and npm

# Build and run
```bash
# Build all Kotlin variants
./gradlew build

# Build GTK 4 variant
./gradlew tlg_gtk:build
./gradlew tlg_gtk:run

# Build Swing variant
./gradlew tlg_swing:build
./gradlew tlg_swing:run

# Build Android variant
./gradlew tlg_android:build

# Browser: install, build and run
cd js/browser
npm install
npm run build
npm run serve

# GTK (node-gtk): install, build and run
cd js/gtk
npm run build
npm run start
```

# Install distribution (Kotlin / GTK-4 variant)
- Install dependencies: `sudo apt install openjdk-11-jre libgtk-4-1`
- Download `tlg-gtk.zip` from [release](https://github.com/bailuk/TLG/releases) or [build pipeline](https://github.com/bailuk/TLG/actions)
- Extract archive: `unzip tlg-gtk.zip`
- Run install script as user: `sh install.sh`

# License
[CC BY 4.0](http://creativecommons.org/licenses/by/4.0/)
