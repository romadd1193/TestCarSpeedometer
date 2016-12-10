package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.amazon.device.ads.*;

import java.util.ArrayList;

import static testcarspeedometer.jirmproductions.com.testcarspeedometer.R.menu.main;


/**
 * Created by romad_000 on 6/7/2015.
 */
public class Menu1_Fragment extends Fragment implements LocationListener {
    private TextView txt;
    private TextView txtBIG;
    private TextView maxtxt;
    private TextView avg;
    private ArrayList<Float> speedList = new ArrayList();
    public static final int MY_PERMISSIONS = 0;
    public static boolean KPH_setting = false;
    public static boolean Mirror_setting = false;
    public static String Color_setting = "Green";


    public Menu1_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        Boolean KPH = sharedPref.getBoolean("switch_pref_KPH",false);
        Boolean mirror = sharedPref.getBoolean("switch_pref_Mirrored",false);
        String color = sharedPref.getString("list_pref_color", "def");
        KPH_setting = KPH;
        Mirror_setting = mirror;
        Color_setting = color;

        Toast.makeText(getActivity().getApplicationContext(),color,Toast.LENGTH_SHORT).show();

        View rootView = inflater.inflate(R.layout.menu1_layout, container, false);

        txt = (TextView) rootView.findViewById(R.id.initial);
        txtBIG = (TextView) rootView.findViewById(R.id.textView7);
        maxtxt = (TextView) rootView.findViewById(R.id.txtmax);
        avg = (TextView) rootView.findViewById(R.id.txtavg);

        String hexCode;

        switch (Color_setting) {
            case "Red":  hexCode = "#FF0000";
                break;
            case "Blue":  hexCode = "#0000FF";
                break;
            case "Purple":  hexCode = "#6600CC";
                break;
            case "Green":  hexCode = "#00FF00";
                break;
            case "White":  hexCode = "#FFFFFF";
                break;
            case "Yellow":  hexCode = "#FFFF00";
                break;
            case "Orange":  hexCode = "#FFA500";
                break;
            case "Pink":    hexCode = "#FF33CC";
                break;
            default: hexCode = "#FF00FF";
                break;

        }

        txtBIG.setTextColor(Color.parseColor(hexCode));
        txt.setTextColor(Color.parseColor(hexCode));
        maxtxt.setTextColor(Color.parseColor(hexCode));
        avg.setTextColor(Color.parseColor(hexCode));

        if(KPH_setting==false){
            txtBIG.setText("MPH");
        }else
        {
            txtBIG.setText("KPH");
        }

        if(Mirror_setting==true){

        }
            if (KPH_setting==false) {
                txtBIG.setText("ʜqM");
            }else
                txtBIG.setText("ʜqʞ");


        AdLayout adView = (AdLayout) rootView.findViewById(R.id.adView);
        AdTargetingOptions adOptions = new AdTargetingOptions();
        // Optional: Set ad targeting options here.
        adView.loadAd(adOptions); // Retrieves an ad on background thread

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS);

        }else{
            //Toast.makeText(getActivity().getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }


        return rootView;

    }


    private float nMaxSpeed = 0;
    @Override
    public void onLocationChanged(Location location) {

        if(location==null){
            txt.setText("-.-");
    }
        else {
            float nCurrentSpeed=location.getSpeed();
            speedList.add(nCurrentSpeed);
            float sum = 0;
            for(int i=0; i<speedList.size();i++)
            {
                 sum += speedList.get(i);
            }
            float averageSpeed = sum/speedList.size();

            if(KPH_setting==false)
            {
                txt.setText((Math.round(nCurrentSpeed * 2.23694)+""));
                if (nCurrentSpeed > nMaxSpeed) {
                    nMaxSpeed=nCurrentSpeed;
                    maxtxt.setText("Top Speed this Session: "+(Math.round(nCurrentSpeed * 2.23694)+" MPH"));
                    avg.setText("Avg Speed this Session: "+(Math.round(averageSpeed * 2.23694))+" MPH");
                }
            }else
            {
                txt.setText((Math.round(nCurrentSpeed)+""));
                if (nCurrentSpeed > nMaxSpeed) {
                    nMaxSpeed=nCurrentSpeed;
                    maxtxt.setText("Top Speed this Session: "+(Math.round(nCurrentSpeed*3.6)+" KPH"));
                    avg.setText("Avg Speed this Session: "+(Math.round(averageSpeed*3.6))+"KPH");
                }
            }



        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
