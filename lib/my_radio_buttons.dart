import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';

class MyRadioButton extends StatefulWidget {
  const MyRadioButton({super.key});

  @override
  State<StatefulWidget> createState() {
    return MyRadioButtonState();
  }
}

enum SingingCharacter { opt1, opt2 }

class MyRadioButtonState extends State<MyRadioButton> {
  String radioButtonStatus = 'SingingCharacter.opt2';
  SingingCharacter? _character = SingingCharacter.opt1;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(key: Key('_radioButtonHeaderTitle'), 'RadioButtons'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Padding(
                padding: const EdgeInsets.all(8),
                child: Text(
                    key: const Key('_radioButtonStatusText'),
                    _character.toString()
                ),
              ),
              ListTile(
                title: const Text('Option 1'),
                leading: Radio<SingingCharacter>(
                  key: const Key('_opt1Key'),
                  value: SingingCharacter.opt1,
                  groupValue: _character,
                  onChanged: (SingingCharacter? value) {
                    setState(() {
                      _character = value;
                    });
                  },
                ),
              ),
              ListTile(
                title: const Text('Option 2'),
                leading: Radio<SingingCharacter>(
                  key: const Key('_opt2Key'),
                  value: SingingCharacter.opt2,
                  groupValue: _character,
                  onChanged: (SingingCharacter? value) {
                    setState(() {
                      _character = value;
                    });
                  },
                ),
              ),
            ]),
      ),
    );
  }
}
