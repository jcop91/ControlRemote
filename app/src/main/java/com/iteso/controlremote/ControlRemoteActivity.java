package com.iteso.controlremote;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.iteso.controlremote.source.Tools;

public class ControlRemoteActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_controlremote);
        ibLuz = findViewById(R.id.ibLuz);
        ibClaxon = findViewById(R.id.ibClaxon);
        ibDerecha = findViewById(R.id.ibDerecha);
        ibAvanza = findViewById(R.id.ibAvanza);
        ibIzquierda = findViewById(R.id.ibIzquierda);
        ibDetener = findViewById(R.id.ibDetener);

        ibLuz.setOnClickListener(this);
        ibClaxon.setOnClickListener(this);
        ibDerecha.setOnClickListener(this);
        ibAvanza.setOnClickListener(this);
        ibIzquierda.setOnClickListener(this);
        ibDetener.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ibLuz:
                Accion = 4;
                Tools.NotificacionToast(this,"Luces");
                TxSendData(Accion);
                break;
            case R.id.ibClaxon:
                Accion = 5;
                Tools.NotificacionToast(this,"Claxón");
                TxSendData(Accion);
                break;
            case R.id.ibDerecha:
                Accion = 3;
                Tools.NotificacionToast(this,"Derecha");
                TxSendData(Accion);
                break;
            case R.id.ibAvanza:
                Accion = 1;
                Tools.NotificacionToast(this,"Avanzar");
                TxSendData(Accion);
                break;
            case R.id.ibIzquierda:
                Accion = 2;
                Tools.NotificacionToast(this,"Izquierda");
                TxSendData(Accion);
                break;
            case R.id.ibDetener:
                Accion = 0;
                Tools.NotificacionToast(this,"Detener");
                TxSendData(Accion);
                break;
        }
    }
    private void TxSendData(int DataInformation){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
        }else{
            Intent SendData = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(SendData,DataInformation);
        }
    }

    private ImageButton ibLuz, ibClaxon, ibDerecha, ibAvanza, ibIzquierda, ibDetener;
    private final static int REQUEST_ENABLE_BT = 1;
    private static int Accion;
}
