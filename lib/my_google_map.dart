import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class MyGoogleMap extends StatelessWidget {
  MyGoogleMap({super.key});

  late GoogleMapController mapController;

  final LatLng _center = const LatLng(50.4108892, 30.4502618);

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(key: Key('_mapHeaderTitle'), 'Google Map page'),
        backgroundColor: Colors.blueGrey,
      ),
      body: Center(
        child: GoogleMap(
          onMapCreated: _onMapCreated,
          initialCameraPosition: CameraPosition(
            target: _center,
            zoom: 10.0,
          ),
        ),
      ),
    );
  }
}
