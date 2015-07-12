package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


/**
 * Created by romad_000 on 6/7/2015.
 */
public class Menu1_Fragment extends Fragment implements LocationListener {
    private TextView txt;
    private TextView maxtxt;
    public Menu1_Fragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.menu1_layout, container, false);

        txt=(TextView)rootView.findViewById(R.id.initial);
        maxtxt=(TextView)rootView.findViewById(R.id.txtmax);

        LocationManager lm= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        return rootView;
    }

    private float nMaxSpeed = 0;
    @Override
    public void onLocationChanged(Location location) {

        if(location==null){
            txt.setText("-.- MPH");
    }
        else {
            float nCurrentSpeed=location.getSpeed();
            txt.setText((Math.round(nCurrentSpeed * 2.23694)+" MPH"));

            if (nCurrentSpeed > nMaxSpeed) {
                nMaxSpeed=nCurrentSpeed;
                maxtxt.setText("Top Speed this Session: "+(Math.round(nCurrentSpeed * 2.23694)+" MPH"));
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
