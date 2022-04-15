package com.niksaen.pcsim.program.gStore;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.classes.ProgramListAndData;
import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.program.Program;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class GStoreSetupWindow extends Program {
    private String programForSetup;
    private String diskID;
    private boolean AddToDesktop;
    private boolean InstallAdditionalSoft;

    public void setProgramForSetup(String programForSetup) { this.programForSetup = programForSetup; }
    public void setDiskID(String disk) { this.diskID = disk; }
    public void setAddToDesktop(boolean addToDesktop) { AddToDesktop = addToDesktop; }
    public void setInstallAdditionalSoft(boolean installAdditionalSoft) { InstallAdditionalSoft = installAdditionalSoft; }

    public GStoreSetupWindow(MainActivity activity) {
        super(activity);
        Title = "GStore";
        ValueRam = new int[]{70,90};
        ValueVideoMemory = new int[]{90,120};
    }

    private ProgressBar progressBar;
    private TextView setupStatus,progressInPercent,description,programName;
    private ImageView programIcon;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_gstore_setup_window,null);
        initView();
        style();
        setup();
    }
    private void initView(){
        progressBar = mainWindow.findViewById(R.id.progressBar);
        setupStatus = mainWindow.findViewById(R.id.setupStatus);
        progressInPercent = mainWindow.findViewById(R.id.setupProgressInPercent);
        description = mainWindow.findViewById(R.id.description);
        programName = mainWindow.findViewById(R.id.program_name);
        programIcon = mainWindow.findViewById(R.id.program_icon);
    }
    int buttonClicks = 0;
    private void style(){
        titleTextView = mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);

        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        buttonClose.setBackgroundResource(R.drawable.button_1_color17);
        buttonFullscreenMode.setBackgroundResource(R.drawable.button_2_2_color17);
        buttonRollUp.setBackgroundResource(R.drawable.button_3_color17);
        titleTextView.setText(activity.words.get(Title));
        titleTextView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        titleTextView.setTextColor(Color.parseColor("#FFFFFF"));

        // настройка кнопок
        buttonClose.setOnClickListener(v->closeProgram(1));
        buttonFullscreenMode.setOnClickListener(v -> {
            if (buttonClicks== 0) {
                mainWindow.setScaleX(0.6f);
                mainWindow.setScaleY(0.6f);
                PortableView view = new PortableView(mainWindow);
                v.setBackgroundResource(R.drawable.button_2_1_color17);
                buttonClicks = 1;
            } else {
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setOnTouchListener((v1, event) -> false);
                mainWindow.setX(0);
                mainWindow.setY(0);
                v.setBackgroundResource(R.drawable.button_2_2_color17);
                buttonClicks = 0;
            }
        });
        buttonRollUp.setOnClickListener(v->rollUpProgram());
        setupStatus.setTypeface(activity.font);
        progressInPercent.setTypeface(activity.font);
        description.setTypeface(activity.font);
        programName.setTypeface(activity.font,Typeface.BOLD);
        LayerDrawable progressBarBackground = (LayerDrawable) progressBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(Color.parseColor("#0042A5"), PorterDuff.Mode.SRC_IN);
        progressBarBackground.getDrawable(1).setColorFilter(Color.parseColor("#002C6E"), PorterDuff.Mode.SRC_IN);

        programIcon.setImageResource(ProgramListAndData.programIcon.get(programForSetup));
        programName.setText(programForSetup);
        description.setText(activity.words.get(programForSetup+":Description"));
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

    private int setupProgress = 0;

    private void setup(){
        TimerTask timerTask;
        Timer timer = new Timer();
        setupStatus.setText(activity.words.get("Preparing to install ..."));
        initDiskMap();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    setupProgress++;
                    switch (setupProgress){
                        case 2: {
                            if (diskHashMap.get(diskID).get("Содержимое").contains(programForSetup)) {
                                setupStatus.setText(activity.words.get("The program is already installed on your PC."));
                                timer.cancel();
                            } else {
                                setupStatus.setText(activity.words.get("Preparation is complete."));
                            }
                            break;
                        }
                        case 4:{
                            setupStatus.setText(activity.words.get("Checking for free space ..."));
                            break;
                        }
                        case 5:{
                            if(activity.pcParametersSave.getEmptyStorageSpace(diskHashMap.get(diskID))>=ProgramListAndData.programSize.get(programForSetup)){
                                setupStatus.setText(activity.words.get("There is enough disk space to install the program."));
                            }
                            else {
                                setupStatus.setText(activity.words.get("There is not enough disk space to install the program!"));
                                timer.cancel();
                            }
                            break;
                        }
                        case 7:{
                            setupStatus.setText(activity.words.get("Downloading archives ..."));
                            break;
                        }
                        case 63:{
                            setupStatus.setText(activity.words.get("Downloading archives completed"));
                            break;
                        }
                        case 64:{
                            setupStatus.setText(activity.words.get("Unpacking archives ..."));
                            break;
                        }
                        case 79:{
                            setupStatus.setText(activity.words.get("Archives unpacked"));
                            diskHashMap.get(diskID).put("Содержимое",diskHashMap.get(diskID).get(("Содержимое"))+programForSetup+",");
                            activity.pcParametersSave.setData(diskID,diskHashMap.get(diskID));
                            activity.getContentOfAllDrives();
                            activity.updateStartMenu();
                            break;
                        }
                        case 80:{
                            if(AddToDesktop){
                                setupStatus.setText(activity.words.get("Adding a program icon to the desktop ..."));
                            }
                            else {
                                setupProgress = 86;
                            }
                            break;
                        }
                        case 85:{
                            if(AddToDesktop){
                                setupStatus.setText(activity.words.get("The program icon has been added to the desktop."));
                                activity.styleSave.setDesktopProgramList(activity.styleSave.getDesktopProgramList()+programForSetup+",");
                                activity.updateDesktop();
                            }
                            break;
                        }
                        case 86:{
                            if(InstallAdditionalSoft){
                                setupStatus.setText(activity.words.get("Installing additional software ..."));
                                InstallAdditionalSoftLogic();
                            }
                            else {
                                setupProgress = 94;
                            }
                            break;
                        }
                        case 93:{
                            if(InstallAdditionalSoft) {
                                setupStatus.setText(activity.words.get("Installation of additional software is complete."));
                                break;
                            }
                        }
                        case 94:{
                            setupStatus.setText(activity.words.get("Clearing cache ..."));
                            break;
                        }
                        case 97:{
                            setupStatus.setText(activity.words.get("Clearing cache completed"));
                            break;
                        }
                        case 100:{
                            setupStatus.setText(activity.words.get("Installation completed"));
                            timer.cancel();
                            break;
                        }
                    }
                    progressBar.setProgress(setupProgress);
                    progressInPercent.setText(setupProgress+"%");
                });
            }
        };
        if(diskHashMap.get(diskID).get("Тип").equals("HDD"))
            timer.scheduleAtFixedRate(timerTask,0, (long) (800*ProgramListAndData.programSize.get(programForSetup)));
        else
            timer.scheduleAtFixedRate(timerTask,0, (long) (400*ProgramListAndData.programSize.get(programForSetup)));
    }

    @Override
    public void closeProgram(int mode) {
        setupProgress = 0;
        super.closeProgram(mode);
    }

    private void InstallAdditionalSoftLogic(){
        switch (programForSetup){
            case "CPU Overclocking":{
                CMD cmd = new CMD(activity);
                cmd.setType(CMD.AUTO);
                cmd.commandList = new String[]{
                        "driver.prepare.select_storage_id:"+diskID,
                        "driver.prepare.extended:true",
                        "driver.install.for_cpu",
                };
                cmd.openProgram();
                break;
            }
            case "RAM Overclocking":{
                CMD cmd = new CMD(activity);
                cmd.setType(CMD.AUTO);
                cmd.commandList = new String[]{
                        "driver.prepare.select_storage_id:"+diskID,
                        "driver.prepare.extended:true",
                        "driver.install.for_ram",
                };
                cmd.openProgram();
                break;
            }
            case "GPU Overclocking":{
                CMD cmd = new CMD(activity);
                cmd.setType(CMD.AUTO);
                cmd.commandList = new String[]{
                        "driver.prepare.select_storage_id:"+diskID,
                        "driver.prepare.extended:true",
                        "driver.install.for_gpu",
                };
                cmd.openProgram();
                break;
            }
        }
    }
}
