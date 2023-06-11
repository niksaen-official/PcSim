package com.niksaen.pcsim.program.styleSettings;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.ProgressBarStylisation;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.os.MakOS;
import com.niksaen.pcsim.os.NapiOS;
import com.niksaen.pcsim.program.Program;

public class StyleSettings extends Program {
    String SettingsGroup = "",BackgroundType="";

    public StyleSettings(MainActivity activity){
        super(activity);
        this.Title = "Personalization";
        ValueRam = new int[]{20,35};
        ValueVideoMemory = new int[]{40,70};
    }
    //main view
    ExpandableListView mainMenu;
    RecyclerView secondMenu;
    ConstraintLayout content;
    Button saveButton;
    TextView secondTitle;

    //test view
    ConstraintLayout testBackground,testWindow;
    LinearLayout testContent;
    View testLaunch,testToolbar;
    TextView appNameLaunch,appNameToolbar;
    ImageView appIconLaunch,appIconToolbar;
    TextView testTitle,testText,testGreeting,desktopText;
    Button testCloseButton,testFullscreenModeButton,testRollUpButton;
    Button testButton;
    SeekBar testSeekBar;
    ProgressBar testProgressBar;
    LinearLayout switchGroup,switchGroup1;
    Switch switch1,switch2,switch3,switch4,switch5;

    private void initView(){
        mainWindow = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.program_style_settings,null);
        mainMenu = mainWindow.findViewById(R.id.menu);
        secondMenu = mainWindow.findViewById(R.id.secondMenu);
        content = mainWindow.findViewById(R.id.content);
        secondTitle = mainWindow.findViewById(R.id.secondTitle);
        saveButton = mainWindow.findViewById(R.id.save);

        testBackground = mainWindow.findViewById(R.id.testBackground);
        testWindow = mainWindow.findViewById(R.id.windowTest);
        testContent = mainWindow.findViewById(R.id.testContent);
        testTitle = mainWindow.findViewById(R.id.testTitle);
        testText = mainWindow.findViewById(R.id.testText);
        testButton = mainWindow.findViewById(R.id.testButton);
        testLaunch = mainWindow.findViewById(R.id.launch);
        testToolbar = mainWindow.findViewById(R.id.toolbar);
        testGreeting = mainWindow.findViewById(R.id.testGreeting);
        testSeekBar = mainWindow.findViewById(R.id.testSeekBar);
        testProgressBar = mainWindow.findViewById(R.id.testProgressBar);
        testCloseButton = mainWindow.findViewById(R.id.testButton1);
        testFullscreenModeButton = mainWindow.findViewById(R.id.testButton2);
        testRollUpButton = mainWindow.findViewById(R.id.testButton3);

