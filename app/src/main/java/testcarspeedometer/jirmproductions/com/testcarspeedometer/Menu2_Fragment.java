package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;
import com.amazon.device.ads.*;

import static testcarspeedometer.jirmproductions.com.testcarspeedometer.Menu1_Fragment.Color_setting;
import static testcarspeedometer.jirmproductions.com.testcarspeedometer.Menu1_Fragment.KPH_setting;
import static testcarspeedometer.jirmproductions.com.testcarspeedometer.Menu1_Fragment.Mirror_setting;
import static testcarspeedometer.jirmproductions.com.testcarspeedometer.R.id.menu2stoptimerbutton;


/**
 * Created by romad_000 on 6/7/2015. Cool!
 */
public class Menu2_Fragment extends Fragment implements LocationListener {
    private TextView txt;
    private TextView timer;
    //private TextView initialLat;
    //private TextView initialLong;
   // private TextView totalDistance;
    private Button z60;
    //private ImageButton quarter;
    private Button stop;
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    double distance = 0L;
    double initLat = 0;
    double initLong = 0;
    String flag = "";
    private Handler handler = new Handler();
    Location l;
    LocationManager lm;
    public static final int MY_PERMISSIONS = 0;

    public Menu2_Fragment() {
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

        //Toast.makeText(getActivity().getApplicationContext(),color,Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.menu2_layout, container, false);

        txt = (TextView) rootView.findViewById(R.id.menu2initial);
        timer = (TextView) rootView.findViewById(R.id.menu2timertxt);
        z60 = (Button) rootView.findViewById(R.id.menu2imageButtonzsixty);
        stop = (Button) rootView.findViewById(R.id.menu2stoptimerbutton);


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

        timer.setTextColor(Color.parseColor(hexCode));
        txt.setTextColor(Color.parseColor(hexCode));
        z60.setTextColor(Color.parseColor(hexCode));
        stop.setTextColor(Color.parseColor(hexCode));


        AdLayout adView = (AdLayout) rootView.findViewById(R.id.adView);
        AdTargetingOptions adOptions = new AdTargetingOptions();
        // Optional: Set ad targeting options here.
        adView.loadAd(adOptions); // Retrieves an ad on background thread

        txt = (TextView) rootView.findViewById(R.id.menu2initial);
        timer = (TextView) rootView.findViewById(R.id.menu2timertxt);

        //initialLat = (TextView) rootView.findViewById(R.id.textView8);
       // initialLong = (TextView) rootView.findViewById(R.id.textView9);
        //totalDistance = (TextView) rootView.findViewById(R.id.textView10);

        z60 = (Button) rootView.findViewById(R.id.menu2imageButtonzsixty);
        //quarter = (ImageButton) rootView.findViewById(R.id.menu2imageButtonquarter);
        stop= (Button) rootView.findViewById(menu2stoptimerbutton);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS);

        }else{
            //Toast.makeText(getActivity().getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }


        //lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        z60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                distance = 0L;
                flag = "sixty";
                handler.postDelayed(updateTimerThread, 0);
            }
        });
/**
        quarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                distance = 0L;
                flag = "quarter";


                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS);

                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
                    l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }


                //l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                initLat = l.getLatitude();
                initialLat.setText("InitLat: "+initLat+"");
                initLong = l.getLongitude();
                initialLong.setText("InitLong:"+initLong+"");
                handler.postDelayed(updateTimerThread, 0);
            }
        });
**/
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimerThread);
                distance = 0L;
                timer.setText("0:00:000");
            }
        });

    return rootView;
}

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };


    @Override
    public void onLocationChanged(Location location) {

        if(location==null){
            txt.setText("-.- MPH");
        }
        else{
            float nCurrentSpeed=location.getSpeed();
            /**
            if(flag.equals("quarter")) {
                double nLat = location.getLatitude();
                double nLong = location.getLongitude();

                double dlon = nLong - initLong;
                double dlat = nLat - initLat;
                double a = Math.pow((Math.sin(dlat / 2)), 2) + Math.cos(initLat) * Math.cos(nLat) * Math.pow((Math.sin(dlon / 2)), 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                distance = (3961.0 * c); // 3961 - radius of the Earth meters/mile - 1609.34
                totalDistance.setText("Distance: " + distance + "");
            }

            	// find the differences between the coordinates
		        dlat = lat2 - lat1;
		        dlon = lon2 - lon1;

		        // here's the heavy lifting
		        a  = Math.pow(Math.sin(dlat/2),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);
		        c  = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a)); // great circle distance in radians
		        dm = c * Rm; // great circle distance in miles
		        dk = c * Rk; // great circle distance in km
             */

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