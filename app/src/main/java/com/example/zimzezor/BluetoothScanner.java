package com.example.zimzezor;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BluetoothScanner {
    private static final String TAG = "BLE";
    private static final long SIGNAL_TIMEOUT_MS = 10000; // 10 saniye zaman aşımı
    private static final int LOST_RSSI_VALUE = -900;
    private static final int RSSI_CHANGE_THRESHOLD = 15; // RSSI değişiklik eşiği 15 dB
    private static final long TRANSITION_COOLDOWN_MS = 5000; // 5 saniye geçiş gecikmesi
    private static final int RSSI_HISTORY_SIZE = 5; // RSSI hareketli ortalama için 5 örnek

    private static BluetoothScanner instance;
    private final Context context;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private boolean isScanning = false;
    private ArrayList<BluetoothDevice> devices;
    private HashMap<BluetoothDevice, Integer> deviceRssiMap;
    private HashMap<BluetoothDevice, Long> deviceLastSeenMap;
    private HashMap<BluetoothDevice, ArrayList<Integer>> deviceRssiHistory;
    private BluetoothDevice closestDevice = null;
    private int closestRssi = Integer.MIN_VALUE;
    private long lastTransitionTime = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ArrayList<OnDeviceDetectedListener> listeners = new ArrayList<>();

    private static final Set<String> ALLOWED_DEVICES = new HashSet<>();
    static {
        ALLOWED_DEVICES.add("Ornitorenk!");
        ALLOWED_DEVICES.add("Ornitorenk-2!");
        ALLOWED_DEVICES.add("Ornitorenk-3!");
    }

    public interface OnDeviceDetectedListener {
        void onClosestDeviceDetected(BluetoothDevice device, String deviceName, int rssi);
    }

    private BluetoothScanner(Context context) {
        this.context = context.getApplicationContext();
        devices = new ArrayList<>();
        deviceRssiMap = new HashMap<>();
        deviceLastSeenMap = new HashMap<>();
        deviceRssiHistory = new HashMap<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        }
    }

    public static synchronized BluetoothScanner getInstance(Context context) {
        if (instance == null) {
            instance = new BluetoothScanner(context);
        }
        return instance;
    }

    public void addOnDeviceDetectedListener(OnDeviceDetectedListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            Log.d(TAG, "Listener added. Total listeners: " + listeners.size());
        }
    }

    public void removeOnDeviceDetectedListener(OnDeviceDetectedListener listener) {
        listeners.remove(listener);
        Log.d(TAG, "Listener removed. Total listeners: " + listeners.size());
    }

    public void clearAllListeners() {
        listeners.clear();
        Log.d(TAG, "All listeners cleared");
    }

    public boolean isBluetoothEnabled() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    @SuppressLint("MissingPermission")
    public void startScanning() {
        if (isScanning || bluetoothLeScanner == null) {
            Log.d(TAG, "Start scanning skipped: already scanning or scanner null");
            return;
        }
        devices.clear();
        deviceRssiMap.clear();
        deviceLastSeenMap.clear();
        deviceRssiHistory.clear();
        closestDevice = null;
        closestRssi = Integer.MIN_VALUE;
        bluetoothLeScanner.startScan(scanCallback);
        isScanning = true;
        startTimeoutChecker();
        Toast.makeText(context, "Scanning for Ornitorenk devices...", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Scanning started");
    }

    @SuppressLint("MissingPermission")
    public void stopScanning() {
        if (!isScanning || bluetoothLeScanner == null) {
            Log.d(TAG, "Stop scanning skipped: not scanning or scanner null");
            return;
        }
        bluetoothLeScanner.stopScan(scanCallback);
        isScanning = false;
        handler.removeCallbacksAndMessages(null);
        Toast.makeText(context, "Scan stopped.", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Scanning stopped");
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            String deviceName = device.getName();

            if (deviceName != null && ALLOWED_DEVICES.contains(deviceName)) {
                int rssi = result.getRssi();
                int filteredRssi = getFilteredRssi(device, rssi);
                deviceLastSeenMap.put(device, System.currentTimeMillis());

                if (!devices.contains(device)) {
                    devices.add(device);
                }
                deviceRssiMap.put(device, filteredRssi);

                Log.d(TAG, "Device found: " + deviceName + ", Raw RSSI: " + rssi + ", Filtered RSSI: " + filteredRssi);
                updateClosestDevice();
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e(TAG, "Scan failed with error code: " + errorCode);
            Toast.makeText(context, "Bluetooth scan failed: " + errorCode, Toast.LENGTH_SHORT).show();
        }
    };

    private int getFilteredRssi(BluetoothDevice device, int newRssi) {
        deviceRssiHistory.putIfAbsent(device, new ArrayList<>());
        ArrayList<Integer> history = deviceRssiHistory.get(device);
        history.add(newRssi);
        if (history.size() > RSSI_HISTORY_SIZE) {
            history.remove(0);
        }
        int sum = 0;
        for (int rssi : history) {
            sum += rssi;
        }
        return sum / history.size();
    }

    @SuppressLint("MissingPermission")
    private void startTimeoutChecker() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                boolean updated = false;

                for (BluetoothDevice device : devices) {
                    Long lastSeen = deviceLastSeenMap.get(device);
                    if (lastSeen != null && (currentTime - lastSeen) > SIGNAL_TIMEOUT_MS) {
                        deviceRssiMap.put(device, LOST_RSSI_VALUE);
                        updated = true;
                        Log.d(TAG, "Device " + device.getName() + " signal lost, RSSI set to -900");
                    }
                }

                if (updated) {
                    updateClosestDevice();
                }

                if (!devices.isEmpty() && isScanning) {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void updateClosestDevice() {
        BluetoothDevice newClosestDevice = null;
        int newClosestRssi = Integer.MIN_VALUE;

        for (BluetoothDevice device : devices) {
            Integer rssi = deviceRssiMap.get(device);
            if (rssi != null && rssi > newClosestRssi && rssi != LOST_RSSI_VALUE) {
                newClosestRssi = rssi;
                newClosestDevice = device;
            }
        }

        if (newClosestDevice != null) {
            boolean shouldNotify = closestDevice == null ||
                    !newClosestDevice.equals(closestDevice) ||
                    (closestDevice != null && Math.abs(newClosestRssi - closestRssi) > RSSI_CHANGE_THRESHOLD);

            closestDevice = newClosestDevice;
            closestRssi = newClosestRssi;

            if (shouldNotify && (System.currentTimeMillis() - lastTransitionTime) > TRANSITION_COOLDOWN_MS) {
                lastTransitionTime = System.currentTimeMillis();
                String deviceName = closestDevice.getName();
                Log.d(TAG, "Notifying listeners: Closest device: " + deviceName + ", RSSI: " + newClosestRssi);
                for (OnDeviceDetectedListener listener : new ArrayList<>(listeners)) {
                    listener.onClosestDeviceDetected(closestDevice, deviceName, newClosestRssi);
                }
            } else {
                Log.d(TAG, "Notification skipped: Recent transition or minor RSSI change");
            }
        } else if (closestDevice != null) {
            closestDevice = null;
            closestRssi = Integer.MIN_VALUE;
            Log.d(TAG, "No active devices, resetting closest device");
        }
    }
}