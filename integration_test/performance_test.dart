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

      int t = DateTime.now().millisecondsSinceEpoch;

      for (var i = 0; i < 10; i++) {
        await $(#_radioButton).tap();
        await $(#_radioButtonHeaderTitle).tap();
        await $('SingingCharacter.opt1').tap();
        expect($('SingingCharacter.opt1').text, 'SingingCharacter.opt1');
        await $(#_opt2Key).tap();
        await $('SingingCharacter.opt2').tap();
        expect($('SingingCharacter.opt2').text, 'SingingCharacter.opt2');
        await $(#_opt1Key).tap();
        await $('SingingCharacter.opt1').tap();
        expect($('SingingCharacter.opt1').text, 'SingingCharacter.opt1');
        await $.tester.pageBack();
        await $(#_homePageTitle).tap();
      }

      for (var i = 0; i < 10; i++) {
        await $(#_toggles).tap();
        await $(#_togglesHeaderTitle).tap();
        await $('Female').tap();
        await $('Male').tap();
        expect($('Female').text, 'Female');
        expect($('Male').text, 'Male');
        await $.tester.pageBack();
        await $(#_homePageTitle).tap();
      }

      for (var i = 0; i < 10; i++) {
        await $(#_checkAccessButton).tap();
        expect($('press check1').text, 'press check1');
        await $(#_checkLocationAccessButton).tap();
        await $('Restricted').tap();
        expect($('Restricted').text, 'Restricted');
        await $.tester.pageBack();
        await $(#_homePageTitle).tap();
      }

      print(
          'Test time in milliseconds = ${DateTime.now().millisecondsSinceEpoch - t}');
    },
  );
}
