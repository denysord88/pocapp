./scripts/devices/ios/run_ios_simulator.sh
patrol devices
flutter devices
patrol drive --target "integration_test/performance_test.dart" --device "NewApp_TA_iPhone_14_Pro_Max_iOS_16_1" --repeat 100
./scripts/devices/ios/delete_ios_simulator.sh