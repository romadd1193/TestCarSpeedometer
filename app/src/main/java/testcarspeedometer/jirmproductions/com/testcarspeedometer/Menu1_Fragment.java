package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.amazon.device.ads.*;

import java.util.ArrayList;


/**
 * Created by romad_000 on 6/7/2015.
 */
public class Menu1_Fragment extends Fragment implements LocationListener {
    private TextView txt;
    private TextView maxtxt;
    private TextView avg;
    private ArrayList<Float> speedList = new ArrayList();
    public static final int MY_PERMISSIONS = 0;

    public Menu1_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.menu1_layout, container, false);

        txt = (TextView) rootView.findViewById(R.id.initial);
        maxtxt = (TextView) rootView.findViewById(R.id.txtmax);
        avg = (TextView) rootView.findViewById(R.id.txtavg);

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
            Toast.makeText(getActivity().getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
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
            txt.setText((Math.round(nCurrentSpeed * 2.23694)+""));

            if (nCurrentSpeed > nMaxSpeed) {
                nMaxSpeed=nCurrentSpeed;
                maxtxt.setText("Top Speed this Session: "+(Math.round(nCurrentSpeed * 2.23694)+""));
                avg.setText("Avg Speed this Session: "+(Math.round(averageSpeed * 2.23694)));
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
