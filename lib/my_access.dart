import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';

class MyAccess extends StatefulWidget {
  const MyAccess({super.key});

  @override
  State<StatefulWidget> createState() {
    return MyAccessState();
  }
}

class MyAccessState extends State<MyAccess> {
  String locationAccess = 'press check';
  String photosAccess = 'press check';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(key: Key('_accessHeaderTitle'), 'Access'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Row(children: [
                const Padding(
                  padding: EdgeInsets.all(8),
                  child: Text('Location access '),
                ),
                Padding(
                  padding: const EdgeInsets.all(8),
                  child: Text(
                      key: const Key('_locationAccessStatusText'),
                      locationAccess),
                ),
              ]),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8),
                    child: TextButton(
                      key: const Key('_requestLocationAccessButton'),
                      style: getButtonStyle(),
                      onPressed: () async {
                        if (!(await Permission.location.status.isGranted)) {
                          Permission.location.request();
                        }
                      },
                      child: const Text('Request'),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8),
                    child: TextButton(
                      key: const Key('_checkLocationAccessButton'),
                      style: getButtonStyle(),
                      onPressed: () async {
                        if (await Permission.location.status.isGranted) {
                          setState(() {
                            locationAccess = 'Granted';
                          });
                        } else {
                          setState(() {
                            locationAccess = 'Restricted';
                          });
                        }
                      },
                      child: const Text('Check'),
                    ),
                  ),
                ],
              ),
              Row(children: [
                const Padding(
                  padding: EdgeInsets.all(8),
                  child: Text('Photos access '),
                ),
                Padding(
                  padding: const EdgeInsets.all(8),
                  child: Text(key: const Key('_photosAccessStatusText'), photosAccess),
                ),
              ]),
              Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.all(8),
                    child: TextButton(
                      key: const Key('_requestPhotosAccessButton'),
                      style: getButtonStyle(),
                      onPressed: () async {
                        if (!(await Permission.photos.status.isGranted)) {
                          Permission.photos.request();
                        }
                      },
                      child: const Text('Request'),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8),
                    child: TextButton(
                      key: const Key('_checkPhotosAccessButton'),
                      style: getButtonStyle(),
                      onPressed: () async {
                        if (await Permission.photos.status.isGranted) {
                          setState(() {
                            photosAccess = 'Granted';
                          });
                        } else {
                          setState(() {
                            photosAccess = 'Restricted';
                          });
                        }
                      },
                      child: const Text('Check'),
                    ),
                  ),
                ],
              ),
            ]),
      ),
    );
  }

  ButtonStyle getButtonStyle() {
    return ButtonStyle(
      foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
      backgroundColor: MaterialStateProperty.all<Color>(Colors.blueGrey),
      minimumSize: MaterialStateProperty.all(const Size(100, 30)),
    );
  }
}
