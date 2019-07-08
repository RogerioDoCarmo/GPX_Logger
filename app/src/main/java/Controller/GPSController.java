package Controller;

/*
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
*/

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import static androidx.core.content.ContextCompat.getSystemService;

//import static androidx.core.content.ContextCompat.getSystemService;

public class GPSController  implements LocationListener{


    private void teste(Context context) {

    }


//    LocationListener locationListener = new LocationListener();

    @Override
    public void onLocationChanged(Location location) {
        
    }

    /**
     * @param s
     * @param i
     * @param bundle
     * @deprecated
     */
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
