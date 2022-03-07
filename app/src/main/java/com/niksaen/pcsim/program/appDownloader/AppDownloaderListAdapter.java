package com.niksaen.pcsim.program.appDownloader;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.ProgramListAndData;
import com.niksaen.pcsim.save.Settings;

import java.util.HashMap;

public class AppDownloaderListAdapter extends ArrayAdapter<String> {
    public AppDownloaderListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        getLanguage();
    }

    public HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(getContext()).getText("language/"+ new Settings(getContext()).Language+".json"),typeToken.getType());
    }

    @Override
    public int getCount() {
        return ProgramListAndData.FreeAppList.length;
    }

    public int BackgroundColor = Color.parseColor("#ffffff");
    public int TextColor = Color.parseColor("#000000");
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View main = LayoutInflater.from(getContext()).inflate(R.layout.item_start_menu,null);
        TextView textView = main.findViewById(R.id.app_name);
        textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/pixelFont.ttf"));
        textView.setTextColor(TextColor);
        textView.setBackgroundColor(BackgroundColor);
        textView.setText(words.get(ProgramListAndData.FreeAppList[position]));
        ImageView image = main.findViewById(R.id.app_icon);
        image.setImageResource(ProgramListAndData.programIcon.get(ProgramListAndData.FreeAppList[position]));
        return main;
    }
}
