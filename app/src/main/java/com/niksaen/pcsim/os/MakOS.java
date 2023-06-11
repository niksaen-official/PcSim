package com.niksaen.pcsim.os;

import android.graphics.Color;
import android.view.View;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.Others;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MakOS extends Program{
    public static String TITLE = "MakOS";
    public MakOS(MainActivity activity) {
        super(activity);
        Title = TITLE;
        Type = Program.BACKGROUND;
        ValueRam = new int[]{450,600};
        ValueVideoMemory = new int[]{120,200};
    }

    @Override
    public void openProgram() {
        CMD cmd = new CMD(activity);
        cmd.setType(CMD.SEMI_AUTO_OS);
        activity.styleSave.getStyle();
        activity.greeting.setVisibility(View.VISIBLE);
        activity.greeting.setTextColor(activity.styleSave.GreetingColor);
        activity.greeting.setText(activity.styleSave.Greeting);
        Timer timer = new Timer();
        int drivePos = 0;
        for (int i = 0; i < 6; i++) {
            HashMap<String, String> disk = activity.pcParametersSave.getDrive(i);
            if (disk != null) {
                drivePos = i;
                break;
            }
        }
        int finalDrivePos = drivePos;
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
                                                activity.programArrayList.add(MakOS.this);
                                                activity.taskManager.update();
                                                activity.updateDesktop();
                                                activity.startMenuOpener.setBackgroundResource(R.drawable.mak_os_logo);
                                            }else {
                                                cmd.commandList = new String[] {
                                                        "driver.prepare.select_storage_slot:"+ finalDrivePos,
                                                        "driver.install.for_gpu"
                                                };
                                                cmd.openProgram();
                                            }
                                        }else {
                                            cmd.commandList = new String[] {
                                                    "driver.prepare.select_storage_slot:"+ finalDrivePos,
                                                    "driver.install.for_storage"
                                            };
                                            cmd.openProgram();
                                        }
                                    }
                                    else {
                                        cmd.commandList = new String[] {
                                                "driver.prepare.select_storage_slot:"+ finalDrivePos,
                                                "driver.install.for_ram"
                                        };
                                        cmd.openProgram();
                                    }
                                }
                                else {
                                    cmd.commandList = new String[] {
                                            "driver.prepare.select_storage_slot:"+ finalDrivePos,
                                            "driver.install.for_cpu"
                                    };
                                    cmd.openProgram();
                                }
                            }else {
                                cmd.commandList = new String[] {
                                        "driver.prepare.select_storage_slot:"+ finalDrivePos,
                                        "driver.install.for_motherboard"
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
            timer.schedule(timerTask,800);
        }
        else{
            timer.schedule(timerTask,1600);
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
}
