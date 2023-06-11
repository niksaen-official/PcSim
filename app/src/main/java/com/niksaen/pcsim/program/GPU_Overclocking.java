package com.niksaen.pcsim.program;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.ProgressBarStylisation;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.program.window.WarningWindow;

import java.util.HashMap;

public class GPU_Overclocking extends Program {

    public GPU_Overclocking(MainActivity activity){
        super(activity);
        Title = "GPU Overclocking";
        ValueRam = new int[]{230,340};
        ValueVideoMemory = new int[]{100,200};
    }
    private int frequency;
    private TextView parameters,gpu_model;
    private SeekBar seekBar;
    private Button save,gpu1,gpu2;
    private ConstraintLayout content;
    private void initView(){
        gpu_model = mainWindow.findViewById(R.id.gpu_model);
        parameters = mainWindow.findViewById(R.id.textView2);

        gpu1 = mainWindow.findViewById(R.id.gpu1);
        gpu2 = mainWindow.findViewById(R.id.gpu2);
        save = mainWindow.findViewById(R.id.button7);
        seekBar = mainWindow.findViewById(R.id.seekBar);
        content = mainWindow.findViewById(R.id.content);
    }

    private void style(){
        gpu_model.setTypeface(activity.font,Typeface.BOLD);
        parameters.setTypeface(activity.font,Typeface.BOLD);
        gpu1.setTypeface(activity.font,Typeface.BOLD);
        gpu2.setTypeface(activity.font,Typeface.BOLD);
        save.setTypeface(activity.font,Typeface.BOLD);

        seekBar.setMax(2000);
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        gpu_model.setTextColor(activity.styleSave.TextColor);
        parameters.setTextColor(activity.styleSave.TextColor);
        gpu1.setTextColor(activity.styleSave.TextButtonColor);
        gpu1.setBackgroundColor(activity.styleSave.ThemeColor2);
        gpu2.setTextColor(activity.styleSave.TextButtonColor);
        gpu2.setBackgroundColor(activity.styleSave.ThemeColor2);
        save.setTextColor(activity.styleSave.TextButtonColor);
        save.setBackgroundColor(activity.styleSave.ThemeColor2);
        save.setText(activity.words.get("Will apply"));
        content.setBackgroundColor(activity.styleSave.ThemeColor1);
        ProgressBarStylisation.setStyle(seekBar,activity);
    }

    @Override
    public void openProgram() {
        super.openProgram();
    }

