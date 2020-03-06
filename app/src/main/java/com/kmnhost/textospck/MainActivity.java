package com.kmnhost.textospck;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private  static final int TTS_ENGINE_REQUEST =101;
    private TextToSpeech textToSpeech;

    private EditText TextForSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextForSpeech = (EditText)findViewById(R.id.speech_text);
    }
    public void performSpeech(View view) {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent,TTS_ENGINE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TTS_ENGINE_REQUEST && resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
        } else {
            Intent installIntent = new Intent();
            installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installIntent);
        }
    }

    @Override
    public void onInit(int status) {
        if (status==TextToSpeech.SUCCESS){
            int languageStatus = textToSpeech.setLanguage(Locale.US);
            if (languageStatus==TextToSpeech.LANG_MISSING_DATA || languageStatus==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Language is not Support..", Toast.LENGTH_SHORT).show();
            }else {
                String data = TextForSpeech.getText().toString();
                int speechStatus = textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);
                if (speechStatus==TextToSpeech.ERROR){
                    Toast.makeText(this, "Error while Speech..", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this, "Text to speech engine failed..", Toast.LENGTH_SHORT).show();
        }
    }

}
