package com.am.ahmad;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class Student_Map extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
Marker mVisitingMarker;
double lat,lon;
FusedLocationProviderClient mFusedLocationClient;
    LocationManager manager;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         manager = (LocationManager)
                getSystemService( Context.LOCATION_SERVICE );
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enable the GPS")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused")
                                        final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        System.exit(0);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        if (ActivityCompat.checkSelfPermission(Student_Map.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Student_Map.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Student_Map.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            if (!mMap.isMyLocationEnabled())
                mMap.setMyLocationEnabled(true);
        }
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(mVisitingMarker!=null) {
                    mVisitingMarker.remove();
                }
                mVisitingMarker=mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                lat=latLng.latitude;
                lon=latLng.longitude;
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Student_Map.this);
                alertDialogBuilder.setMessage("هل تريد تحديد الموقع ؟ ")
                        .setCancelable(false)
                        .setPositiveButton("تأكيد",
                                new DialogInterface.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int id){
                                        Intent intent = new Intent(Student_Map.this,Teacher_list.class);
                                        intent.putExtra("lat",lat);
                                        intent.putExtra("lon",lon);
                                        startActivity(intent);
                                        finish();

                                    }
                                });
                alertDialogBuilder.setNegativeButton("الغاء",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }

        });
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); // two minute interval
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                Log.e("MAPPA1","PERMISSION GRANTED");
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mMap.setMyLocationEnabled(true);
                Toast.makeText(this,"i am in onMap 2 methode",Toast.LENGTH_LONG).show();
                Log.e("TAG","i am in on OnMap 2  methode");

            //    info.setText(R.string.location_s4);

            } else {
            //    info.setText(R.string.location_s5);
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        //    info.setText(R.string.location_s6);
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                Log.d("System out", "onMarkerDragEnd...");
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
            }
        });

//Don't forget to Set draggable(true) to marker, if this not set marker does not drag.

    }

    LocationCallback mLocationCallback = new LocationCallback() {



        @Override
        public void onLocationResult(LocationResult locationResult) {
          //  info.setText(R.string.location_s7);
            Log.e("TAG","i am in onLocationResult Msthod");

            Log.e("MAPPA1","LOCATION DONES");
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
              //  info.setText(R.string.location_s8);

                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                Log.e("TAG","my current location is : " +location.getLatitude() +" , " + location.getLongitude() );

                // MarkerOptions markerOptions = new MarkerOptions();
                // markerOptions.position(latLng);
                // markerOptions.title("Current Position");
                //  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                //  mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
              //  info.setText(R.string.location_s1);
           //     me=latLng;
                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
//                mGoogleMap.addCircle(new CircleOptions()
//                        .center(latLng)
//                        .radius(20000)
//                        .strokeWidth(0f)
//                        .fillColor(0x550000FF));

            }else{
               // info.setText(R.string.location_s2);

                Toast.makeText(Student_Map.this, "No locations found right now !", Toast.LENGTH_SHORT).show();
            }
        }



        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {

            Log.e("TAG","i am in on OnLocationAvailabilty methode");

        }
    };
    @Override
    public boolean onMyLocationButtonClick() {
        if(!manager.isProviderEnabled( LocationManager.GPS_PROVIDER ))
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Student_Map.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

}