    public void initProgram(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_gpu_overclocking,null);
        initView();
        style();
        if (activity.pcParametersSave.GPU1 != null && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForGPU+activity.pcParametersSave.Gpu1)) {
            gpu1.setVisibility(View.VISIBLE);
            gpu1.setOnClickListener(v -> {
                if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForGPU+activity.pcParametersSave.Gpu1+"\n"+DriverInstaller.EXTENDED_TYPE)) {
                    gpu_model.setVisibility(View.VISIBLE);
                    parameters.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    gpu_model.setText(activity.pcParametersSave.Gpu1);
                    overclocking(activity.pcParametersSave.GPU1, 1);
                }else{
                    WarningWindow window = new WarningWindow(activity);
                    window.setMessageText(activity.words.get("An error has occurred in the program")+"\n"+activity.words.get("Installed drivers are not compatible with this program"));
                    window.setButtonOkClick(v1->window.closeProgram(1));
                    window.openProgram();
                }
            });
        }
        if (activity.pcParametersSave.GPU2 != null && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForGPU+activity.pcParametersSave.Gpu2)) {
            gpu2.setVisibility(View.VISIBLE);
            gpu2.setOnClickListener(v -> {
                if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForGPU+activity.pcParametersSave.Gpu2+"\n"+DriverInstaller.EXTENDED_TYPE)) {
                    gpu_model.setVisibility(View.VISIBLE);
                    parameters.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    gpu_model.setText(activity.pcParametersSave.Gpu2);
                    overclocking(activity.pcParametersSave.GPU2, 2);
                }
                else{
                    WarningWindow window = new WarningWindow(activity);
                    window.setMessageText(activity.words.get("An error has occurred in the program")+"\n"+activity.words.get("Installed drivers are not compatible with this program"));
                    window.setButtonOkClick(v1->window.closeProgram(1));
                    window.openProgram();
                }
            });
        }
        super.initProgram();
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
            case "Passive":{ temperature_coefficient = 1.4f;break; }
            case "Cooler":{ temperature_coefficient = 1.15f;power_fan = 10;break; }
            default: throw new IllegalStateException("Unexpected value: " + GPU.get("Тип охлаждения"));
        }
        seekBar.setProgress(frequency);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                frequency = progress;
                main = (progress /power_coefficient)/5;
                temperature = main*temperature_coefficient/1.75f;
                power =(main*power_coefficient)/1.6f+power_fan;
                parameters.setText(
                        activity.words.get("Frequency")+": "+progress+"MHz"+"\n" +
                                activity.words.get("Temperature")+": "+temperature+"C\n" +
                                activity.words.get("Energy consumption")+": "+power+"W");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress() < 500){
                    seekBar.setProgress(500);
                    frequency = 500;
                    main = (500 /power_coefficient)/5;
                    temperature = main*temperature_coefficient/1.75f;
                    power = (main*power_coefficient)/1.6f+power_fan;
                    parameters.setText(
                            activity.words.get("Frequency")+": "+500+"MHz"+"\n" +
                                    activity.words.get("Temperature")+": "+temperature+"C\n" +
                                    activity.words.get("Maximum temperature")+": "+"75C\n" +
                                    activity.words.get("Energy consumption")+": "+power+"W");
                }
            }
        });

        main = (frequency /power_coefficient)/5;
        temperature = main*temperature_coefficient/1.75f;
        power = (main*power_coefficient)/1.6f+power_fan;
        parameters.setText(
                activity.words.get("Frequency")+": "+frequency+"MHz"+"\n" +
                        activity.words.get("Temperature")+": "+temperature+"C\n" +
                        activity.words.get("Maximum temperature")+": "+"75C\n" +
                        activity.words.get("Energy consumption")+": "+power+"W");

        save.setOnClickListener(v -> {
            if (slot == 1) {
                GPU.put("Частота", String.valueOf(frequency));
                GPU.put("Пропускная способность", String.valueOf(throughput));
                activity.pcParametersSave.setGpu1(activity.pcParametersSave.Gpu1, GPU);
                if(temperature>75 && !activity.pcParametersSave.psuEnoughPower()){
                    activity.pcParametersSave.setGpu1(activity.pcParametersSave.Gpu1 + "[Сломано]", null);
                    if(activity.pcParametersSave.PSU.get("Защита").equals("-")) {
                        activity.pcParametersSave.setPsu(activity.pcParametersSave.Psu + "[Сломано]", null);
                    }
                    activity.blackDeadScreen(new String[]{"0x0003","0x0005"});
                }
                else if(!activity.pcParametersSave.psuEnoughPower()){
                    if(activity.pcParametersSave.PSU.get("Защита").equals("-")) {
                        activity.pcParametersSave.setPsu(activity.pcParametersSave.Psu + "[Сломано]", null);
                    }
                    activity.blackDeadScreen(new String[]{"0x0005"});
                }
            }
            if (slot == 2) {
                GPU.put("Частота", String.valueOf(frequency));
                GPU.put("Пропускная способность", String.valueOf(throughput));
                activity.pcParametersSave.setGpu2(activity.pcParametersSave.Gpu2, GPU);
                if(temperature>75 && !activity.pcParametersSave.psuEnoughPower()){
                    activity.pcParametersSave.setGpu2(activity.pcParametersSave.Gpu2 + "[Сломано]",null);
                    if(activity.pcParametersSave.PSU.get("Защита").equals("-")) {
                        activity.pcParametersSave.setPsu(activity.pcParametersSave.Psu + "[Сломано]", null);
                    }
                    activity.blackDeadScreen(new String[]{activity.words.get("Video chip overheating"),activity.words.get("The power supply is overloaded")});
                }
                else if(!activity.pcParametersSave.psuEnoughPower()){
                    if(activity.pcParametersSave.PSU.get("Защита").equals("-")) {
                        activity.pcParametersSave.setPsu(activity.pcParametersSave.Psu + "[Сломано]", null);
                    }
                    activity.blackDeadScreen(new String[]{activity.words.get("The power supply is overloaded")});
                }
            }
        });
    }
}
