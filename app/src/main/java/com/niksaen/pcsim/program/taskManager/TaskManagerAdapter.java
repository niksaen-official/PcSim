package com.niksaen.pcsim.program.taskManager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.save.Settings;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManagerAdapter extends ArrayAdapter<Program> {
    ArrayList<Program> programs;
    Context context;
    public TaskManagerAdapter(Context context, int resource, ArrayList<Program> objects) {
        super(context, resource, objects);
        programs = objects;
        this.context = context;
        getLanguage();
    }
    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ new Settings(context).Language+".json"),typeToken.getType());
    }

    @Override
    public int getCount() {
        return programs.size();
    }

    int TextColor = Color.parseColor("#000000"),
            BackgroundColor;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.item_taskmanager,null);
        TextView
                programName = layout.findViewById(R.id.program_name),
                ramUse = layout.findViewById(R.id.ram_use),
                videoMemoryUse = layout.findViewById(R.id.video_memory_use);

        programName.setTextColor(TextColor);
        ramUse.setTextColor(TextColor);
        videoMemoryUse.setTextColor(TextColor);
        layout.setBackgroundColor(BackgroundColor);

        programName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf"));
        ramUse.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf"));
        videoMemoryUse.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf"));

        if(programs.get(position).Title.startsWith("virus.")){
            layout.setScaleY(0);
            layout.setScaleX(0);
            return layout;
        }
        programName.setText(words.get(programs.get(position).Title));
        ramUse.setText(programs.get(position).CurrentRamUse+"Mb");
        videoMemoryUse.setText(programs.get(position).CurrentVideoMemoryUse+"Mb");
        return layout;
    }
}
