import 'package:flutter/material.dart';
import 'package:flutter_driver/driver_extension.dart';

import 'my_access.dart';

void main() {
  enableFlutterDriverExtension();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: 'Retrieve Text Input',
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
        title: const Text('App example for automation testing'),
        backgroundColor: Colors.blueGrey,
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 16),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('Text fields testing'),
            ),
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('Colors testing'),
            ),
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('Gallery management'),
            ),
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('Files management'),
            ),
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('Camera testing'),
            ),
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('List'),
            ),
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('Table'),
            ),
            TextButton(
              style: getButtonStyle(),
              onPressed: () {},
              child: const Text('Сarrousel'),
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
