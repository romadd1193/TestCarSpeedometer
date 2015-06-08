package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by romad_000 on 6/7/2015.
 */
public class Menu1_Fragment extends Fragment implements LocationListener {
    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu1_layout, container, false);
        return rootview;

        LocationManager lm = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        this.onLocationChanged(null);


        @Override
        public void onLocationChanged (Location location){
            TextView initial = (TextView) this.getActivity().findViewById(R.id.initial);

            if (location == null) {
                initial.setText("0.0");
            }
            else
            {
                float nCurrentSpeed = loc.getSpeed();
                initial.setText((int) nCurrentSpeed);



                 }


    }
