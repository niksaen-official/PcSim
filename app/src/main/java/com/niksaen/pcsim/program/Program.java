package com.niksaen.pcsim.program;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.appDownloader.AppDownloader;
import com.niksaen.pcsim.program.calculator.Calculator;
import com.niksaen.pcsim.program.deviceManager.DeviceManager;
import com.niksaen.pcsim.program.diskManager.DiskManager;
import com.niksaen.pcsim.program.fileManager.FileManager;
import com.niksaen.pcsim.program.miner.Miner;
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
    public static String AdditionalSoftPrefix = "SOFT: ";
    public static String DriversPrefix = "DRIVER: ";

    /** список всех программ*/
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
            "Personalization",
            "Paint",
            "Task Manager",
            "App Downloader",
            "Miner",
            "Disk manager",
            "Calculator"
    };
    public static HashMap<String,Integer> programIcon = new HashMap<>();
    public static HashMap<String,Float> programSize = new HashMap<>();
    static {
        // иконки программ
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
        programIcon.put("Personalization",R.drawable.icon_personalization);
        programIcon.put("Paint",R.drawable.icon_paint);
        programIcon.put("Image Viewer",R.drawable.image_file);
        programIcon.put("Text Viewer",R.drawable.text_file);
        programIcon.put("Opening file",R.drawable.folder_icon);
        programIcon.put("Saving a file",R.drawable.folder_icon);
        programIcon.put("Task Manager",R.drawable.icon_taskmanager);
        programIcon.put("App Downloader",R.drawable.icon_downloader);
        programIcon.put("Installation Wizard",R.drawable.icon_default);
        programIcon.put("Miner",R.drawable.icon_default);
        programIcon.put("Disk manager",R.drawable.icon_default);
        programIcon.put("App manager",R.drawable.icon_default);
        programIcon.put( "Warning",R.drawable.icon_warning);
        programIcon.put( "Error",R.drawable.icon_error);
        programIcon.put("Calculator",R.drawable.icon_default);

        //вес програм
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
        programSize.put("Calculator",0.08f);
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
    }

    /** @param  CurrentRamUse - показывает сколько программа использует оперативной памяти в мб*/
    public int CurrentRamUse;

    /**@param  ValueRam - показывает сколько минимум и максимум оперативной памяти использует программа*/
    public int[] ValueRam = {100,200};

    /**@param  CurrentVideoMemoryUse - показывает сколько программа использует видопамяти в мб*/
    public int CurrentVideoMemoryUse;

    /**@param  ValueVideoMemory - показывает сколько минимум и максимум программа использует видопамяти в мб*/
    public int[] ValueVideoMemory = {100,200};

    /**@param  Title - название программы на английском*/
    public String Title;
    public MainActivity activity;

    /** @param  mainWindow - главное окно программы*/
    public View mainWindow;

    /**@param  status  используется для определения статуса программы
     *-1 - означает что программа закрыта
     *0 - означает что программа открыта
     *1 - означает что программа свёрнута*/
    public int status = -1;

    public Program(MainActivity activity){
        this.activity = activity;
    }

    public Button buttonClose;  /** кнопка закрытия программы*/
    public Button buttonFullscreenMode;  /** кнопка полноэкранного режима*/
    public Button buttonRollUp;  /** кнопка сворачивания программы*/
    public TextView titleTextView; /** заголовок программы */

    private int buttonClicks = 0;
    /** инициализация программы перед открытием
     * т.е. применение стилей и настройка логики*/
    @SuppressLint("ClickableViewAccessibility")
    public void initProgram(){
        titleTextView = mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);

        CurrentRamUse = Others.random(ValueRam[0],ValueRam[1]);
        CurrentVideoMemoryUse = Others.random(ValueVideoMemory[0],ValueVideoMemory[1]);
        // настройка кнопок
        buttonClose.setOnClickListener(v->closeProgram(1));
        buttonFullscreenMode.setOnClickListener(v -> {
                if (buttonClicks== 0) {
                    mainWindow.setScaleX(0.6f);
                    mainWindow.setScaleY(0.6f);
                    PortableView view = new PortableView(mainWindow);
                    v.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);
                    buttonClicks = 1;
                } else {
                    mainWindow.setScaleX(1);
                    mainWindow.setScaleY(1);
                    mainWindow.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    v.setBackgroundResource(activity.styleSave.FullScreenMode2ImageRes);
                    buttonClicks = 0;
                }
        });
        buttonRollUp.setOnClickListener(v->rollUpProgram(1));

        //настройка базовой части стиля программы
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        titleTextView.setText(activity.words.get(Title));
        titleTextView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        titleTextView.setTextColor(activity.styleSave.TitleColor);
        buttonClose.setBackgroundResource(activity.styleSave.CloseButtonImageRes);
        buttonFullscreenMode.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);
        buttonRollUp.setBackgroundResource(activity.styleSave.RollUpButtonImageRes);
    }

    /** открытие программы*/
    // сначала необходимо инициализировать программу
    public void openProgram(){
        initProgram();
        if(activity.pcParametersSave.getEmptyRam(activity.programArrayList) > CurrentRamUse) {
            if(activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList)>CurrentVideoMemoryUse) {
                if (status == -1) {
                    status = 0;
                    activity.programArrayList.add(this);
                    activity.updateToolbar();
                    activity.taskManager.update();
                    if (mainWindow.getParent() == null) {
                        activity.layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    } else {
                        mainWindow.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    /** закрытие программы
     *  @param mode
     * в значении 1 используется для закрытия программы во время работы пк,
     * в значении 0 используется для закрытия программы при прекращении работы пк */
    public void closeProgram(int mode){
        if(mode == 1) {
            status = -1;
            activity.programArrayList.remove(this);
            activity.updateToolbar();
            activity.taskManager.update();
            mainWindow.setVisibility(View.GONE);
        }else if(status == 0){
            mainWindow.setVisibility(View.GONE);
            status = -1;
        }
    }

    /** сворачивание программы
     * @param mode
     * в значении 1 используется для сворачивания
     * в значении 0 используется для развертывания*/
    public void rollUpProgram(int mode){
        if(mode == 1){
            mainWindow.setVisibility(View.GONE);
        }
        else{
            mainWindow.setVisibility(View.VISIBLE);
        }
    }
}
