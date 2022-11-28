import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:pocapp/main.dart';
import 'package:patrol/patrol.dart';

void main() {
  testWebView();
}

void testWebView() {
  patrolTest(
    'interacts with Google',
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

      await $(#_webViewTextField).enterText('https://google.com');
      await $(#_webViewButton).tap();

      await $(#_my_web_view).waitUntilVisible(
        timeout: const Duration(seconds: 15),
      );
      await $.pump(const Duration(seconds: 5));
      await $.native.tap(Selector(textContains: 'English'));
      await $.native.tap(Selector(text: 'Увійти'));
      await $.native
          .tap(Selector(text: 'Електронна адреса або номер телефону'));
      await $.native.enterTextByIndex('pocapp88@gmail.com', index: 0);
      await $.native.tap(Selector(text: 'Далі'));
      await $.native.tap(Selector(text: 'Введіть пароль'));
      await $.native.enterTextByIndex('XXX007RpS1mT', index: 0);
      await $.native.tap(Selector(text: 'Далі'));
      await $.native.tap(Selector(text: 'Google offered in:'));

      await $.tester.pageBack();
      await $.pump(const Duration(seconds: 2));
      expect($(#_webViewButton), findsOneWidget);

      await $.pump(const Duration(seconds: 2));
    },
  );
}