        switchGroup = mainWindow.findViewById(R.id.switchGroup);
        switchGroup1 = mainWindow.findViewById(R.id.switchGroup1);
        switch1 = mainWindow.findViewById(R.id.switch1);
        switch2 = mainWindow.findViewById(R.id.switch2);
        switch3 = mainWindow.findViewById(R.id.switch3);
        switch4 = mainWindow.findViewById(R.id.switch4);
        switch5 = mainWindow.findViewById(R.id.switch5);
        appIconLaunch = mainWindow.findViewById(R.id.app_icon_launch);
        appIconToolbar = mainWindow.findViewById(R.id.app_icon_toolbar);
        appNameLaunch = mainWindow.findViewById(R.id.app_name_launch);
        appNameToolbar = mainWindow.findViewById(R.id.app_name_toolbar);
        desktopText = mainWindow.findViewById(R.id.desktopText);
    }

    //adapter
    MenuAdapter menuAdapter;
    BackgroundResourceColorAdapter backgroundResourceColorAdapter;
    BackgroundResourceGradientAdapter backgroundResourceGradientAdapter;
    BackgroundResourceImageAdapter backgroundResourceImageAdapter;
    ColorAdapter windowBackColor,launchBackColor,toolbarBackColor;
    ProgressBarColorAdapter progressBarColorAdapter,progressBarBgColorAdapter;
    ButtonColorAdapter windowButtonColorAdapter;
    TextColorAdapter
            titleTextColorAdapter,
            textColorAdapter,
            buttonTextColorAdapter,
            greetingTextColorAdapter,
            launchTextColorAdapter,
            toolbarTextColorAdapter,
            desktopTextColorAdapter;
    ThemeAdapter themeAdapter;
    SeekBarColorAdapter seekBarColorAdapter,seekThumbColorAdapter,seekBarColorBgAdapter;
    EditTextAdapter editTextAdapter;

    //layout manager
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    private void initAdapters(){
        menuAdapter = new MenuAdapter(activity.getBaseContext());

        backgroundResourceColorAdapter = new BackgroundResourceColorAdapter(activity.getBaseContext(),testBackground);
        backgroundResourceGradientAdapter = new BackgroundResourceGradientAdapter(activity.getBaseContext(), testBackground);
        backgroundResourceImageAdapter = new BackgroundResourceImageAdapter(activity.getBaseContext(), testBackground);
        windowBackColor = new ColorAdapter(activity.getBaseContext(), testWindow, activity.styleSave, 0);
        windowButtonColorAdapter = new ButtonColorAdapter(activity.getBaseContext(), new Button[]{testCloseButton, testFullscreenModeButton, testRollUpButton}, activity.styleSave);
        titleTextColorAdapter = new TextColorAdapter(activity.getBaseContext(), testTitle, activity.styleSave, 0);
        textColorAdapter = new TextColorAdapter(activity.getBaseContext(), testText, activity.styleSave, 1);
        buttonTextColorAdapter = new TextColorAdapter(activity.getBaseContext(), testButton, activity.styleSave);
        themeAdapter = new ThemeAdapter(activity.getBaseContext(), new View[]{testContent, testButton}, activity.styleSave);
        launchBackColor = new ColorAdapter(activity.getBaseContext(), testLaunch, activity.styleSave, 1);

        toolbarBackColor = new ColorAdapter(activity.getBaseContext(), testToolbar, activity.styleSave, 2);
        progressBarColorAdapter = new ProgressBarColorAdapter(activity.getBaseContext(), testProgressBar, activity.styleSave);
        seekBarColorAdapter = new SeekBarColorAdapter(activity.getBaseContext(), testSeekBar, activity.styleSave, 0);
        progressBarBgColorAdapter = new ProgressBarColorAdapter(activity.getBaseContext(), testProgressBar, activity.styleSave,1);
        seekBarColorBgAdapter = new SeekBarColorAdapter(activity.getBaseContext(), testSeekBar, activity.styleSave, 2);
        seekThumbColorAdapter = new SeekBarColorAdapter(activity.getBaseContext(), testSeekBar, activity.styleSave, 1);
        greetingTextColorAdapter = new TextColorAdapter(activity.getBaseContext(), testGreeting, activity.styleSave, 2);
        editTextAdapter = new EditTextAdapter(activity.getBaseContext(), activity.styleSave);
        gridLayoutManager = new GridLayoutManager(activity.getBaseContext(),10);
        linearLayoutManager = new LinearLayoutManager(activity.getBaseContext());
        launchTextColorAdapter = new TextColorAdapter(activity.getBaseContext(), appNameLaunch, activity.styleSave, 4);
        toolbarTextColorAdapter = new TextColorAdapter(activity.getBaseContext(), appNameToolbar, activity.styleSave, 3);
        desktopTextColorAdapter = new TextColorAdapter(activity.getBaseContext(), desktopText, activity.styleSave, 5);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    private void style(){
        //set font
        saveButton.setTypeface(activity.font,Typeface.BOLD);
        secondTitle.setTypeface(activity.font,Typeface.BOLD);
        testButton.setTypeface(activity.font,Typeface.BOLD);
        testGreeting.setTypeface(activity.font,Typeface.BOLD);
        testText.setTypeface(activity.font,Typeface.BOLD);
        testTitle.setTypeface(activity.font,Typeface.BOLD);
        switch1.setTypeface(activity.font);
        switch2.setTypeface(activity.font);
        switch3.setTypeface(activity.font);
        switch4.setTypeface(activity.font);

        switchGroup.setVisibility(View.GONE);
        switchGroup1.setVisibility(View.GONE);
        switch1.setTextColor(activity.styleSave.TextColor);
        switch2.setTextColor(activity.styleSave.TextColor);
        switch3.setTextColor(activity.styleSave.TextColor);
        switch4.setTextColor(activity.styleSave.TextColor);
        switch5.setTextColor(activity.styleSave.TextColor);

        //set text
        saveButton.setText(activity.words.get("Will apply"));
        switch1.setText(activity.words.get("Show application icons"));
        switch2.setText(activity.words.get("Show app names"));
        switch3.setText(activity.words.get("Show application icons"));
        switch4.setText(activity.words.get("Show app names"));
        switch5.setText(activity.words.get("Show app names"));
        //set window button style
        testCloseButton.setBackgroundResource(activity.styleSave.CloseButtonImageRes);
        testRollUpButton.setBackgroundResource(activity.styleSave.RollUpButtonImageRes);
        testFullscreenModeButton.setBackgroundResource(activity.styleSave.FullScreenMode1ImageRes);

        //style main
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        content.setBackgroundColor(activity.styleSave.ThemeColor1);
        saveButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        saveButton.setTextColor(activity.styleSave.TextButtonColor);
        secondTitle.setTextColor(activity.styleSave.TextColor);

        //style test
        testBackground.setBackgroundResource(activity.styleSave.BackgroundResource);
        testWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        testTitle.setTextColor(activity.styleSave.TitleColor);
        testContent.setBackgroundColor(activity.styleSave.ThemeColor1);
        testButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        testButton.setTextColor(activity.styleSave.TextButtonColor);
        testText.setTextColor(activity.styleSave.TextColor);
        testLaunch.setBackgroundColor(activity.styleSave.StartMenuColor);
        testToolbar.setBackgroundColor(activity.styleSave.ToolbarColor);
        testGreeting.setText(activity.styleSave.Greeting);
        testGreeting.setTextColor(activity.styleSave.GreetingColor);
        appNameToolbar.setTextColor(activity.styleSave.ToolbarTextColor);
        appNameLaunch.setTextColor(activity.styleSave.StartMenuTextColor);

        ProgressBarStylisation.setStyle(testProgressBar,activity);
        ProgressBarStylisation.setStyle(testSeekBar,activity);

        //menu style
        menuAdapter.textColor = activity.styleSave.TextColor;
        menuAdapter.backgroundGroupColor = activity.styleSave.ThemeColor1;
        menuAdapter.backgroundChildColor = activity.styleSave.ThemeColor2;
        mainMenu.setBackgroundColor(activity.styleSave.ThemeColor1);
        mainMenu.setAdapter(menuAdapter);

        //adapter style
        themeAdapter.BackgroundColor = activity.styleSave.ThemeColor1;
        windowButtonColorAdapter.backgroundColor = activity.styleSave.ThemeColor1;

        switch1.setChecked(activity.styleSave.StartMenuAppIconVisible);
        switch2.setChecked(activity.styleSave.StartMenuAppNameVisible);
        switch3.setChecked(activity.styleSave.ToolbarAppIconVisible);
        switch4.setChecked(activity.styleSave.ToolbarAppNameVisible);
        switch5.setChecked(activity.styleSave.DesktopAppNameVisible);
        if(activity.styleSave.DesktopAppNameVisible) desktopText.setVisibility(View.VISIBLE);
        else desktopText.setVisibility(View.GONE);

        launchAndToolbarPreviews();
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!switch2.isChecked() && !isChecked)
                switch2.setChecked(!switch2.isChecked());
            launchAndToolbarPreviews();
        });
        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!switch1.isChecked() && !isChecked)
                switch1.setChecked(!switch1.isChecked());
            launchAndToolbarPreviews();
        });
        switch3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!switch4.isChecked() && !isChecked)
                switch4.setChecked(!switch4.isChecked());
            launchAndToolbarPreviews();
        });
        switch4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(!switch3.isChecked() && !isChecked)
                switch3.setChecked(!switch3.isChecked());
            launchAndToolbarPreviews();
        });
        switch5.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked)desktopText.setVisibility(View.VISIBLE);
            else desktopText.setVisibility(View.GONE);
        }));

        ColorStateList thumbStateList = ColorStateList.valueOf(activity.styleSave.TextColor);
        ColorStateList trackStateList = ColorStateList.valueOf(activity.styleSave.ThemeColor3);
        switch1.setThumbTintList(thumbStateList);
        switch1.setTrackTintList(trackStateList);
        switch2.setThumbTintList(thumbStateList);
        switch2.setTrackTintList(trackStateList);
        switch3.setThumbTintList(thumbStateList);
        switch3.setTrackTintList(trackStateList);
        switch4.setThumbTintList(thumbStateList);
        switch4.setTrackTintList(trackStateList);
        switch5.setThumbTintList(thumbStateList);
        switch5.setTrackTintList(trackStateList);
    }
    public void initProgram(){
        initView();
        initAdapters();
        style();

        mainMenu.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            //settings background
            if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Background"))) {
                switch5.setVisibility(View.GONE);
                switchGroup.setVisibility(View.GONE);
                switchGroup1.setVisibility(View.GONE);
                SettingsGroup = "Background";
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
                testBackground.setBackgroundResource(activity.styleSave.BackgroundResource);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Color"))) {
                    BackgroundType = "Color";
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select background color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(backgroundResourceColorAdapter);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Gradient"))) {
                    BackgroundType = "Gradient";
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select gradient background"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(backgroundResourceGradientAdapter);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Wallpaper"))) {
                    BackgroundType = "Image";
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select background image"));
                    secondMenu.setLayoutManager(new GridLayoutManager(activity.getBaseContext(), 2));
                    secondMenu.setAdapter(backgroundResourceImageAdapter);
                }
            }
            //settings window
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Window"))) {
                switch5.setVisibility(View.GONE);
                switchGroup.setVisibility(View.GONE);
                switchGroup1.setVisibility(View.GONE);
                testWindow.setVisibility(View.VISIBLE);
                testGreeting.setVisibility(View.GONE);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Window Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select window color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(windowBackColor);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Button Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select the color of the button"));
                    secondMenu.setLayoutManager(linearLayoutManager);
                    secondMenu.setAdapter(windowButtonColorAdapter);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Title Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select a title color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(titleTextColorAdapter);
                }
            }
            //settings theme
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Colors"))) {
                switch5.setVisibility(View.GONE);
                switchGroup.setVisibility(View.GONE);
                switchGroup1.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
                testSeekBar.setVisibility(View.GONE);
                testProgressBar.setVisibility(View.GONE);
                testButton.setVisibility(View.VISIBLE);
                testText.setVisibility(View.VISIBLE);
                testWindow.setVisibility(View.VISIBLE);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Theme"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select theme"));
                    secondMenu.setLayoutManager(new GridLayoutManager(activity.getBaseContext(), 2));
                    secondMenu.setAdapter(themeAdapter);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Text Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select text color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(textColorAdapter);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Button text color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select the text color of the button"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(buttonTextColorAdapter);
                }
            }
            //settings launch
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Start"))) {
                switch5.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.VISIBLE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(activity.words.get("Text Color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select text color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(launchTextColorAdapter);
                    switchGroup1.setVisibility(View.GONE);
                    switchGroup.setVisibility(View.GONE);
                }
                else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Color"))) {
                    secondTitle.setText(activity.words.get("Select start menu color"));
                    secondTitle.setVisibility(View.VISIBLE);
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(launchBackColor);
                    switchGroup1.setVisibility(View.GONE);
                    switchGroup.setVisibility(View.GONE);
                }else if(menuAdapter.getChild(groupPosition,childPosition).equals(activity.words.get("Other"))){
                    secondTitle.setText(activity.words.get("Advanced start menu settings"));
                    secondTitle.setVisibility(View.VISIBLE);
                    secondMenu.setAdapter(null);
                    switchGroup.setVisibility(View.VISIBLE);
                    switchGroup1.setVisibility(View.GONE);
                }
            }
            //settings toolbar
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Task bar"))) {
                switch5.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(activity.words.get("Text Color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    switchGroup1.setVisibility(View.GONE);
                    switchGroup.setVisibility(View.GONE);
                    secondTitle.setText(activity.words.get("Select text color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(toolbarTextColorAdapter);
                }
                else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select the color of the task bar"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(toolbarBackColor);
                    switchGroup1.setVisibility(View.GONE);
                    switchGroup.setVisibility(View.GONE);
                }else if(menuAdapter.getChild(groupPosition,childPosition).equals(activity.words.get("Other"))){
                    secondTitle.setText(activity.words.get("Additional taskbar settings"));
                    secondTitle.setVisibility(View.VISIBLE);
                    secondMenu.setAdapter(null);
                    switchGroup1.setVisibility(View.VISIBLE);
                    switchGroup.setVisibility(View.GONE);
                }
            }
            //settings progress bar
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("ProgressBar"))) {
                switch5.setVisibility(View.GONE);
                switchGroup.setVisibility(View.GONE);
                switchGroup1.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
                testSeekBar.setVisibility(View.GONE);
                testText.setVisibility(View.GONE);
                testButton.setVisibility(View.GONE);
                testWindow.setVisibility(View.VISIBLE);
                testProgressBar.setVisibility(View.VISIBLE);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select indicator color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(progressBarColorAdapter);
                }else{
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select background color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(progressBarBgColorAdapter);
                }
            }
            //settings seekbar
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("SeekBar"))) {
                switch5.setVisibility(View.GONE);
                switchGroup.setVisibility(View.GONE);
                switchGroup1.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                testText.setVisibility(View.GONE);
                testButton.setVisibility(View.GONE);
                testWindow.setVisibility(View.VISIBLE);
                testProgressBar.setVisibility(View.GONE);
                testSeekBar.setVisibility(View.VISIBLE);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Color of progress"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select the SeekBar color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(seekBarColorAdapter);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Slider color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select the color of the SeekBar slider"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(seekThumbColorAdapter);
                }else {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select background color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(seekBarColorBgAdapter);
                }
            }
            //other settings
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Greeting"))) {
                switch5.setVisibility(View.GONE);
                switchGroup.setVisibility(View.GONE);
                switchGroup1.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testGreeting.setVisibility(View.VISIBLE);
                testBackground.setBackgroundColor(Color.BLACK);
                testToolbar.setBackgroundColor(Color.BLACK);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Greeting color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select a greeting color"));
                    secondMenu.setAdapter(greetingTextColorAdapter);
                    secondMenu.setLayoutManager(gridLayoutManager);
                } else if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Greeting text"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Enter welcome text"));
                    secondMenu.setLayoutManager(linearLayoutManager);
                    secondMenu.setAdapter(editTextAdapter);
                }
            }else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Desktop"))) {
                testGreeting.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(activity.words.get("Text Color"))){
                    switch5.setVisibility(View.GONE);
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select text color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(desktopTextColorAdapter);
                }else if(menuAdapter.getChild(groupPosition,childPosition).equals(activity.words.get("Other"))){
                    secondTitle.setText(activity.words.get("Additional desktop settings"));
                    secondTitle.setVisibility(View.VISIBLE);
                    secondMenu.setAdapter(null);
                    switch5.setVisibility(View.VISIBLE);
                    switchGroup1.setVisibility(View.GONE);
                    switchGroup.setVisibility(View.GONE);
                }
            }
            return true;
        });
        saveButton.setOnClickListener(v -> {
            // background settings save
            if (SettingsGroup.equals("Background")) {
                switch (BackgroundType) {
                    case "Color": {
                        activity.layout.setBackgroundResource(backgroundResourceColorAdapter.currentColorId);
                        activity.styleSave.BackgroundResource = backgroundResourceColorAdapter.currentColorId;
                        break;
                    }
                    case "Gradient": {
                        activity.layout.setBackgroundResource(backgroundResourceGradientAdapter.currentGradientId);
                        activity.styleSave.BackgroundResource = backgroundResourceGradientAdapter.currentGradientId;
                        break;
                    }
                    case "Image": {
                        activity.layout.setBackgroundResource(backgroundResourceImageAdapter.currentImageId);
                        activity.styleSave.BackgroundResource = backgroundResourceImageAdapter.currentImageId;
                        break;
                    }
                }
            }
            if(StringArrayWork.ArrayListToString(activity.apps).contains(NapiOS.TITLE+",")||
                    StringArrayWork.ArrayListToString(activity.apps).contains(MakOS.TITLE+",")) {
                //window settings save
                activity.styleSave.ColorWindow = windowBackColor.currentColor;
                activity.styleSave.CloseButtonImageRes = windowButtonColorAdapter.currentCloseButtonImageRes;
                activity.styleSave.FullScreenMode1ImageRes = windowButtonColorAdapter.currentFullscreen1ButtonImageRes;
                activity.styleSave.FullScreenMode2ImageRes = windowButtonColorAdapter.currentFullscreen2ButtonImageRes;
                activity.styleSave.RollUpButtonImageRes = windowButtonColorAdapter.currentRollUpButtonImageRes;
                activity.styleSave.TitleColor = titleTextColorAdapter.currentTextColor;
                //theme settings save
                activity.styleSave.ThemeColor1 = themeAdapter.ColorTheme1;
                activity.styleSave.ThemeColor2 = themeAdapter.ColorTheme2;
                activity.styleSave.ThemeColor3 = themeAdapter.ColorTheme3;
                activity.styleSave.PauseButtonRes = themeAdapter.PauseButtonRes;
                activity.styleSave.PlayButtonImage = themeAdapter.PlayButtonImage;
                activity.styleSave.PrevOrNextImageRes = themeAdapter.PrevOrNextImageRes;
                activity.styleSave.ArrowButtonImage = themeAdapter.ArrowButtonImage;

                //text color save
                activity.styleSave.TextColor = textColorAdapter.currentTextColor;
                activity.styleSave.TextButtonColor = buttonTextColorAdapter.currentTextColor;

                //launch settings save
                activity.styleSave.StartMenuColor = launchBackColor.currentColor;
                activity.styleSave.StartMenuTextColor = launchTextColorAdapter.currentTextColor;

                //toolbar settings save
                activity.styleSave.ToolbarColor = toolbarBackColor.currentColor;
                activity.toolbar.setBackgroundColor(toolbarBackColor.currentColor);
                activity.styleSave.ToolbarTextColor = toolbarTextColorAdapter.currentTextColor;

                //progressBar save
                activity.styleSave.ProgressBarResource = progressBarColorAdapter.currentDrawableResource;
                activity.styleSave.ProgressBarBgColor = progressBarBgColorAdapter.currentBgColor;

                //seekBar settings save
                activity.styleSave.SeekBarProgressResource = seekBarColorAdapter.currentDrawableResource;
                activity.styleSave.SeekBarThumbResource = seekThumbColorAdapter.currentDrawableResource;
                activity.styleSave.SeekBarBgColor = seekBarColorBgAdapter.currentBgColor;

                //greeting settings save
                activity.styleSave.Greeting = editTextAdapter.getCurrentText();
                activity.styleSave.GreetingColor = greetingTextColorAdapter.currentTextColor;

                activity.styleSave.StartMenuAppIconVisible = switch1.isChecked();
                activity.styleSave.StartMenuAppNameVisible = switch2.isChecked();
                activity.styleSave.ToolbarAppIconVisible = switch3.isChecked();
                activity.styleSave.ToolbarAppNameVisible = switch4.isChecked();

                activity.styleSave.DesktopAppNameVisible = switch5.isChecked();
                activity.styleSave.DesktopTextColor = desktopTextColorAdapter.currentTextColor;
                secondMenu.setAdapter(null);
                secondTitle.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
            }
            activity.updateDesktop();
            activity.updateToolbar();
            activity.updateStartMenu();
            activity.styleSave.setStyle();
            style();
        });
        super.initProgram();
    }
    void launchAndToolbarPreviews(){
        if(switch1.isChecked()) appIconLaunch.setVisibility(View.VISIBLE);
        else appIconLaunch.setVisibility(View.GONE);
        if(switch2.isChecked()) appNameLaunch.setVisibility(View.VISIBLE);
        else appNameLaunch.setVisibility(View.GONE);
        if(switch3.isChecked()) appIconToolbar.setVisibility(View.VISIBLE);
        else appIconToolbar.setVisibility(View.GONE);
        if(switch4.isChecked()) appNameToolbar.setVisibility(View.VISIBLE);
        else appNameToolbar.setVisibility(View.GONE);
    }
}