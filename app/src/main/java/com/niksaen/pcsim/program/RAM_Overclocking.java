package com.niksaen.pcsim.program;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;

import java.util.HashMap;

public class RAM_Overclocking extends Program {

    public RAM_Overclocking(MainActivity activity){
        super(activity);
        this.Title = "RAM Overclocking";
    }

    private TextView parameters,ram_model;
    private SeekBar seekBar;
    private Button save,ram1,ram2,ram3,ram4;
    private ConstraintLayout content;
    private void initView(){
        titleTextView = mainWindow.findViewById(R.id.title);
        ram_model = mainWindow.findViewById(R.id.ram_model);
        parameters = mainWindow.findViewById(R.id.textView2);
        content = mainWindow.findViewById(R.id.content);
        ram1 = mainWindow.findViewById(R.id.ram1);
        ram2 = mainWindow.findViewById(R.id.ram2);
        ram3 = mainWindow.findViewById(R.id.ram3);
        ram4 = mainWindow.findViewById(R.id.ram4);
        save = mainWindow.findViewById(R.id.button7);
        seekBar = mainWindow.findViewById(R.id.seekBar);

        buttonClose = mainWindow.findViewById(R.id.close);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
    }

    private void style(){
        ram_model.setTypeface(activity.font,Typeface.BOLD);
        parameters.setTypeface(activity.font,Typeface.BOLD);
        ram1.setTypeface(activity.font,Typeface.BOLD);
        ram2.setTypeface(activity.font,Typeface.BOLD);
        ram3.setTypeface(activity.font,Typeface.BOLD);
        ram4.setTypeface(activity.font,Typeface.BOLD);
        save.setTypeface(activity.font,Typeface.BOLD);

        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        content.setBackgroundColor(activity.styleSave.ThemeColor1);
        ram1.setBackgroundColor(activity.styleSave.ThemeColor2);
        ram1.setTextColor(activity.styleSave.TextButtonColor);
        ram2.setBackgroundColor(activity.styleSave.ThemeColor2);
        ram2.setTextColor(activity.styleSave.TextButtonColor);
        ram3.setBackgroundColor(activity.styleSave.ThemeColor2);
        ram3.setTextColor(activity.styleSave.TextButtonColor);
        ram4.setBackgroundColor(activity.styleSave.ThemeColor2);
        ram4.setTextColor(activity.styleSave.TextButtonColor);

        ram_model.setTextColor(activity.styleSave.TextColor);
        parameters.setTextColor(activity.styleSave.TextColor);

        save.setBackgroundColor(activity.styleSave.ThemeColor2);
        save.setTextColor(activity.styleSave.TextButtonColor);
        save.setText(activity.words.get("Will apply"));
        seekBar.setThumb(activity.getDrawable(activity.styleSave.SeekBarThumbResource));
        seekBar.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
    }

    private int frequency;
    private double k,power,temperature,throughput;

