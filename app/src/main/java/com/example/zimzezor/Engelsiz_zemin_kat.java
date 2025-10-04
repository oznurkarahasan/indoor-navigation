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
import android.util.Log;
import android.view.View;
import android.os.Handler;
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

import com.squareup.picasso.Picasso;

public class Engelsiz_zemin_kat extends AppCompatActivity implements SpeechRecognitionHelper.OnCommandListener{
    private static final int REQUEST_CODE_PERMISSIONS = 1;
    private ImageView imageView;
    private DatabaseOperations databaseOperations;
    private TextView textView;
    public static String lyeclabsgiris = "lyeclabsgiris";
    public static String zeminDanisma = "zeminDanisma";
    public static String zeminSesliKutup = "zeminSesliKutup";
    public static String zeminMutfak = "zeminMutfak";
    public static String zeminWC = "zeminWC";
    public static String kat1foto = "kat1";
    public static String zeminAsansor = "zeminAsansor";
    public static String zeminBekleme = "zeminBekleme";
    public static String zeminkatZon_bekleme = "zeminkatZon_bekleme";
    public static String zeminZonlyecvideo = "zeminZonlyecvideo";
    public static String zeminkatZon_sesli = "zeminkatZon_sesli";
    public static String zeminkatZon_mutfak = "zeminkatZon_mutfak";
    public static String zeminkatZon_wc = "zeminkatZon_wc";
    public static String zeminkatZon_asansor = "zeminkatZon_asansor";
    public static String zeminkatZon_danisma = "zeminkatZon_danisma" ;
    public static String zeminkatZon_kat1 = "zeminkatZon_kat1";
    public static String zemin_hocavideo = "zemin_hocavideo";
    public static String tolunaydemirci = "tolunaydemirci";
    public static String kat1Revir = "kat1Revir";
    public static String kat1Resim = "kat1Resim";
    public static String kat1Muzik = "kat1Muzik";
    public static String kat1idari = "kat1idari";
    public static String kat1bilgisayar = "kat1bilgisayar";
    public static String kat1aktivite = "kat1aktivite";
    public static String zemin_resimvideo = "zemin_resimvideo";
    public static String zemin_idarivideo = "zemin_idarivideo";
    public static String zemin_muzikvideo = "zemin_muzikvideo";
    public static String zemin_revirvideo = "zemin_revirvideo";
    public static String zemin_sporvideo = "zemin_sporvideo";
    public static String zemin_bilgisayarvideo = "zemin_bilgisayarvideo";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognitionHelper voiceHandler;

    private Button buttonHarita;
    private Button LyecLabs;
    private Button btnHoca3;
    private Button btnKat1;
    private Button btnResim3;
    private Button btnIdari2;
    private Button btnMuzik2;
    private Button btnBekleme;
    private Button btnSpor2;
    private Button btnBilgisayar2;
    private Button btnDanisma;
    private Button btnRevir2;
    private Button btnSesliKutup;
    private Button btnMutfak;
    private Button btnWc;
    private Button btnAsansor;
    private Button MatBtn2;

    private BluetoothLeScanner bluetoothLeScanner;
    private ScanCallback scanCallback;
    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_engelsiz_zemin_kat);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView = findViewById(R.id.textView);
        textView.setText("Şuan zemin Kattasınız. Lütfen gitmek istediğiniz yeri seçiniz ya da kat planını görmek için haritayı açınız. Mikrofona tıklayarak gitmek istediğiniz yeri söyleyebilirsiniz. Yolunuz aydınlık olsun.");


        buttonHarita = findViewById(R.id.buttonHarita);
        LyecLabs = findViewById(R.id.LyecLabs);
        btnHoca3 = findViewById(R.id.btnHoca3);
        btnKat1 = findViewById(R.id.btnKat1);
        btnResim3 = findViewById(R.id.btnResim3);
        btnIdari2 = findViewById(R.id.btnIdari2);
        btnMuzik2 = findViewById(R.id.btnMuzik2);
        btnBekleme = findViewById(R.id.btnBekleme);
        btnSpor2 = findViewById(R.id.btnSpor2);
        btnBilgisayar2 = findViewById(R.id.btnBilgisayar2);
        btnDanisma = findViewById(R.id.btnDanisma);
        btnRevir2 = findViewById(R.id.btnRevir2);
        btnSesliKutup = findViewById(R.id.btnSesliKutup);
        btnMutfak = findViewById(R.id.btnMutfak);
        btnWc = findViewById(R.id.btnWc);
        btnAsansor = findViewById(R.id.btnAsansor);
        MatBtn2 = findViewById(R.id.MatBtn2);


        Button button = (Button) findViewById(R.id.MatBtn2);
