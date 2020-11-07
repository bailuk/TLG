# About
*TLG - Tetris Like Game* is a minimalistic tetris-like Game written in Java. It supports multible GUI-platforms.
    
# Screenshot
![TLG running on Android](screenshot.png)  
TLG running on Android.

# Build and run
## All variants:
Open file `settings.gradle.kts` in text editor and comment/uncomment lines to enable specific variants.

## awt / swing / swt:
In terminal execute `./gradlew assemble` to build variant(s) and then execute `./gradlew run` to run variant(s).  
Note: when building the swt variant [SWT jars](https://plugins.gradle.org/plugin/com.diffplug.gradle.swt.nativedeps) will be downloaded from the gradle maven repository.

## android:
1. Install adb and android SDK. Or install [Android Studio](https://developer.android.com/studio/).
2. Set `ANDROID_SDK_ROOT` environment variable. For example: `export ANDROID_SDK_ROOT=$HOME/Android/Sdk/`
3. In terminal execute `./gradlew assemble` to download artifacts and build apks.
4. In terminal execute `adb install ./tlg_android/build/outputs/apk/debug/tlg_android-debug.apk` to install apk on connected android device.

## gnome:
1. Install [gnome java](http://java-gnome.sourceforge.net/)
2. Adjust dependencie for `gtk.jar` in `tlg_gnome/build.gradle.kts`
3. Execute `./gradlew assemble` to build and `./gradlew run` to run.  
Note: timer is not implemented (this task is for you).

## angular:
[Angular](https://angular.io/)/[TypeScript](https://www.typescriptlang.org/) port. [Play here!](https://bailu.ch/tlg/). 
1. Install `npm` and `node.js`: [nodejs.org](https://nodejs.org)
2. Install `angular/cli` globaly by running `npm install -g @angular/cli`
3. Change to directory `tlg_angular`.
4. Run `npm install` to download and install dependencies.
5. Run `ng serve` to start development server. Open [URL](http://localhost:4200/) in browser to run.  
See `tlg_angular/README.md` for more commands.

# Copyright
(c) 2016-2020 [Lukas Bai](mailto:bailu@bailu.ch)  
[CC BY 4.0](http://creativecommons.org/licenses/by/4.0/)

