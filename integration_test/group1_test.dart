import 'package:flutter_test/flutter_test.dart';

import 'webview_test.dart';
import 'google_map_test.dart';

void main() {
  group('MyTestGroup1', () {
    testWebView();
    testGoogleMap();
  });
}
