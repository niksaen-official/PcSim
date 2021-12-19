package com.niksaen.pcsim.program.cmd;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.ErrorCodeList;
import com.niksaen.pcsim.classes.adapters.CMD_Adapter;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.cmd.libs.Base;
import com.niksaen.pcsim.program.cmd.libs.Drive;
import com.niksaen.pcsim.program.cmd.libs.InstallerFromDrive;
import com.niksaen.pcsim.program.cmd.libs.Pc;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CMD extends Program {
    public final String OS = "OS";
    public static final String WINDOW = "WINDOW";
    String Type = OS;
    public void setType(String type) { Type = type; }

    public static final String ERROR = "ERROR: ";
    public static final String SUCCESS = "SUCCESS: ";
    public static final String WARN = "WARN: ";

    public CMD(MainActivity activity) {
        super(activity);
        Title = "CMD";
        ValueRam = new int[]{20,25};
        ValueVideoMemory = new int[]{15,25};
    }

    public static CMD_Adapter adapter;
    public static ArrayList<String> outputCommand = new ArrayList<>();

    ListView output;
    EditText input;
    Button enter;
    @Override
    public void initProgram() {
        outputCommand.add(CMD.SUCCESS+"CMD is start");
        if(Type.equals(WINDOW)) {
            mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_cmd,null);
            super.initProgram();
        }
        else{
            mainWindow = LayoutInflater.from(activity).inflate(R.layout.os_console,null);
        }
        initView();
        style();

        enter.setOnClickListener(v->logic());
    }
    private void initView(){
        output = mainWindow.findViewById(R.id.output_field);
        input = mainWindow.findViewById(R.id.input_field);
        enter = mainWindow.findViewById(R.id.enter);
    }
    private void style(){
        adapter = new CMD_Adapter(activity,outputCommand);
        input.setTypeface(activity.font);
        enter.setTypeface(activity.font);
        output.setAdapter(adapter);
    }
    private void logic(){
        enter.setClickable(false);
        String command = input.getText().toString();
        outputCommand.add(command);
        adapter.notifyDataSetChanged();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    input.setText("");
                    enter.setClickable(true);
                    if(command.startsWith("cmd."))Base.start(command.replace("cmd.",""));
                    else if(command.startsWith("drive.")) Drive.start(activity,command.replace("drive.",""));
                    else if(command.startsWith("pc.")) Pc.start(activity,command.replace("pc.",""));
                    else if(command.startsWith("ifd.")) InstallerFromDrive.start(activity,command.replace("ifd.",""));
                    else outputCommand.add(ERROR + "0xDD0001" +"\n"+ErrorCodeList.ErrorCodeText.get("0xDD0001"));
                    adapter.notifyDataSetChanged();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,500);
    }

    @Override
    public void closeProgram(int mode) {
        super.closeProgram(mode);
        outputCommand.clear();
    }
}
