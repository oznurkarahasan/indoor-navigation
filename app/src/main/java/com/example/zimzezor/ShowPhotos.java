package com.example.zimzezor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ShowPhotos extends AppCompatActivity {
    private ImageView imageView;
    private DatabaseOperations databaseOperations;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photos);

        // MyApplication'dan BluetoothController'ı al
        MyApplication app = (MyApplication) getApplication();
        BluetoothController bluetoothController = app.getBluetoothController();

        // Taramayı durdur
        if (bluetoothController != null) {
            bluetoothController.stopScanning();
        } else {
            Toast.makeText(this, "BluetoothController is not available.", Toast.LENGTH_SHORT).show();
        }

        databaseOperations = new DatabaseOperations(this);
        imageView = findViewById(R.id.imageView2);
        Button homeButton = findViewById(R.id.btnMain);
        Button backButton = findViewById(R.id.btnGeri);

        homeButton.setOnClickListener(view -> {
            if (bluetoothController != null) {
                bluetoothController.startScanning();
            } else {
                Toast.makeText(this, "BluetoothController is not available.", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(ShowPhotos.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        String photoName = getIntent().getStringExtra("photoName");
        String imageUrl = databaseOperations.getUrlByName(photoName);

        if (photoName == null || imageUrl == null) {
            showError();
            finish();
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.ornitorenk)
                    .placeholder(R.drawable.loading)
                    .into(imageView);
        }

        backButton.setOnClickListener(v -> {
            if (bluetoothController != null) {
                bluetoothController.stopScanning();
            } else {
                Toast.makeText(this, "BluetoothController is not available.", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    private void showError() {
        Toast.makeText(this, "URL bulunamadı veya geçersiz!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}