package com.example.zimzezor;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShowVideos extends AppCompatActivity {

    private VideoView videoView;
    private DatabaseOperations databaseOperations;
    private Button playButton;
    private Button homeButton;
    private Button backButton; // Yeni field ekleyelim
    private BluetoothLeScanner bluetoothLeScanner;
    private ScanCallback scanCallback;
    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_videos);

        // MyApplication'dan BluetoothController'ı al
        MyApplication app = (MyApplication) getApplication();
        BluetoothController bluetoothController = app.getBluetoothController();

        // Taramayı durdur
        if (bluetoothController != null) {
            bluetoothController.stopScanning();
        } else {
            Toast.makeText(this, "BluetoothController is not available.", Toast.LENGTH_SHORT).show();
        }

        videoView = findViewById(R.id.videoView);
        playButton = findViewById(R.id.playButton);
        homeButton = findViewById(R.id.homeButton);
        backButton = findViewById(R.id.btnGeri);
        databaseOperations = new DatabaseOperations(this);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ShowVideos.this, MainActivity.class);
            startActivity(intent);   //ana sayfaya gidiyoz
        });

        backButton.setOnClickListener(v -> finish());  //geri dön butonu

        handleVideoPlayback(); // Video işlemleri
    }

    private void handleVideoPlayback() {
        String videoName = getIntent().getStringExtra("videoName");
        if (videoName == null) {
            showError("Video adı bulunamadı");
            return;
        }

        showVideos(videoName);
    }

    private void showVideos(String targetName) {
        List<String[]> urlList = databaseOperations.getAllUrls();
        if (urlList.isEmpty()) {
            showError("Video listesi boş");
            return;
        }

        urlList.stream()
                .filter(urlData -> urlData[2].equals(targetName))
                .findFirst()
                .ifPresentOrElse(
                        urlData -> {
                            String mediaUrl = urlData[1];
                            // Google Drive URL kontrolü eklendi
                            boolean isVideo = mediaUrl.toLowerCase().contains(".mp4") ||
                                    mediaUrl.toLowerCase().contains("drive.google.com") ||
                                    urlData[2].contains("video");

                            if (isVideo) {
                                Oynat(mediaUrl);
                            } else {
                                showError("Bu bir video dosyası değil");
                            }
                        },
                        () -> showError("Video bulunamadı")
                );
    }

    private void Oynat(String videoUrl) {
        playButton.setOnClickListener(v -> showVideo(videoUrl));
    }

    private void showVideo(String videoUrl) {
        videoView.setVisibility(View.VISIBLE);

        try {
            videoView.setVideoURI(Uri.parse(videoUrl));
            videoView.setOnPreparedListener(mp -> {
                mp.start();
                mp.setLooping(true); // Videoyu döngüye al
            });
            videoView.setOnErrorListener((mp, what, extra) -> {
                Toast.makeText(this, "Video oynatma hatası!", Toast.LENGTH_SHORT).show();
                return true;
            });
        } catch (Exception e) {
            Toast.makeText(this, "Geçersiz video URL!", Toast.LENGTH_SHORT).show();
        }
    }
    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

    }


}
