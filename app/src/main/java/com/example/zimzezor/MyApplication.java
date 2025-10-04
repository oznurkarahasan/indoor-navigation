package com.example.zimzezor;

import android.app.Application;

public class MyApplication extends Application {
    private BluetoothController bluetoothController;

    public void setBluetoothController(BluetoothController controller) {
        this.bluetoothController = controller;
    }

    public BluetoothController getBluetoothController() {
        return bluetoothController;
    }
}