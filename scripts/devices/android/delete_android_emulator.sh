adb -e emu kill
avdmanager delete avd --name "NewApp_TA_Pixel_4XL_Android_33"
emulator -list-avds
rm -rf ~/.android/avd
