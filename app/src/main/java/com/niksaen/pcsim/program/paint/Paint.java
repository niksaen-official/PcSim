package com.niksaen.pcsim.program.paint;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.notepad.NotepadSpinnerAdapter;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;
import java.util.List;

public class Paint extends Program {

    Context context;
    ConstraintLayout layout;
    PcParametersSave pcParametersSave;

    private LayoutInflater layoutInflater;
    private Typeface font;
    private StyleSave styleSave;


    private View mainWindow;

    public Paint(Context context, PcParametersSave pcParametersSave, ConstraintLayout layout) {
        this.context = context;
        this.pcParametersSave = pcParametersSave;
        this.layout = layout;
        styleSave = new StyleSave(context);
        layoutInflater = LayoutInflater.from(context);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    Spinner file,alpha,weight;
    TextView title,currentColor;
    RecyclerView recyclerView;
    LinearLayout canvas;
    ConstraintLayout main;
    Button fullscreen,close,rollUp;

    private void initView(){
        mainWindow = layoutInflater.inflate(R.layout.program_paint,null);
        file = mainWindow.findViewById(R.id.spinner);
        alpha = mainWindow.findViewById(R.id.spinner2);
        weight = mainWindow.findViewById(R.id.spinner3);
        currentColor = mainWindow.findViewById(R.id.current_color);
        title = mainWindow.findViewById(R.id.title);
        recyclerView = mainWindow.findViewById(R.id.colors);
        canvas = mainWindow.findViewById(R.id.canvas);
        main = mainWindow.findViewById(R.id.main);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        close = mainWindow.findViewById(R.id.close);
        rollUp = mainWindow.findViewById(R.id.roll_up);
    }

    private void style(){
        title.setTypeface(font,Typeface.BOLD);
        title.setTextColor(styleSave.TitleColor);
        main.setBackgroundColor(styleSave.ThemeColor1);
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);

        alpha.setAdapter(alphaAdapter);
        file.setAdapter(fileAdapter);
        weight.setAdapter(weightAdapter);
        recyclerView.setAdapter(paintRecyclerView);
    }

    HashMap<String,String> words;
    PaintCanvas paintCanvas;
    PaintRecyclerView paintRecyclerView;
    NotepadSpinnerAdapter fileAdapter,alphaAdapter,weightAdapter;

    private void adapters(){
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),new TypeToken<HashMap<String,String>>(){}.getType());

        List<String> colors = PaintColorPalette.setColorPalette1();
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        paintRecyclerView = new PaintRecyclerView(context,colors,currentColor,paintCanvas);

        fileAdapter = new NotepadSpinnerAdapter(context,0,
                new String[]{
                        words.get("File")+":",
                        words.get("New file"),
                        words.get("Save"),
                        words.get("Open"),
                        words.get("Exit")},
                words.get("File"));
        fileAdapter.TextColor = styleSave.TextColor;
        fileAdapter.BackgroundColor = styleSave.ThemeColor2;

        alphaAdapter = new NotepadSpinnerAdapter(context,0,
                new String[]{words.get("Transparency")+":", "20%","40%","60%","80%","100%"},
                words.get("Transparency"));
        alphaAdapter.TextColor = styleSave.TextColor;
        alphaAdapter.BackgroundColor = styleSave.ThemeColor2;

        weightAdapter = new NotepadSpinnerAdapter(context,0,
                new String[]{words.get("Thickness")+":","3px","6px","9px","12px","15px"},
                words.get("Thickness"));
        weightAdapter.TextColor = styleSave.TextColor;
        weightAdapter.BackgroundColor = styleSave.ThemeColor2;
    }

    public void openProgram(Drawable drawable){
        paintCanvas= new PaintCanvas(context);

        initView();adapters();style();

        if(drawable != null){
            paintCanvas.setBackground(drawable);
            canvas.addView(paintCanvas,
                    (int)(drawable.getIntrinsicWidth()*1.4),
                    (int)(drawable.getIntrinsicHeight()*1.4));
            canvas.setBackgroundColor(styleSave.ThemeColor1);
        }
        else {
            canvas.addView(paintCanvas);
        }

        file.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    paintCanvas.reset();
                }
                else if(position == 2){
                    layout.addView(new PaintSaveFile(new Paint(context,pcParametersSave,layout)).saveFile(paintCanvas), LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                }
                else if(position == 3){
                    mainWindow.setVisibility(View.GONE);
                    mainWindow = null;
                    layout.addView(new PaintOpenFile(new Paint(context,pcParametersSave,layout)).openFile(), LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                }
                else if(position == 4){
                    closeProgram();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        alpha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    paintCanvas.strokeAlpha = Integer.parseInt(alphaAdapter.getItem(position).replace("%", ""));
                    paintCanvas.setStyle();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    paintCanvas.strokeWidth = Integer.parseInt(weightAdapter.getItem(position).replace("px", ""));
                    paintCanvas.setStyle();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final int[] button2ClickCount = {0};
        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button2ClickCount[0]==0){
                    fullscreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                    mainWindow.setScaleX(0.7f);
                    mainWindow.setScaleY(0.7f);
                    mainWindow.setX(0f);
                    mainWindow.setY(0f);
                    PortableView portableView = new PortableView(mainWindow);
                    mainWindow.setZ(0f);
                    button2ClickCount[0]++;
                }
                else{
                    fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                    mainWindow.setScaleX(1);
                    mainWindow.setScaleY(1);
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    mainWindow.setOnTouchListener(null);
                    mainWindow.setZ(10f);
                    button2ClickCount[0]=0;
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeProgram();
            }
        });

        layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public void closeProgram(){
        mainWindow.setVisibility(View.GONE);
        mainWindow = null;
    }
}
