import 'package:flutter/material.dart';
import 'package:flutter_driver/driver_extension.dart';

import 'my_access.dart';

void main() {
  // enableTextEntryEmulation: false (shows physical keyboard and Appium will
  // be able to send keys using driver with NATIVE_APP context)
  // enableTextEntryEmulation: true (hide physical keyboard and Appium will
  // be able to send keys using FlutterFinder class with FLUTTER context)
  enableFlutterDriverExtension(enableTextEntryEmulation: false);
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'App example',
      home: MyCustomForm(),
    );
  }
}

class MyCustomForm extends StatefulWidget {
  const MyCustomForm({super.key});

  @override
  State<MyCustomForm> createState() => FirstRoute();
}

class FirstRoute extends State<MyCustomForm> {
  final myController = TextEditingController();

  @override
  void dispose() {
    myController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var myController = TextEditingController();
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: const Text('App example'),
        backgroundColor: Colors.blueGrey,
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const TextField(),
            const TextField(
              key: ValueKey('_TextFieldValueKey'),
            ),
            TextButton(
              key: const Key('_checkAccessButton'),
              style: getButtonStyle(),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => MyAccess()),
                );
              },
              child: const Text('Check access'),
            ),
          ],
        ),
      ),
    );
  }

  ButtonStyle getButtonStyle() {
    return ButtonStyle(
      foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
      backgroundColor: MaterialStateProperty.all<Color>(Colors.blueGrey),
      minimumSize: MaterialStateProperty.all(const Size(200, 30)),
    );
  }
}
