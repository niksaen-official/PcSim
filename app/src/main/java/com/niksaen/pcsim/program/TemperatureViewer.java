package com.niksaen.pcsim.program;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.CustomListViewAdapter;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.ArrayList;
import java.util.HashMap;

public class TemperatureViewer extends Program {
    Context context;
    PcParametersSave pcParametersSave;
    ConstraintLayout layout;
    MainActivity mainActivity;

    Typeface font;
    View mainWindow;
    StyleSave styleSave;

    public TemperatureViewer(MainActivity activity){
        super(activity);
        this.Title = "Temperature Viewer";
        this.context = activity.getBaseContext();
        this.pcParametersSave = activity.pcParametersSave;
        this.layout = activity.layout;
        mainActivity = activity;

        font = Typeface.createFromAsset(context.getAssets(),"fonts/pixelFont.ttf");
        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_temperature_viewer,null);
        styleSave = new StyleSave(context);
        getLanguage();
    }

    // get language
    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),typeToken.getType());
    }

    private TextView title;
    private Button close,fullscreen,rollUp;
    private ListView temperatureView;
    private void initView(){
        title = mainWindow.findViewById(R.id.title);
        close = mainWindow.findViewById(R.id.close);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        temperatureView = mainWindow.findViewById(R.id.main);
    }

    private void styleView(){
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        title.setTextColor(styleSave.TitleColor);
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        temperatureView.setBackgroundColor(styleSave.ThemeColor1);

        title.setTypeface(font,Typeface.BOLD);
        title.setText(words.get("Temperature Viewer"));
        temperatureView.setAdapter(adapter);
    }

    CustomListViewAdapter adapter;
    private void initAdapter(){
        /* получение температуры железа*/
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(words.get("CPU") +": "+pcParametersSave.Cpu +"\n"+
                words.get("Temperature") +": "+pcParametersSave.currentCpuTemperature()+"C");
        if(pcParametersSave.RAM1 != null){
            arrayList.add(words.get("RAM") +"("+words.get("Slot")+" 1) : "+pcParametersSave.Ram1
                    +"\n"+ words.get("Temperature") +": "+pcParametersSave.currentRamTemperature(pcParametersSave.RAM1)+"C");
        }
        if(pcParametersSave.RAM2 != null){
            arrayList.add(words.get("RAM") +"("+words.get("Slot")+" 2) : "+pcParametersSave.Ram2
                    +"\n"+ words.get("Temperature") +": "+pcParametersSave.currentRamTemperature(pcParametersSave.RAM2)+"C");
        }
        if(pcParametersSave.RAM3 != null){
            arrayList.add(words.get("RAM") +"("+words.get("Slot")+" 3) : "+pcParametersSave.Ram3
                    +"\n"+ words.get("Temperature") +": "+pcParametersSave.currentRamTemperature(pcParametersSave.RAM3)+"C");
        }
        if(pcParametersSave.RAM4 != null){
            arrayList.add(words.get("RAM") +"("+words.get("Slot")+" 4) : "+pcParametersSave.Ram4
                    +"\n"+ words.get("Temperature") +": "+pcParametersSave.currentRamTemperature(pcParametersSave.RAM4)+"C");
        }
        if(pcParametersSave.GPU1 != null){
            arrayList.add(words.get("Graphics card") +" : "+pcParametersSave.Gpu1
                    +"\n"+ words.get("Temperature") +": "+pcParametersSave.currentGpuTemperature(pcParametersSave.GPU1)+"C");
        }
        if(pcParametersSave.GPU2 != null){
            arrayList.add(words.get("Graphics card") +"("+words.get("Slot")+" 2) : "+pcParametersSave.Gpu2
                    +"\n"+ words.get("Temperature") +": "+pcParametersSave.currentGpuTemperature(pcParametersSave.GPU2)+"C");
        }

        adapter = new CustomListViewAdapter(context,0,arrayList);
        adapter.BackgroundColor1 = styleSave.ThemeColor1;
        adapter.BackgroundColor2 = styleSave.ThemeColor1;
        adapter.TextColor = styleSave.TextColor;
    }

    public void openProgram(){
        if(status == -1) {
            super.openProgram();
            getLanguage();
            initAdapter();
            initView();
            styleView();

            close.setOnClickListener(v -> closeProgram(1));
            int[] buttonClicks = {0};
            fullscreen.setOnClickListener(v -> {
                if (buttonClicks[0] == 0) {
                    mainWindow.setScaleX(0.6f);
                    mainWindow.setScaleY(0.6f);
                    PortableView view = new PortableView(mainWindow);
                    v.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                    buttonClicks[0] = 1;
                } else {
                    mainWindow.setScaleX(1);
                    mainWindow.setScaleY(1);
                    mainWindow.setOnTouchListener(null);
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    v.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                    buttonClicks[0] = 0;
                }
            });
            if (mainWindow.getParent() == null) {
                layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            } else {
                mainWindow.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void closeProgram(int mode){
        super.closeProgram(mode);
        mainWindow.setVisibility(View.GONE);
    }
}

