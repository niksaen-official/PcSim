package com.niksaen.pcsim.program;

import android.content.Context;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;

public class GPU_Overclocking extends Program {

    ConstraintLayout layout;
    Context context;
    PcParametersSave pcParametersSave;
    MainActivity mainActivity;

    View mainWindow;
    Typeface typeface;
    int button_2_1,
            button_2_2;
    StyleSave styleSave;
    HashMap<String,String> words;

    public GPU_Overclocking(MainActivity activity){
        this.context = activity.getBaseContext();
        this.layout = activity.layout;
        this.pcParametersSave = activity.pcParametersSave;
        mainActivity = activity;
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        styleSave = new StyleSave(context);

        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_gpu_overclocking,null);
    }

    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),typeToken.getType());
    }

    private int frequency;
    private TextView parameters,title,gpu_model;
    private SeekBar seekBar;
    private Button save,gpu1,gpu2,rollUp,close,fullscreen;
    private ConstraintLayout content;

    private void initView(){
        title = mainWindow.findViewById(R.id.title);
        gpu_model = mainWindow.findViewById(R.id.gpu_model);
        parameters = mainWindow.findViewById(R.id.textView2);

        gpu1 = mainWindow.findViewById(R.id.gpu1);
        gpu2 = mainWindow.findViewById(R.id.gpu2);
        save = mainWindow.findViewById(R.id.button7);
        seekBar = mainWindow.findViewById(R.id.seekBar);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        close = mainWindow.findViewById(R.id.close);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        content = mainWindow.findViewById(R.id.content);
    }

    private void style(){
        title.setTypeface(typeface,Typeface.BOLD);
        gpu_model.setTypeface(typeface,Typeface.BOLD);
        parameters.setTypeface(typeface,Typeface.BOLD);
        gpu1.setTypeface(typeface,Typeface.BOLD);
        gpu2.setTypeface(typeface,Typeface.BOLD);
        save.setTypeface(typeface,Typeface.BOLD);

        seekBar.setMax(2000);
        title.setTextColor(styleSave.TitleColor);
        title.setText(words.get("GPU Overclocking"));
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        gpu_model.setTextColor(styleSave.TextColor);
        parameters.setTextColor(styleSave.TextColor);
        gpu1.setTextColor(styleSave.TextButtonColor);
        gpu1.setBackgroundColor(styleSave.ThemeColor2);
        gpu2.setTextColor(styleSave.TextButtonColor);
        gpu2.setBackgroundColor(styleSave.ThemeColor2);
        save.setTextColor(styleSave.TextButtonColor);
        save.setBackgroundColor(styleSave.ThemeColor2);
        save.setText(words.get("Will apply"));
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        button_2_1 = styleSave.FullScreenMode1ImageRes;
        button_2_2 = styleSave.FullScreenMode2ImageRes;
        fullscreen.setBackgroundResource(button_2_2);
        content.setBackgroundColor(styleSave.ThemeColor1);
        seekBar.setProgressDrawable(context.getDrawable(styleSave.SeekBarProgressResource));
        seekBar.setThumb(context.getDrawable(styleSave.SeekBarThumbResource));
    }

    public void openProgram(){
        this.status = 0;
        getLanguage();
        initView();style();

        int[] buttonClicks = {0};
        close.setOnClickListener(v -> {
            closeProgram();
        });
        fullscreen.setOnClickListener(v->{
            if(buttonClicks[0] == 0){
                mainWindow.setScaleX(0.6f);
                mainWindow.setScaleY(0.6f);
                PortableView view = new PortableView(mainWindow);
                v.setBackgroundResource(button_2_1);
                buttonClicks[0]=1;
            }else{
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setOnTouchListener(null);
                mainWindow.setX(0);
                mainWindow.setY(0);
                v.setBackgroundResource(button_2_2);
                buttonClicks[0]=0;
            }
        });


        if(pcParametersSave.GPU1 != null){
            gpu1.setVisibility(View.VISIBLE);
            gpu1.setOnClickListener(v->{
                gpu_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                gpu_model.setText(pcParametersSave.Gpu1);
                overclocking(pcParametersSave.GPU1,1);
            });
        }
        if (Integer.parseInt(pcParametersSave.MOBO.get("Слотов PCI")) >= 2) {
            if (pcParametersSave.GPU2 != null) {
                gpu2.setVisibility(View.VISIBLE);
                gpu2.setOnClickListener(v -> {
                    gpu_model.setVisibility(View.VISIBLE);
                    parameters.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    gpu_model.setText(pcParametersSave.Gpu2);
                    overclocking(pcParametersSave.GPU2, 2);
                });
            }
        }
        if(mainWindow.getParent() == null) {
            layout.addView(mainWindow, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        }else{
            mainWindow.setVisibility(View.VISIBLE);
        }
        mainActivity.programArrayList.add(this);
    }
    float throughput,k,temperature,temperature_coefficient,power,power_coefficient,power_fan,main;
    private void overclocking(HashMap<String,String> GPU,int slot){
        main = 0;
        frequency = Integer.parseInt(GPU.get("Частота"));
        throughput = Float.parseFloat(GPU.get( "Пропускная способность"));
        k = throughput/frequency;
        switch (GPU.get("Тип памяти")){
            case "GDDR":{power_coefficient = 1.4f*1.4f;break;}
            case "GDDR2":{power_coefficient = 1.5f*1.4f;break;}
            case "GDDR3":{power_coefficient = 1.6f*1.4f;break;}
            case "GDDR4":{power_coefficient = 1.7f*1.4f;break;}
            case "GDDR5":{power_coefficient = 1.8f*1.4f;break;}
            default: throw new IllegalStateException("Unexpected value: " + GPU.get("Тип памяти"));
        }
        switch (GPU.get("Тип охлаждения")){
            case "Радиатор":{ temperature_coefficient = 1.4f;break; }
            case "Кулер":{ temperature_coefficient = 1.15f;power_fan = 10;break; }
            default: throw new IllegalStateException("Unexpected value: " + GPU.get("Тип охлаждения"));
        }
        seekBar.setProgress(frequency);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                throughput = k*progress;
                frequency = progress;
                main = (progress /power_coefficient)/5;
                temperature = (float) main*temperature_coefficient/1.75f;
                power = (float)(main*power_coefficient)/1.6f+power_fan;
                parameters.setText(
                        words.get("Frequency")+": "+progress+"MHz"+"\n" +
                                words.get("Throughput")+": "+throughput+"Gb/s"+"\n" +
                                words.get("Temperature")+": "+temperature+"C\n" +
                                words.get("Energy consumption")+": "+power+"W");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress() < 500){
                    seekBar.setProgress(500);
                    throughput = k*500;
                    frequency = 500;
                    main = (500 /power_coefficient)/5;
                    temperature = (float) main*temperature_coefficient/1.75f;
                    power = (float)(main*power_coefficient)/1.6f+power_fan;
                    parameters.setText(
                            words.get("Frequency")+": "+500+"MHz"+"\n" +
                                    words.get("Throughput")+": "+throughput+"Gb/s"+"\n" +
                                    words.get("Temperature")+": "+temperature+"C\n" +
                                    words.get("Energy consumption")+": "+power+"W");
                }
            }
        });

        main = (frequency /power_coefficient)/5;
        temperature = (float) main*temperature_coefficient/1.75f;
        power = (float)(main*power_coefficient)/1.6f+power_fan;
        parameters.setText(
                words.get("Frequency")+": "+frequency+"MHz"+"\n" +
                        words.get("Throughput")+": "+throughput+"Gb/s"+"\n" +
                        words.get("Temperature")+": "+temperature+"C\n" +
                        words.get("Energy consumption")+": "+power+"W");

        save.setOnClickListener(v -> {
            if (slot == 1) {
                GPU.put("Частота", String.valueOf(frequency));
                GPU.put("Пропускная способность", String.valueOf(throughput));
                pcParametersSave.setGpu1(pcParametersSave.Gpu1, GPU);
                if(temperature>85 && !pcParametersSave.psuEnoughPower()){
                    pcParametersSave.setGpu1(null,null);
                    pcParametersSave.setPsu(null,null);
                }
                else if(!pcParametersSave.psuEnoughPower()){
                    pcParametersSave.setPsu(null,null);
                }
            }
            if (slot == 2) {
                GPU.put("Частота", String.valueOf(frequency));
                GPU.put("Пропускная способность", String.valueOf(throughput));
                pcParametersSave.setGpu2(pcParametersSave.Gpu2, GPU);
                if(temperature>85 && !pcParametersSave.psuEnoughPower()){
                    pcParametersSave.setGpu2(null,null);
                    pcParametersSave.setPsu(null,null);
                }
                else if(!pcParametersSave.psuEnoughPower()){
                    pcParametersSave.setPsu(null,null);
                }
            }
        });
    }

    @Override
    public void closeProgram() {
        mainWindow.setVisibility(View.GONE);
        this.status = -1;
    }
}
