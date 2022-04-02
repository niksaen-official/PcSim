package com.niksaen.pcsim.program.calculator;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.window.ErrorWindow;

public class Calculator extends Program {
    public Calculator(MainActivity activity) {
        super(activity);
        Title = "Calculator";
        ValueRam = new int[]{200,250};
        ValueVideoMemory = new int[]{150,250};
    }

    RecyclerView numpad;
    TextView inputField,outputField;
    Button backSpace,button;
    @Override
    public void initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_calculator,null);
        initView();
        style();
        super.initProgram();
        backSpace.setOnClickListener(v->{
            if(inputField.getText().toString().length()>0) {
                inputField.setText(inputField.getText().toString().substring(0, inputField.getText().toString().length() - 1));
                outputField.setText("");
            }
        });
        button.setOnClickListener(v->{
            try {
                if (inputField.getText().toString().endsWith(")")) {
                    outputField.setText(String.valueOf(Logic.evaluate(inputField.getText().toString() + " + 0")));
                } else {
                    outputField.setText(String.valueOf(Logic.evaluate(inputField.getText().toString())));
                }
            }catch (Exception e){
                ErrorWindow errorWindow = new ErrorWindow(activity);
                if(e.toString().contains("ArrayIndexOutOfBoundsException")){
                    errorWindow.setMessageText(activity.words.get("An example must not start with an arithmetic sign, except for a minus sign."));
                }
                else if(e.toString().contains("IndexOutOfBoundsException")){
                    errorWindow.setMessageText(activity.words.get("The example must not end with an arithmetic sign."));
                }
                else  if(e.toString().contains("NumberFormatException")){
                    errorWindow.setMessageText(activity.words.get("Invalid number format."));
                }else{
                    outputField.setText(e.toString());
                }
                errorWindow.setButtonOkClick(v1 -> {
                    closeProgram(1);
                    errorWindow.closeProgram(1);
                });
                errorWindow.setButtonOkText(activity.words.get("Close program"));
                errorWindow.openProgram();
            }
        });
    }
    private void initView(){
        numpad = mainWindow.findViewById(R.id.numpad);
        inputField = mainWindow.findViewById(R.id.input_field);
        outputField = mainWindow.findViewById(R.id.output_field);
        backSpace= mainWindow.findViewById(R.id.backspace);
        button = mainWindow.findViewById(R.id.button);
    }
    private void style(){
        outputField.setBackgroundColor(activity.styleSave.ThemeColor2);
        outputField.setTextColor(activity.styleSave.TextColor);
        outputField.setTypeface(activity.font);
        inputField.setBackgroundColor(activity.styleSave.ThemeColor2);
        inputField.setTextColor(activity.styleSave.TextColor);
        inputField.setTypeface(activity.font);
        backSpace.setBackgroundColor(activity.styleSave.ThemeColor2);
        backSpace.setTextColor(activity.styleSave.TextColor);
        backSpace.setTypeface(activity.font, Typeface.BOLD);
        button.setBackgroundColor(activity.styleSave.ThemeColor2);
        button.setTextColor(activity.styleSave.TextColor);
        button.setTypeface(activity.font, Typeface.BOLD);

        backSpace.setText("<--");
        button.setText("=");
        NumpadAdapter adapter = new NumpadAdapter(activity.getBaseContext());
        adapter.InputField = inputField;
        adapter.OutputField = outputField;
        adapter.BackgroundColor = activity.styleSave.ThemeColor2;
        adapter.TextColor = activity.styleSave.TextColor;
        numpad.setLayoutManager( new GridLayoutManager(activity,4));
        numpad.setAdapter(adapter);
    }
}
