import 'package:flutter/material.dart';
import 'package:toggle_switch/toggle_switch.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class MyToggles extends StatefulWidget {
  const MyToggles({super.key});

  @override
  State<StatefulWidget> createState() {
    return MyTogglesState();
  }
}

class MyTogglesState extends State<MyToggles> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(key: Key('_togglesHeaderTitle'), 'Toggles'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              ToggleSwitch(
                key: const Key('_toggleSwitch'),
                minWidth: 90.0,
                initialLabelIndex: 0,
                cornerRadius: 20.0,
                activeFgColor: Colors.white,
                inactiveBgColor: Colors.grey,
                inactiveFgColor: Colors.white,
                totalSwitches: 2,
                labels: const ['Male', 'Female'],
                icons: const [FontAwesomeIcons.mars, FontAwesomeIcons.venus],
                activeBgColors: const [
                  [Colors.blue],
                  [Colors.pink]
                ],
                onToggle: (index) {
                },
              ),
            ]),
      ),
    );
  }
}
