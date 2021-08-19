package com.niksaen.pcsim.program.taskManager;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;

public class TaskManager extends Program {
    public TaskManager(MainActivity activity) {
        super(activity);
        Title = "Task Manager";
    }

    @Override
    public void openProgram() {
        super.openProgram();
        initView();
        update();
        style();

        int[] buttonClicks = {0};
        close.setOnClickListener(v -> {
            closeProgram(1);
        });
        fullscreen.setOnClickListener(v -> {
            if (buttonClicks[0] == 0) {
                mainWindow.setScaleX(0.6f);
                mainWindow.setScaleY(0.6f);
                PortableView view = new PortableView(mainWindow);
                v.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);
                buttonClicks[0] = 1;
            } else {
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setOnTouchListener(null);
                mainWindow.setX(0);
                mainWindow.setY(0);
                v.setBackgroundResource(activity.styleSave.FullScreenMode2ImageRes);
                buttonClicks[0] = 0;
            }
        });

        if (mainWindow.getParent() == null) {
            activity.layout.addView(mainWindow, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        } else {
            mainWindow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void closeProgram(int mode) {
        mainWindow.setVisibility(View.GONE);
        super.closeProgram(mode);
    }

    private View mainWindow;
    private TextView title,programName,useRam,useStream,useVideoMemory;
    private Button close,fullscreen,rollUp;
    private ListView programList;
    private LinearLayout main;
    private void initView(){
        mainWindow = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.program_taskmanager,null);
        title = mainWindow.findViewById(R.id.title);
        close = mainWindow.findViewById(R.id.close);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        programList = mainWindow.findViewById(R.id.app_list);
        main = mainWindow.findViewById(R.id.main);
        programName = mainWindow.findViewById(R.id.program_name);
        useRam = mainWindow.findViewById(R.id.ram_use);
        useVideoMemory = mainWindow.findViewById(R.id.video_memory_use);
        useStream = mainWindow.findViewById(R.id.stream_use);
    }
    private void style(){
        title.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"),Typeface.BOLD);
        title.setText(activity.words.get("Task Manager"));
        title.setTextColor(activity.styleSave.TitleColor);

        programName.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"));
        programName.setText(activity.words.get("Application"));
        programName.setTextColor(activity.styleSave.TextColor);

        useRam.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"));
        useRam.setText(activity.words.get("Memory"));
        useRam.setTextColor(activity.styleSave.TextColor);

        useStream.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"));
        useStream.setText(activity.words.get("Streams"));
        useStream.setTextColor(activity.styleSave.TextColor);

        useVideoMemory.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/pixelFont.ttf"));
        useVideoMemory.setText(activity.words.get("Video memory"));
        useVideoMemory.setTextColor(activity.styleSave.TextColor);

        close.setBackgroundResource(activity.styleSave.CloseButtonImageRes);
        fullscreen.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);
        rollUp.setBackgroundResource(activity.styleSave.RollUpButtonImageRes);

        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        main.setBackgroundColor(activity.styleSave.ThemeColor1);
        programList.setAdapter(adapter);
    }

    private TaskManagerAdapter adapter;
    public void update(){
        adapter = new TaskManagerAdapter(activity.getBaseContext(),0, activity.programArrayList);
        adapter.BackgroundColor = activity.styleSave.ThemeColor1;
        adapter.TextColor = activity.styleSave.TextColor;
        if(programList != null){
            programList.setAdapter(adapter);
        }
    }
}
