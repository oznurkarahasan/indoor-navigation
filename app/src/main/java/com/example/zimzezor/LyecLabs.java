package com.example.zimzezor;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LyecLabs extends AppCompatActivity implements SpeechRecognitionHelper.OnCommandListener {
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private ImageView imageView;
    private DatabaseOperations databaseOperations;
    private TextView textView;
    public static String zemingiris = "zemingiris";
    public static String lyeczeminZonvideo = "lyeczeminZonvideo";
    public static String kat1WC = "kat1WC";
    public static String kat1Revir = "kat1Revir";
    public static String kat1Resim = "kat1Resim";
    public static String kat1Muzik = "kat1Muzik";
    public static String kat1idari = "kat1idari";
    public static String kat1asansor = "kat1asansor";
    public static String kat1bilgisayar = "kat1bilgisayar";
    public static String kat1aktivite = "kat1aktivite";
    public static String zeminDanisma = "zeminDanisma";
    public static String zeminSesliKutup = "zeminSesliKutup";
    public static String zeminMutfak = "zeminMutfak";
    public static String zeminBekleme = "zeminBekleme";
    public static String tolunaydemirci = "tolunaydemirci";
    public static String lyec_hocavideo = "lyec_hocavideo";
    public static String lyec_kat1video = "lyec_kat1video";
    public static String kat1foto = "kat1";
    public static String lyec_idarivideo = "lyec_idarivideo";
    public static String lyec_danismavideo = "lyec_danismavideo";
    public static String lyec_beklemevideo = "lyec_beklemevideo";
    public static String lyec_bilgisayarvideo = "lyec_bilgisayarvideo";
    public static String lyec_asansorvideo = "lyec_asansorvideo";
    public static String lyec_sporvideo = "lyec_sporvideo";
    public static String lyec_seslivideo = "lyec_seslivideo";
    public static String lyec_revirvideo = "lyec_revirvideo";
    public static String lyec_mutfakvideo = "lyec_mutfakvideo";
    public static String lyec_resimvideo = "lyec_resimvideo";
    public static String lyec_muzikvideo = "lyec_muzikvideo";
    public static String lyec_kat1WCvideo = "lyec_kat1WCvideo";

    private Button buttonHarita;
    private Button zeminKat;
    private Button btnHoca;
    private Button btnKat1;
    private Button btnResim2;
    private Button btnIdari2;
    private Button btnMuzik;
    private Button btnBekleme;
    private Button btnSpor;
    private Button btnBilgisayar;
    private Button btnDanisma;
    private Button btnRevir;
    private Button btnSesliKutup;
    private Button btnMutfak;
    private Button btnkat1wc;
    private Button btnAsansor;
    private Button MapBtn;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognitionHelper voiceHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lyec_labs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonHarita = findViewById(R.id.buttonHarita);
        zeminKat = findViewById(R.id.zeminKat);
        btnHoca = findViewById(R.id.btnHoca);
        btnKat1 = findViewById(R.id.btnKat1);
        btnResim2 = findViewById(R.id.btnResim2);
        btnIdari2 = findViewById(R.id.btnIdari2);
        btnMuzik = findViewById(R.id.btnMuzik);
        btnBekleme = findViewById(R.id.btnBekleme);
        btnSpor = findViewById(R.id.btnSpor);
        btnBilgisayar = findViewById(R.id.btnBilgisayar);
        btnDanisma = findViewById(R.id.btnDanisma);
        btnRevir = findViewById(R.id.btnRevir);
        btnSesliKutup = findViewById(R.id.btnSesliKutup);
        btnMutfak = findViewById(R.id.btnMutfak);
        btnkat1wc = findViewById(R.id.btnkat1wc);
        btnAsansor = findViewById(R.id.btnAsansor);
        MapBtn = findViewById(R.id.MapBtn);


        textView = findViewById(R.id.textView);
        textView.setText("Şuan 2. Kattasınız. Lütfen gitmek istediğiniz yeri seçiniz ya da kat planını görmek için haritayı açınız. Mikrofona tıklayarak gitmek istediğiniz yeri söyleyebilirsiniz. Yolunuz aydınlık olsun.");

        Button button = (Button) findViewById(R.id.MapBtn);
//geri dön
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LyecLabs.this, MainActivity.class);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonMap = (Button) findViewById(R.id.buttonHarita);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LyecLabs.this, ShowPhotos.class);
                intent.putExtra("photoName", "lyeclabs");
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button zeminkat = (Button) findViewById(R.id.zeminKat);

        zeminkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", zemingiris); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyeczeminZonvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        /////////////////////////////////
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button revir = (Button) findViewById(R.id.btnRevir);

        revir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Revir); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_revirvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonKat1 = (Button) findViewById(R.id.btnKat1);

        buttonKat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1foto); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_kat1video); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonHoca = (Button) findViewById(R.id.btnHoca);

        buttonHoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", tolunaydemirci); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_hocavideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonResim = (Button) findViewById(R.id.btnResim2);

        buttonResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Resim); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_resimvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonMuzik = (Button) findViewById(R.id.btnMuzik);

        buttonMuzik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Muzik); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_muzikvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonBekleme = (Button) findViewById(R.id.btnBekleme);

        buttonBekleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminBekleme); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_beklemevideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonSpor = (Button) findViewById(R.id.btnSpor);

        buttonSpor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1aktivite); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_sporvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonBilgisayar = (Button) findViewById(R.id.btnBilgisayar);

        buttonBilgisayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1bilgisayar); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_bilgisayarvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonDanisma = (Button) findViewById(R.id.btnDanisma);

        buttonDanisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminDanisma); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_danismavideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonSesli = (Button) findViewById(R.id.btnSesliKutup);

        buttonSesli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminSesliKutup); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_seslivideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonMutfak = (Button) findViewById(R.id.btnMutfak);

        buttonMutfak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminMutfak); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_mutfakvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonKat1WC = (Button) findViewById(R.id.btnkat1wc);

        buttonKat1WC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1WC); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_kat1WCvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        Button buttonAsansor = (Button) findViewById(R.id.btnAsansor);

        buttonAsansor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1asansor); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_asansorvideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonIdari = (Button) findViewById(R.id.btnIdari2);

        buttonIdari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LyecLabs.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1idari); // "zeminkat" gönderilecek
                intent.putExtra("videoName", lyec_idarivideo); // "lyeczeminvideo" gönderilecek
                startActivity(intent);
            }
        });

        // VoiceCommandHandler'ı başlat
        voiceHandler = new SpeechRecognitionHelper(this, this);

        // Ses kaydı izni kontrolü
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
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
            if (zeminKat != null) {zeminKat.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("tolunay demirci")) {
            if (btnHoca != null) {btnHoca.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("kat 1")) {
            if (btnKat1 != null) {btnKat1.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("resim sınıfı")) {
            if (btnResim2 != null) {btnResim2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("idari büro")) {
            if (btnIdari2 != null) {btnIdari2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("Müzik Sınıfı")) {
            if (btnMuzik != null) {btnMuzik.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("bekleme salonu")) {
            if (btnBekleme != null) {btnBekleme.performClick();} else {
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
            if (btnSesliKutup != null) {btnSesliKutup.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("mutfak")) {
            if (btnMutfak != null) {btnMutfak.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("kat 1 wc")) {
            if (btnkat1wc != null) {btnkat1wc.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("asansör")) {
            if (btnAsansor != null) {btnAsansor.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
            else if (command.toLowerCase().contains("geri dön")) {
            if (MapBtn != null) {MapBtn.performClick();} else {
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
