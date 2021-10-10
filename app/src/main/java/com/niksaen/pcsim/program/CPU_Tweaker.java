package com.niksaen.pcsim.program;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;

public class CPU_Tweaker extends Program {

    public CPU_Tweaker(MainActivity activity){
        super(activity);
        this.Title = "CPU Overclocking";
        ValueRam = new int[]{200,300};
        ValueVideoMemory = new int[]{200,250};
    }

    private int current_frequency;
    private double current_temperature,max_temperature,power;

    private TextView cpu_model,temperature,frequency;
    private SeekBar setFrequency;
    private Button save;
    private LinearLayout linearLayout;

    private void initView(){
        cpu_model = mainWindow.findViewById(R.id.cpu_model);
        temperature = mainWindow.findViewById(R.id.temperature);
        frequency = mainWindow.findViewById(R.id.textView);
        setFrequency = mainWindow.findViewById(R.id.frequency);
        save = mainWindow.findViewById(R.id.save);
        linearLayout = mainWindow.findViewById(R.id.content);
    }
    private void style(){
        linearLayout.setBackgroundColor(activity.styleSave.ThemeColor1);
        save.setBackgroundColor(activity.styleSave.ThemeColor2);
        save.setTextColor(activity.styleSave.TextButtonColor);
        save.setText(activity.words.get("Will apply"));
        cpu_model.setTextColor(activity.styleSave.TextColor);
        frequency.setTextColor(activity.styleSave.TextColor);
        temperature.setTextColor(activity.styleSave.TextColor);
        setFrequency.setThumb(activity.getDrawable(activity.styleSave.SeekBarThumbResource));
        setFrequency.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
        LayerDrawable progressBarBackground = (LayerDrawable) setFrequency.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN);

        cpu_model.setTypeface(activity.font,Typeface.BOLD);
        temperature.setTypeface(activity.font,Typeface.BOLD);
        frequency.setTypeface(activity.font,Typeface.BOLD);
        save.setTypeface(activity.font,Typeface.BOLD);
    }

    public void initProgram(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_cpu_tweaker,null);
        initView();
        style();

        cpu_model.setText(activity.pcParametersSave.Cpu);
        //logic
        current_frequency = Integer.parseInt(activity.pcParametersSave.CPU.get("Частота"));
        current_temperature = activity.pcParametersSave.currentCpuTemperature();
        max_temperature = activity.pcParametersSave.maxCpuTemperature();
        power = activity.pcParametersSave.cpuPower;

        frequency.setText(activity.words.get("Frequency") + ": " + current_frequency + "MHz");
        temperature.setText(activity.words.get("CPU temperature") + ": " + (int) current_temperature + "C\n" +
                activity.words.get("Maximum cpu temperature") + ": " + (int) max_temperature + "C\n" +
                activity.words.get("Energy consumption") + ": " + (int) power + "W");

        setFrequency.setProgress(current_frequency);
        setFrequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (activity.pcParametersSave.CPU.get("Возможность разгона").equals("+")) {
                    frequency.setText(activity.words.get("Frequency") + ": " + progress + "MHz");
                    current_frequency = progress;
                    power = (progress * 0.025);
                    current_temperature = progress * 0.016f - (Double.parseDouble(activity.pcParametersSave.COOLER.get("TDP")) - Integer.parseInt(activity.pcParametersSave.CPU.get("TDP"))) / 8;
                    temperature.setText(
                            activity.words.get("CPU temperature") + ": " + (int) current_temperature + "C\n" +
                                    activity.words.get("Maximum cpu temperature") + ": " + (int) max_temperature + "C\n" +
                                    activity.words.get("Energy consumption") + ": " + (int) power + "W");
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (activity.pcParametersSave.CPU.get("Возможность разгона").equals("+")) {
                        if (seekBar.getProgress() < 1000) {
                            seekBar.setProgress(1000);
                            frequency.setText(activity.words.get("Frequency") + ": " + 1000 + "MHz");
                            current_frequency = 1000;
                            power = (1000 * 0.025);
                            current_temperature = 1000 * 0.016f - (Double.parseDouble(activity.pcParametersSave.COOLER.get("TDP")) - Integer.parseInt(activity.pcParametersSave.CPU.get("TDP"))) / 8;
                            temperature.setText(
                                    activity.words.get("CPU temperature") + ": " + (int) current_temperature + "C\n" +
                                            activity.words.get("Maximum cpu temperature") + ": " + (int) max_temperature + "C\n" +
                                            activity.words.get("Energy consumption") + ": " + (int) power + "W");
                        }
                    }
                }
            });
            save.setOnClickListener(v -> {
                activity.pcParametersSave.CPU.put("Частота", String.valueOf(current_frequency));
                activity.pcParametersSave.setCpu(activity.pcParametersSave.Cpu, activity.pcParametersSave.CPU);
                if (current_temperature > max_temperature && !activity.pcParametersSave.psuEnoughPower()) {
                    activity.pcParametersSave.setCpu(activity.pcParametersSave.Cpu + "[Сломано]", null);
                    activity.pcParametersSave.setPsu(activity.pcParametersSave.Psu + "[Сломано]", null);
                    activity.blackDeadScreen(new String[]{"0xAA0001", "0xBB0004"});
                } else if (current_temperature > max_temperature) {
                    activity.pcParametersSave.setCpu(activity.pcParametersSave.Cpu + "[Сломано]", null);
                    activity.blackDeadScreen(new String[]{"0xAA0001"});
                } else if (current_temperature > max_temperature + 20) {
                    activity.pcParametersSave.setCpu(activity.pcParametersSave.Cpu + "[Сломано]", null);
                    activity.pcParametersSave.setCooler(activity.pcParametersSave.Cooler + "[Сломано]", null);
                    activity.blackDeadScreen(new String[]{"0xAA0001", "0xBB0004"});
                }
            });
            super.initProgram();
    }
}
