package testcarspeedometer.jirmproductions.com.testcarspeedometer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/**
 * Created by romad_000 on 6/7/2015.
 */
public class Conversion_Fragment extends DialogFragment {
    AlertPositiveListener alertPositiveListener;

    public void show(FragmentManager manager, String alert_dialog_radio) {
    }

    interface AlertPositiveListener {
        public void onPositiveClick(int position);
    }

    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try{
            alertPositiveListener = (AlertPositiveListener) activity;
        }catch(ClassCastException e){
            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }

    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog)dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClick(position);
        }
    };


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int position = bundle.getInt("position");

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle("Choose your version");
        b.setSingleChoiceItems(Store.code, position, null);
        b.setPositiveButton("OK",positiveListener);
        b.setNegativeButton("Cancel", null);
        AlertDialog d = b.create();

        return d;
    }
}