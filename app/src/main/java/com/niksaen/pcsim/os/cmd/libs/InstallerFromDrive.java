package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.os.LiriOS;
import com.niksaen.pcsim.os.NapiOS;
import com.niksaen.pcsim.classes.ProgramListAndData;
import com.niksaen.pcsim.os.cmd.CMD;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class InstallerFromDrive {
    static String disk;
    static String storagePos;
    public static void start(MainActivity activity,CMD cmd, String command){
        command = command.replace("#","");
        HashMap<String, HashMap<String,String>> storage = new HashMap<>();
        if(activity.pcParametersSave.DATA1 != null){
            storage.put("0",activity.pcParametersSave.DATA1);
            storage.put(activity.pcParametersSave.DATA1.get("name"),activity.pcParametersSave.DATA1);
        }
        if(activity.pcParametersSave.DATA2 != null){
            storage.put("1",activity.pcParametersSave.DATA2);
            storage.put(activity.pcParametersSave.DATA2.get("name"),activity.pcParametersSave.DATA2);
        }
        if(activity.pcParametersSave.DATA3 != null){
            storage.put("2",activity.pcParametersSave.DATA3);
            storage.put(activity.pcParametersSave.DATA3.get("name"),activity.pcParametersSave.DATA3);
        }
        if(activity.pcParametersSave.DATA4 != null){
            storage.put("3",activity.pcParametersSave.DATA4);
            storage.put(activity.pcParametersSave.DATA4.get("name"), activity.pcParametersSave.DATA4);
        }
        if(activity.pcParametersSave.DATA5 != null){
            storage.put("4",activity.pcParametersSave.DATA5);
            storage.put(activity.pcParametersSave.DATA5.get("name"),activity.pcParametersSave.DATA5);
        }
        if(activity.pcParametersSave.DATA6 != null){
            storage.put("5",activity.pcParametersSave.DATA6);
            storage.put(activity.pcParametersSave.DATA6.get("name"),activity.pcParametersSave.DATA6);
        }
        command = command.replace("ifd.","");
        if(command.startsWith("prepare.")){
            command = command.replace("prepare.","");
            if (command.startsWith("get_disk")){
                if(activity.DiskInDrive != null) {
                    if(cmd.storageSlot >-1){
                        storagePos = String.valueOf(cmd.storageSlot);
                    }
                    cmd.success(activity.words.get("Disc selected"));
                    disk = activity.DiskInDrive;
                    if(disk.contains("OS Installer Simplified")){
                        disk = disk.replace("Simplified","").trim();
                        cmd.commandList = new String[]{
                                "driver.prepare.select_storage_slot:"+storagePos,
                                "driver.install.all",
                                "ifd.reinstall"
                        };
                        cmd.startCommandList();
                    }
                }else cmd.error("Disk not found");
            }
            else if(command.startsWith("storage_list_slots:")){
                cmd.success(activity.words.get("List of drives for selection:"));
                if (activity.pcParametersSave.DATA1 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_slot:0\" "+activity.words.get("for select storage device under id:")+" " + activity.pcParametersSave.DATA1.get("name"));
                }
                if (activity.pcParametersSave.DATA2 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_slot:1\" "+activity.words.get("for select storage device under id:")+" " + activity.pcParametersSave.DATA2.get("name"));
                }
                if (activity.pcParametersSave.DATA3 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_slot:2\" "+activity.words.get("for select storage device under id:")+" " + activity.pcParametersSave.DATA3.get("name"));
                }
                if (activity.pcParametersSave.DATA4 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_slot:3\""+activity.words.get("for select storage device under id:")+" " + activity.pcParametersSave.DATA4.get("name"));
                }
                if (activity.pcParametersSave.DATA5 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_slot:4\""+activity.words.get("for select storage device under id:")+" " + activity.pcParametersSave.DATA5.get("name"));
                }
                if (activity.pcParametersSave.DATA6 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_slot:5\""+activity.words.get("for select storage device under id:")+" " + activity.pcParametersSave.DATA6.get("name"));
                }
            }
            else if(command.startsWith("storage_list_ids:")){
                cmd.success(activity.words.get("List of drives for selection:"));
                if (activity.pcParametersSave.DATA1 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_id:"+activity.pcParametersSave.DATA1.get("name")+"\" "+activity.words.get("for select storage device in slot:")+" 0");
                }
                if (activity.pcParametersSave.DATA2 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_id:"+activity.pcParametersSave.DATA2.get("name")+"\" "+activity.words.get("for select storage device in slot:")+" 1");
                }
                if (activity.pcParametersSave.DATA3 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_id:"+activity.pcParametersSave.DATA3.get("name")+"\" "+activity.words.get("for select storage device in slot:")+" 2" );
                }
                if (activity.pcParametersSave.DATA4 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_id:"+activity.pcParametersSave.DATA4.get("name")+"\" "+activity.words.get("for select storage device in slot:")+" 3");
                }
                if (activity.pcParametersSave.DATA5 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_id:"+activity.pcParametersSave.DATA5.get("name")+"\" "+activity.words.get("for select storage device in slot:")+" 4");
                }
                if (activity.pcParametersSave.DATA6 != null) {
                    cmd.output(activity.words.get("Enter:")+" \"ifd.prepare.select_storage_id:"+activity.pcParametersSave.DATA6.get("name")+"\" "+activity.words.get("for select storage device in slot:")+" 5");
                }
            }
            else if(command.startsWith("select_storage_slot:")) {
                storagePos = command.replace("select_storage_slot:", "");
                if(storage.get(storagePos)!=null) {
                    cmd.success(activity.words.get("Drive selected"));
                }else{
                    cmd.error(activity.words.get("Drive not found"));
                }
            }
            else if(command.startsWith("select_storage_id:")) {
                storagePos = command.replace("select_storage_id:", "");
                if(storage.get(storagePos)!=null) {
                    cmd.success(activity.words.get("Drive selected"));
                }else{
                    cmd.error(activity.words.get("Drive not found"));
                }
            }
            else cmd.error(activity.words.get("The command was entered incorrectly"));
        }
        else if(command.startsWith("install")){
            if(StringArrayWork.ArrayListToString(activity.apps).contains("OS,")&&disk.contains("OS")){
                cmd.warn(activity.words.get("An operating system is already installed on your pc"));
                cmd.output(activity.words.get("To reinstall the operating system, enter the following command:")+" \"ifd.reinstall\"");
                return;
            }
            if(disk == null){
                cmd.error(activity.words.get("No disk selected"));
                return;
            }
            if (storagePos == null){
                cmd.error(activity.words.get("No drive selected"));
                return;
            }

            final int[] progress = {0};
            if(activity.pcParametersSave.getEmptyStorageSpace(storage.get(storagePos))>= ProgramListAndData.programSize.get(disk)){
                cmd.success(activity.words.get("Enough space for installation"));
                progress[0] +=2;
            }
            else {
                cmd.error(activity.words.get("Not enough space for installation"));
                return;
            }
            Timer timer = new Timer();
            TimerTask unpacking = new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(() -> {
                        progress[0]++;
                        cmd.output(activity.words.get("Installation progress:")+progress[0]+"%");
                        cmd.adapter.notifyDataSetChanged();
                        if(progress[0] == 100){
                            storage.get(storagePos).put("Содержимое", storage.get(storagePos).get(("Содержимое")) + disk.replace("Installer","").trim()+",");
                            activity.pcParametersSave.setData(storage.get(storagePos).get("name"),storage.get(storagePos));
                            cmd.success(activity.words.get("Installation completed"));
                            setOsBaseStyle(activity,disk.replace("Installer",""));
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
        else if(command.startsWith("reinstall")){
            if(StringArrayWork.ArrayListToString(activity.apps).contains("OS,")&&disk.contains("OS")) {
                activity.styleSave.resetAllStyle();
                storage.get(storagePos).put("Содержимое",
                        storage.get(storagePos).get(("Содержимое")).
                                replace(NapiOS.TITLE+",", "").
                                replace(LiriOS.TITLE+",",""));
                activity.pcParametersSave.setData(storage.get(storagePos).get("name"), storage.get(storagePos));
            }
            if(disk == null){
                cmd.error(activity.words.get("No disk selected"));
                return;
            }
            if (storagePos == null){
                cmd.error(activity.words.get("No drive selected"));
                return;
            }

            final int[] progress = {0};
            if(activity.pcParametersSave.getEmptyStorageSpace(storage.get(storagePos))>= ProgramListAndData.programSize.get(disk)){
                cmd.success(activity.words.get("Enough space for installation"));
                progress[0] +=2;
            }
            else {
                cmd.error(activity.words.get("Not enough space for installation"));
                return;
            }
            Timer timer = new Timer();
            TimerTask unpacking = new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(() -> {
                        progress[0]++;
                        cmd.output(activity.words.get("Installation progress:")+progress[0]+"%");
                        cmd.adapter.notifyDataSetChanged();
                        if(progress[0] == 100){
                            storage.get(storagePos).put("Содержимое", storage.get(storagePos).get(("Содержимое")) + disk.replace("Installer","").trim()+",");
                            activity.pcParametersSave.setData(storage.get(storagePos).get("name"),storage.get(storagePos));
                            cmd.success(activity.words.get("Installation completed"));
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
        else cmd.error(activity.words.get("The command was entered incorrectly"));
    }
    private static void setOsBaseStyle(MainActivity activity,String osName){
        if(osName.equals(NapiOS.TITLE)){
            activity.styleSave.resetAllStyle();
        }else if(osName.equals(LiriOS.TITLE)){
            activity.styleSave.BackgroundResource = R.drawable.background1;
        }
    }
}
