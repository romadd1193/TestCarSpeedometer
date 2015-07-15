package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by romad_000 on 6/7/2015.
 */
public class Menu3_Fragment extends Fragment {

    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.importantinfo, container,false);


        return rootview;


    }



}
