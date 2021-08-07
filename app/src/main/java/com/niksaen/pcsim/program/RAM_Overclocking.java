package com.niksaen.pcsim.program;

import android.annotation.SuppressLint;
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
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;

public class RAM_Overclocking {

    PcParametersSave pcParametersSave;
    ConstraintLayout layout;
    Context context;

    View mainWindow;
    Typeface typeface;
    int button_2_1 = R.drawable.button_2_1_color17,
            button_2_2=R.drawable.button_2_2_color17;
    StyleSave styleSave;

    public RAM_Overclocking(Context context, PcParametersSave pcParametersSave, ConstraintLayout layout){
        this.pcParametersSave = pcParametersSave;
        this.layout = layout;
        this.context = context;
        styleSave = new StyleSave(context);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        mainWindow=LayoutInflater.from(context).inflate(R.layout.program_ram_overclocking,null);
        getLanguage();
    }

    private TextView parameters,title,ram_model;
    private SeekBar seekBar;
    private Button save,ram1,ram2,ram3,ram4;
    private Button close,rollUp,fullscreen;
    private ConstraintLayout content;
    private void initView(){
        title = mainWindow.findViewById(R.id.title);
        ram_model = mainWindow.findViewById(R.id.ram_model);
        parameters = mainWindow.findViewById(R.id.textView2);
        content = mainWindow.findViewById(R.id.content);
        ram1 = mainWindow.findViewById(R.id.ram1);
        ram2 = mainWindow.findViewById(R.id.ram2);
        ram3 = mainWindow.findViewById(R.id.ram3);
        ram4 = mainWindow.findViewById(R.id.ram4);
        save = mainWindow.findViewById(R.id.button7);
        seekBar = mainWindow.findViewById(R.id.seekBar);

        close = mainWindow.findViewById(R.id.close);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
    }

    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),typeToken.getType());
    }

    private void style(){
        title.setTypeface(typeface,Typeface.BOLD);
        ram_model.setTypeface(typeface,Typeface.BOLD);
        parameters.setTypeface(typeface,Typeface.BOLD);
        ram1.setTypeface(typeface,Typeface.BOLD);
        ram2.setTypeface(typeface,Typeface.BOLD);
        ram3.setTypeface(typeface,Typeface.BOLD);
        ram4.setTypeface(typeface,Typeface.BOLD);
        save.setTypeface(typeface,Typeface.BOLD);

        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        content.setBackgroundColor(styleSave.ThemeColor1);
        ram1.setBackgroundColor(styleSave.ThemeColor2);
        ram1.setTextColor(styleSave.TextButtonColor);
        ram2.setBackgroundColor(styleSave.ThemeColor2);
        ram2.setTextColor(styleSave.TextButtonColor);
        ram3.setBackgroundColor(styleSave.ThemeColor2);
        ram3.setTextColor(styleSave.TextButtonColor);
        ram4.setBackgroundColor(styleSave.ThemeColor2);
        ram4.setTextColor(styleSave.TextButtonColor);

        ram_model.setTextColor(styleSave.TextColor);
        parameters.setTextColor(styleSave.TextColor);

        save.setBackgroundColor(styleSave.ThemeColor2);
        save.setTextColor(styleSave.TextButtonColor);
        save.setText(words.get("Will apply"));
        title.setTextColor(styleSave.TitleColor);
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        button_2_1 = styleSave.FullScreenMode1ImageRes;
        button_2_2 = styleSave.FullScreenMode2ImageRes;
        fullscreen.setBackgroundResource(button_2_2);
        seekBar.setThumb(context.getDrawable(styleSave.SeekBarThumbResource));
        seekBar.setProgressDrawable(context.getDrawable(styleSave.SeekBarProgressResource));
    }

    private int frequency;
    private double k,power,temperature,throughput;

    public void openProgram(){
        initView();style();

        int[] buttonClicks = {0};
        close.setOnClickListener(v -> {
            mainWindow.setVisibility(View.GONE);
            mainWindow = null;
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

        if(pcParametersSave.RAM1 != null){
            ram1.setVisibility(View.VISIBLE);
            ram1.setOnClickListener(v->{
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(pcParametersSave.Ram1);
                ram_overclocking(pcParametersSave.RAM1,1);
            });
        }
        if(pcParametersSave.RAM2 != null){
            ram2.setVisibility(View.VISIBLE);
            ram2.setOnClickListener(v->{
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(pcParametersSave.Ram2);
                ram_overclocking(pcParametersSave.RAM2,2);
            });
        }
        if(pcParametersSave.RAM3 != null && (Integer.parseInt(pcParametersSave.MOBO.get("Кол-во слотов"))>=3)){
            ram3.setVisibility(View.VISIBLE);
            ram3.setOnClickListener(v->{
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(pcParametersSave.Ram3);
                ram_overclocking(pcParametersSave.RAM3,3);
            });
        }
        if(pcParametersSave.RAM4 != null && (Integer.parseInt(pcParametersSave.MOBO.get("Кол-во слотов"))>=4)){
            ram4.setVisibility(View.VISIBLE);
            ram4.setOnClickListener(v->{
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(pcParametersSave.Ram4);
                ram_overclocking(pcParametersSave.RAM4,4);
            });
        }

        layout.addView(mainWindow, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
    }

    @SuppressLint("SetTextI18n")
    private void ram_overclocking(HashMap<String,String> RAM, int slot){
        switch(RAM.get("Тип памяти")){
            case "DDR2":{
                seekBar.setMax(600);
                break;
            }
            case "DDR3":{
                seekBar.setMax(2600);
                break;
            }
            case "DDR4":{
                seekBar.setMax(4200);
                break;
            }
        }
        frequency = Integer.parseInt(RAM.get("Частота"));
        throughput = Integer.parseInt(RAM.get("Пропускная способность"));
        k = frequency/throughput;
        power = frequency*(k/100);
        temperature = frequency*(k*0.25);
        seekBar.setProgress(frequency);
        parameters.setText(
                words.get("Frequency")+": "+frequency + "MHz\n" +
                        words.get("Throughput")+": " +(int)throughput+"PC\n" +
                        words.get("Energy consumption")+": "+(float)power+"W\n" +
                        words.get("Temperature")+": "+(int)temperature+"C\n" +
                        words.get("Maximum temperature")+": "+"90С\n" +
                        words.get("Maximum frequency")+": "+pcParametersSave.maxRamFrequency+"MHz\n" +
                        words.get("Minimum frequency")+": "+pcParametersSave.minRamFrequency+"MHz");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                frequency = progress;
                power = frequency*(k/100);
                temperature = frequency*(k*0.25);
                throughput = frequency/k;
                parameters.setText(
                        words.get("Frequency")+": "+frequency + "MHz\n" +
                                words.get("Throughput")+": " +(int)throughput+"PC\n" +
                                words.get("Energy consumption")+": "+(float)power+"W\n" +
                                words.get("Temperature")+": "+(int)temperature+"C\n" +
                                words.get("Maximum temperature")+": "+"90С\n" +
                                words.get("Maximum frequency")+": "+pcParametersSave.maxRamFrequency+"MHz\n" +
                                words.get("Minimum frequency")+": "+pcParametersSave.minRamFrequency+"MHz");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @SuppressLint("SetTextI18n")
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                power = frequency*(k/100);
                temperature = frequency*(k*0.25);
                throughput = frequency/k;
                parameters.setText(
                        words.get("Frequency")+": "+frequency + "MHz\n" +
                                words.get("Throughput")+": " +(int)throughput+"PC\n" +
                                words.get("Energy consumption")+": "+(float)power+"W\n" +
                                words.get("Temperature")+": "+(int)temperature+"C\n" +
                                words.get("Maximum temperature")+": "+"90С\n" +
                                words.get("Maximum frequency")+": "+pcParametersSave.maxRamFrequency+"MHz\n" +
                                words.get("Minimum frequency")+": "+pcParametersSave.minRamFrequency+"MHz");
            }
        });
        save.setOnClickListener(v -> {
            if(temperature<=90){
                RAM.put("Частота",String.valueOf(frequency));
                RAM.put("Пропускная способность", String.valueOf((int)throughput));
                if(pcParametersSave.ramValid(RAM)){
                    switch (slot){
                        case 1:{pcParametersSave.setRam1(pcParametersSave.Ram1,RAM);break;}
                        case 2:{pcParametersSave.setRam2(pcParametersSave.Ram2,RAM);break;}
                        case 3:{pcParametersSave.setRam3(pcParametersSave.Ram3,RAM);break;}
                        case 4:{pcParametersSave.setRam4(pcParametersSave.Ram4,RAM);break;}
                    }
                }
                else{
                    if(frequency>pcParametersSave.maxRamFrequency){
                        new Warning(context,layout).warn(words.get("The frequency of the RAM is too high"));
                    }
                    else if(frequency<pcParametersSave.minRamFrequency){
                        new Warning(context,layout).warn(words.get("RAM frequency is too low"));
                    }
                }
            }
            else{
                switch (slot){
                    case 1:{pcParametersSave.setRam1(null,null);break;}
                    case 2:{pcParametersSave.setRam2(null,null);break;}
                    case 3:{pcParametersSave.setRam3(null,null);break;}
                    case 4:{pcParametersSave.setRam4(null,null);break;}
                }
            }
        });
    }
}
