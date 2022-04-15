package com.niksaen.pcsim.program.driverInstaller;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.program.Program;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Install extends Program {

    String[] DriversForSetup;
    String DriveID;

    public Install(MainActivity activity) {
        super(activity);
        Title = "Driver installer";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{50,75};
    }

    ProgressBar progressBar;
    TextView setupStep;
    View main;
    int setupProgress = 0;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_setup,null);
        initView();
        style();
        super.initProgram();
    }

    private void initView(){
        main = mainWindow.findViewById(R.id.main);
        progressBar = mainWindow.findViewById(R.id.progressBar);
        setupStep = mainWindow.findViewById(R.id.setupStatus);
        setup();
    }
    private void style(){
        main.setBackgroundColor(activity.styleSave.ThemeColor1);
        setupStep.setBackgroundColor(activity.styleSave.ThemeColor2);
        setupStep.setTextColor(activity.styleSave.TextColor);
        setupStep.setTypeface(activity.font);

        progressBar.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
        LayerDrawable progressBarBackground = (LayerDrawable) progressBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN);
        progressBar.setMax(DriversForSetup.length-1);
    }
    private HashMap<String, HashMap<String,String>> diskHashMap;
    private void initDiskMap(){
        diskHashMap = new HashMap<>();
        if(activity.pcParametersSave.DATA1 != null){
            diskHashMap.put(activity.pcParametersSave.DATA1.get("name"),activity.pcParametersSave.DATA1);
        }
        if(activity.pcParametersSave.DATA2 != null){
            diskHashMap.put(activity.pcParametersSave.DATA2.get("name"),activity.pcParametersSave.DATA2);
        }
        if(activity.pcParametersSave.DATA3 != null){
            diskHashMap.put(activity.pcParametersSave.DATA3.get("name"),activity.pcParametersSave.DATA3);
        }
        if(activity.pcParametersSave.DATA4 != null){
            diskHashMap.put(activity.pcParametersSave.DATA4.get("name"),activity.pcParametersSave.DATA4);
        }
        if(activity.pcParametersSave.DATA5 != null){
            diskHashMap.put(activity.pcParametersSave.DATA5.get("name"),activity.pcParametersSave.DATA5);
        }
        if(activity.pcParametersSave.DATA6 != null){
            diskHashMap.put(activity.pcParametersSave.DATA6.get("name"),activity.pcParametersSave.DATA6);
        }
    }
    private void setup(){
        initDiskMap();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    if(setupProgress == DriversForSetup.length){
                        timer.cancel();
                        setupStep.setText(setupStep.getText().toString() +"\n" + activity.words.get("Driver installation completed"));
                        activity.pcParametersSave.setData(DriveID,diskHashMap.get(DriveID));
                    }else {
                        String text = activity.words.get("Installation") +
                                DriversForSetup[setupProgress]
                                        .replace(DriverInstaller.DriverForMotherboard, activity.words.get(DriverInstaller.DriverForMotherboard).toLowerCase())
                                        .replace(DriverInstaller.DriverForCPU, activity.words.get(DriverInstaller.DriverForCPU).toLowerCase())
                                        .replace(DriverInstaller.DriverForRAM, activity.words.get(DriverInstaller.DriverForRAM).toLowerCase())
                                        .replace(DriverInstaller.DriverForStorageDevices, activity.words.get(DriverInstaller.DriverForStorageDevices).toLowerCase())
                                        .replace(DriverInstaller.DriverForGPU, activity.words.get(DriverInstaller.DriverForGPU).toLowerCase())
                                        .replace(DriverInstaller.EXTENDED_TYPE, activity.words.get(DriverInstaller.EXTENDED_TYPE))
                                        .replace(DriverInstaller.BASE_TYPE, activity.words.get(DriverInstaller.BASE_TYPE));
                        setupStep.setText(setupStep.getText().toString() +"\n" + text);
                        diskHashMap.get(DriveID).put("Содержимое", diskHashMap.get(DriveID).get("Содержимое") + DriversForSetup[setupProgress] + ",");
                        setupProgress++;
                        progressBar.setProgress(setupProgress);
                    }
                });
            }
        };
        if(diskHashMap.get(DriveID).get("Тип").equals("HDD"))
            timer.scheduleAtFixedRate(task,0,800);
        else
            timer.scheduleAtFixedRate(task,0, 400);
    }
}
