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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import Controller.FileHandler;
import Controller.GPXparser;
import Model.GPX_Structure;

public class Activity_Main extends AppCompatActivity {

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    GPX_Structure gpx_structure = new GPX_Structure();

    int PERMISSION_LOCATION_REQUEST_CODE = 9;

    TextView txtContent = null;
    Button btnShow = null;


    private void addMessage(String msg) {
        txtContent.append(msg + "\n");
    }

    private void getLastKnowLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

            Toast.makeText(getApplicationContext(), "Permissão necessária!!!", Toast.LENGTH_SHORT).show();

            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location == null) {
            Toast.makeText(getApplicationContext(), "Última localização desconhecida!", Toast.LENGTH_SHORT).show();
            return;
        }

        Date date = new Date(location.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String time = format.format(date);

        addMessage(time + "; " +
                String.format("%s", new DecimalFormat("###.###").format(location.getLatitude())) + "; " +
                String.format("%s", new DecimalFormat("###.###").format(location.getLongitude())) + "; " +
                String.format("%s", new DecimalFormat("###.###").format(location.getAltitude())) + "; "
        );



        Toast.makeText(getApplicationContext(), "Ultima localizacao conhecida!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        txtContent = findViewById(R.id.textContent);
        txtContent.setMovementMethod(new ScrollingMovementMethod());

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    Toast.makeText(getApplicationContext(), "Permissão requerida!", Toast.LENGTH_SHORT).show();
                    return;
                }

                getLastKnowLocation();

                locationManager.requestLocationUpdates(LocationManager
                        .GPS_PROVIDER, 1000, 1, locationListener);

                GPXparser parser = new GPXparser(gpx_structure);
                Log.i("TESTE","\n\n" + parser.getFullText());

                FileHandler fileHandler = new FileHandler();
                fileHandler.WriteText2Internal(getApplicationContext(),parser.getStringBuilder(),"TESTE02.gpx");

                fileHandler.ShareFileURI(getApplicationContext(),"TESTE02.gpx");

            }
        });

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

            addMessage("Time (UTC); Latitude (GMS); Longitude (GMS); Altitude (m);  ");

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Date date = new Date(location.getTime());
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    format.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String time = format.format(date);

                    addMessage(time + "; " +
                            String.format("%s", new DecimalFormat("###.###").format(location.getLatitude())) + "; " +
                            String.format("%s", new DecimalFormat("###.###").format(location.getLongitude())) + "; " +
                            String.format("%s", new DecimalFormat("###.###").format(location.getAltitude())) + "; "
                    );

                    /* Criando o arquivo GPX TODO: SEPARAR EM UMA FUNÇÃO*/
                    gpx_structure.add_GPXpoint(location.getTime(), (float)location.getLatitude(),  (float)location.getLongitude(), location.getAltitude()); // TODO REVIEW TYPES
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
