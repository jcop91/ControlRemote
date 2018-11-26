package com.iteso.controlremote.source;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class Tools {
    public static void NotificacionToast(final FragmentActivity activity, final String msj, final int time) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(),msj,time).show();
            }
        });
    }
}
