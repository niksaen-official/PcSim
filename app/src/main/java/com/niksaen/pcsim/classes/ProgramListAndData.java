package com.niksaen.pcsim.classes;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.games.memory.MemoryGame;
import com.niksaen.pcsim.games.mines.Minesweeper;
import com.niksaen.pcsim.games.snake.SnakeGame;
import com.niksaen.pcsim.games.tic_tac_toe.TicTacToe;
import com.niksaen.pcsim.os.LiriOS;
import com.niksaen.pcsim.os.MakOS;
import com.niksaen.pcsim.os.NapiOS;
import com.niksaen.pcsim.program.Benchmark;
import com.niksaen.pcsim.program.Browser;
import com.niksaen.pcsim.program.CPU_Overclocking;
import com.niksaen.pcsim.program.GPU_Overclocking;
import com.niksaen.pcsim.program.InstallFD;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.RAM_Overclocking;
import com.niksaen.pcsim.program.TemperatureViewer;
import com.niksaen.pcsim.program.ViewPowerSupplyLoad;
import com.niksaen.pcsim.program.antivirus.Antivirus;
import com.niksaen.pcsim.program.appDownloader.AppDownloader;
import com.niksaen.pcsim.program.calculator.Calculator;
import com.niksaen.pcsim.program.deviceManager.DeviceManager;
import com.niksaen.pcsim.program.diskManager.DiskManager;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.program.fileManager.FileManager;
import com.niksaen.pcsim.program.gStore.GStoreMain;
import com.niksaen.pcsim.program.hSoftStore.HSoftStore;
import com.niksaen.pcsim.program.miner.Miner;
import com.niksaen.pcsim.program.musicplayer.MusicPlayer;
import com.niksaen.pcsim.program.notepad.Notepad;
import com.niksaen.pcsim.program.paint.Paint;
import com.niksaen.pcsim.program.styleSettings.StyleSettings;
import com.niksaen.pcsim.program.videoplayer.VideoPlayer;
import com.niksaen.pcsim.viruses.CADARTC;
import com.niksaen.pcsim.viruses.DD;
import com.niksaen.pcsim.viruses.DVC;
import com.niksaen.pcsim.viruses.DWM;
import com.niksaen.pcsim.viruses.FAOYR;
import com.niksaen.pcsim.viruses.FARDOTC;
import com.niksaen.pcsim.viruses.LTMP;
import com.niksaen.pcsim.viruses.OCP10TASDC;
import com.niksaen.pcsim.viruses.RAD;
import com.niksaen.pcsim.viruses.RAP;
import com.niksaen.pcsim.viruses.RCS;
import com.niksaen.pcsim.viruses.RTOS;
import com.niksaen.pcsim.viruses.TOC30SAS;

import java.util.HashMap;
import java.util.Locale;

public class ProgramListAndData {

    /** список бесплатных программ*/
    public static String[] FreeAppList = {
            "Browser",
            "Temperature Viewer",
            "View Power Supply Load",
            "Device manager",
            "File manager",
            "Music player",
            "Video player",
            "Personalization",
            "Paint",
            "Notepad",
            "Task Manager",
            "App Downloader",
            "Disk manager",
            "Calculator",
            "GStore",
            "InstallFD"
    };
    /**список платных программ*/
    public static String[] DontFreeAppList = {
            "Tic Tac Toe",
            "CPU Overclocking",
            "RAM Overclocking",
            "GPU Overclocking",
            "Benchmark",
            "Miner",
            "Driver installer",
            "Minesweeper",
            "Snake",
            "Memory"
    };

