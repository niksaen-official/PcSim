package com.niksaen.pcsim.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.niksaen.pcsim.databinding.LoadingActivityBinding;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.Settings;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    int progress = 0;
    LoadingActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoadingActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //style
        binding.progressText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf"));
        if(new Settings(this).Language.equals("")){
            Language language = new Language(this);
            language.saveClickListener = v -> {
                new Settings(LoadingActivity.this).setLanguage(language.getLangCode());
                language.dialog.dismiss();
                loadingAnimation();
            };
            language.ChangeLanguage();
        }else {
            loadingAnimation();
        }
    }

    void loadingAnimation(){
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    progress++;
                    if(progress == 101){
                        timer.cancel();
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        binding.progressText.setText(progress+"%");
                        binding.progressBar2.setProgress(progress);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,50);
    }
}