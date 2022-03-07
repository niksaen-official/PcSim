package com.niksaen.pcsim.program.appDownloader;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.classes.ProgramListAndData;

public class AcceptPolitic extends Program {

    private String programForSetup;
    PrepareForInstall prepareForInstall;

    public void setProgramForSetup(String programForSetup) {
        this.programForSetup = programForSetup;
    }

    public AcceptPolitic(MainActivity activity) {
        super(activity);
        Title = "Installation Wizard";
        ValueRam = new int[]{100,150};
        ValueVideoMemory = new int[]{80,100};
        prepareForInstall = new PrepareForInstall(activity);
    }

    ImageView appIcon;
    TextView programName,text;
    Button continueButton,cancelButton;
    ConstraintLayout main;

    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_disk_change,null);
        initView();
        style();
        appIcon.setImageResource(ProgramListAndData.programIcon.get(programForSetup));
        programName.setText(activity.words.get("Welcome to the installation wizard")+" \""+activity.words.get(programForSetup)+"\"");

        continueButton.setText(activity.words.get("Next"+" >"));
        cancelButton.setText(activity.words.get("Cancel"));
        text.setText(activity.words.get("The program will install")+" \""+activity.words.get(programForSetup) +"\" "+activity.words.get("to your computer. \n\nIt is recommended that you close all other programs before continuing. \n\nClick \"Next\"to continue, or click \"Cancel\"to exit."));

        continueButton.setOnClickListener(v -> {
            prepareForInstall.setProgramForSetup(programForSetup);
            prepareForInstall.openProgram();
            closeProgram(1);
        });
        cancelButton.setOnClickListener(v -> closeProgram(1));

        super.initProgram();
    }
    private void initView(){
        appIcon = mainWindow.findViewById(R.id.app_icon);
        programName = mainWindow.findViewById(R.id.program_name);
        text = mainWindow.findViewById(R.id.text);
        continueButton = mainWindow.findViewById(R.id.button);
        cancelButton = mainWindow.findViewById(R.id.button1);
        main = mainWindow.findViewById(R.id.main);
    }
    private void style(){
        programName.setTypeface(activity.font, Typeface.BOLD);
        programName.setTextColor(activity.styleSave.TextColor);
        text.setTypeface(activity.font);
        text.setTextColor(activity.styleSave.TextColor);
        continueButton.setTypeface(activity.font,Typeface.BOLD);
        continueButton.setTextColor(activity.styleSave.TextButtonColor);
        continueButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        cancelButton.setTypeface(activity.font,Typeface.BOLD);
        cancelButton.setTextColor(activity.styleSave.TextButtonColor);
        cancelButton.setBackgroundColor(activity.styleSave.ThemeColor2);
        main.setBackgroundColor(activity.styleSave.ThemeColor1);
    }
}
