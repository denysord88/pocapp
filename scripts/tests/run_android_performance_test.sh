#./scripts/devices/android/run_android_emulator.sh
patrol devices
flutter devices
patrol drive --target "integration_test/performance_test.dart" --device "emulator-5554" --repeat 100
./scripts/devices/android/delete_android_emulator.sh