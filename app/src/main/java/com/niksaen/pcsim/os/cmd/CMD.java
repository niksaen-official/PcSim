package com.niksaen.pcsim.os.cmd;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.adapters.CMD_Adapter;
import com.niksaen.pcsim.os.cmd.libs.Customization;
import com.niksaen.pcsim.os.cmd.libs.Installer;
import com.niksaen.pcsim.os.cmd.libs.OS;
import com.niksaen.pcsim.os.cmd.libs.Task;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.os.cmd.libs.Base;
import com.niksaen.pcsim.os.cmd.libs.Drive;
import com.niksaen.pcsim.os.cmd.libs.DriverInstallerExtended;
import com.niksaen.pcsim.os.cmd.libs.InstallerFromDrive;
import com.niksaen.pcsim.os.cmd.libs.Pc;
import com.niksaen.pcsim.viruses.CADARTC;
import com.niksaen.pcsim.viruses.DWM;
import com.niksaen.pcsim.viruses.FAOYR;
import com.niksaen.pcsim.viruses.FARDOTC;
import com.niksaen.pcsim.viruses.LTMP;
import com.niksaen.pcsim.viruses.OCP10TASDC;
import com.niksaen.pcsim.viruses.RAD;
import com.niksaen.pcsim.viruses.RAP;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CMD extends Program {
    public Context Context;
    public static final String OS = "OS";
    public static final String WINDOW = "WINDOW";
    public static final String AUTO = "AUTO";
    public static final String SEMI_AUTO_OS = "SEMI_AUTO_OS";
    String Type = OS;
    public void setType(String type) { Type = type; }

    public static final String ERROR = "ERROR: ";
    public static final String SUCCESS = "SUCCESS: ";
    public static final String WARN = "WARN: ";

    public String[] commandList = {};
    public String buffer;
    public CMD(MainActivity activity) {
        super(activity);
        Title = "CMD";
        ValueRam = new int[]{20,25};
        ValueVideoMemory = new int[]{15,25};
        Context = activity.getBaseContext();
        initProgram();
    }

    public CMD_Adapter adapter;
    public ArrayList<String> outputCommand = new ArrayList<>();

    ListView output;
    public EditText input;
    Button enter;
    @Override
    public void initProgram() {
        outputCommand.add(CMD.SUCCESS+"CMD is start");
        switch (Type) {
            case OS:
                mainWindow = LayoutInflater.from(activity).inflate(R.layout.os_console, null);
                ValueRam = new int[]{0,0};
                ValueVideoMemory = new int[]{0,0};
                break;
            case WINDOW:
                mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_cmd, null);
                super.initProgram();
                break;
            case AUTO:
                mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_cmd, null);
                super.initProgram();
                initView();
                style();
                enter.setClickable(false);
                for (String command : commandList) {
                    input.setText(command);
                    logic();
                }
                break;
            case SEMI_AUTO_OS:
                mainWindow = LayoutInflater.from(activity).inflate(R.layout.os_console, null);
                initView();
                style();
                for (String command : commandList) {
                    logic(command);
                }
                break;
            default:
                mainWindow = LayoutInflater.from(activity).inflate(R.layout.os_console, null);
                break;
        }
        initView();
        style();

        enter.setOnClickListener(v->{
            buffer = input.getText().toString();
            commandList = buffer.split("\n");
            for (String command : commandList) {
                logic(command);
            }
        });
    }
    private void initView(){
        output = mainWindow.findViewById(R.id.output_field);
        input = mainWindow.findViewById(R.id.input_field);
        enter = mainWindow.findViewById(R.id.enter);
    }
    private void style(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            input.setTextCursorDrawable(R.color.color17);
        }
        adapter = new CMD_Adapter(activity,outputCommand);
        input.setTypeface(activity.font);
        enter.setTypeface(activity.font);
        output.setAdapter(adapter);
    }
    private void logic(){
        enter.setClickable(false);
        String command = input.getText().toString();
        logic(command);
    }
    public void logic(String command) {
        if (command.startsWith("#")) {
            command = command.replace("#","").trim();
        }
        if(command.equals("start")){
            CADARTC v = new CADARTC(activity);
            v.openProgram();
            return;
        }
        adapter.notifyDataSetChanged();
        output.smoothScrollToPosition(outputCommand.size());

        String finalCommand = command;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    input.setText("");
                    enter.setClickable(true);
                    if (finalCommand.startsWith("cmd.")) Base.start(CMD.this, finalCommand);
                    else if (finalCommand.startsWith("drive.")) Drive.start(activity,CMD.this, finalCommand);
                    else if (finalCommand.startsWith("pc.")) Pc.start(activity,CMD.this, finalCommand);
                    else if (finalCommand.startsWith("ifd.")) InstallerFromDrive.start(activity,CMD.this, finalCommand);
                    else if (finalCommand.startsWith("driver.")) DriverInstallerExtended.start(activity,CMD.this, finalCommand);
                    else if (finalCommand.startsWith("task.")) Task.start(finalCommand, CMD.this);
                    else if (finalCommand.startsWith("installer.")) Installer.start(activity, CMD.this, finalCommand);
                    else if (finalCommand.startsWith("cstm.")) Customization.start(CMD.this, finalCommand);
                    else if (finalCommand.startsWith("os.")) com.niksaen.pcsim.os.cmd.libs.OS.start(activity, CMD.this, finalCommand);
                    else error(activity.words.get("The package will not find"));
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
        adapter.notifyDataSetChanged();
    }
    public void startCommandList(){
        for (String command : commandList) {
            input.setText(command);
            logic();
        }
    }
    public void error(String text) {
        outputCommand.add(ERROR + text);
        adapter.notifyDataSetChanged();
        output.smoothScrollToPosition(outputCommand.size());
    }
    public void output(String text) {
        outputCommand.add(text);
        adapter.notifyDataSetChanged();
        output.smoothScrollToPosition(outputCommand.size());
    }
    public void warn(String text){
        outputCommand.add(WARN + text);
        adapter.notifyDataSetChanged();
        output.smoothScrollToPosition(outputCommand.size());
    }
    public void success(String text){
        outputCommand.add(SUCCESS + text);
        adapter.notifyDataSetChanged();
        output.smoothScrollToPosition(outputCommand.size());
    }
}
