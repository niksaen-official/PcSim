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

public class LiriOS extends Program {
    public static String TITLE = "LiriOS";
    public OSSettings settings;
    private CMD cmd;
    public LiriOS(MainActivity activity) {
        super(activity);
        settings = new OSSettings(activity);
        Title = TITLE;
        Type = Program.BACKGROUND;
        ValueRam = new int[]{650,700};
        ValueVideoMemory = new int[]{100,150};
    }

    @Override
    public void openProgram() {
        cmd = new CMD(activity);
        cmd.setType(CMD.SEMI_AUTO_OS);
        mainWindow = new View(activity);
        style();
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
                                                activity.desktop.setVisibility(View.VISIBLE);
                                                activity.toolbar.setBackgroundColor(activity.styleSave.ToolbarColor);
                                                activity.layout.setBackgroundResource(activity.styleSave.BackgroundResource);
                                                activity.toolbar.setVisibility(View.VISIBLE);
                                                status = 0;
                                                activity.programArrayList.add(LiriOS.this);
                                                activity.taskManager.update();
                                                activity.updateDesktop();
                                                activity.startMenuOpener.setBackgroundResource(R.drawable.liri_os_logo);
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
            timer.schedule(timerTask,1000);
        }
        else{
            timer.schedule(timerTask,2000);
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
    private void style(){
        activity.styleSave.ArrowButtonImage = R.drawable.arrow_color16;
        activity.styleSave.PauseButtonRes = R.drawable.pause_color16;
        activity.styleSave.PlayButtonImage = R.drawable.play_color16;
        activity.styleSave.PrevOrNextImageRes = R.drawable.prev_or_next_color16;
        activity.styleSave.CloseButtonImageRes = R.drawable.button_1_color17;
        activity.styleSave.FullScreenMode1ImageRes = R.drawable.button_2_1_color17;
        activity.styleSave.FullScreenMode2ImageRes = R.drawable.button_2_2_color17;
        activity.styleSave.RollUpButtonImageRes = R.drawable.button_3_color17;
        activity.styleSave.ColorWindow = Color.parseColor("#000000");
        activity.styleSave.ToolbarColor = Color.parseColor("#212121");
        activity.styleSave.StartMenuColor = Color.parseColor("#323232");
        activity.styleSave.ThemeColor1 = Color.parseColor("#121212");
        activity.styleSave.ThemeColor2 = Color.parseColor("#222222");
        activity.styleSave.ThemeColor3 = Color.parseColor("#404040");
        activity.styleSave.TitleColor = Color.parseColor("#FFFFFF");
        activity.styleSave.StartMenuTextColor = activity.styleSave.TitleColor;
        activity.styleSave.ToolbarTextColor = activity.styleSave.TitleColor;
        activity.styleSave.TextButtonColor = activity.styleSave.TitleColor;
        activity.styleSave.TextColor = activity.styleSave.TitleColor;
        activity.styleSave.ProgressBarResource = R.drawable.progress_bar_circle_color17;
        activity.styleSave.SeekBarProgressResource = R.drawable.seek_progress_color17;
        activity.styleSave.SeekBarThumbResource = R.drawable.seek_thumb_color18;
        activity.styleSave.ToolbarAppNameVisible = false;
        activity.styleSave.ToolbarAppIconVisible = true;
        activity.styleSave.StartMenuAppNameVisible = true;
        activity.styleSave.StartMenuAppIconVisible = false;
        activity.styleSave.Greeting = activity.words.get("Welcome");
        activity.styleSave.GreetingColor = activity.styleSave.TitleColor;
    }
    private void autoRunPrograms(){
        if(!settings.getAutoRunList().isEmpty()){
            for(String programId:settings.getAutoRunList()){
                cmd.logic("os.start:"+programId);
            }
        }
    }
}
