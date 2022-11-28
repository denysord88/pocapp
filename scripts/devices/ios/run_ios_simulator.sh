xcrun simctl list runtimes
xcrun simctl list devices
xcrun simctl create NewApp_TA_iPhone_14_Pro_Max_iOS_16_1 "iPhone 14 Pro Max" com.apple.CoreSimulator.SimRuntime.iOS-16-1
xcrun simctl boot NewApp_TA_iPhone_14_Pro_Max_iOS_16_1
xcrun simctl list devices