package com.niksaen.pcsim.program.fileManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;

public class TextViewer  extends Program {
    public TextViewer(MainActivity activity){
        super(activity);
        this.Title = "Text Viewer";
    }

    TextView textView;
    private void initView(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_filemanager_textviewer,null);
        titleTextView = mainWindow.findViewById(R.id.title);
        textView = mainWindow.findViewById(R.id.textView);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
    }

    private void style(){
        textView.setTextColor(activity.styleSave.TextColor);
        textView.setBackgroundColor(activity.styleSave.ThemeColor1);
        textView.setTypeface(activity.font);
    }

    public void openProgram(String text){
        initView();
        style();
        textView.setText(text);
        initProgram();
        super.openProgram();
    }

    public void closeProgram(int mode){
        super.closeProgram(mode);
        mainWindow.setVisibility(View.GONE);
    }
}
