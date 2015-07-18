package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * Created by romad_000 on 6/7/2015.
 */
public class Menu2_Fragment extends Fragment implements LocationListener {
    private TextView txt;
    private TextView timer;
    private TextView initialLat;
    private TextView initialLong;
    private TextView totalDistance;
    private ImageButton z60;
    private ImageButton quarter;
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

    public Menu2_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.menu2_layout, container, false);

        txt = (TextView) rootView.findViewById(R.id.menu2initial);
        timer = (TextView) rootView.findViewById(R.id.menu2timertxt);

        initialLat = (TextView) rootView.findViewById(R.id.textView8);
        initialLong = (TextView) rootView.findViewById(R.id.textView9);
        totalDistance = (TextView) rootView.findViewById(R.id.textView10);

        z60 = (ImageButton) rootView.findViewById(R.id.menu2imageButtonzsixty);
        quarter = (ImageButton) rootView.findViewById(R.id.menu2imageButtonquarter);
        stop= (Button) rootView.findViewById(R.id.menu2stoptimerbutton);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        z60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                distance = 0L;
                flag = "sixty";
                handler.postDelayed(updateTimerThread, 0);
            }
        });

        quarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                distance = 0L;
                flag = "quarter";
                l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                initLat = l.getLatitude();
                initialLat.setText("InitLat: "+initLat+"");
                initLong = l.getLongitude();
                initialLong.setText("InitLong:"+initLong+"");
                handler.postDelayed(updateTimerThread, 0);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimerThread);
                distance = 0L;
                timer.setText("0:00:000");
            }
        });

        AdView mAdView = (AdView)rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

            if(flag=="quarter") {
                double nLat = location.getLatitude();
                double nLong = location.getLongitude();

                double dlon = nLong - initLong;
                double dlat = nLat - initLat;
                double a = Math.pow((Math.sin(dlat / 2)), 2) + Math.cos(initLat) * Math.cos(nLat) * Math.pow((Math.sin(dlon / 2)), 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                distance = (3961.0 * c); // (where 3961 is the radius of the Earth);
                totalDistance.setText("Distance: " + distance + "");
            }
            /*
            dlon = lon2 - lon1
            dlat = lat2 - lat1
            a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
            c = 2 * atan2( sqrt(a), sqrt(1-a) )
            d = R * c (where R is the radius of the Earth)
             */
            txt.setText((Math.round(nCurrentSpeed * 2.23694)+" MPH"));
            if((nCurrentSpeed*2.23694)>=60)
            {
                handler.removeCallbacks(updateTimerThread);
            }
            if(distance>=0.25&&flag=="quarter")
            {
                handler.removeCallbacks(updateTimerThread);
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