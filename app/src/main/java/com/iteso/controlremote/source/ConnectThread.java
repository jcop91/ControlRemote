package com.iteso.controlremote.source;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {

        private final BluetoothSocket thisSocket;
        private final BluetoothAdapter thisAdapter;
        private final BluetoothDevice thisDevice;
        private String thisSERVICE_ID = null;
        private BluetoothSocket outSocket;

        public ConnectThread(BluetoothDevice device, String SERVICE_ID, BluetoothAdapter bluetoothAdapter) {
            BluetoothSocket tmp = null;
            this.thisDevice = device;
            this.thisSERVICE_ID = SERVICE_ID;
            this.thisAdapter = bluetoothAdapter;

            try {
                tmp = thisDevice.createRfcommSocketToServiceRecord(UUID.fromString(thisSERVICE_ID));
            } catch (IOException e) {
                Log.e("TEST", "Can't connect to service");
            }
            thisSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            thisAdapter.cancelDiscovery();

            try {
                thisSocket.connect();
                Log.d("TESTING", "Connected to shit");
            } catch (IOException connectException) {
                try {
                    thisSocket.close();
                } catch (IOException closeException) {
                    Log.e("TEST", "Can't close socket");
                }
                return;
            }

            outSocket = thisSocket;

        }
        public void cancel() {
            try {
                thisSocket.close();
            } catch (IOException e) {
                Log.e("TEST", "Can't close socket");
            }
        }

    public BluetoothSocket getOutSocket() {
        return outSocket;
    }
}
