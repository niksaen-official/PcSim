package com.niksaen.pcsim.program;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.deviceManager.DeviceManager;
import com.niksaen.pcsim.program.fileManager.FileManager;
import com.niksaen.pcsim.program.musicplayer.MusicPlayer;
import com.niksaen.pcsim.program.notepad.Notepad;
import com.niksaen.pcsim.program.paint.Paint;
import com.niksaen.pcsim.program.styleSettings.StyleSettings;
import com.niksaen.pcsim.program.videoplayer.VideoPlayer;

import java.util.HashMap;

/*
* Базовый класс для создания программ
* */
public class Program {
    public static String[] programList = {
        "Benchmark",
            "Browser",
            "CPU Overclocking",
            "RAM Overclocking",
            "GPU Overclocking",
            "Temperature Viewer",
            "View Power Supply Load",
            "Device manager",
            "File manager",
            "Music player",
            "Video player",
            "Notepad",
            "Personalization",
            "Paint"
    };
    public static HashMap<String,Integer> programIcon = new HashMap<>();
    static {
        programIcon.put("Benchmark",R.drawable.icon_benchmark);
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
        programIcon.put("Personalization",R.drawable.icon_default);
        programIcon.put("Paint",R.drawable.icon_paint);
    }

    public HashMap<String,Program> programHashMap = new HashMap<>();
    public void initHashMap(MainActivity activity){
        programHashMap.put("Benchmark",new Benchmark(activity));
        programHashMap.put("Browser",new Browser(activity));
        programHashMap.put("CPU Overclocking",new CPU_Tweaker(activity));
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
    }

    /* переменная используется для определения статуса программы
     * status = -1 - означает что программа закрыта
     * status = 0 - означает что программа открыта
     * status = 1 - означает что программа свёрнута*/
    public int status = -1;
    public void openProgram(){}
    public void closeProgram(){}
}
