package com.example.zimzezor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Yol_yardim extends AppCompatActivity {
    private TextView textView;
    private Handler handler = new Handler(Looper.getMainLooper());

    private static final class Constants {
        static final String EXTRA_PHOTO_NAME = "photoName";
        static final String EXTRA_VIDEO_NAME = "videoName";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yol_yardim);

        // MyApplication'dan BluetoothController'ı al
        MyApplication app = (MyApplication) getApplication();
        BluetoothController bluetoothController = app.getBluetoothController();

        // Taramayı durdur
        if (bluetoothController != null) {
            bluetoothController.stopScanning();
        } else {
            Toast.makeText(this, "BluetoothController is not available.", Toast.LENGTH_SHORT).show();
        }

        textView = findViewById(R.id.textView6);
        textView.setText("Ne yapmak istiyorsunuz?");

        setupClickListeners(bluetoothController);
    }

    private void setupClickListeners(BluetoothController bluetoothController) {
        Button buttongidis = findViewById(R.id.btnYol);
        Button buttonvaris = findViewById(R.id.btnVaris);
        Button backButton = findViewById(R.id.btnGeri);
        Button mainButton = findViewById(R.id.btnMain);

        buttongidis.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShowVideos.class);
            intent.putExtra(Constants.EXTRA_VIDEO_NAME, getIntent().getStringExtra(Constants.EXTRA_VIDEO_NAME));
            startActivity(intent);
        });

        buttonvaris.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShowPhotos.class);
            intent.putExtra(Constants.EXTRA_PHOTO_NAME, getIntent().getStringExtra(Constants.EXTRA_PHOTO_NAME));
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            if (bluetoothController != null) {
                bluetoothController.startScanning();
            } else {
                Toast.makeText(this, "BluetoothController is not available.", Toast.LENGTH_SHORT).show();
            }
            finish();
        });

        mainButton.setOnClickListener(view -> {
            if (bluetoothController != null) {
                bluetoothController.startScanning();
            } else {
                Toast.makeText(this, "BluetoothController is not available.", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}