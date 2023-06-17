package com.niksaen.pcsim.program;

import android.annotation.SuppressLint;
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

public class RAM_Overclocking extends Program {

    public RAM_Overclocking(MainActivity activity){
        super(activity);
        this.Title = "RAM Overclocking";
        ValueRam = new int[]{200,300};
        ValueVideoMemory = new int[]{100,250};
    }

    private TextView parameters,ram_model;
    private SeekBar seekBar;
    private Button save,ram1,ram2,ram3,ram4;
    private ConstraintLayout content;
    private void initView(){
        ram_model = mainWindow.findViewById(R.id.ram_model);
        parameters = mainWindow.findViewById(R.id.textView2);
        content = mainWindow.findViewById(R.id.content);
        ram1 = mainWindow.findViewById(R.id.ram1);
        ram2 = mainWindow.findViewById(R.id.ram2);
        ram3 = mainWindow.findViewById(R.id.ram3);
        ram4 = mainWindow.findViewById(R.id.ram4);
        save = mainWindow.findViewById(R.id.button7);
        seekBar = mainWindow.findViewById(R.id.seekBar);
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
        ProgressBarStylisation.setStyle(seekBar,activity);
    }

    private int frequency;
    private double k,power,temperature,throughput;

    @Override
    public void openProgram() {
        super.openProgram();
    }

    public void initProgram(){
        mainWindow=LayoutInflater.from(activity).inflate(R.layout.program_ram_overclocking,null);
        initView();
        style();

        if (activity.pcParametersSave.RAM1 != null && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram1)) {
            ram1.setVisibility(View.VISIBLE);
            ram1.setOnClickListener(v -> {
                if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram1+"\n"+DriverInstaller.EXTENDED_TYPE)) {
                    ram_model.setVisibility(View.VISIBLE);
                    parameters.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    ram_model.setText(activity.pcParametersSave.Ram1);
                    ram_overclocking(activity.pcParametersSave.RAM1, 1);
                }else{
                    WarningWindow window = new WarningWindow(activity);
                    window.setMessageText(activity.words.get("An error has occurred in the program")+"\n"+activity.words.get("Installed drivers are not compatible with this program"));
                    window.setButtonOkClick(v1->window.closeProgram(1));
                    window.openProgram();
                }
            });
        }
        if (activity.pcParametersSave.RAM2 != null && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram2)) {
            ram2.setVisibility(View.VISIBLE);
            ram2.setOnClickListener(v -> {
                if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram2+"\n"+DriverInstaller.EXTENDED_TYPE)) {
                    ram_model.setVisibility(View.VISIBLE);
                    parameters.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    ram_model.setText(activity.pcParametersSave.Ram2);
                    ram_overclocking(activity.pcParametersSave.RAM2, 2);
                }
                else{
                    WarningWindow window = new WarningWindow(activity);
                    window.setMessageText(activity.words.get("An error has occurred in the program")+"\n"+activity.words.get("Installed drivers are not compatible with this program"));
                    window.setButtonOkClick(v1->window.closeProgram(1));
                    window.openProgram();
                }
            });
        }
        if (activity.pcParametersSave.RAM3 != null && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram3)) {
            ram3.setVisibility(View.VISIBLE);
            ram3.setOnClickListener(v -> {
                if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram3+"\n"+DriverInstaller.EXTENDED_TYPE)) {
                    ram_model.setVisibility(View.VISIBLE);
                    parameters.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    ram_model.setText(activity.pcParametersSave.Ram3);
                    ram_overclocking(activity.pcParametersSave.RAM3, 3);
                }
                else{
                    WarningWindow window = new WarningWindow(activity);
                    window.setMessageText(activity.words.get("An error has occurred in the program")+"\n"+activity.words.get("Installed drivers are not compatible with this program"));
                    window.setButtonOkClick(v1->window.closeProgram(1));
                    window.openProgram();
                }
            });
        }
        if (activity.pcParametersSave.RAM4 != null && StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram4)) {
            ram4.setVisibility(View.VISIBLE);
            ram4.setOnClickListener(v -> {
                if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForRAM+activity.pcParametersSave.Ram4+"\n"+DriverInstaller.EXTENDED_TYPE)) {
                    ram_model.setVisibility(View.VISIBLE);
                    parameters.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    ram_model.setText(activity.pcParametersSave.Ram4);
                    ram_overclocking(activity.pcParametersSave.RAM4, 4);
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
                        activity.blackDeadScreen(new String[]{activity.words.get("This RAM frequency is not supported by your PC")});
                    }
                    else if(frequency<activity.pcParametersSave.minRamFrequency){
                        activity.blackDeadScreen(new String[]{activity.words.get("This RAM frequency is not supported by your PC")});
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
                activity.blackDeadScreen(new String[]{activity.words.get("Overheating of RAM")});
            }
        });
    }
}
