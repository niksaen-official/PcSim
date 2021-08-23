package com.niksaen.pcsim.program.paint;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.notepad.NotepadSpinnerAdapter;

import java.util.List;

public class Paint extends Program {

    public Paint(MainActivity activity) {
        super(activity);
        this.Title = "Paint";
        ValueRam = new int[]{10,20};
        ValueVideoMemory = new int[]{60,77};
    }

    Spinner file,alpha,weight;
    TextView currentColor;
    RecyclerView recyclerView;
    LinearLayout canvas;
    ConstraintLayout main;

    private void initView(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_paint,null);
        file = mainWindow.findViewById(R.id.spinner);
        alpha = mainWindow.findViewById(R.id.spinner2);
        weight = mainWindow.findViewById(R.id.spinner3);
        currentColor = mainWindow.findViewById(R.id.current_color);
        titleTextView = mainWindow.findViewById(R.id.title);
        recyclerView = mainWindow.findViewById(R.id.colors);
        canvas = mainWindow.findViewById(R.id.canvas);
        main = mainWindow.findViewById(R.id.main);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
    }

    private void style(){
        main.setBackgroundColor(activity.styleSave.ThemeColor1);

        alpha.setAdapter(alphaAdapter);
        file.setAdapter(fileAdapter);
        weight.setAdapter(weightAdapter);
        recyclerView.setAdapter(paintRecyclerView);
    }
    PaintCanvas paintCanvas;
    PaintRecyclerView paintRecyclerView;
    NotepadSpinnerAdapter fileAdapter,alphaAdapter,weightAdapter;

    private void adapters(){
        List<String> colors = PaintColorPalette.setColorPalette1();
        recyclerView.setLayoutManager(new GridLayoutManager(activity,3));
        paintRecyclerView = new PaintRecyclerView(activity.getBaseContext(),colors,currentColor,paintCanvas);

        fileAdapter = new NotepadSpinnerAdapter(activity.getBaseContext(),0,
                new String[]{
                        activity.words.get("File")+":",
                        activity.words.get("New file"),
                        activity.words.get("Save"),
                        activity.words.get("Open"),
                        activity.words.get("Exit")},
                activity.words.get("File"));
        fileAdapter.TextColor = activity.styleSave.TextColor;
        fileAdapter.BackgroundColor = activity.styleSave.ThemeColor2;

        alphaAdapter = new NotepadSpinnerAdapter(activity.getBaseContext(),0,
                new String[]{activity.words.get("Transparency")+":", "20%","40%","60%","80%","100%"},
                activity.words.get("Transparency"));
        alphaAdapter.TextColor = activity.styleSave.TextColor;
        alphaAdapter.BackgroundColor = activity.styleSave.ThemeColor2;

        weightAdapter = new NotepadSpinnerAdapter(activity.getBaseContext(),0,
                new String[]{activity.words.get("Thickness")+":","3px","6px","9px","12px","15px"},
                activity.words.get("Thickness"));
        weightAdapter.TextColor = activity.styleSave.TextColor;
        weightAdapter.BackgroundColor = activity.styleSave.ThemeColor2;
    }

    private void logicSpinner(){
        file.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    paintCanvas.reset();
                } else if (position == 2) {
                    new PaintSaveFile(activity).openProgram(paintCanvas);
                } else if (position == 3) {
                    closeProgram(1);
                    new PaintOpenFile(activity).openProgram();
                } else if (position == 4) {
                    closeProgram(1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        alpha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    paintCanvas.strokeAlpha = Integer.parseInt(alphaAdapter.getItem(position).replace("%", ""));
                    paintCanvas.setStyle();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    paintCanvas.strokeWidth = Integer.parseInt(weightAdapter.getItem(position).replace("px", ""));
                    paintCanvas.setStyle();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public void openProgram(Drawable drawable){
        paintCanvas = new PaintCanvas(activity.getBaseContext());
        initView();
        adapters();
        style();
        logicSpinner();
        paintCanvas.setBackground(drawable);
        canvas.addView(paintCanvas,
                (int) (drawable.getIntrinsicWidth() * 1.4),
                (int) (drawable.getIntrinsicHeight() * 1.4));
        canvas.setBackgroundColor(activity.styleSave.ThemeColor1);

        initProgram();
        super.openProgram();
    }
    public void openProgram(){
        paintCanvas = new PaintCanvas(activity.getBaseContext());
        initView();
        adapters();
        logicSpinner();
        style();
        canvas.addView(paintCanvas);
        initProgram();
        super.openProgram();
    }
}
