package com.niksaen.pcsim.program.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.program.Program;

public class Notepad extends Program {

    public Notepad(MainActivity activity){
        super(activity);
        this.Title = "Notepad";
        ValueRam = new int[]{20,35};
        ValueVideoMemory = new int[]{42,73};
    }

    EditText editText;
    Spinner spinner;
    LinearLayout toolbar;

    NotepadSpinnerAdapter notepadSpinnerAdapter;

    private void initView(){
        mainWindow = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.program_notepad,null);
        editText = mainWindow.findViewById(R.id.editText);
        spinner = mainWindow.findViewById(R.id.file);
        toolbar = mainWindow.findViewById(R.id.toolbar);
    }
    private void style(){
        editText.setTextColor(activity.styleSave.TextColor);
        editText.setTypeface(activity.font);
        editText.setBackgroundColor(activity.styleSave.ThemeColor1);
        toolbar.setBackgroundColor(activity.styleSave.ThemeColor2);
        String[] fileWork = new String[]{
              activity.words.get("File")+":",
                activity.words.get("New file"),
                activity.words.get("Save"),
                activity.words.get("Open"),
                activity.words.get("Exit")
        };
        notepadSpinnerAdapter = new NotepadSpinnerAdapter(activity.getBaseContext(),R.layout.item_textview,fileWork,activity.words.get("File"));
        notepadSpinnerAdapter.BackgroundColor = activity.styleSave.ThemeColor2;
        notepadSpinnerAdapter.TextColor = activity.styleSave.TextColor;

        spinner.setAdapter(notepadSpinnerAdapter);
    }

    public void openProgram(String text){
        initView();
        style();
        editText.setText(text);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    editText.setText("");
                } else if (position == 2) {
                    if (StringArrayWork.ArrayListToString(activity.apps).contains(Program.AdditionalSoftPrefix + "File manager: SaveLibs")) {
                        new NotepadFileSave(activity).openProgram(editText.getText().toString());
                    }
                } else if (position == 3) {
                    if (StringArrayWork.ArrayListToString(activity.apps).contains(Program.AdditionalSoftPrefix + "File manager: OpenLibs")) {
                        closeProgram(1);
                        new NotepadFileOpen(activity).openProgram();
                    }
                } else if (position == 4) {
                    closeProgram(1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        initProgram();
        super.openProgram();
    }

    public void openProgram(){
        initView();
        style();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    editText.setText("");
                } else if (position == 2) {
                    new NotepadFileSave(activity).openProgram(editText.getText().toString());
                } else if (position == 3) {
                    closeProgram(1);
                    new NotepadFileOpen(activity).openProgram();
                } else if (position == 4) {
                    closeProgram(1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        initProgram();
        super.openProgram();
    }
    public void closeProgram(int mode){
        super.closeProgram(mode);
        mainWindow.setVisibility(View.GONE);
    }
}
