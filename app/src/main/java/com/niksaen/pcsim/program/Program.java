package com.niksaen.pcsim.program;

import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.PortableView;
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
            "Notepad",
            "Personalization",
            "Paint",
            "Task Manager"
    };
    /** иконки программ*/
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
        programIcon.put("Image Viewer",R.drawable.image_file);
        programIcon.put("Text Viewer",R.drawable.text_file);
        programIcon.put("Opening file",R.drawable.folder_icon);
        programIcon.put("Saving a file",R.drawable.folder_icon);
        programIcon.put("Warning",R.drawable.icon_warning);
        programIcon.put("Task Manager",R.drawable.icon_taskmanager);
    }

    /** код программ*/
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
        programHashMap.put("Task Manager", activity.taskManager);
    }

    /** сколько программа использует оперативной памяти в мб*/
    public int RamUse = 256;

    /** сколько программе требуется потоков*/
    public int StreamUse = 1;

    /** сколько программа использует видопамяти в мб*/
    public int VideoMemoryUse = 128;

    /** название программы на английском*/
    public String Title;
    public MainActivity activity;

    /** главное окно программы*/
    public View mainWindow;

    /**@param  status  используется для определения статуса программы
     * в значении -1 - означает что программа закрыта
     * в значении 0 - означает что программа открыта
     * в значении 1 - означает что программа свёрнута*/
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
    public void initProgram(){
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
                    mainWindow.setOnTouchListener(null);
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    v.setBackgroundResource(activity.styleSave.FullScreenMode2ImageRes);
                    buttonClicks = 0;
                }
        });
        buttonRollUp.setOnClickListener(v->rollUpProgram(1));

        //настройка стиля программы
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        titleTextView.setText(activity.words.get(Title));
        titleTextView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        titleTextView.setTextColor(activity.styleSave.TitleColor);
        buttonClose.setBackgroundResource(activity.styleSave.CloseButtonImageRes);
        buttonFullscreenMode.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);
        buttonRollUp.setBackgroundResource(activity.styleSave.RollUpButtonImageRes);
    }

    /** открытие программы после инициализации*/
    public void openProgram(){
        if(status == -1) {
            initProgram();
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
