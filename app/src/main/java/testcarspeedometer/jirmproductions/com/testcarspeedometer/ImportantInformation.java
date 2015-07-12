package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by romad_000 on 6/29/2015.
 */
public class ImportantInformation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //the corresponding layout
        setContentView(R.layout.importantinfo);
        //the back button is ID'd
        final Button gps = (Button) findViewById(R.id.backtomainbtn);
        //onCLickListener listens for user interaction
        gps.setOnClickListener(new View.OnClickListener() {

            @Override
            //when the user clicks on the back button, they are taken from the help activity back to the main activity
            public void onClick(View v) {
                startActivity(new Intent(ImportantInformation.this, MainActivity.class));

            }
        });}

}


