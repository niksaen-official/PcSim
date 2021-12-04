package com.niksaen.pcsim.program.appDownloader;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.ProgramListAndData;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SetupWindow extends Program {
    private String programForSetup;
    private String disk;
    private boolean AddToDesktop;
    private boolean InstallAdditionalSoft;

    public void setProgramForSetup(String programForSetup) { this.programForSetup = programForSetup; }
    public void setDisk(String disk) { this.disk = disk; }
    public void setAddToDesktop(boolean addToDesktop) { AddToDesktop = addToDesktop; }
    public void setInstallAdditionalSoft(boolean installAdditionalSoft) { InstallAdditionalSoft = installAdditionalSoft; }

    public SetupWindow(MainActivity activity) {
        super(activity);
        Title = "Installation Wizard";
        ValueRam = new int[]{70,90};
        ValueVideoMemory = new int[]{90,120};
    }

    private LinearLayout main;
    private ProgressBar progressBar;
    private TextView setupStatus;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_setup,null);
        initView();
        style();
        setup();
        super.initProgram();
    }
    private void initView(){
        main = mainWindow.findViewById(R.id.main);
        progressBar = mainWindow.findViewById(R.id.progressBar);
        setupStatus = mainWindow.findViewById(R.id.setupStatus);
    }
    private void style(){
        main.setBackgroundColor(activity.styleSave.ThemeColor1);
        progressBar.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
        LayerDrawable progressBarBackground = (LayerDrawable) progressBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN);

        setupStatus.setBackgroundColor(activity.styleSave.ThemeColor2);
        setupStatus.setTextColor(activity.styleSave.TextColor);
        setupStatus.setTypeface(activity.font);
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

    private int setupProgress = 0,downloadArchiveProgress = 0,unpackingProgress = 0,installAdditionalSoftProgress = 0;
    private String setupText = "Подготовка к установке...\n";

    TimerTask downloadArchiveTask,setupTaskMain,unpackingArchiveTask,installAdditionalSoftTask;
    Timer timer;
    private void setup(){
        initDiskMap();

        timer = new Timer();
        downloadArchiveTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    downloadArchiveProgress++;
                    if(downloadArchiveProgress == 1){
                        setupText += "Загружено: " + downloadArchiveProgress + "%\n";
                    }else {
                        setupText = setupText.replace("Загружено: " + (downloadArchiveProgress - 1) + "%\n", "Загружено: " + downloadArchiveProgress + "%\n");
                    }
                    if(downloadArchiveProgress%10 == 0){
                        setupProgress++;
                        progressBar.setProgress(setupProgress);
                    }
                    if(downloadArchiveProgress == 100){
                        setupText += "Архивы загружены!\n";
                        timer.cancel();
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                setupTaskMain.run();
                            }
                        }, 0, 1000);
                    }
                    setupStatus.setText(setupText);
                });
            }
        };

        unpackingArchiveTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    unpackingProgress++;
                    if(unpackingProgress == 1){
                        setupText += "Распаковано: " + unpackingProgress + "%\n";
                    }else {
                        setupText = setupText.replace("Распаковано: " + (unpackingProgress - 1) + "%\n", "Распаковано: " + unpackingProgress + "%\n");
                    }
                    if(unpackingProgress%10 == 0){
                        setupProgress++;
                        progressBar.setProgress(setupProgress);
                    }
                    if(unpackingProgress == 100){
                        setupText += "Архивы распакованы!\n";
                        diskHashMap.get(disk).put("Содержимое",diskHashMap.get(disk).get(("Содержимое"))+programForSetup+",");
                        InstallMainLibs();
                        activity.pcParametersSave.setData(disk,diskHashMap.get(disk));
                        timer.cancel();
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                setupTaskMain.run();
                            }
                        }, 0, 1000);
                    }
                    setupStatus.setText(setupText);
                });
            }
        };
        installAdditionalSoftTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    installAdditionalSoftProgress++;
                    if(installAdditionalSoftProgress == 1){
                        setupText += "Установлено: " + installAdditionalSoftProgress + "%\n";
                    }else {
                        setupText = setupText.replace( "Установлено: " +(installAdditionalSoftProgress - 1) + "%\n",   "Установлено: " +installAdditionalSoftProgress + "%\n");
                    }
                    if(installAdditionalSoftProgress%10 == 0){
                        setupProgress++;
                        progressBar.setProgress(setupProgress);
                    }
                    if(installAdditionalSoftProgress == 100){
                        setupText += "Установка дополнительного программного обеспечения завершена.\n";
                        InstallAdditionalSoftLogic();
                        timer.cancel();
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                setupTaskMain.run();
                            }
                        }, 0, 1000);
                    }
                    setupStatus.setText(setupText);
                });
            }
        };

        setupTaskMain= new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    setupProgress++;
                    switch (setupProgress){
                        case 1:{
                            if(diskHashMap.get(disk).get("Содержимое").contains(programForSetup)){
                                timer.cancel();
                                setupText += "Программа уже установлена на ваш ПК\n";
                            }
                            else {
                                setupText += "Подготовка завершена.\n";
                            }
                            break;
                        }
                        case 2:{
                            setupText += "Проверка на наличие свободного места...\n";
                            break;
                        }
                        // загрузка главного архива
                        case 3:{
                            if(activity.pcParametersSave.getEmptyStorageSpace(diskHashMap.get(disk))>= ProgramListAndData.programSize.get(programForSetup)){
                                setupText += "Места на диске достаточно для установки программы.\nСкачивание архивов...\n";
                                timer.cancel();
                                timer = new Timer();
                                if(diskHashMap.get(disk).get("Тип").equals("HDD")) {
                                    timer.scheduleAtFixedRate(downloadArchiveTask, 0, (long) ((ProgramListAndData.programSize.get(programForSetup) * 70)*10 ));
                                }else {
                                    timer.scheduleAtFixedRate(downloadArchiveTask, 0, (long) ((ProgramListAndData.programSize.get(programForSetup) * 70)*7));
                                }
                            }
                            else {
                                setupText += "Места на диске не достаточно для установки программы!\n";
                                timer.cancel();
                            }
                            break;
                        }
                        // распаковка архива
                        case 14:{
                            setupText+= "Распаковка архивов...\n";
                            break;
                        }
                        case 15:{
                            timer.cancel();
                            timer = new Timer();
                            if(diskHashMap.get(disk).get("Тип").equals("HDD")) {
                                timer.scheduleAtFixedRate(unpackingArchiveTask, 0, (long) ((ProgramListAndData.programSize.get(programForSetup) * 300)));
                            }else {
                                timer.scheduleAtFixedRate(unpackingArchiveTask, 0, (long) ((ProgramListAndData.programSize.get(programForSetup) * 210)));
                            }
                            break;
                        }
                        // установка доп софта
                        case 26:{
                            if(InstallAdditionalSoft){
                                setupText += "Установка дополнительного программного обеспечения...\n";
                                timer.cancel();
                                timer = new Timer();
                                if(diskHashMap.get(disk).get("Тип").equals("HDD")) {
                                    timer.scheduleAtFixedRate(installAdditionalSoftTask, 0, 300);
                                }else {
                                    timer.scheduleAtFixedRate(installAdditionalSoftTask, 0, 200);
                                }
                            }
                            else {
                                setupProgress = 36;
                            }
                            break;
                        }
                        // добавление значка на главный экран
                        case 37:{
                            if(AddToDesktop){
                                setupText += "Добавление значка программы на рабочий стол...\n";
                            }
                            else {
                                setupProgress = 39;
                            }
                            break;
                        }
                        case 38:{
                            if(AddToDesktop){
                                setupText += "Значок программы добавлен на рабочий стол.\n";
                                activity.styleSave.setDesktopProgramList(activity.styleSave.getDesktopProgramList()+programForSetup+",");
                                activity.updateDesktop();
                            }
                            break;
                        }
                        // окончание установки
                        case 39:{
                            setupText += "Очистка кэша...\n";
                            break;
                        }
                        case 40:{
                            setupText += "Очистка кэша завершена\n";
                            break;
                        }
                        case 41:{
                            setupText += "Установка завершена";
                            activity.updateAppList();
                            break;
                        }
                        case 42:{
                            timer.cancel();
                            break;
                        }
                    }
                    setupStatus.setText(setupText);
                    progressBar.setProgress(setupProgress);
                });
            }
        };
        timer.scheduleAtFixedRate(setupTaskMain,0,1500);
    }

    @Override
    public void closeProgram(int mode) {
        setupProgress = 0;
        timer.cancel();
        setupText = "Подготовка к установке...\n";
        super.closeProgram(mode);
    }

    private void InstallAdditionalSoftLogic(){
        switch (programForSetup){
            case "Paint":
            case"Notepad":{
                if(!StringArrayWork.ArrayListToString(activity.apps).contains("File manager")) {
                    diskHashMap.get(disk).put("Содержимое", diskHashMap.get(disk).get(("Содержимое")) + Program.AdditionalSoftPrefix + "File manager: OpenLibs," + Program.AdditionalSoftPrefix + "File manager: SaveLibs,");
                }
                break;
            }
            case "Music player":
            case "Video player": {
                if(!StringArrayWork.ArrayListToString(activity.apps).contains("File manager")) {
                    diskHashMap.get(disk).put("Содержимое", diskHashMap.get(disk).get(("Содержимое")) + Program.AdditionalSoftPrefix + "File manager: OpenLibs,");
                }
                break;
            }
            case "CPU Overclocking":{
                diskHashMap.get(disk).put("Содержимое",diskHashMap.get(disk).get(("Содержимое"))+Program.DriversPrefix+"CPU_PRO,");
                break;
            }
            case "RAM Overclocking":{
                diskHashMap.get(disk).put("Содержимое",diskHashMap.get(disk).get(("Содержимое"))+Program.DriversPrefix+"RAM_PRO,");
                break;
            }
            case "GPU Overclocking":{
                diskHashMap.get(disk).put("Содержимое",diskHashMap.get(disk).get(("Содержимое"))+Program.DriversPrefix+"GPU_PRO,");
                break;
            }
            case "File manager":{
                diskHashMap.get(disk).put("Содержимое", diskHashMap.get(disk).get(("Содержимое")) + Program.AdditionalSoftPrefix + "File manager: Image Viewer,"+ Program.AdditionalSoftPrefix + "File manager: Text Viewer,");
            }
        }
        activity.pcParametersSave.setData(disk,diskHashMap.get(disk));
    }
    private void InstallMainLibs(){
        switch (programForSetup){
            case "File manager":{
                diskHashMap.get(disk).put("Содержимое", diskHashMap.get(disk).get(("Содержимое")) + Program.AdditionalSoftPrefix + "File manager: OpenLibs" + "," + Program.AdditionalSoftPrefix + "File manager: SaveLibs" + ",");
                break;
            }
        }
    }
}
