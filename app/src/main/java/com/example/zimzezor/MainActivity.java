package com.example.zimzezor;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements SpeechRecognitionHelper.OnCommandListener, BluetoothController {
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private static final int REQUEST_PERMISSIONS = 2;
    private static final long SIGNAL_TIMEOUT_MS = 5000;
    private static final int LOST_RSSI_VALUE = -900;
    private static final String PREFS_NAME = "ZimzezorPrefs";
    private static final String KEY_PERMISSIONS_REQUESTED = "permissionsRequested";

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private ArrayList<BluetoothDevice> devices;
    private HashMap<BluetoothDevice, Integer> deviceRssiMap;
    private HashMap<BluetoothDevice, Long> deviceLastSeenMap;

    private BluetoothDevice closestDevice = null;
    private int closestRssi = Integer.MIN_VALUE;
    private String lastStartedActivity = null;
    private TextView textView;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognitionHelper voiceHandler;
    private Button scanButton;
    private Button exitButton;
    private boolean shouldStartScanning = false;


    // Taranacak cihaz isimleri
    private static final Set<String> ALLOWED_DEVICES = new HashSet<>();
    static {
        ALLOWED_DEVICES.add("Ornitorenk!");
        ALLOWED_DEVICES.add("Ornitorenk-2!");
        ALLOWED_DEVICES.add("Ornitorenk-3!");
        ALLOWED_DEVICES.add("Ornitorenk-4!");
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private SharedPreferences prefs;

    // Bluetooth etkinleştirme için ActivityResultLauncher
    private final androidx.activity.result.ActivityResultLauncher<Intent> enableBluetoothLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(this, "Bluetooth enabled.", Toast.LENGTH_SHORT).show();
                    requestPermissionsFromUser();
                } else {
                    Toast.makeText(this, "Bluetooth is required for this app.", Toast.LENGTH_LONG).show();
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.scanButton);
        exitButton = findViewById(R.id.exitButton);

        textView = findViewById(R.id.textView);
        textView.setText("Merhaba! Bina içi navigasyon uygulamamıza hoşgeldiniz!! " +
                       "Hangi katta olduğunuzu bulmak için lütfen 'Neredeyim' butonuna tıklayınız.");

        devices = new ArrayList<>();
        deviceRssiMap = new HashMap<>();
        deviceLastSeenMap = new HashMap<>();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        checkBluetoothAndPermissions();

        // MyApplication'a BluetoothController'ı kaydet
        MyApplication app = (MyApplication) getApplication();
        app.setBluetoothController(this);

        scanButton.setOnClickListener(v -> {
            if (!bluetoothAdapter.isEnabled()) {
                Toast.makeText(MainActivity.this, "Please enable Bluetooth!", Toast.LENGTH_SHORT).show();
                requestEnableBluetooth();
                return;
            }

            if (!hasRequiredPermissions()) {
                Toast.makeText(MainActivity.this, "Permissions are required!", Toast.LENGTH_SHORT).show();
                requestPermissionsFromUser();
                return;
            }
            shouldStartScanning = true;
            startScanning();
        });

        exitButton.setOnClickListener(v -> {
            // Tüm etkinlikleri kapat
            moveTaskToBack(true); // Uygulamayı arka plana alır
            android.os.Process.killProcess(android.os.Process.myPid()); // Mevcut süreç ID'sini öldürür
            System.exit(1); // JVM çıkışını gerçekleştirir
        });


        // VoiceCommandHandler'ı başlat
        voiceHandler = new SpeechRecognitionHelper(this, this);

        // Ses kaydı izni kontrolü
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }

        // Ses tanıma başlatma butonu
        findViewById(R.id.dinle).setOnClickListener(v -> voiceHandler.startVoiceRecognition());

        
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        voiceHandler.handleVoiceResult(requestCode, resultCode, data);
    }

    @Override
    public void onCommandReceived(String command) { // Bu metod arayüzden geliyor
        Log.d(TAG, "Received command: " + command);  // Komutun doğru alındığını logla
        if (command.toLowerCase().contains("neredeyim")) {
            if (scanButton != null) {scanButton.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");}}
        else if (command.toLowerCase().contains("çıkış yap")) {
            if (exitButton != null) {exitButton.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
    }

    private void checkBluetoothAndPermissions() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "This device does not support Bluetooth.", Toast.LENGTH_LONG).show();
            return;
        }

        boolean permissionsRequested = prefs.getBoolean(KEY_PERMISSIONS_REQUESTED, false);

        if (!permissionsRequested) {
            if (!bluetoothAdapter.isEnabled()) {
                requestEnableBluetooth();
            } else {
                requestPermissionsFromUser();
            }
        }
        // Otomatik tarama başlatma kaldırıldı
    }

    private void requestEnableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        enableBluetoothLauncher.launch(enableBtIntent);
    }

    private boolean hasRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12 ve sonrasında
            boolean hasBluetoothScan = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED;
            boolean hasBluetoothConnect = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
            boolean hasFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            boolean hasRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

            return hasBluetoothScan && hasBluetoothConnect && hasFineLocation && hasRecordAudio;
        } else { // Android 11 ve öncesi
            boolean hasBluetooth = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED;
            boolean hasBluetoothAdmin = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
            boolean hasFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            boolean hasRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

            return hasBluetooth && hasBluetoothAdmin && hasFineLocation && hasRecordAudio;
        }
    }


    private void requestPermissionsFromUser() {
        ArrayList<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+
            permissions.add(Manifest.permission.BLUETOOTH_SCAN);
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT);
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.RECORD_AUDIO);
        } else { // Android 11 ve altı
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.RECORD_AUDIO);
        }
        ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), REQUEST_PERMISSIONS);
    }

    @SuppressLint("MissingPermission")
    public void startScanning() {
        devices.clear();
        deviceRssiMap.clear();
        deviceLastSeenMap.clear();
        closestDevice = null;
        closestRssi = Integer.MIN_VALUE;
        lastStartedActivity = null;
        bluetoothLeScanner.startScan(scanCallback);
        startTimeoutChecker();
        Toast.makeText(this, "Scanning for Ornitorenk devices...", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    public void stopScanning() {
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(scanCallback);
        }
        handler.removeCallbacksAndMessages(null);
        Toast.makeText(this, "Scan stopped.", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            String deviceName = device.getName();

            if (deviceName != null && ALLOWED_DEVICES.contains(deviceName)) {
                int rssi = result.getRssi();
                deviceLastSeenMap.put(device, System.currentTimeMillis());

                if (!devices.contains(device)) {
                    devices.add(device);
                }
                deviceRssiMap.put(device, rssi);

                updateClosestDevice();
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            Log.e("BLE", "Scan failed with error code: " + errorCode);
        }
    };

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
                        Log.d("BLE", "Device " + device.getName() + " signal lost, RSSI set to -900");
                    }
                }

                if (updated) {
                    updateClosestDevice();
                }

                if (!devices.isEmpty()) {
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

        if (newClosestDevice != null && (closestDevice == null || !newClosestDevice.equals(closestDevice))) {
            closestDevice = newClosestDevice;
            closestRssi = newClosestRssi;
            updateActivityBasedOnDevice();
        } else if (newClosestDevice == null && closestDevice != null) {
            closestDevice = null;
            closestRssi = Integer.MIN_VALUE;
            lastStartedActivity = null;
            Log.d("BLE", "No active devices, resetting closest device");
        }
    }

    @SuppressLint("MissingPermission")
    private void updateActivityBasedOnDevice() {
        if (closestDevice != null) {
            String deviceName = closestDevice.getName();

            if (deviceName != null && ALLOWED_DEVICES.contains(deviceName)) {
                Intent intent = null;
                String targetActivity = null;

                if (deviceName.equals("Ornitorenk-3!")) {
                    targetActivity = Engelsiz_Kat_1.class.getSimpleName();
                    if (!targetActivity.equals(lastStartedActivity)) {
                        intent = new Intent(MainActivity.this, Engelsiz_Kat_1.class);
                    }
                } else if (deviceName.equals("Ornitorenk!")) {
                    targetActivity = Engelsiz_zemin_kat.class.getSimpleName();
                    if (!targetActivity.equals(lastStartedActivity)) {
                        intent = new Intent(MainActivity.this, Engelsiz_zemin_kat.class);
                    }
                }else if (deviceName.equals("Ornitorenk-2!")) {
                    targetActivity = Engelsiz_zemin_kat.class.getSimpleName();
                    if (!targetActivity.equals(lastStartedActivity)) {
                        intent = new Intent(MainActivity.this, LyecLabs.class);
                    }
                }
            /*    else if (deviceName.equals("Ornitorenk-4!")) {
                    targetActivity = Engelsiz_zemin_kat.class.getSimpleName();
                    if (!targetActivity.equals(lastStartedActivity)) {
                        intent = new Intent(MainActivity.this, LyecLabs.class);
                    }
                }*/

                if (intent != null) {
                    startActivity(intent);
                    lastStartedActivity = targetActivity;
                    Log.d("BLE", "Started activity for device: " + deviceName);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && allPermissionsGranted(grantResults)) {
                prefs.edit().putBoolean(KEY_PERMISSIONS_REQUESTED, true).apply();
                Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                // İzinler verildiyse tarama otomatik başlamıyor, butona basmayı bekliyor
            } else {
                Toast.makeText(this, "Microphone and Bluetooth permissions are required.", Toast.LENGTH_LONG).show();
                if (shouldShowPermissionRationale()) {
                    showPermissionRationaleDialog();
                }
            }
            if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Ses kaydı izni verildi.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Ses kaydı izni reddedildi.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean allPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldShowPermissionRationale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_SCAN) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_CONNECT) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO);
        } else {
            return ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO);
        }
    }

    private void showPermissionRationaleDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Permissions Required")
                .setMessage("This app needs microphone and Bluetooth permissions to function properly. Please grant them to continue.")
                .setPositiveButton("Retry", (dialog, which) -> requestPermissionsFromUser())
                .setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(this, "Cannot proceed without permissions.", Toast.LENGTH_LONG).show())
                .setCancelable(false)
                .show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Geri dönüldüğünde taramayı yeniden başlat (eğer tarama isteniyorsa)
        if (shouldStartScanning) {
            startScanning();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        stopScanning();
    }
}