    public static HashMap<String,String> programType = new HashMap<>();
    public static HashMap<String,Integer> programPrice = new HashMap<>();
    public static HashMap<String,Integer> programIcon = new HashMap<>();
    public static HashMap<String,Float> programSize = new HashMap<>();
    static {
        //цены для приложений
        programPrice.put("Tic Tac Toe",600);
        programPrice.put("CPU Overclocking",900);
        programPrice.put("RAM Overclocking",780);
        programPrice.put("GPU Overclocking",690);
        programPrice.put("Benchmark",400);
        programPrice.put("Miner",200);
        programPrice.put("Driver installer",1000);
        programPrice.put("Minesweeper",2500);
        programPrice.put("Snake",2399);
        programPrice.put("Memory",1599);
        //установка типа приложения, только для платных
        programType.put("Tic Tac Toe","Game");
        programType.put("Minesweeper","Game");
        programType.put("CPU Overclocking","Program");
        programType.put("RAM Overclocking","Program");
        programType.put("GPU Overclocking","Program");
        programType.put("Benchmark","Program");
        programType.put("Miner","Program");
        programType.put("Driver installer","Program");
        programType.put("Snake","Game");
        programType.put("Memory","Game");

        // иконки программ и игр
        programIcon.put("Benchmark", R.drawable.icon_benchmark);
        programIcon.put("Browser",R.drawable.icon_browser);
        programIcon.put("CPU Overclocking",R.drawable.icon_cputweaker);
        programIcon.put("RAM Overclocking",R.drawable.icon_ramoverclocking);
        programIcon.put("GPU Overclocking",R.drawable.icon_gpuiverclocking);
        programIcon.put("Temperature Viewer",R.drawable.icon_temperatureviewer);
        programIcon.put("View Power Supply Load",R.drawable.icon_viewpsuload);
        programIcon.put("Device manager",R.drawable.icon_checkiron);
        programIcon.put("File manager",R.drawable.icon_filemanager);
        programIcon.put("Music player",R.drawable.icon_musicplayer);
        programIcon.put("Video player",R.drawable.icon_videoplayer);
        programIcon.put("Notepad",R.drawable.icon_notepad);
        programIcon.put("Personalization",R.drawable.icon_personalization);
        programIcon.put("Paint",R.drawable.icon_paint);
        programIcon.put("Image Viewer",R.drawable.image_file);
        programIcon.put("Text Viewer",R.drawable.text_file);
        programIcon.put("Opening file",R.drawable.folder_icon);
        programIcon.put("Saving a file",R.drawable.folder_icon);
        programIcon.put("Task Manager",R.drawable.icon_taskmanager);
        programIcon.put("App Downloader",R.drawable.icon_downloader);
        programIcon.put("Installation Wizard",R.drawable.icon_downloader);
        programIcon.put("Miner",R.drawable.icon_default);
        programIcon.put("Disk manager",R.drawable.icon_default);
        programIcon.put("App manager",R.drawable.icon_default);
        programIcon.put("Warning",R.drawable.icon_warning);
        programIcon.put("Error",R.drawable.icon_error);
        programIcon.put("Confirm",R.drawable.icon_default);
        programIcon.put("Calculator",R.drawable.icon_calculator);
        programIcon.put("Tic Tac Toe",R.drawable.icon_tictactoe);
        programIcon.put("GStore",R.drawable.icon_gamestore);
        programIcon.put("Application description page",R.drawable.icon_gamestore);
        programIcon.put("Confirm Purchase",R.drawable.icon_gamestore);
        programIcon.put("CMD",R.drawable.cmd_icon);
        programIcon.put(NapiOS.TITLE,R.drawable.napi_os_logo);
        programIcon.put(LiriOS.TITLE,R.drawable.liri_os_logo);
        programIcon.put(MakOS.TITLE,R.drawable.mak_os_logo);
        programIcon.put("Driver installer",R.drawable.icon_downloader);
        programIcon.put("Minesweeper",R.drawable.minersweeper_icon);
        programIcon.put("Snake",R.drawable.snake_icon);
        programIcon.put("Memory",R.drawable.memory_icon);
        programIcon.put("HSoftStore",R.drawable.icon_hsoftstore);
        programIcon.put("Antivirus",R.drawable.antivirus);
        programIcon.put("InstallFD",R.drawable.icon_default);

        //вес програм и игр
        programSize.put("InstallFD",0.75f);
        programSize.put("Benchmark",2f);
        programSize.put("HSoftStore",1f);
        programSize.put("Browser",0.5f);
        programSize.put("CPU Overclocking",1f);
        programSize.put("RAM Overclocking",1f);
        programSize.put("GPU Overclocking",1.5f);
        programSize.put("Temperature Viewer",0.25f);
        programSize.put("View Power Supply Load",0.25f);
        programSize.put("Device manager",0.1f);
        programSize.put("File manager",0.65f);
        programSize.put("Music player",1.9f);
        programSize.put("Video player",1.6f);
        programSize.put("Notepad",1.1f);
        programSize.put("Personalization",1.5f);
        programSize.put("Paint",3.9f);
        programSize.put("Task Manager",0.23f);
        programSize.put("App Downloader",0.1f);
        programSize.put("Miner",0.2f);
        programSize.put("Disk manager",0.1f);
        programSize.put("Calculator",0.2f);
        programSize.put("Tic Tac Toe",3f);
        programSize.put("GStore",0.1f);
        programSize.put("NapiOS Installer",8f);
        programSize.put("NapiOS",32f);
        programSize.put("LiriOS Installer",5f);
        programSize.put("LiriOS",20f);
        programSize.put("Driver installer",0.2f);
        programSize.put("Minesweeper",4.2f);
        programSize.put("Snake",2.4f);
        programSize.put("Memory",1f);
        programSize.put("MakOS Installer", 6f);
        programSize.put("MakOS",24f);
        programSize.put("virus.ocp10tasdc",0.1f);
        programSize.put("virus.fardotc",0.1f);
        programSize.put("virus.faoyr",0.1f);
        programSize.put("virus.rap",0.1f);
        programSize.put("virus.rad",0.1f);
        programSize.put("virus.rtos",0.1f);
        programSize.put("virus.ltmp",0.1f);
        programSize.put("virus.dwm",0.1f);
        programSize.put("virus.dd",0.1f);
        programSize.put("virus.dvc",0.1f);
        programSize.put("virus.cadartc",0.1f);
        programSize.put("virus.rcs",0.1f);
        programSize.put("virus.toc30sas",0.1f);
        programSize.put("Antivirus",2f);
        programSize.put("Antivirus Installer",1f);
        programSize.put("HSoftStore Installer", 0.5f);
    }

