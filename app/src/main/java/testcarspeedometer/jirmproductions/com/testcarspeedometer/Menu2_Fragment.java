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


/**
 * Created by romad_000 on 6/7/2015.
 */
public class Menu2_Fragment extends Fragment implements LocationListener {
    private TextView txt;
    private TextView timer;
    private ImageButton z60;
    private ImageButton quarter;
    private Button stop;
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Handler handler = new Handler();

    public Menu2_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.menu2_layout, container, false);

        txt = (TextView) rootView.findViewById(R.id.menu2initial);
        timer = (TextView) rootView.findViewById(R.id.menu2timertxt);
        z60 = (ImageButton) rootView.findViewById(R.id.menu2imageButtonzsixty);
        quarter = (ImageButton) rootView.findViewById(R.id.menu2imageButtonquarter);
        stop= (Button) rootView.findViewById(R.id.menu2stoptimerbutton);
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        z60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimerThread, 0);
            }
        });

        quarter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimerThread, 0);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimerThread);
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
            txt.setText((Math.round(nCurrentSpeed * 2.23694)+" MPH"));
            if((nCurrentSpeed*2.23694)>=60)
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