package com.niksaen.pcsim.program;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.games.tic_tac_toe.TicTacToe;
import com.niksaen.pcsim.program.appDownloader.AppDownloader;
import com.niksaen.pcsim.program.calculator.Calculator;
import com.niksaen.pcsim.program.deviceManager.DeviceManager;
import com.niksaen.pcsim.program.diskManager.DiskManager;
import com.niksaen.pcsim.program.fileManager.FileManager;
import com.niksaen.pcsim.program.gStore.GStoreMain;
import com.niksaen.pcsim.program.miner.Miner;
import com.niksaen.pcsim.program.musicplayer.MusicPlayer;
import com.niksaen.pcsim.program.notepad.Notepad;
import com.niksaen.pcsim.program.paint.Paint;
import com.niksaen.pcsim.program.styleSettings.StyleSettings;
import com.niksaen.pcsim.program.videoplayer.VideoPlayer;

import java.util.HashMap;

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
            "Task Manager",
            "App Downloader",
            "Disk manager",
            "Calculator",
            "GStore"
    };
    /** список платных программ*/
    public static String[] DontFreeAppList = {
            "Tic Tac Toe",
            "CPU Overclocking",
            "RAM Overclocking",
            "GPU Overclocking",
            "Benchmark",
            "Miner"
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
        //установка типа приложения, только для платных
        programType.put("Tic Tac Toe","Game");
        programType.put("CPU Overclocking","Program");
        programType.put("RAM Overclocking","Program");
        programType.put("GPU Overclocking","Program");
        programType.put("Benchmark","Program");
        programType.put("Miner","Program");

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
        programIcon.put( "Warning",R.drawable.icon_warning);
        programIcon.put( "Error",R.drawable.icon_error);
        programIcon.put("Calculator",R.drawable.icon_calculator);
        programIcon.put("Tic Tac Toe",R.drawable.icon_tictactoe);
        programIcon.put("GStore",R.drawable.icon_gamestore);
        programIcon.put("Application description page",R.drawable.icon_gamestore);
        programIcon.put("Confirm Purchase",R.drawable.icon_gamestore);

        //вес програм и игр
        programSize.put("Benchmark",2f);
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
        programSize.put("Personalization",5f);
        programSize.put("Paint",3.9f);
        programSize.put("Task Manager",0.23f);
        programSize.put("App Downloader",0.1f);
        programSize.put("Miner",0.2f);
        programSize.put("Disk manager",0.1f);
        programSize.put("Calculator",0.2f);
        programSize.put( "Tic Tac Toe",3f);
        programSize.put("GStore",0.1f);
    }

    /** классы программ*/
    public HashMap<String,Program> programHashMap = new HashMap<>();
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
        programHashMap.put( "Tic Tac Toe",new TicTacToe(activity));
        programHashMap.put("GStore",new GStoreMain(activity));
    }
}
