./scripts/devices/ios/run_ios_simulator.sh
patrol devices
flutter devices
patrol drive --target "integration_test/webview_test.dart" --target "integration_test/google_map_test.dart" --device "NewApp_TA_iPhone_14_Pro_Max_iOS_16_1"
./scripts/devices/ios/delete_ios_simulator.sh