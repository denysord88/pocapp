import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

class MyWebView extends StatelessWidget {
  const MyWebView({super.key, required this.url});

  final String url;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(key: Key('_webviewHeaderTitle'), 'WebView'),
        backgroundColor: Colors.blueGrey,
      ),
      body: WebView(
        initialUrl: url,
        key: const Key('_my_web_view'),
        javascriptMode: JavascriptMode.unrestricted,
      ),
    );
  }
}
