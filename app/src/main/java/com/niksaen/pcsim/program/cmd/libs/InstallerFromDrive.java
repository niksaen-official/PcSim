package com.niksaen.pcsim.program.cmd.libs;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.ErrorCodeList;
import com.niksaen.pcsim.program.ProgramListAndData;
import com.niksaen.pcsim.program.cmd.CMD;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class InstallerFromDrive {
    static String disk;
    static String storagePos;
    public static void start(MainActivity activity, String command){
        if (command.startsWith("cd")){
            if(activity.DiskInDrive != null) {
                CMD.outputCommand.add(CMD.SUCCESS+"Disk changed");
                disk = activity.DiskInDrive;
            }else {
                CMD.outputCommand.add(CMD.ERROR +"0xDD0001"+ ErrorCodeList.ErrorCodeText.get("0xDD0001"));
            }
        }
        else if(command.startsWith("csd")){
            if(activity.DiskInDrive != null) {
                CMD.outputCommand.add(CMD.SUCCESS+"Change storage device:");
                if (activity.pcParametersSave.DATA1 != null) {
                    CMD.outputCommand.add("sd1 for change storage device under id: " + activity.pcParametersSave.DATA1.get("name"));
                }
                if (activity.pcParametersSave.DATA2 != null) {
                    CMD.outputCommand.add("sd2 for change storage device under id: " + activity.pcParametersSave.DATA2.get("name"));
                }
                if (activity.pcParametersSave.DATA3 != null) {
                    CMD.outputCommand.add("sd3 for change storage device under id: " + activity.pcParametersSave.DATA3.get("name"));
                }
                if (activity.pcParametersSave.DATA4 != null) {
                    CMD.outputCommand.add("sd4 for change storage device under id: " + activity.pcParametersSave.DATA4.get("name"));
                }
                if (activity.pcParametersSave.DATA5 != null) {
                    CMD.outputCommand.add("sd5 for change storage device under id: " + activity.pcParametersSave.DATA5.get("name"));
                }
                if (activity.pcParametersSave.DATA6 != null) {
                    CMD.outputCommand.add("sd6 for change storage device under id: " + activity.pcParametersSave.DATA6.get("name"));
                }
            }
        }
        else if(command.startsWith("sd")){
            storagePos = command.replace("sd","");
            CMD.outputCommand.add(CMD.SUCCESS+"storage changed");
        }else if(command.startsWith("install")){
            if(disk == null){
                CMD.outputCommand.add(CMD.ERROR +"0xDD0001"+ ErrorCodeList.ErrorCodeText.get("0xDD0001"));
                return;
            }
            if (storagePos == null){
                CMD.outputCommand.add(CMD.ERROR +"0xCMD0003"+ ErrorCodeList.ErrorCodeText.get("0xCMD0003"));
                return;
            }
            HashMap<String, HashMap<String,String>> storage = new HashMap<>();
            if(activity.pcParametersSave.DATA1 != null){
                storage.put("1",activity.pcParametersSave.DATA1);
            }
            if(activity.pcParametersSave.DATA2 != null){
                storage.put("2",activity.pcParametersSave.DATA2);
            }
            if(activity.pcParametersSave.DATA3 != null){
                storage.put("3",activity.pcParametersSave.DATA3);
            }
            if(activity.pcParametersSave.DATA4 != null){
                storage.put("4",activity.pcParametersSave.DATA4);
            }
            if(activity.pcParametersSave.DATA5 != null){
                storage.put("5",activity.pcParametersSave.DATA5);
            }
            if(activity.pcParametersSave.DATA6 != null){
                storage.put("6",activity.pcParametersSave.DATA6);
            }
            final int[] progress = {0};
            if(activity.pcParametersSave.getEmptyStorageSpace(storage.get(storagePos))>= ProgramListAndData.programSize.get(disk)){
                CMD.outputCommand.add(CMD.SUCCESS+"enough space for installation");
                progress[0] +=2;
            }
            else {
                CMD.outputCommand.add(CMD.ERROR +"0xCMD0004"+ ErrorCodeList.ErrorCodeText.get("0xCMD0004"));
            }
            Timer timer = new Timer();
            TimerTask unpacking = new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(() -> {
                        progress[0]++;
                        CMD.outputCommand.add("Installation progress: "+progress[0]+"%");
                        CMD.adapter.notifyDataSetChanged();
                        if(progress[0] == 100){
                            if(disk.equals("OS Installer")) {
                                storage.get(storagePos).put("Содержимое", storage.get(storagePos).get(("Содержимое")) + "OS,");
                            }else {
                                storage.get(storagePos).put("Содержимое", storage.get(storagePos).get(("Содержимое")) + disk + ",");
                            }
                            CMD.outputCommand.add(CMD.SUCCESS +"0xCMD0005"+ ErrorCodeList.ErrorCodeText.get("0xCMD0005"));
                            timer.cancel();
                        }
                    });
                }
            };
            if(storage.get(storagePos).get("Тип").equals("HDD")) {
                timer.scheduleAtFixedRate(unpacking, 0, (long) ((ProgramListAndData.programSize.get(disk) * 300)));
            }else {
                timer.scheduleAtFixedRate(unpacking, 0, (long) ((ProgramListAndData.programSize.get(disk) * 210)));
            }
        }
    }
}
