package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.ProgramListAndData;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.os.LiriOS;
import com.niksaen.pcsim.os.MakOS;
import com.niksaen.pcsim.os.NapiOS;
import com.niksaen.pcsim.os.cmd.CMD;

public class OS {
    public static void start(MainActivity activity, CMD cmd, String command){
        command = command.replace("os.","");
        if(haveOs(activity)){
            if(command.startsWith("start:")){
                command=command.replace("start:","").trim();
                String program = Installer.getProgramsId(activity).get(command);
                ProgramListAndData programMap = new ProgramListAndData();
                programMap.initHashMap(activity);
                if(program != null) {
                    if (haveProgram(activity, program)) {
                        programMap.programHashMap.get(program).openProgram();
                    } else {
                        cmd.error(activity.words.get("Runtime package not found or not installed"));
                    }
                }else {
                    cmd.error(activity.words.get("Runtime package not found or not installed"));
                }
            }
            else if(command.startsWith("autorun.")){
                if(activity.programArrayList.get(0).Title.contains(NapiOS.TITLE)
                        || activity.programArrayList.get(0).Title.contains(LiriOS.TITLE)) {
                    command = command.replace("autorun.", "");
                    if (command.startsWith("add:")) {
                        if (activity.programArrayList.get(0).Title.equals(NapiOS.TITLE)) {
                            NapiOS os = (NapiOS) activity.programArrayList.get(0);
                            if(os.settings.addToAutoRun(command.replace("add:", ""))){
                                cmd.success(activity.words.get("Program added to autorun"));
                            }
                            else {
                                cmd.error(activity.words.get("The program is already in the startup list"));
                            }
                        } else {
                            LiriOS os = (LiriOS) activity.programArrayList.get(0);
                            if(os.settings.addToAutoRun(command.replace("add:", ""))){
                                cmd.success(activity.words.get("Program added to autorun"));
                            }
                            else {
                                cmd.error(activity.words.get("The program is already in the startup list"));
                            }
                        }
                    }
                    else if (command.startsWith("remove:")) {
                        if (activity.programArrayList.get(0).Title.equals(NapiOS.TITLE)) {
                            NapiOS os = (NapiOS) activity.programArrayList.get(0);
                            os.settings.removeAutoRunList(command.replace("remove:", "").trim());
                            cmd.success(activity.words.get("Program removed from startup"));
                        } else {
                            LiriOS os = (LiriOS) activity.programArrayList.get(0);
                            os.settings.removeAutoRunList(command.replace("remove:", "").trim());
                            cmd.success(activity.words.get("Program removed to autorun"));
                        }
                    }
                    else if (command.equals("clear")) {
                        if (activity.programArrayList.get(0).Title.equals(NapiOS.TITLE)) {
                            NapiOS os = (NapiOS) activity.programArrayList.get(0);
                            os.settings.clearAutoRunList();
                            cmd.success(activity.words.get("Program removed from startup"));
                        } else {
                            LiriOS os = (LiriOS) activity.programArrayList.get(0);
                            os.settings.clearAutoRunList();
                            cmd.success(activity.words.get("List of auto-startup programs cleared"));
                        }
                    }
                    else if (command.equals("get")) {
                        if (activity.programArrayList.get(0).Title.equals(NapiOS.TITLE)) {
                            NapiOS os = (NapiOS) activity.programArrayList.get(0);
                            cmd.success("Autorun program list");
                            for (String id:os.settings.getAutoRunList()){
                                cmd.output(id);
                            }
                        } else {
                            LiriOS os = (LiriOS) activity.programArrayList.get(0);
                            cmd.success("Autorun program list");
                            for (String id:os.settings.getAutoRunList()){
                                cmd.output(id);
                            }
                        }
                    }
                    else cmd.error("The command was entered incorrectly");
                }else cmd.error(activity.words.get("Your operating system does not support autorun program"));
            }
        }else {
            cmd.error(activity.words.get("Operating system not found"));
        }
    }
    private static boolean haveOs(MainActivity activity){
        String apps = StringArrayWork.ArrayListToString(activity.apps);
        return apps.contains(NapiOS.TITLE) || apps.contains(LiriOS.TITLE) || apps.contains(MakOS.TITLE);
    }
    private static boolean haveProgram(MainActivity activity,String program){
        String apps = StringArrayWork.ArrayListToString(activity.apps);
        return apps.contains(program);
    }
}
