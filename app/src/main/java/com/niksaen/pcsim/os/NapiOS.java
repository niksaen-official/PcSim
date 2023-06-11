package com.niksaen.pcsim.os;

import android.graphics.Color;
import android.view.View;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.save.OSSettings;

import java.util.Timer;
import java.util.TimerTask;

public class NapiOS extends Program {
    public static String TITLE = "NapiOS";
    public OSSettings settings;
    private CMD cmd;
    public NapiOS(MainActivity activity) {
        super(activity);
        settings = new OSSettings(activity);
        Title = TITLE;
        Type = Program.BACKGROUND;
        ValueRam = new int[]{850,900};
        ValueVideoMemory = new int[]{120,200};
    }

    @Override
    public void openProgram() {
        cmd = new CMD(activity);
        cmd.setType(CMD.SEMI_AUTO_OS);
        activity.styleSave.getStyle();
        activity.greeting.setVisibility(View.VISIBLE);
        activity.greeting.setTextColor(activity.styleSave.GreetingColor);
        activity.greeting.setText(activity.styleSave.Greeting);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    CurrentRamUse = Others.random(ValueRam[0],ValueRam[1]);
                    CurrentVideoMemoryUse = Others.random(ValueVideoMemory[0],ValueVideoMemory[1]);
                    if(activity.pcParametersSave.getEmptyRam(activity.programArrayList) > CurrentRamUse) {
                        if(activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList)>CurrentVideoMemoryUse) {
                            if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForMotherboard+activity.pcParametersSave.Mobo)) {
                                if(StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.DriverForCPU+activity.pcParametersSave.Cpu)) {
                                    if(activity.pcParametersSave.ramDriverValid()) {
                                        if(activity.pcParametersSave.driveDriverValid()) {
                                            if(activity.pcParametersSave.gpuDriverValid() || activity.pcParametersSave.CPU.get("Графическое ядро").equals("+")) {
                                                activity.greeting.setVisibility(View.GONE);
                                                activity.layout.setBackgroundResource(activity.styleSave.BackgroundResource);
                                                activity.desktop.setVisibility(View.VISIBLE);
                                                activity.toolbar.setBackgroundColor(activity.styleSave.ToolbarColor);
                                                activity.toolbar.setVisibility(View.VISIBLE);
                                                status = 0;
                                                activity.programArrayList.add(NapiOS.this);
                                                activity.taskManager.update();
                                                activity.updateDesktop();
                                                activity.startMenuOpener.setBackgroundResource(R.drawable.napi_os_logo);
                                                autoRunPrograms();
                                            }else {
                                                cmd.commandList = new String[] {
                                                        "#cmd.write:"+activity.words.get("No video card drivers found"),
                                                };
                                                cmd.openProgram();
                                            }
                                        }else {
                                            cmd.commandList = new String[] {
                                                    "#cmd.write:"+activity.words.get("No drivers found for the drive"),
                                            };
                                            cmd.openProgram();
                                        }
                                    }
                                    else {
                                        cmd.commandList = new String[] {
                                                "#cmd.write:"+activity.words.get("Drivers for RAM not found"),
                                        };
                                        cmd.openProgram();
                                    }
                                }
                                else {
                                    cmd.commandList = new String[] {
                                            "#cmd.write:"+activity.words.get("No drivers found for the processor"),
                                    };
                                    cmd.openProgram();
                                }
                            }else {
                                cmd.commandList = new String[] {
                                        "#cmd.write:"+activity.words.get("No drivers found for the motherboard"),
                                };
                                cmd.openProgram();
                            }
                        }
                        else {
                            cmd.commandList = new String[] {
                                    "#cmd.write:"+activity.words.get("Not enough video memory"),
                            };
                            cmd.openProgram();
                        }
                    } else {
                        cmd.commandList = new String[] {
                                "#cmd.write:"+activity.words.get("Not enough RAM to start the operating system"),
                        };
                        cmd.openProgram();
                    }
                });
            }
        };
        if(activity.pcParametersSave.getMainDiskType().equals("SSD")){
            timer.schedule(timerTask,1500);
        }
        else{
            timer.schedule(timerTask,3000);
        }
    }

    @Override
    public void closeProgram(int mode) {
        activity.programArrayList.remove(this);
        activity.layout.setBackgroundColor(Color.BLACK);
        activity.toolbar.setVisibility(View.GONE);
        activity.desktop.setVisibility(View.GONE);
        activity.startMenu.setVisibility(View.GONE);
        activity.greeting.setVisibility(View.GONE);
        activity.taskManager.update();
    }

    @Override
    public void rollUpProgram() {

    }
    private void autoRunPrograms(){
        if(!settings.getAutoRunList().isEmpty()){
            for(String programId:settings.getAutoRunList()){
                cmd.logic("os.start:"+programId);
            }
        }
    }
}
