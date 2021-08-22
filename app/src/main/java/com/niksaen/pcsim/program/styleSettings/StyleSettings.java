package com.niksaen.pcsim.program.styleSettings;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

public class StyleSettings extends Program {
    String SettingsGroup = "",BackgroundType="";

    public StyleSettings(MainActivity activity){
        super(activity);
        this.Title = "Personalization";
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
    TextView testTitle,testText,testGreeting;
    Button testCloseButton,testFullscreenModeButton,testRollUpButton;
    Button testButton;
    SeekBar testSeekBar;
    ProgressBar testProgressBar;

    private void initView(){
        mainWindow = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.program_style_settings,null);
        mainMenu = mainWindow.findViewById(R.id.menu);
        secondMenu = mainWindow.findViewById(R.id.secondMenu);
        content = mainWindow.findViewById(R.id.content);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
        titleTextView = mainWindow.findViewById(R.id.title);
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
        menuAdapter = new MenuAdapter(activity.getBaseContext());

        backgroundResourceColorAdapter = new BackgroundResourceColorAdapter(activity.getBaseContext(),testBackground);
        backgroundResourceGradientAdapter = new BackgroundResourceGradientAdapter(activity.getBaseContext(),testBackground);
        backgroundResourceImageAdapter = new BackgroundResourceImageAdapter(activity.getBaseContext(),testBackground);
        windowBackColor = new ColorAdapter(activity.getBaseContext(),testWindow,activity.styleSave,0);
        windowButtonColorAdapter = new ButtonColorAdapter(activity.getBaseContext(),new Button[]{testCloseButton,testFullscreenModeButton,testRollUpButton},activity.styleSave);
        titleTextColorAdapter = new TextColorAdapter(activity.getBaseContext(),testTitle,activity.styleSave,0);
        textColorAdapter = new TextColorAdapter(activity.getBaseContext(),testText,activity.styleSave,1);
        buttonTextColorAdapter = new TextColorAdapter(activity.getBaseContext(),testButton,activity.styleSave);
        themeAdapter = new ThemeAdapter(activity.getBaseContext(),new View[]{testContent,testButton},activity.styleSave);
        launchBackColor = new ColorAdapter(activity.getBaseContext(),testLaunch,activity.styleSave,1);
        toolbarBackColor = new ColorAdapter(activity.getBaseContext(),testToolbar,activity.styleSave,2);
        progressBarColorAdapter = new ProgressBarColorAdapter(activity.getBaseContext(),testProgressBar,activity.styleSave);
        seekBarColorAdapter = new SeekBarColorAdapter(activity.getBaseContext(),testSeekBar,activity.styleSave,0);
        seekThumbColorAdapter = new SeekBarColorAdapter(activity.getBaseContext(),testSeekBar,activity.styleSave,1);
        greetingTextColorAdapter = new TextColorAdapter(activity.getBaseContext(),testGreeting,activity.styleSave,2);
        editTextAdapter = new EditTextAdapter(activity.getBaseContext(),activity.styleSave);

        gridLayoutManager = new GridLayoutManager(activity.getBaseContext(),10);
        linearLayoutManager = new LinearLayoutManager(activity.getBaseContext());
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

        //set text
        saveButton.setText(activity.words.get("Will apply"));

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

        testProgressBar.setProgressDrawable(activity.getBaseContext().getDrawable(activity.styleSave.ProgressBarResource));
        testSeekBar.setProgressDrawable(activity.getBaseContext().getDrawable(activity.styleSave.SeekBarProgressResource));
        testSeekBar.setThumb(activity.getBaseContext().getDrawable(activity.styleSave.SeekBarThumbResource));

        //menu style
        menuAdapter.textColor = activity.styleSave.TextColor;
        menuAdapter.backgroundGroupColor = activity.styleSave.ThemeColor1;
        menuAdapter.backgroundChildColor = activity.styleSave.ThemeColor2;
        mainMenu.setBackgroundColor(activity.styleSave.ThemeColor1);
        mainMenu.setAdapter(menuAdapter);

        //adapter style
        themeAdapter.BackgroundColor = activity.styleSave.ThemeColor1;
        windowButtonColorAdapter.backgroundColor = activity.styleSave.ThemeColor1;
    }
    public void initProgram(){
        initView();
        initAdapters();
        style();

        mainMenu.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            //settings background
            if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Background"))) {
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
                testGreeting.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.VISIBLE);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Color"))) {
                    secondTitle.setText(activity.words.get("Select start menu color"));
                    secondTitle.setVisibility(View.VISIBLE);
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(launchBackColor);
                }
            }
            //settings toolbar
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Task bar"))) {
                testGreeting.setVisibility(View.GONE);
                testWindow.setVisibility(View.GONE);
                testLaunch.setVisibility(View.GONE);
                if (menuAdapter.getChild(groupPosition, childPosition).equals(activity.words.get("Color"))) {
                    secondTitle.setVisibility(View.VISIBLE);
                    secondTitle.setText(activity.words.get("Select the color of the task bar"));
                    secondMenu.setLayoutManager(gridLayoutManager);
                    secondMenu.setAdapter(toolbarBackColor);
                }
            }
            //settings progress bar
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("ProgressBar"))) {
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
                }
            }
            //settings seekbar
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("SeekBar"))) {
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
                }
            }
            //other settings
            else if (menuAdapter.getGroup(groupPosition).equals(activity.words.get("Greeting"))) {
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

            //toolbar settings save
            activity.styleSave.ToolbarColor = toolbarBackColor.currentColor;
            activity.toolbar.setBackgroundColor(toolbarBackColor.currentColor);

            //progressBar save
            activity.styleSave.ProgressBarResource = progressBarColorAdapter.currentDrawableResource;

            //seekBar settings save
            activity.styleSave.SeekBarProgressResource = seekBarColorAdapter.currentDrawableResource;
            activity.styleSave.SeekBarThumbResource = seekThumbColorAdapter.currentDrawableResource;

            //greeting settings save
            activity.styleSave.Greeting = editTextAdapter.getCurrentText();
            activity.styleSave.GreetingColor = greetingTextColorAdapter.currentTextColor;

            secondMenu.setAdapter(null);
            secondTitle.setVisibility(View.GONE);
            testWindow.setVisibility(View.GONE);
            testLaunch.setVisibility(View.GONE);
            testGreeting.setVisibility(View.GONE);

            activity.styleSave.setStyle();
            style();
        });
        super.initProgram();
    }
}