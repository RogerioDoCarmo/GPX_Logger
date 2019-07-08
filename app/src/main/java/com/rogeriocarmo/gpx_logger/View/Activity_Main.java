package com.rogeriocarmo.gpx_logger.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.rogeriocarmo.gpx_logger.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Activity_Main extends AppCompatActivity {

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;
    int PERMISSION_LOCATION_REQUEST_CODE = 9;

    StringBuilder text = new StringBuilder();


    TextView txtContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        txtContent = findViewById(R.id.textContent);

        if (displayGpsStatus()) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                //return;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);


            }

            text.append("Time (UTC); Latitude (GMS); Longitude (GMS); Altitude (m)  ");

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    text.append("\n" + String.format("%s", new DecimalFormat("###.####").format(location.getTime())) + "; " +
                                       String.format("%s", new DecimalFormat("###.###").format(location.getLatitude())) + "; " +
                                       String.format("%s", new DecimalFormat("###.###").format(location.getLongitude())) + "; " +
                                       String.format("%s", new DecimalFormat("###.###").format(location.getAltitude())) + "; "
                    );

                    txtContent.setText(text.toString());
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 1000, 1, locationListener);
        } else{
            txtContent.setText("GPS Disabled!");
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_LOCATION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(LocationManager
                                .GPS_PROVIDER, 1000, 1, locationListener);
                    } else {
                        //requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_CODE);
                    }
                }
            }
        }
    }

    //----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

}
