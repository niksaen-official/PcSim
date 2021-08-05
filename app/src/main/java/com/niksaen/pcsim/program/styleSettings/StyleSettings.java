package com.niksaen.pcsim.program.styleSettings;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;

public class StyleSettings {
    View[] testView;
    ConstraintLayout layout;
    View toolbar,launch;
    View mainWindow;
    Typeface font;
    PcParametersSave pcParametersSave;
    StyleSave styleSave;
    Context context;
    String SettingsGroup = "",BackgroundType="";

    public StyleSettings(Context context,View[] testView,PcParametersSave pcParametersSave){
        this.testView = testView;

        layout = (ConstraintLayout) testView[0];
        toolbar = testView[1];

        this.pcParametersSave = pcParametersSave;
        this.context = context;
        styleSave = new StyleSave(context);
        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_style_settings,null);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),typeToken.getType());
    }

    //main view
    ExpandableListView mainMenu;
    RecyclerView secondMenu;
    ConstraintLayout content;
    Button closeButton,fullscreenModeButton,rollUpButton,saveButton;
    TextView title,secondTitle;
    int fullscreenMode1ImageRes,fullscreenMode2ImageRes;

    //test view
    ConstraintLayout testBackground,testWindow;
    LinearLayout testContent;
    View testLaunch,testToolbar;
    TextView testTitle,testText,testGreeting;
    Button testCloseButton,testFullscreenModeButton,testRollUpButton;
    Button testButton;
    SeekBar testSeekBar;
    ProgressBar testProgressBar;

    private void initView(){
        mainMenu = mainWindow.findViewById(R.id.menu);
        secondMenu = mainWindow.findViewById(R.id.secondMenu);
        content = mainWindow.findViewById(R.id.content);
        closeButton = mainWindow.findViewById(R.id.close);
        fullscreenModeButton = mainWindow.findViewById(R.id.fullscreenMode);
        rollUpButton = mainWindow.findViewById(R.id.roll_up);
        title = mainWindow.findViewById(R.id.title);
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
    }

    //adapter
    MenuAdapter menuAdapter;
    BackgroundResourceColorAdapter backgroundResourceColorAdapter;
    BackgroundResourceGradientAdapter backgroundResourceGradientAdapter;
    BackgroundResourceImageAdapter backgroundResourceImageAdapter;
    ColorAdapter windowBackColor,launchBackColor,toolbarBackColor;
    ProgressBarColorAdapter progressBarColorAdapter;
    ButtonColorAdapter windowButtonColorAdapter;
    TextColorAdapter titleTextColorAdapter,textColorAdapter,buttonTextColorAdapter,greetingTextColorAdapter;
    ThemeAdapter themeAdapter;
    SeekBarColorAdapter seekBarColorAdapter,seekThumbColorAdapter;
    EditTextAdapter editTextAdapter;

    //layout manager
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    private void initAdapters(){
        menuAdapter = new MenuAdapter(context);

        backgroundResourceColorAdapter = new BackgroundResourceColorAdapter(context,testBackground);
        backgroundResourceGradientAdapter = new BackgroundResourceGradientAdapter(context,testBackground);
        backgroundResourceImageAdapter = new BackgroundResourceImageAdapter(context,testBackground);
        windowBackColor = new ColorAdapter(context,testWindow,styleSave,0);
        windowButtonColorAdapter = new ButtonColorAdapter(context,new Button[]{testCloseButton,testFullscreenModeButton,testRollUpButton},styleSave);
        titleTextColorAdapter = new TextColorAdapter(context,testTitle,styleSave,0);
        textColorAdapter = new TextColorAdapter(context,testText,styleSave,1);
        buttonTextColorAdapter = new TextColorAdapter(context,testButton,styleSave);
        themeAdapter = new ThemeAdapter(context,new View[]{testContent,testButton},styleSave);
        launchBackColor = new ColorAdapter(context,testLaunch,styleSave,1);
        toolbarBackColor = new ColorAdapter(context,testToolbar,styleSave,2);
        progressBarColorAdapter = new ProgressBarColorAdapter(context,testProgressBar,styleSave);
        seekBarColorAdapter = new SeekBarColorAdapter(context,testSeekBar,styleSave,0);
        seekThumbColorAdapter = new SeekBarColorAdapter(context,testSeekBar,styleSave,1);
        greetingTextColorAdapter = new TextColorAdapter(context,testGreeting,styleSave,2);
        editTextAdapter = new EditTextAdapter(context,styleSave);

        gridLayoutManager = new GridLayoutManager(context,10);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    private void style(){
        getLanguage();
        //set font
        saveButton.setTypeface(font,Typeface.BOLD);
        title.setTypeface(font,Typeface.BOLD);
        secondTitle.setTypeface(font,Typeface.BOLD);
        testButton.setTypeface(font,Typeface.BOLD);
        testGreeting.setTypeface(font,Typeface.BOLD);
        testText.setTypeface(font,Typeface.BOLD);
        testTitle.setTypeface(font,Typeface.BOLD);

        //set text
        title.setText(words.get("Personalization"));
        saveButton.setText(words.get("Will apply"));

        //set window button style
        closeButton.setBackgroundResource(styleSave.CloseButtonImageRes);
        testCloseButton.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUpButton.setBackgroundResource(styleSave.RollUpButtonImageRes);
        testRollUpButton.setBackgroundResource(styleSave.RollUpButtonImageRes);
        fullscreenMode1ImageRes = styleSave.FullScreenMode1ImageRes;
        fullscreenMode2ImageRes = styleSave.FullScreenMode2ImageRes;
        fullscreenModeButton.setBackgroundResource(fullscreenMode2ImageRes);
        testFullscreenModeButton.setBackgroundResource(fullscreenMode1ImageRes);

        //style main
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        title.setTextColor(styleSave.TitleColor);
        content.setBackgroundColor(styleSave.ThemeColor1);
        saveButton.setBackgroundColor(styleSave.ThemeColor2);
        saveButton.setTextColor(styleSave.TextButtonColor);
        secondTitle.setTextColor(styleSave.TextColor);

        //style test
        testBackground.setBackgroundResource(styleSave.BackgroundResource);
        testWindow.setBackgroundColor(styleSave.ColorWindow);
        testTitle.setTextColor(styleSave.TitleColor);
        testContent.setBackgroundColor(styleSave.ThemeColor1);
        testButton.setBackgroundColor(styleSave.ThemeColor2);
        testButton.setTextColor(styleSave.TextButtonColor);
        testText.setTextColor(styleSave.TextColor);
        testLaunch.setBackgroundColor(styleSave.LaunchColor);
        testToolbar.setBackgroundColor(styleSave.ToolbarColor);
        testGreeting.setText(styleSave.Greeting);
        testGreeting.setTextColor(styleSave.GreetingColor);

        testProgressBar.setProgressDrawable(context.getDrawable(styleSave.ProgressBarResource));
        testSeekBar.setProgressDrawable(context.getDrawable(styleSave.SeekBarProgressResource));
        testSeekBar.setThumb(context.getDrawable(styleSave.SeekBarThumbResource));

        //menu style
        menuAdapter.textColor = styleSave.TextColor;
        menuAdapter.backgroundGroupColor = styleSave.ThemeColor1;
        menuAdapter.backgroundChildColor = styleSave.ThemeColor2;
        mainMenu.setBackgroundColor(styleSave.ThemeColor1);
        mainMenu.setAdapter(menuAdapter);

        //adapter style
        themeAdapter.BackgroundColor = styleSave.ThemeColor1;
        windowButtonColorAdapter.backgroundColor = styleSave.ThemeColor1;
    }

    int i = 0;
    public void openProgram(){
        initView();initAdapters();style();

        mainMenu.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            //settings background
            if(menuAdapter.getGroup(groupPosition).equals(words.get("Background"))) {
                SettingsGroup = "Background";
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                testGreeting.setVisibility(View.GONE);
                testBackground.setBackgroundResource(styleSave.BackgroundResource);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Color"))){
                    BackgroundType = "Color";
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select background color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(backgroundResourceColorAdapter);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Gradient"))){
                    BackgroundType = "Gradient";
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select gradient background"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(backgroundResourceGradientAdapter);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Wallpaper"))){
                    BackgroundType = "Image";
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select background image"));
                    secondMenu.setLayoutManager(new GridLayoutManager(context, 2));
                    secondMenu.setAdapter(backgroundResourceImageAdapter);
                }
            }
            //settings window
            else if(menuAdapter.getGroup(groupPosition).equals(words.get("Window"))){
                testWindow.setVisibility(View.VISIBLE);
                testGreeting.setVisibility(View.GONE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Window Color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select window color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(windowBackColor);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Button Color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select the color of the button"));
                    secondMenu.setLayoutManager(linearLayoutManager);
                    secondMenu.setAdapter(windowButtonColorAdapter);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Title Color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select a title color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(titleTextColorAdapter);
                }
            }
            //settings theme
            else if(menuAdapter.getGroup(groupPosition).equals(words.get("Colors"))){
                testGreeting.setVisibility(View.GONE);
                testSeekBar.setVisibility(View.GONE);
                testProgressBar.setVisibility(View.GONE);
                testButton.setVisibility(View.VISIBLE);
                testText.setVisibility(View.VISIBLE);
                testWindow.setVisibility(View.VISIBLE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Theme"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select theme"));
                    secondMenu.setLayoutManager(new GridLayoutManager(context,2));
                    secondMenu.setAdapter(themeAdapter);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Text Color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select text color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(textColorAdapter);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Button text color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select the text color of the button"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(buttonTextColorAdapter);
                }
            }
            //settings launch
            else if(menuAdapter.getGroup(groupPosition).equals(words.get("Start"))){
                testGreeting.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.VISIBLE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Color"))){
                    secondTitle.setText(words.get("Select start menu color"));
                    secondTitle.setVisibility(View.VISIBLE);
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(launchBackColor);
                }
            }
            //settings toolbar
            else if(menuAdapter.getGroup(groupPosition).equals(words.get("Task bar"))){
                testGreeting.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select the color of the task bar"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(toolbarBackColor);
                }
            }
            //settings progress bar
            else if(menuAdapter.getGroup(groupPosition).equals(words.get("ProgressBar"))){
                testGreeting.setVisibility(View.GONE);
                testSeekBar.setVisibility(View.GONE);
                testText.setVisibility(View.GONE);
                testButton.setVisibility(View.GONE);
                testWindow.setVisibility(View.VISIBLE);
                testProgressBar.setVisibility(View.VISIBLE);
                if (menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select indicator color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(progressBarColorAdapter);
                }
            }
            //settings seekbar
            else if(menuAdapter.getGroup(groupPosition).equals(words.get("SeekBar"))){
                testGreeting.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                testText.setVisibility(View.GONE);
                testButton.setVisibility(View.GONE);
                testWindow.setVisibility(View.VISIBLE);
                testProgressBar.setVisibility(View.GONE);
                testSeekBar.setVisibility(View.VISIBLE);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Color of progress"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select the SeekBar color"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(seekBarColorAdapter);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Slider color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select the color of the SeekBar slider"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(seekThumbColorAdapter);
                }
            }
            //other settings
            else if(menuAdapter.getGroup(groupPosition).equals(words.get("Greeting"))){
                testLaunch.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testGreeting.setVisibility(View.VISIBLE);
                testBackground.setBackgroundColor(Color.BLACK);
                testToolbar.setBackgroundColor(Color.BLACK);
                if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Greeting color"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Select a greeting color"));
                    secondMenu.setAdapter(greetingTextColorAdapter);
                    secondMenu.setLayoutManager(gridLayoutManager);
                }
                else if(menuAdapter.getChild(groupPosition,childPosition).equals(words.get("Greeting text"))){
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(words.get("Enter welcome text"));
                    secondMenu.setLayoutManager(linearLayoutManager);
                    secondMenu.setAdapter(editTextAdapter);
                }
            }
            return true;
        });
        saveButton.setOnClickListener(v -> {
            if (SettingsGroup.equals("Background")) {
                switch (BackgroundType) {
                    case "Color": {
                        layout.setBackgroundResource(backgroundResourceColorAdapter.currentColorId);
                        styleSave.BackgroundResource = backgroundResourceColorAdapter.currentColorId;
                        break;
                    }
                    case "Gradient": {
                        layout.setBackgroundResource(backgroundResourceGradientAdapter.currentGradientId);
                        styleSave.BackgroundResource = backgroundResourceGradientAdapter.currentGradientId;
                        break;
                    }
                    case "Image": {
                        layout.setBackgroundResource(backgroundResourceImageAdapter.currentImageId);
                        styleSave.BackgroundResource = backgroundResourceImageAdapter.currentImageId;
                        break;
                    }
                }
            }
            
            //window settings save
            styleSave.ColorWindow = windowBackColor.currentColor;
            styleSave.CloseButtonImageRes = windowButtonColorAdapter.currentCloseButtonImageRes;
            styleSave.FullScreenMode1ImageRes = windowButtonColorAdapter.currentFullscreen1ButtonImageRes;
            styleSave.FullScreenMode2ImageRes = windowButtonColorAdapter.currentFullscreen2ButtonImageRes;
            styleSave.RollUpButtonImageRes = windowButtonColorAdapter.currentRollUpButtonImageRes;
            styleSave.TitleColor = titleTextColorAdapter.currentTextColor;

            //theme settings save
            styleSave.ThemeColor1 = themeAdapter.ColorTheme1;
            styleSave.ThemeColor2 = themeAdapter.ColorTheme2;
            styleSave.ThemeColor3 = themeAdapter.ColorTheme3;
            styleSave.PauseButtonRes = themeAdapter.PauseButtonRes;
            styleSave.PlayButtonImage = themeAdapter.PlayButtonImage;
            styleSave.PrevOrNextImageRes = themeAdapter.PrevOrNextImageRes;
            styleSave.ArrowButtonImage = themeAdapter.ArrowButtonImage;

            //text color save
            styleSave.TextColor = textColorAdapter.currentTextColor;
            styleSave.TextButtonColor = buttonTextColorAdapter.currentTextColor;

            //launch settings save
            styleSave.LaunchColor=launchBackColor.currentColor;

            //toolbar settings save
            styleSave.ToolbarColor=toolbarBackColor.currentColor;
            toolbar.setBackgroundColor(toolbarBackColor.currentColor);

            //progressBar save
            styleSave.ProgressBarResource = progressBarColorAdapter.currentDrawableResource;

            //seekBar settings save
            styleSave.SeekBarProgressResource = seekBarColorAdapter.currentDrawableResource;
            styleSave.SeekBarThumbResource = seekThumbColorAdapter.currentDrawableResource;

            //greeting settings save
            styleSave.Greeting = editTextAdapter.getCurrentText();
            styleSave.GreetingColor = greetingTextColorAdapter.currentTextColor;

            secondMenu.setAdapter(null);
            secondTitle.setVisibility(View.GONE);
            testWindow.setVisibility(View.GONE);
            testLaunch.setVisibility(View.GONE);
            testGreeting.setVisibility(View.GONE);

            styleSave.setStyle();
            style();
        });
        closeButton.setOnClickListener(v -> closeProgram());
        fullscreenModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){
                    mainWindow.setScaleY(0.65f);
                    mainWindow.setScaleX(0.65f);
                    PortableView portableView = new PortableView(mainWindow);
                    fullscreenModeButton.setBackgroundResource(fullscreenMode1ImageRes);
                    i++;
                }
                else {
                    mainWindow.setY(0);
                    mainWindow.setX(0);
                    mainWindow.setScaleX(1);
                    mainWindow.setScaleY(1);
                    mainWindow.setOnTouchListener(null);
                    i=0;                }
            }
        });

        layout.addView(mainWindow,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }
    public void closeProgram(){
        mainWindow.setVisibility(View.GONE);
        mainWindow = null;
    }
}