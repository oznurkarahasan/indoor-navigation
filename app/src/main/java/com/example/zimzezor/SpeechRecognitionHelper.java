package com.example.zimzezor;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;
import java.util.ArrayList;

public class SpeechRecognitionHelper {

    private static final int SPEECH_REQUEST_CODE = 100;
    private Activity activity;
    private OnCommandListener commandListener;

    public SpeechRecognitionHelper(Activity activity, OnCommandListener listener) {
        this.activity = activity;
        this.commandListener = listener;
    }

    public void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "tr-TR");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Komutunu söyle...");

        try {
            activity.startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(activity, "Ses tanıma başlatılamadı.", Toast.LENGTH_SHORT).show();
        }
    }

    // Bu metodun tanımlı olduğundan emin olun
    public void handleVoiceResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String komut = result.get(0);
            if (commandListener != null) {
                commandListener.onCommandReceived(komut);
            }
        }
    }

    public interface OnCommandListener {
        void onCommandReceived(String command);
    }
}