//geri dön
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Engelsiz_zemin_kat.this, MainActivity.class);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonMap = (Button) findViewById(R.id.buttonHarita);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Engelsiz_zemin_kat.this, ShowPhotos.class);
                intent.putExtra("photoName", "engelsizzeminkat");
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button lyeclabs = (Button) findViewById(R.id.LyecLabs);

        lyeclabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", lyeclabsgiris); // "lyeclabsgiris" gönderilecek
                intent.putExtra("videoName", zeminZonlyecvideo); // "zeminlyecvideo" gönderilecek
                startActivity(intent);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button kat1 = (Button) findViewById(R.id.btnKat1);

        kat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1foto);
                intent.putExtra("videoName", zeminkatZon_kat1);
                startActivity(intent);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button danisma = (Button) findViewById(R.id.btnDanisma);

        danisma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminDanisma);
                intent.putExtra("videoName", zeminkatZon_danisma);
                startActivity(intent);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button bekleme = (Button) findViewById(R.id.btnBekleme);

        bekleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminBekleme);
                intent.putExtra("videoName", zeminkatZon_bekleme);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button asansor = (Button) findViewById(R.id.btnAsansor);

        asansor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminAsansor);
                intent.putExtra("videoName", zeminkatZon_asansor);
                startActivity(intent);
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button seslikutup = (Button) findViewById(R.id.btnSesliKutup);

        seslikutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminSesliKutup);
                intent.putExtra("videoName", zeminkatZon_sesli);
                startActivity(intent);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button mutfak = (Button) findViewById(R.id.btnMutfak);

        mutfak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminMutfak);
                intent.putExtra("videoName", zeminkatZon_mutfak);
                startActivity(intent);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button wc = (Button) findViewById(R.id.btnWc);

        wc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", zeminWC);
                intent.putExtra("videoName", zeminkatZon_wc);
                startActivity(intent);
            }
        });

        ///////////////////////////////////////////////////////////////////
        Button buttonHoca = (Button) findViewById(R.id.btnHoca3);

        buttonHoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", tolunaydemirci);
                intent.putExtra("videoName", zemin_hocavideo);
                startActivity(intent);
            }
        });
        Button buttonResim = (Button) findViewById(R.id.btnResim3);

        buttonResim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Resim);
                intent.putExtra("videoName", zemin_resimvideo);
                startActivity(intent);
            }
        });
        Button buttonİdari = (Button) findViewById(R.id.btnİdari2);

        buttonİdari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1idari);
                intent.putExtra("videoName", zemin_idarivideo);
                startActivity(intent);
            }
        });
        Button buttonMuzik = (Button) findViewById(R.id.btnMuzik2);

        buttonMuzik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Muzik);
                intent.putExtra("videoName", zemin_muzikvideo);
                startActivity(intent);
            }
        });
        Button buttonRevir = (Button) findViewById(R.id.btnRevir2);

        buttonRevir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1Revir);
                intent.putExtra("videoName", zemin_revirvideo);
                startActivity(intent);
            }
        });
        Button buttonSpor = (Button) findViewById(R.id.btnSpor2);

        buttonSpor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1aktivite);
                intent.putExtra("videoName", zemin_sporvideo);
                startActivity(intent);
            }
        });
        Button buttonBilgisayar = (Button) findViewById(R.id.btnBilgisayar2);

        buttonBilgisayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Engelsiz_zemin_kat.this, Yol_yardim.class);
                intent.putExtra("photoName", kat1bilgisayar);
                intent.putExtra("videoName", zemin_bilgisayarvideo);
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
        else if (command.toLowerCase().contains("Lyec Labs")) {
            if (LyecLabs != null) {LyecLabs.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("tolunay demirci")) {
            if (btnHoca3 != null) {btnHoca3.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("kat 1")) {
            if (btnKat1 != null) {btnKat1.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("resim sınıfı")) {
            if (btnResim3 != null) {btnResim3.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("idari büro")) {
            if (btnIdari2 != null) {btnIdari2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("Müzik Sınıfı")) {
            if (btnMuzik2 != null) {btnMuzik2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("bekleme salonu")) {
            if (btnBekleme != null) {btnBekleme.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("aktivite ve spor odası")) {
            if (btnSpor2 != null) {btnSpor2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("bilgisayar ve robotik")) {
            if (btnBilgisayar2 != null) {btnBilgisayar2.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("danışma")) {
            if (btnDanisma != null) {btnDanisma.performClick();} else {
                Log.d(TAG, "Buton tanımlı değil.: ");
            }}
        else if (command.toLowerCase().contains("revir")) {
            if (btnRevir2 != null) {btnRevir2.performClick();} else {
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