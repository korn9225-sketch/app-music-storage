#!/usr/bin/env bash
echo "========================================================"
echo "  Setting up Android Development Workspace for On-The-Go"
echo "========================================================"
echo ""

TARGET_DIR="$HOME/AndroidStudioProjects"
mkdir -p "$TARGET_DIR"
cd "$TARGET_DIR" || exit

echo "Cloning repositories from GitHub into $TARGET_DIR..."
echo ""

echo "[1/4] Cloning Music Media Storage..."
git clone https://github.com/korn9225-sketch/app-music-storage.git

echo "[2/4] Cloning Music Player App..."
git clone https://github.com/korn9225-sketch/Helloworldandmyfirstapp.git

echo "[3/4] Cloning Video Downloader App..."
git clone https://github.com/korn9225-sketch/VideoDownloaderApp.git

echo "[4/4] Cloning CRT Animation Mod..."
git clone https://github.com/korn9225-sketch/CRTAnimationMod.git

echo ""
echo "========================================================"
echo "  SUCCESS! All projects cloned into $TARGET_DIR."
echo "  Open Android Studio and click 'Open' on any folder!"
echo "========================================================"
