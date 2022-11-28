import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:pocapp/main.dart';
import 'package:patrol/patrol.dart';
import 'package:pocapp/my_google_map.dart';

void main() {
  testGoogleMap();
}

void testGoogleMap() {
  patrolTest(
    'interacts with Google Map',
    config: const PatrolTestConfig(
      existsTimeout: Duration(seconds: 100),
      visibleTimeout: Duration(seconds: 100),
      settleTimeout: Duration(seconds: 100),
      appName: 'Pocapp',
      packageName: 'com.denysord1988.pocapp',
      bundleId: 'com.denysord1988.pocapp',
    ),
    nativeAutomation: true,
    ($) async {
      await $.pumpWidgetAndSettle(const MyApp());

      await $(#_checkAccessButton).tap();

      await $(#_requestLocationAccessButton).tap();
      await $.pump(const Duration(seconds: 1));
      await $.native.grantPermissionWhenInUse();
      //await $.native.tap(Selector(textContains: 'While'));
      await $(#_checkLocationAccessButton).tap();
      await $.pump(const Duration(seconds: 1));
      expect($(#_locationAccessStatusText).text, 'Granted');

      await $.tester.pageBack();
      await $.pump(const Duration(seconds: 2));
      expect($(#_map), findsOneWidget);

      await $(#_map).tap();
      await $.pump(const Duration(seconds: 3));
      expect($('Google Map page'), findsOneWidget);
      await $.pump(const Duration(seconds: 3));

      await $.tester.pageBack();
      await $.pump(const Duration(seconds: 2));
      expect($(#_map), findsOneWidget);

      await $.pump(const Duration(seconds: 2));
    },
  );
}
