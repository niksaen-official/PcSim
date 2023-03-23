package com.niksaen.pcsim.os.cmd.libs;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.ProgramListAndData;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.save.Settings;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Installer {
    static String storagePos;
    public static void start(MainActivity activity, CMD cmd, String command){
        command = command.replace("installer.","");
        HashMap<String, HashMap<String,String>> storage = new HashMap<>();
        if(activity.pcParametersSave.DATA1 != null){
            storage.put("0",activity.pcParametersSave.DATA1);
        }
        if(activity.pcParametersSave.DATA2 != null){
            storage.put("1",activity.pcParametersSave.DATA2);
        }
        if(activity.pcParametersSave.DATA3 != null){
            storage.put("2",activity.pcParametersSave.DATA3);
        }
        if(activity.pcParametersSave.DATA4 != null){
            storage.put("3",activity.pcParametersSave.DATA4);
        }
        if(activity.pcParametersSave.DATA5 != null){
            storage.put("4",activity.pcParametersSave.DATA5);
        }
        if(activity.pcParametersSave.DATA6 != null){
            storage.put("5",activity.pcParametersSave.DATA6);
        }
        if(command.startsWith("install:")){
            cmd.output("Preparing to install ...");
            String programId = command.replace("install:","").trim();
            if(programId.startsWith("gstore.")){
                cmd.error(activity.words.get("Download package not found"));
                return;
            }
            String program = getProgramsId(activity).get(programId);
            if(!StringArrayWork.ArrayListToString(activity.apps).contains(program)) {
                final int[] progress = {0, 0};
                if (activity.pcParametersSave.getEmptyStorageSpace(storage.get(storagePos)) >= ProgramListAndData.programSize.get(program)) {
                    cmd.success(activity.words.get("Enough space for installation"));
                    progress[0] += 2;
                } else {
                    cmd.error(activity.words.get("Not enough space for installation"));
                    return;
                }
                Timer timer = new Timer();
                Timer timer1 = new Timer();
                TimerTask unpacking = new TimerTask() {
                    @Override
                    public void run() {
                        activity.runOnUiThread(() -> {
                            progress[0]++;
                            cmd.output(activity.words.get("Unpacked") + ":" + progress[0] + "%");
                            cmd.adapter.notifyDataSetChanged();
                            if (progress[0] == 100) {
                                cmd.output(activity.words.get("Archives unpacked"));
                                storage.get(storagePos).put("Содержимое", storage.get(storagePos).get(("Содержимое")) + program + ",");
                                activity.pcParametersSave.setData(storage.get(storagePos).get("name"), storage.get(storagePos));
                                cmd.success(activity.words.get("Installation completed"));
                                timer1.cancel();
                            }
                        });
                    }
                };
                TimerTask download = new TimerTask() {
                    @Override
                    public void run() {
                        activity.runOnUiThread(() -> {
                            progress[1]++;
                            cmd.output(activity.words.get("Uploaded") + ":" + progress[1] + "%");
                            if (progress[1] == 100) {
                                cmd.output(activity.words.get("Archives loaded"));
                                timer.cancel();
                                if (storage.get(storagePos).get("Тип").equals("HDD")) {
                                    timer1.scheduleAtFixedRate(unpacking, 0, (long) ((ProgramListAndData.programSize.get(program) * 700)));
                                } else {
                                    timer1.scheduleAtFixedRate(unpacking, 0, (long) ((ProgramListAndData.programSize.get(program) * 490)));
                                }
                            }
                        });
                    }
                };

                if (storage.get(storagePos).get("Тип").equals("HDD")) {
                    timer.scheduleAtFixedRate(download, 0, (long) ((ProgramListAndData.programSize.get(program) * 300)));
                } else {
                    timer.scheduleAtFixedRate(download, 0, (long) ((ProgramListAndData.programSize.get(program) * 210)));
                }
            }else{
                cmd.error(activity.words.get("The program is already installed on your PC"));
            }
        }
        if(command.startsWith("prepare.")) {
            command = command.replace("prepare.", "");
            if (command.startsWith("storage_list_slots:")) {
                cmd.success(activity.words.get("List of drives for selection:"));
                if (activity.pcParametersSave.DATA1 != null) {
                    cmd.output(activity.words.get("Enter:") + " \"ifd.prepare.select_storage_slot:0\" " + activity.words.get("for select storage device under id:") + " " + activity.pcParametersSave.DATA1.get("name"));
                }
                if (activity.pcParametersSave.DATA2 != null) {
                    cmd.output(activity.words.get("Enter:") + " \"ifd.prepare.select_storage_slot:1\" " + activity.words.get("for select storage device under id:") + " " + activity.pcParametersSave.DATA2.get("name"));
                }
                if (activity.pcParametersSave.DATA3 != null) {
                    cmd.output(activity.words.get("Enter:") + " \"ifd.prepare.select_storage_slot:2\" " + activity.words.get("for select storage device under id:") + " " + activity.pcParametersSave.DATA3.get("name"));
                }
                if (activity.pcParametersSave.DATA4 != null) {
                    cmd.output(activity.words.get("Enter:") + " \"ifd.prepare.select_storage_slot:3\"" + activity.words.get("for select storage device under id:") + " " + activity.pcParametersSave.DATA4.get("name"));
                }
                if (activity.pcParametersSave.DATA5 != null) {
                    cmd.output(activity.words.get("Enter:") + " \"ifd.prepare.select_storage_slot:4\"" + activity.words.get("for select storage device under id:") + " " + activity.pcParametersSave.DATA5.get("name"));
                }
                if (activity.pcParametersSave.DATA6 != null) {
                    cmd.output(activity.words.get("Enter:") + " \"ifd.prepare.select_storage_slot:5\"" + activity.words.get("for select storage device under id:") + " " + activity.pcParametersSave.DATA6.get("name"));
                }
            } else if (command.startsWith("select_storage_slot:")) {
                storagePos = command.replace("select_storage_slot:", "");
                if (storage.get(storagePos) != null) {
                    cmd.success(activity.words.get("Drive selected"));
                } else {
                    cmd.error(activity.words.get("Drive not found"));
                }
            } else cmd.error(activity.words.get("The command was entered incorrectly"));
        }
    }
    public static HashMap<String,String> getProgramsId(Context context){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        return new Gson().fromJson(new AssetFile(context).getText("program/program_ids.json"),typeToken.getType());
    }
}
