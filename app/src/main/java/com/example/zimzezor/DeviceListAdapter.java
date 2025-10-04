package com.example.zimzezor;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {

    private final ArrayList<BluetoothDevice> devices;
    private final HashMap<BluetoothDevice, Integer> deviceRssiMap;

    public DeviceListAdapter(ArrayList<BluetoothDevice> devices, HashMap<BluetoothDevice, Integer> deviceRssiMap) {
        this.devices = devices;
        this.deviceRssiMap = deviceRssiMap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BluetoothDevice device = devices.get(position);
        String deviceName = device.getName() != null ? device.getName() : "Unknown Device";
        int rssi = deviceRssiMap.getOrDefault(device, 0); // Güncel RSSI değerini alın

        holder.textView.setText(deviceName + " \nRSSI: " + rssi);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
