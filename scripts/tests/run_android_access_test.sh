./scripts/devices/android/run_android_emulator.sh
patrol devices
flutter devices
patrol drive --target "integration_test/access_test.dart" --device "emulator-5554"
./scripts/devices/android/delete_android_emulator.sh