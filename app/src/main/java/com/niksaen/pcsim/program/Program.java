package com.niksaen.pcsim.program;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.os.MakOS;
import com.niksaen.pcsim.os.cmd.CMD;

/*
* Базовый класс для создания программ
* */
public class Program {

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

    public static String BACKGROUND = "BACKGROUND";
    public static String FOREGROUND = "FOREGROUND";
    public String Type = FOREGROUND;

    public boolean HidesTaskbar = false;
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
                    mainWindow.setOnTouchListener((v1, event) -> false);
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    v.setBackgroundResource(activity.styleSave.FullScreenMode2ImageRes);
                    buttonClicks = 0;
                }
        });
        buttonRollUp.setOnClickListener(v->rollUpProgram());

        //настройка базовой части стиля программы
        titleTextView.setText(activity.words.get(Title));
        titleTextView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"), Typeface.BOLD);
        titleTextView.setTextColor(activity.styleSave.TitleColor);
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        buttonClose.setBackgroundResource(activity.styleSave.CloseButtonImageRes);
        buttonFullscreenMode.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);
        buttonRollUp.setBackgroundResource(activity.styleSave.RollUpButtonImageRes);
    }

    /** открытие программы*/
    // сначала необходимо инициализировать программу
    public void openProgram(){
        initProgram();
        CurrentRamUse = Others.random(ValueRam[0],ValueRam[1]);
        CurrentVideoMemoryUse = Others.random(ValueVideoMemory[0],ValueVideoMemory[1]);
        if(activity.pcParametersSave.getEmptyRam(activity.programArrayList) > CurrentRamUse) {
            if(activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList)>CurrentVideoMemoryUse) {
                status = 0;
                activity.programArrayList.add(this);
                activity.updateToolbar();
                activity.taskManager.update();
                if(HidesTaskbar) activity.toolbar.setVisibility(View.GONE);
                if (mainWindow.getParent() == null) {
                    activity.layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                } else {
                    mainWindow.setVisibility(View.VISIBLE);
                }
            }
            else {
                CMD cmd = new CMD(activity);
                cmd.commandList = new String[] {
                        "#cmd.error:"+activity.words.get("Not enough video memory"),
                };
                cmd.setType(CMD.AUTO);
                cmd.openProgram();
            }
        }else {
            CMD cmd = new CMD(activity);
            cmd.commandList = new String[] {
                    "#cmd.error:"+activity.words.get("Not enough RAM"),
            };
            cmd.setType(CMD.AUTO);
            cmd.openProgram();
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
        }else if(mode == 0) {
            mainWindow.setVisibility(View.GONE);
            status = -1;
        }
        if(HidesTaskbar) activity.toolbar.setVisibility(View.VISIBLE);
    }

    /** сворачивание программы*/
    public void rollUpProgram(){
        if(mainWindow.getVisibility() == View.VISIBLE){
            mainWindow.setVisibility(View.GONE);
        }else {
            mainWindow.setVisibility(View.VISIBLE);
        }
    }
    public boolean programIsOpen(){
        for(Program program:activity.programArrayList){
            if (program.Title.equals(Title)){
                return true;
            }
        }
        return false;
    }
}
