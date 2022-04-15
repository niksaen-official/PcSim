package com.niksaen.pcsim.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter;
import com.niksaen.pcsim.databinding.ActivityListviewBinding;
import com.niksaen.pcsim.save.Settings;

import java.util.HashMap;

public class TutorialActivity extends AppCompatActivity {


    public HashMap<String,String> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTranslate();
        String[] items = new String[]{
                words.get("How to build a PC"),
                words.get("Initial PC setup"),
                words.get("Making money")
        };
        String title = words.get("Game Guides");

        ActivityListviewBinding binding = ActivityListviewBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        CustomListViewAdapter adapter = new CustomListViewAdapter(this,0,items);
        adapter.TextColor = Color.parseColor("#FFFFFF");
        adapter.BackgroundColor2 = Color.parseColor("#1C1C1C");
        adapter.TextSize = 45;
        binding.listview.setAdapter(adapter);
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            Uri address = Uri.parse("https://www.google.com/");
            switch (position){
                case 0:{
                    address = Uri.parse("https://www.youtube.com/watch?v=0mg7G2-i3TI");
                    break;
                }
                case 1:{
                    address = Uri.parse("https://www.youtube.com/watch?v=DhSnZyaccVo");
                    break;
                }
                case 2:{
                    address = Uri.parse("https://www.youtube.com/watch?v=fvFNUnaCyEY");
                    break;
                }
            }
            Intent openlink = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlink);
        });

        binding.title.setText(title);
        binding.title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/pixelFont.ttf"));
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    private void getTranslate(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(this).getText("language/"+ new Settings(this).Language+".json"),typeToken.getType());
    }
}