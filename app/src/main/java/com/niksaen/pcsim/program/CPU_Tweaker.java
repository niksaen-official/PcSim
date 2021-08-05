package com.niksaen.pcsim.program;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class CPU_Tweaker {

    PcParametersSave pcParametersSave;
    ConstraintLayout layout;
    Context context;
    StyleSave styleSave;

    View mainWindow;
    Typeface typeface;
    int button_2_1, button_2_2;

    public CPU_Tweaker(Context context, PcParametersSave pcParametersSave, ConstraintLayout layout){
        this.pcParametersSave = pcParametersSave;
        this.layout = layout;
        this.context = context;
        styleSave = new StyleSave(context);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");

        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_cpu_tweaker,null);
    }

    private int current_frequency;
    private double current_temperature,max_temperature,power;

    private TextView title,cpu_model,temperature,frequency;
    private SeekBar setFrequency;
    private Button save,close,rollUp,fullScreen;
    private LinearLayout linearLayout;

    HashMap<String,String> words;

    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),typeToken.getType());
    }

    private void initView(){
        title = mainWindow.findViewById(R.id.title);
        cpu_model = mainWindow.findViewById(R.id.cpu_model);
        temperature = mainWindow.findViewById(R.id.temperature);
        frequency = mainWindow.findViewById(R.id.textView);
        setFrequency = mainWindow.findViewById(R.id.frequency);
        save = mainWindow.findViewById(R.id.save);
        linearLayout = mainWindow.findViewById(R.id.content);
        close = mainWindow.findViewById(R.id.close);
        fullScreen = mainWindow.findViewById(R.id.fullscreenMode);
        rollUp = mainWindow.findViewById(R.id.roll_up);
    }
    private void style(){
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        title.setTextColor(styleSave.TitleColor);
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        button_2_1 = styleSave.FullScreenMode1ImageRes;
        button_2_2 = styleSave.FullScreenMode2ImageRes;
        fullScreen.setBackgroundResource(button_2_2);
        linearLayout.setBackgroundColor(styleSave.ThemeColor1);
        save.setBackgroundColor(styleSave.ThemeColor2);
        save.setTextColor(styleSave.TextButtonColor);
        save.setText(words.get("Will apply"));
        cpu_model.setTextColor(styleSave.TextColor);
        frequency.setTextColor(styleSave.TextColor);
        temperature.setTextColor(styleSave.TextColor);
        setFrequency.setThumb(context.getDrawable(styleSave.SeekBarThumbResource));
        setFrequency.setProgressDrawable(context.getDrawable(styleSave.SeekBarProgressResource));

        title.setTypeface(typeface,Typeface.BOLD);
        cpu_model.setTypeface(typeface,Typeface.BOLD);
        temperature.setTypeface(typeface,Typeface.BOLD);
        frequency.setTypeface(typeface,Typeface.BOLD);
        save.setTypeface(typeface,Typeface.BOLD);
    }

    public void openProgram(){

        getLanguage();initView();style();

        int[] buttonClicks = {0};
        close.setOnClickListener(v -> {
            mainWindow.setVisibility(View.GONE);
            mainWindow = null;
        });
        fullScreen.setOnClickListener(v->{
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

        cpu_model.setText(pcParametersSave.Cpu);

        //logic
        current_frequency = Integer.parseInt(pcParametersSave.CPU.get("Частота"));
        current_temperature = pcParametersSave.currentCpuTemperature();
        max_temperature = pcParametersSave.maxCpuTemperature();
        power = pcParametersSave.cpuPower;

        frequency.setText(words.get("Frequency")+": "+ current_frequency+"MHz");
        temperature.setText(
                words.get("CPU temperature")+": " + (int) current_temperature + "C\n" +
                        words.get("Maximum CPU temperature")+": " + (int) max_temperature + "C\n" +
                        words.get("Energy consumption")+": " + (int) power + "W");

        setFrequency.setProgress(current_frequency);
        setFrequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(pcParametersSave.CPU.get("Возможность разгона").equals("+")) {
                    frequency.setText(words.get("Frequency")+": " + progress + "MHz");
                    current_frequency = progress;
                    power = (progress * 0.025);
                    current_temperature = progress * 0.016f - (Double.parseDouble(pcParametersSave.COOLER.get("TDP")) - Integer.parseInt(pcParametersSave.CPU.get("TDP"))) / 8;
                    temperature.setText(
                            words.get("CPU temperature")+": " + (int) current_temperature + "C\n" +
                                    words.get("Maximum CPU temperature")+": " + (int) max_temperature + "C\n" +
                                    words.get("Energy consumption")+": " + (int) power + "W");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (pcParametersSave.CPU.get("Возможность разгона").equals("+")) {
                    if (seekBar.getProgress() < 1000) {
                        seekBar.setProgress(1000);
                        frequency.setText(words.get("Frequency") + ": " + 1000 + "MHz");
                        current_frequency = 1000;
                        power = (1000 * 0.025);
                        current_temperature = 1000 * 0.016f - (Double.parseDouble(pcParametersSave.COOLER.get("TDP")) - Integer.parseInt(pcParametersSave.CPU.get("TDP"))) / 8;
                        temperature.setText(
                                words.get("CPU temperature") + ": " + (int) current_temperature + "C\n" +
                                        words.get("Maximum CPU temperature") + ": " + (int) max_temperature + "C\n" +
                                        words.get("Energy consumption") + ": " + (int) power + "W");
                    }
                }
            }
        });

        save.setOnClickListener(v -> {
            if(pcParametersSave.CPU != null) {
                pcParametersSave.CPU.put("Частота", String.valueOf(current_frequency));
                pcParametersSave.setCpu(pcParametersSave.Cpu, pcParametersSave.CPU);
                if (current_temperature > max_temperature && !pcParametersSave.psuEnoughPower()) {
                    pcParametersSave.setCpu(null,null);
                    pcParametersSave.setPsu(null,null);
                } else if (current_temperature > max_temperature) {
                    pcParametersSave.setCpu(null,null);
                }
            }
        });
        layout.addView(mainWindow,ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT);
    }
    public void closeProgram(){
        mainWindow.setVisibility(View.GONE);
        mainWindow = null;
    }
}
