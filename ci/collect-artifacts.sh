#!/bin/sh

mkdir -p dist/gtk || exit 1
cp tlg_gtk/build/libs/* dist/gtk || exit 1
cp tlg_gtk/util/install.sh dist/gtk || exit 1
cp tlg_gtk/src/main/resources/svg/app-icon.svg dist/gtk || exit 1

mkdir -p dist/android || exit 1
cp -r tlg_android/build/outputs/apk/* dist/android || exit 1

mkdir -p dist/swing || exit 1
cp tlg_swing/build/libs/* dist/swing || exit 1

mkdir -p dist/awt || exit 1
cp tlg_awt/build/libs/* dist/awt || exit 1
