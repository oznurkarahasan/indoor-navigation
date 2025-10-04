package com.example.zimzezor;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Engelsiz_Kat_1 extends AppCompatActivity implements SpeechRecognitionHelper.OnCommandListener {
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private DatabaseOperations databaseOperations;
    private TextView textView;
    public static String lyeclabsgirisfoto = "lyeclabsgiris";
    public static String kat1_lyecvideo = "kat1_lyecvideo";
    public static String zeminmerdivenfoto = "zeminmerdivenfoto";
    public static String kat1_zeminvideo = "kat1_zeminvideo";
    public static String kat1WC = "kat1WC";
    public static String kat1WCvideo = "kat1_wc";
    public static String kat1Revir = "kat1Revir";
    public static String kat1Revirvideo = "kat1_revir";
    public static String kat1Resim = "kat1Resim";
    public static String kat1Resimvideo = "kat1_resim";
    public static String kat1Muzik = "kat1Muzik";
    public static String kat1Muzikvideo = "kat1_muzik";
    public static String kat1idari = "kat1idari";
    public static String kat1idarivideo = "kat1_idari";
    public static String kat1asansor = "kat1asansor";
    public static String kat1asansorvideo = "kat1_asansor";
    public static String kat1bilgisayar = "kat1bilgisayar";
    public static String kat1bilgisayarvideo = "kat1_bilgisayar";
    public static String kat1aktivite = "kat1aktivite";
    public static String kat1aktivitevideo = "kat1_spor";
    public static String kat1_hoca_photo = "tolunaydemirci";
    public static String kat1_hoca_video = "kat1_tolunayvideo";
    public static String zeminDanisma = "zeminDanisma";
    public static String zeminSesliKutup = "zeminSesliKutup";
    public static String zeminMutfak = "zeminMutfak";
    public static String zeminBekleme = "zeminBekleme";
    public static String kat1_beklemevideo = "kat1_beklemevideo";
    public static String kat1_danismavideo = "kat1_danismavideo";
    public static String kat1_mutfakvideo = "kat1_mutfakvideo";
    public static String kat1_seslivideo = "kat1_seslivideo";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognitionHelper voiceHandler;

    private Button buttonHarita;
    private Button btnZemin;
    private Button btnHoca2;
    private Button btnLyec;
    private Button btnResim;
    private Button btnIdari;
    private Button btnMuzik;
    private Button btnBekleme2;
    private Button btnSpor;
    private Button btnBilgisayar;
    private Button btnDanisma;
    private Button btnRevir;
    private Button btnSesliKutup2;
    private Button btnMutfak2;
    private Button btnWc;
    private Button btnAsansor;
    private Button MatBtn2;

    private BluetoothLeScanner bluetoothLeScanner;
    private ScanCallback scanCallback;
    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_engelsiz_kat1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        textView.setText("Şuan 1. Kattasınız. Lütfen gitmek istediğiniz yeri seçiniz ya da kat planını görmek için haritayı açınız. Mikrofona tıklayarak gitmek istediğiniz yeri söyleyebilirsiniz. Yolunuz aydınlık olsun.");


        buttonHarita = findViewById(R.id.buttonHarita);
        btnZemin = findViewById(R.id.btnZemin);
        btnHoca2 = findViewById(R.id.btnHoca2);
        btnLyec = findViewById(R.id.btnLyec);
        btnResim = findViewById(R.id.btnResim);
        btnIdari = findViewById(R.id.btnIdari);
        btnMuzik = findViewById(R.id.btnMuzik);
        btnBekleme2 = findViewById(R.id.btnBekleme2);
        btnSpor = findViewById(R.id.btnSpor);
        btnBilgisayar = findViewById(R.id.btnBilgisayar);
        btnDanisma = findViewById(R.id.btnDanisma);
        btnRevir = findViewById(R.id.btnRevir);
        btnSesliKutup2 = findViewById(R.id.btnSesliKutup2);
        btnMutfak2 = findViewById(R.id.btnMutfak2);
        btnWc = findViewById(R.id.btnWc);
        btnAsansor = findViewById(R.id.btnAsansor);
        MatBtn2 = findViewById(R.id.MatBtn2);

        Button button = (Button) findViewById(R.id.MatBtn2);
//geri dönme butonu
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Engelsiz_Kat_1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonMap = (Button) findViewById(R.id.buttonHarita);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Engelsiz_Kat_1.this, ShowPhotos.class);
                intent.putExtra("photoName", "kat1map");
                startActivity(intent);
            }
        });

        Button buttonWC = (Button) findViewById(R.id.btnWc);

        buttonWC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1WC );
                intent.putExtra("videoName", kat1WCvideo);
                startActivity(intent);
            }
        });
        Button buttonHoca = (Button) findViewById(R.id.btnHoca2);

        buttonHoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1_hoca_photo );
                intent.putExtra("videoName", kat1_hoca_video);
                startActivity(intent);
            }
        });
        Button buttonLyec = (Button) findViewById(R.id.btnLyec);

        buttonLyec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", lyeclabsgirisfoto ); // "lyeclabsgiris" gönderilecek
                intent.putExtra("videoName", kat1_lyecvideo); // "kat1_lyecvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonZemin = (Button) findViewById(R.id.btnZemin);

        buttonZemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminmerdivenfoto );
                intent.putExtra("videoName", kat1_zeminvideo);
                startActivity(intent);
            }
        });
        Button buttonAktivite = (Button) findViewById(R.id.btnSpor);

        buttonAktivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1aktivite );
                intent.putExtra("videoName", kat1aktivitevideo);
                startActivity(intent);
            }
        });
        Button buttonRevir = (Button) findViewById(R.id.btnRevir);

        buttonRevir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Revir );
                intent.putExtra("videoName", kat1Revirvideo);
                startActivity(intent);
            }
        });
        Button buttonMuzik = (Button) findViewById(R.id.btnMuzik);

        buttonMuzik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Muzik );
                intent.putExtra("videoName", kat1Muzikvideo);
                startActivity(intent);
            }
        });
        Button buttonResim = (Button) findViewById(R.id.btnResim);

        buttonResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Resim );
                intent.putExtra("videoName", kat1Resimvideo);
                startActivity(intent);
            }
        });
        Button buttonIdari = (Button) findViewById(R.id.btnIdari);

        buttonIdari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1idari );
                intent.putExtra("videoName", kat1idarivideo);
                startActivity(intent);
            }
        });
        Button buttonAsansor = (Button) findViewById(R.id.btnAsansor);

        buttonAsansor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1asansor );
                intent.putExtra("videoName", kat1asansorvideo);
                startActivity(intent);
            }
        });
        Button buttonBilgisayar = (Button) findViewById(R.id.btnBilgisayar);

        buttonBilgisayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1bilgisayar );
                intent.putExtra("videoName", kat1bilgisayarvideo);
                startActivity(intent);
            }
        });
        Button buttonDanisma = (Button) findViewById(R.id.btnDanisma);

        buttonDanisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminDanisma );
                intent.putExtra("videoName", kat1_danismavideo);
                startActivity(intent);
            }
        });
        Button buttonBekleme = (Button) findViewById(R.id.btnBekleme2);

        buttonBekleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminBekleme );
                intent.putExtra("videoName", kat1_beklemevideo);
                startActivity(intent);
            }
        });
        Button buttonSesli = (Button) findViewById(R.id.btnSesliKutup2);

        buttonSesli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminSesliKutup );
                intent.putExtra("videoName", kat1_seslivideo);
                startActivity(intent);
            }
        });
        Button buttonMutfak = (Button) findViewById(R.id.btnMutfak2);

        buttonMutfak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_Kat_1.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminMutfak );
                intent.putExtra("videoName", kat1_mutfakvideo);
                startActivity(intent);
            }
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
        if (command.toLowerCase().contains("harita")) {
            if (buttonHarita != null) {buttonHarita.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");}}
        else if (command.toLowerCase().contains("zemin kat")) {
            if (btnZemin != null) {btnZemin.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("tolunay demirci")) {
            if (btnHoca2 != null) {btnHoca2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("kat 1")) {
            if (btnLyec != null) {btnLyec.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("resim sınıfı")) {
            if (btnResim != null) {btnResim.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("idari büro")) {
            if (btnIdari != null) {btnIdari.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("Müzik Sınıfı")) {
            if (btnMuzik != null) {btnMuzik.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("bekleme salonu")) {
            if (btnBekleme2 != null) {btnBekleme2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("aktivite ve spor odası")) {
            if (btnSpor != null) {btnSpor.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("bilgisayar ve robotik")) {
            if (btnBilgisayar != null) {btnBilgisayar.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("danışma")) {
            if (btnDanisma != null) {btnDanisma.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("revir")) {
            if (btnRevir != null) {btnRevir.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("sesli kütüphane")) {
            if (btnSesliKutup2 != null) {btnSesliKutup2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("mutfak")) {
            if (btnMutfak2 != null) {btnMutfak2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("kat 1 wc")) {
            if (btnWc != null) {btnWc.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("asansör")) {
            if (btnAsansor != null) {btnAsansor.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("geri dön")) {
            if (MatBtn2 != null) {MatBtn2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gerekli izinler verilmedi. Uygulama düzgün çalışmayabilir.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Toast.makeText(this, "Tüm izinler verildi.", Toast.LENGTH_SHORT).show();
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