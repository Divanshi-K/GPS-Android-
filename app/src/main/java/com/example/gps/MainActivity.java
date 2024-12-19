package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private static final long minupdatedistance = 100; // in Meters
    private static final long minupdatetime = 1000; // in Milliseconds
    private LocationManager locationManager;
    private Button bncurloc;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bncurloc = (Button) findViewById(R.id.bncurloc);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minupdatetime,
                minupdatedistance,
                new MyLocationListener()
        );
        bncurloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCurrentLocation();
            }
        });
    }
    protected void showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);
        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude : %1$s \n Latitude : %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(MainActivity.this, message,
                    Toast.LENGTH_LONG).show();
        }
    }
    private class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            String message = String.format(
                    "Change to New Location \n Longitude : %1$s \n Latitude : %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MainActivity.this, "GPS Provider Status changed",
                    Toast.LENGTH_LONG).show();
        }
        public void onProviderDisabled(String s) {
            Toast.makeText(MainActivity.this,
                    "GPS turned off",
                    Toast.LENGTH_LONG).show();
        }
        public void onProviderEnabled(String s) {
            Toast.makeText(MainActivity.this,
                    "GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }
}
//finish