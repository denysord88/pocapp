# pocapp

A new Flutter project.

## Getting Started

This project is a starting point for a Flutter application.

A few resources to get you started if this is your first Flutter project:

- [Lab: Write your first Flutter app](https://docs.flutter.dev/get-started/codelab)
- [Cookbook: Useful Flutter samples](https://docs.flutter.dev/cookbook)

For help getting started with Flutter development, view the
[online documentation](https://docs.flutter.dev/), which offers tutorials,
samples, guidance on mobile development, and a full API reference.

## flutter create --org com.denysord1988 pocapp

## https://pub.dev/packages/permission_handler/install
# flutter pub add permission_handler
This will add a line like this to your package's pubspec.yaml (and run an implicit flutter pub get):
dependencies:
    permission_handler: ^10.2.0

Android:
Add the following to your "gradle.properties" file:
android.useAndroidX=true
android.enableJetifier=true

Make sure you set the compileSdkVersion in your "android/app/build.gradle" file to 33:
android {
    defaultConfig {
        minSdkVersion 20
        targetSdkVersion 33
        compileSdkVersion 33
...
}}

Add permissions in file android/app/src/main/AndroidManifest.xml under the section <manifest>

    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

iOS:
Add this in file ios/Runner/Info.plist in the <dict> section:

	<!-- Permission options for the `location` group -->
    <key>NSLocationWhenInUseUsageDescription</key>
    <string>Need location when in use</string>
    <key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
    <string>Always and when in use!</string>
    <key>NSLocationUsageDescription</key>
    <string>Older devices need location.</string>
    <key>NSLocationAlwaysUsageDescription</key>
    <string>Can I have location always?</string>

    <!-- Permission options for the `photos` group -->
    <key>NSPhotoLibraryUsageDescription</key>
    <string>photos</string>

Add this in file ios/Podfile before "end end" in the end of the file:
# Start of the permission_handler configuration
    target.build_configurations.each do |config|

      # You can enable the permissions needed here. For example to enable camera
      # permission, just remove the `#` character in front so it looks like this:
      #
      # ## dart: PermissionGroup.camera
      # 'PERMISSION_CAMERA=1'
      #
      #  Preprocessor definitions can be found in: https://github.com/Baseflow/flutter-permission-handler/blob/master/permission_handler_apple/ios/Classes/PermissionHandlerEnums.h
      config.build_settings['GCC_PREPROCESSOR_DEFINITIONS'] ||= [
        '$(inherited)',

        ## dart: PermissionGroup.calendar
        # 'PERMISSION_EVENTS=1',

        ## dart: PermissionGroup.reminders
        # 'PERMISSION_REMINDERS=1',

        ## dart: PermissionGroup.contacts
        # 'PERMISSION_CONTACTS=1',

        ## dart: PermissionGroup.camera
        # 'PERMISSION_CAMERA=1',

        ## dart: PermissionGroup.microphone
        # 'PERMISSION_MICROPHONE=1',

        ## dart: PermissionGroup.speech
        # 'PERMISSION_SPEECH_RECOGNIZER=1',

        ## dart: PermissionGroup.photos
        'PERMISSION_PHOTOS=1',

        ## dart: [PermissionGroup.location, PermissionGroup.locationAlways, PermissionGroup.locationWhenInUse]
        'PERMISSION_LOCATION=1',

        ## dart: PermissionGroup.notification
        # 'PERMISSION_NOTIFICATIONS=1',

        ## dart: PermissionGroup.mediaLibrary
        # 'PERMISSION_MEDIA_LIBRARY=1',

        ## dart: PermissionGroup.sensors
        # 'PERMISSION_SENSORS=1',   

        ## dart: PermissionGroup.bluetooth
        # 'PERMISSION_BLUETOOTH=1',

        ## dart: PermissionGroup.appTrackingTransparency
        # 'PERMISSION_APP_TRACKING_TRANSPARENCY=1',

        ## dart: PermissionGroup.criticalAlerts
        # 'PERMISSION_CRITICAL_ALERTS=1'
      ]

    end 
    # End of the permission_handler configuration

## https://pub.dev/packages/google_maps_flutter/install
# flutter pub add google_maps_flutter
This will add a line like this to your package's pubspec.yaml (and run an implicit flutter pub get):
dependencies:
    google_maps_flutter: ^2.2.1

Android:
Specify your API key in the application manifest android/app/src/main/AndroidManifest.xml:
<manifest ...
<application ...
<meta-data android:name="com.google.android.geo.API_KEY"
android:value="YOUR KEY HERE"/>

iOS:
in your swift code, specify your API key in the application delegate ios/Runner/AppDelegate.swift:

import UIKit
import Flutter
import GoogleMaps

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
override func application(
_ application: UIApplication,
didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
) -> Bool {

GMSServices.provideAPIKey("YOUR KEY HERE")

GeneratedPluginRegistrant.register(with: self)
return super.application(application, didFinishLaunchingWithOptions: launchOptions)
}
}

## https://pub.dev/packages/webview_flutter/install
# flutter pub add webview_flutter
This will add a line like this to your package's pubspec.yaml (and run an implicit flutter pub get):
dependencies:
    webview_flutter: ^3.0.4

## Build the Android project
# flutter build apk --debug
Result should be:
Running Gradle task 'assembleRelease'...                           ..s
✓  Built .../build/app/outputs/flutter-apk/app-release.apk

Try to run apk build on Android emulator

## Build the iOS project
- Open ios/Runner.xcodeproj in XCode
- On Runner details page choose "Signing & Capabilities" tab
and in "Signing section check the "Automatically manage signing" checkbox
and choose your developer team (Personal Team)
# flutter build ios --simulator --debug
Result should be:
Xcode build done.                                           ...s
Built .../build/ios/iphonesimulator/Runner.app.