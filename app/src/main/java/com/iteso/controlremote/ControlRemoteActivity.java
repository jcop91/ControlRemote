package com.iteso.controlremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.iteso.controlremote.source.ConnectThread;
import com.iteso.controlremote.source.Tools;

import java.io.IOException;
import java.io.OutputStream;

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
        Tools.NotificacionToast(this,"Se crea la conexión con Bluetooth HC-05", Toast.LENGTH_SHORT);
        BluetoothConnections();
    }

    @Override
    public void onClick(View v){

        if(mBluetoothSocket != null){
            switch (v.getId()){
                case R.id.ibLuz:
                    Accion = "4";
                    Tools.NotificacionToast(this,"Luces",Toast.LENGTH_SHORT);
                    TxSendData(Accion);
                    break;
                case R.id.ibClaxon:
                    Accion = "5";
                    Tools.NotificacionToast(this,"Claxón",Toast.LENGTH_SHORT);
                    TxSendData(Accion);
                    break;
                case R.id.ibDerecha:
                    Accion = "3";
                    Tools.NotificacionToast(this,"Derecha",Toast.LENGTH_SHORT);
                    TxSendData(Accion);
                    break;
                case R.id.ibAvanza:
                    Accion = "1";
                    Tools.NotificacionToast(this,"Avanzar",Toast.LENGTH_SHORT);
                    TxSendData(Accion);
                    break;
                case R.id.ibIzquierda:
                    Accion = "2";
                    Tools.NotificacionToast(this,"Izquierda",Toast.LENGTH_SHORT);
                    TxSendData(Accion);
                    break;
                case R.id.ibDetener:
                    Accion = "0";
                    Tools.NotificacionToast(this,"Detener",Toast.LENGTH_SHORT);
                    TxSendData(Accion);
                    break;
            }
        }else {
            Tools.NotificacionToast(this,"Se intenta reconectar", Toast.LENGTH_SHORT);
            BluetoothConnections();
        }
    }
    private void BluetoothConnections (){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            Tools.NotificacionToast(this,"Bluetooth no disponible", Toast.LENGTH_LONG);
        } else{
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
            }else{
                mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(SERVICE_ADDRESS);
                connect = new ConnectThread(mBluetoothDevice,SERVICE_ID,mBluetoothAdapter);
                connect.start();
                mBluetoothSocket = connect.getOutSocket();
            }
        }
    }
    
    private void TxSendData(String DataInformation){
        try{
            outData = mBluetoothSocket.getOutputStream();
            outData.write(DataInformation.getBytes());
            outData.flush();
        }catch (IOException ex){
            ex.getStackTrace();
            Tools.NotificacionToast(this,ex.getMessage(), Toast.LENGTH_LONG);
        }
    }
    
    //Variables Globales
    private ImageButton ibLuz, ibClaxon, ibDerecha, ibAvanza, ibIzquierda, ibDetener;
    public static final String SERVICE_ID = "00001101-0000-1000-8000-00805f9b34fb"; //SPP UUID
    public static final String SERVICE_ADDRESS = "98:D3:33:80:B2:DB"; // HC-05 BT ADDRESS
    private final static int REQUEST_ENABLE_BT = 1;
    private static BluetoothSocket mBluetoothSocket;
    private BluetoothDevice mBluetoothDevice = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private OutputStream outData;
    private static String Accion;
    ConnectThread connect;
}
