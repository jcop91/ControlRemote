package com.iteso.controlremote;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public static final int segundos = 3;
    public static final int milisegundos = segundos*1000;
    public static final int delay = 2;
    private ProgressBar pbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.pbLogin = (ProgressBar) findViewById(R.id.pbLogin);
        this.pbLogin.setMax(MaximoProgreso());
        EmpezarAnimacion();
    }

    private void EmpezarAnimacion(){
        new CountDownTimer(milisegundos,1000) {
            public void onTick(long millisUntilFinished) {
                pbLogin.setProgress(EstablecerProgreso(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent startApp = new Intent(MainActivity.this, ControlRemoteActivity.class);
                startActivity(startApp);
                finish();
            }
        }.start();
    }

    private int EstablecerProgreso (long miliseconds){
        return (int)((milisegundos - miliseconds)/1000);
    }

    private int MaximoProgreso(){
        return segundos-delay;
    }
}
