package com.niksaen.pcsim.program.notepad;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class Notepad  {

    Context context;
    PcParametersSave pcParametersSave;
    LayoutInflater layoutInflater;
    StyleSave styleSave;

    View mainWindow;
    ConstraintLayout layout;

    Typeface font;
    public Notepad(Context context, PcParametersSave pcParametersSave, ConstraintLayout layout){
        this.context=context;
        this.pcParametersSave=pcParametersSave;
        this.layout = layout;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        layoutInflater = LayoutInflater.from(context);
        styleSave = new StyleSave(context);
    }

    TextView title;
    EditText editText;
    Spinner spinner;
    Button close,rollUp,fullscreen;
    LinearLayout toolbar;

    NotepadSpinnerAdapter notepadSpinnerAdapter;
    HashMap<String,String> words;

    private void initView(){
        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_notepad,null);
        title = mainWindow.findViewById(R.id.title);
        editText = mainWindow.findViewById(R.id.editText);
        spinner = mainWindow.findViewById(R.id.file);
        close = mainWindow.findViewById(R.id.close);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        toolbar = mainWindow.findViewById(R.id.toolbar);
    }
    private void style(){
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        title.setTextColor(styleSave.TitleColor);
        title.setTypeface(font,Typeface.BOLD);
        editText.setTextColor(styleSave.TextColor);
        editText.setTypeface(font);
        editText.setBackgroundColor(styleSave.ThemeColor1);
        toolbar.setBackgroundColor(styleSave.ThemeColor2);
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);

        //установка текста
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),new TypeToken<HashMap<String,String>>(){}.getType());
        String[] fileWork = new String[]{
              words.get("File")+":",
                words.get("New file"),
                words.get("Save"),
                words.get("Open"),
                words.get("Exit")
        };
        title.setText(words.get("Notepad"));
        notepadSpinnerAdapter = new NotepadSpinnerAdapter(context,R.layout.item_textview,fileWork,words.get("File"));
        notepadSpinnerAdapter.BackgroundColor = styleSave.ThemeColor2;
        notepadSpinnerAdapter.TextColor = styleSave.TextColor;

        spinner.setAdapter(notepadSpinnerAdapter);
    }

    public void openProgram(String text){
        initView();style();
        if(text != null){
            editText.setText(text);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    editText.setText("");
                }
                else if(position == 2){
                    layout.addView(new NotepadFileSave(context).saveFile(editText.getText().toString()), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                }
                else if(position == 3) {
                    mainWindow.setVisibility(View.GONE);
                    mainWindow = null;
                    layout.addView(
                            new NotepadFileOpen(context,new Notepad(context,pcParametersSave,layout)).openFile(),
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                }
                else if(position == 4){
                    closeProgram();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final int[] button2ClickCount = {0};
        fullscreen.setOnClickListener(v -> {
            if(button2ClickCount[0]==0){
                fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                mainWindow.setScaleX(0.7f);
                mainWindow.setScaleY(0.7f);
                mainWindow.setX(0f);
                mainWindow.setY(0f);
                PortableView portableView1 = new PortableView(mainWindow);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mainWindow.setZ(0f);
                }
                button2ClickCount[0]++;
            }
            else{
                fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setX(0);
                mainWindow.setY(0);
                mainWindow.setOnTouchListener(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mainWindow.setZ(10f);
                }
                button2ClickCount[0]=0;
            }
        });
        close.setOnClickListener(v -> closeProgram());
        layout.addView(mainWindow,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }
    public void closeProgram(){
        if(mainWindow != null){
            mainWindow.setVisibility(View.GONE);
            mainWindow = null;
        }
    }
}