    public void initProgram(){
        mainWindow=LayoutInflater.from(activity).inflate(R.layout.program_ram_overclocking,null);
        initView();
        style();

        if (activity.pcParametersSave.RAM1 != null) {
            ram1.setVisibility(View.VISIBLE);
            ram1.setOnClickListener(v -> {
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(activity.pcParametersSave.Ram1);
                ram_overclocking(activity.pcParametersSave.RAM1, 1);
            });
        }
        if (activity.pcParametersSave.RAM2 != null) {
            ram2.setVisibility(View.VISIBLE);
            ram2.setOnClickListener(v -> {
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(activity.pcParametersSave.Ram2);
                ram_overclocking(activity.pcParametersSave.RAM2, 2);
            });
        }
        if (activity.pcParametersSave.RAM3 != null && (Integer.parseInt(activity.pcParametersSave.MOBO.get("Кол-во слотов")) >= 3)) {
            ram3.setVisibility(View.VISIBLE);
            ram3.setOnClickListener(v -> {
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(activity.pcParametersSave.Ram3);
                ram_overclocking(activity.pcParametersSave.RAM3, 3);
            });
        }
        if (activity.pcParametersSave.RAM4 != null && (Integer.parseInt(activity.pcParametersSave.MOBO.get("Кол-во слотов")) >= 4)) {
            ram4.setVisibility(View.VISIBLE);
            ram4.setOnClickListener(v -> {
                ram_model.setVisibility(View.VISIBLE);
                parameters.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                ram_model.setText(activity.pcParametersSave.Ram4);
                ram_overclocking(activity.pcParametersSave.RAM4, 4);
            });
        }
       super.initProgram();
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
                activity.words.get("Frequency")+": "+frequency + "MHz\n" +
                        activity.words.get("Throughput")+": " +(int)throughput+"PC\n" +
                        activity.words.get("Energy consumption")+": "+(float)power+"W\n" +
                        activity.words.get("Temperature")+": "+(int)temperature+"C\n" +
                        activity.words.get("Maximum temperature")+": "+"90С\n" +
                        activity.words.get("Maximum frequency")+": "+activity.pcParametersSave.maxRamFrequency+"MHz\n" +
                        activity.words.get("Minimum frequency")+": "+activity.pcParametersSave.minRamFrequency+"MHz");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                frequency = progress;
                power = frequency*(k/100);
                temperature = frequency*(k*0.25);
                throughput = frequency/k;
                parameters.setText(
                        activity.words.get("Frequency")+": "+frequency + "MHz\n" +
                                activity.words.get("Throughput")+": " +(int)throughput+"PC\n" +
                                activity.words.get("Energy consumption")+": "+(float)power+"W\n" +
                                activity.words.get("Temperature")+": "+(int)temperature+"C\n" +
                                activity.words.get("Maximum temperature")+": "+"90С\n" +
                                activity.words.get("Maximum frequency")+": "+activity.pcParametersSave.maxRamFrequency+"MHz\n" +
                                activity.words.get("Minimum frequency")+": "+activity.pcParametersSave.minRamFrequency+"MHz");
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
                        activity.words.get("Frequency")+": "+frequency + "MHz\n" +
                                activity.words.get("Throughput")+": " +(int)throughput+"PC\n" +
                                activity.words.get("Energy consumption")+": "+(float)power+"W\n" +
                                activity.words.get("Temperature")+": "+(int)temperature+"C\n" +
                                activity.words.get("Maximum temperature")+": "+"90С\n" +
                                activity.words.get("Maximum frequency")+": "+activity.pcParametersSave.maxRamFrequency+"MHz\n" +
                                activity.words.get("Minimum frequency")+": "+activity.pcParametersSave.minRamFrequency+"MHz");
            }
        });
        save.setOnClickListener(v -> {
            if(temperature<=90){
                RAM.put("Частота",String.valueOf(frequency));
                RAM.put("Пропускная способность", String.valueOf((int)throughput));
                if(activity.pcParametersSave.ramValid(RAM)){
                    switch (slot){
                        case 1:{activity.pcParametersSave.setRam1(activity.pcParametersSave.Ram1,RAM);break;}
                        case 2:{activity.pcParametersSave.setRam2(activity.pcParametersSave.Ram2,RAM);break;}
                        case 3:{activity.pcParametersSave.setRam3(activity.pcParametersSave.Ram3,RAM);break;}
                        case 4:{activity.pcParametersSave.setRam4(activity.pcParametersSave.Ram4,RAM);break;}
                    }
                }
                else{
                    if(frequency>activity.pcParametersSave.maxRamFrequency){
                        activity.blackDeadScreen(new String[]{"0xAA0004"});
                    }
                    else if(frequency<activity.pcParametersSave.minRamFrequency){
                        activity.blackDeadScreen(new String[]{"0xAA0005"});
                    }
                }
            }
            else{
                switch (slot){
                    case 1:{activity.pcParametersSave.setRam1(activity.pcParametersSave.Ram1 +"[Сломано]",null);break;}
                    case 2:{activity.pcParametersSave.setRam2(activity.pcParametersSave.Ram2 +"[Сломано]",null);break;}
                    case 3:{activity.pcParametersSave.setRam3(activity.pcParametersSave.Ram3 +"[Сломано]",null);break;}
                    case 4:{activity.pcParametersSave.setRam4(activity.pcParametersSave.Ram4 +"[Сломано]",null);break;}
                }
                activity.blackDeadScreen(new String[]{"0xAA0003"});
            }
        });
    }
}