    /** классы программ*/
    public HashMap<String, Program> programHashMap = new HashMap<>();
    public void initHashMap(MainActivity activity){
        programHashMap.put("Benchmark",new Benchmark(activity));
        programHashMap.put("Browser",new Browser(activity));
        programHashMap.put("CPU Overclocking",new CPU_Overclocking(activity));
        programHashMap.put("RAM Overclocking",new RAM_Overclocking(activity));
        programHashMap.put("GPU Overclocking",new GPU_Overclocking(activity));
        programHashMap.put("Temperature Viewer",new TemperatureViewer(activity));
        programHashMap.put("View Power Supply Load",new ViewPowerSupplyLoad(activity));
        programHashMap.put("Device manager",new DeviceManager(activity));
        programHashMap.put("File manager",new FileManager(activity));
        programHashMap.put("Music player",new MusicPlayer(activity));
        programHashMap.put("Video player",new VideoPlayer(activity));
        programHashMap.put("Notepad",new Notepad(activity));
        programHashMap.put("Personalization",new StyleSettings(activity));
        programHashMap.put("Paint",new Paint(activity));
        programHashMap.put("Task Manager", activity.taskManager);
        programHashMap.put("App Downloader", new AppDownloader(activity));
        programHashMap.put("Miner",new Miner(activity));
        programHashMap.put("Disk manager",new DiskManager(activity));
        programHashMap.put("Calculator",new Calculator(activity));
        programHashMap.put("Tic Tac Toe",new TicTacToe(activity));
        programHashMap.put("GStore",new GStoreMain(activity));
        programHashMap.put("Driver installer",new DriverInstaller(activity));
        programHashMap.put("Minesweeper",new Minesweeper(activity));
        programHashMap.put("Snake",new SnakeGame(activity));
        programHashMap.put("Memory",new MemoryGame(activity));
        programHashMap.put("virus.ocp10tasdc",new OCP10TASDC(activity));
        programHashMap.put("virus.fardotc",new FARDOTC(activity));
        programHashMap.put("virus.faoyr",new FAOYR(activity));
        programHashMap.put("virus.rap",new RAP(activity));
        programHashMap.put("virus.rad",new RAD(activity));
        programHashMap.put("virus.rtos",new RTOS(activity));
        programHashMap.put("virus.ltmp",new LTMP(activity));
        programHashMap.put("virus.dwm",new DWM(activity));
        programHashMap.put("virus.dd", new DD(activity));
        programHashMap.put("virus.dvc",new DVC(activity));
        programHashMap.put("virus.rcs",new RCS(activity));
        programHashMap.put("virus.cadartc",new CADARTC(activity));
        programHashMap.put("virus.toc30sas",new TOC30SAS(activity));
        programHashMap.put("HSoftStore",new HSoftStore(activity));
        programHashMap.put("Antivirus",new Antivirus(activity));
        programHashMap.put("InstallFD",new InstallFD(activity));
    }
}
