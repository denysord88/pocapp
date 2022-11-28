# sdkmanager --list | grep android-33 | grep arm | grep playstore
# sdkmanager --install "system-images;android-33;google_apis_playstore;arm64-v8a"

rm -rf ~/.android/avd
sdkmanager --list_installed
avdmanager list device
avdmanager create avd --name "NewApp_TA_Pixel_4XL_Android_33" --device "pixel_4_xl" -k "system-images;android-33;google_apis_playstore;arm64-v8a"
nohup emulator -avd NewApp_TA_Pixel_4XL_Android_33 >/dev/null 2>&1 &
emulator -list-avds
