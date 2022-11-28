import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:pocapp/main.dart';
import 'package:patrol/patrol.dart';
import 'dart:io' show Platform;

void main() {
  testAccess();
}

void testAccess() {
  patrolTest(
    'Interacts with system dialog',
    config: const PatrolTestConfig(
      existsTimeout: Duration(seconds: 15),
      visibleTimeout: Duration(seconds: 15),
      settleTimeout: Duration(seconds: 15),
      appName: 'Pocapp',
      packageName: 'com.denysord1988.pocapp',
      bundleId: 'com.denysord1988.pocapp',
    ),
    nativeAutomation: true,
    ($) async {
      await $.pumpWidgetAndSettle(const MyApp());

      await $(#_checkAccessButton).tap();
      await $(#_checkLocationAccessButton).tap();
      await $.pump(const Duration(seconds: 1));
      expect($(#_locationAccessStatusText).text, 'Restricted');

      await $(#_requestLocationAccessButton).tap();
      await $.pump(const Duration(seconds: 1));
      await $.native.grantPermissionWhenInUse();
      await $(#_checkLocationAccessButton).tap();
      await $.pump(const Duration(seconds: 1));
      expect($(#_locationAccessStatusText).text, 'Granted');

      await $(#_checkPhotosAccessButton).tap();
      await $.pump(const Duration(seconds: 1));
      expect($(#_photosAccessStatusText).text, 'Restricted');

      await $(#_requestPhotosAccessButton).tap();


      final request = await $.native.isPermissionDialogVisible(
        timeout: const Duration(seconds: 5),
      );
      if (request) {
        await $.native.grantPermissionWhenInUse();
      }
      await $.pump(const Duration(seconds: 1));

      if (Platform.isAndroid) {
        await $.native.tap(Selector(text: 'Allow'));
      } else if (Platform.isIOS) {
        await $.native.tap(
          Selector(text: 'Allow Access to All Photos'),
          appId: 'com.apple.springboard',
        );
      }
      await $(#_checkPhotosAccessButton).tap();
      await $.pump(const Duration(seconds: 1));
      expect($(#_photosAccessStatusText).text, 'Granted');

      await $.tester.pageBack();
      await $.pump(const Duration(seconds: 2));
      expect($(#_webViewButton), findsOneWidget);
    },
  );
}
