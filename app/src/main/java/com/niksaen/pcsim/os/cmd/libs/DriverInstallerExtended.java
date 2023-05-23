package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.os.cmd.CMD;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class DriverInstallerExtended {
    static boolean extended = false;
    static int currentStoragePos = 0;
    public static void start(MainActivity activity,CMD cmd, String command){
        command = command.replace("driver.","");
        command = command.replace("#","");
        HashMap<String, String>[] StorageDeviceList = new HashMap[]{
                activity.pcParametersSave.DATA1,
                activity.pcParametersSave.DATA2,
                activity.pcParametersSave.DATA3,
                activity.pcParametersSave.DATA4,
                activity.pcParametersSave.DATA5,
                activity.pcParametersSave.DATA6
        };
        String[] ramList = {
                activity.pcParametersSave.Ram1,
                activity.pcParametersSave.Ram2,
                activity.pcParametersSave.Ram3,
                activity.pcParametersSave.Ram4,
        };
        String[] dataList = {
                activity.pcParametersSave.Data1,
                activity.pcParametersSave.Data2,
                activity.pcParametersSave.Data3,
                activity.pcParametersSave.Data4,
                activity.pcParametersSave.Data5,
                activity.pcParametersSave.Data6,
        };
        String[] gpuList = {
                activity.pcParametersSave.Gpu1,
                activity.pcParametersSave.Gpu2
        };
        if(command.startsWith("install.")){
            command = command.replace("install.","");

            int finalStoragePos;
            if(cmd.storageSlot >-1){
                finalStoragePos = cmd.storageSlot;
            }else {
                finalStoragePos = currentStoragePos;
            }
            Timer timer = new Timer();
            TimerTask task;
            Runnable action;
            int delay = 1000;
            switch (command) {
                case "all": {
                    delay = 4000;
                    cmd.warn(activity.words.get("Driver installation started"));
                    Runnable action1 = ()-> {
                        start(activity, cmd,"driver.install.for_motherboard");
                        start(activity, cmd,"driver.install.for_cpu");
                        start(activity, cmd,"driver.install.for_ram");
                        start(activity, cmd,"driver.install.for_gpu");
                        start(activity, cmd,"driver.install.for_storage");
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                activity.runOnUiThread(()->{
                                    cmd.success(activity.words.get("Driver installation completed"));
                                    timer.cancel();
                                });
                            }
                        }, 10000);
                    };
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(action1);
                        }
                    };
                    timer.schedule(task, delay);
                    break;
                }
                case "for_motherboard": {
                    cmd.warn(activity.words.get("Installation of drivers for the motherboard has started"));
                    String driver = DriverInstaller.DriverForMotherboard + activity.pcParametersSave.Mobo;
                    if(extended){
                        driver+="\n"+DriverInstaller.EXTENDED_TYPE;
                    }else {
                        driver +="\n"+ DriverInstaller.BASE_TYPE;
                    }
                    StorageDeviceList[finalStoragePos].put("Содержимое", StorageDeviceList[finalStoragePos].get("Содержимое") + driver + ",");
                    delay = 1000;
                    action = () -> {
                        cmd.success(activity.words.get("Installation of drivers for the motherboard is complete"));
                        activity.pcParametersSave.setData(StorageDeviceList[finalStoragePos].get("name"), StorageDeviceList[finalStoragePos]);
                    };
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(action);
                        }
                    };
                    timer.schedule(task, delay);
                    break;
                }
                case "for_cpu": {
                    cmd.warn(activity.words.get("Installation of drivers for the processor has started"));
                    String driver = DriverInstaller.DriverForCPU + activity.pcParametersSave.Cpu;
                    if(extended){
                        driver+="\n"+DriverInstaller.EXTENDED_TYPE;
                    }else {
                        driver +="\n"+DriverInstaller.BASE_TYPE;
                    }
                    StorageDeviceList[finalStoragePos].put("Содержимое", StorageDeviceList[finalStoragePos].get("Содержимое") + driver + ",");
                    delay = 1000;
                    action = () -> {
                        cmd.success(activity.words.get("The driver installation for the processor is complete"));
                        activity.pcParametersSave.setData(StorageDeviceList[finalStoragePos].get("name"), StorageDeviceList[finalStoragePos]);
                    };
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(action);
                        }
                    };
                    timer.schedule(task, delay);
                    break;
                }
                case "for_gpu": {
                    cmd.warn(activity.words.get("The installation of drivers for the video card has started"));
                    for(int i=0;i<gpuList.length;i++){
                        if (gpuList[i] != null) {
                            delay += 1000;
                            String driver = DriverInstaller.DriverForGPU + gpuList[i];
                            if(extended){
                                driver+="\n"+DriverInstaller.EXTENDED_TYPE;
                            }else {
                                driver +="\n"+DriverInstaller.BASE_TYPE;
                            }
                            if (!StorageDeviceList[finalStoragePos].get("Содержимое").contains(driver + ",")) {
                                StorageDeviceList[finalStoragePos].put("Содержимое", StorageDeviceList[finalStoragePos].get("Содержимое") + driver + ",");
                            } else {
                                cmd.output(activity.words.get("Drivers for graphics card [slot n] are already installed").replace("n",String.valueOf(i+1)));
                            }
                        }
                    }
                    action = () -> {
                        cmd.success(activity.words.get("The installation of drivers for the video card is complete"));
                        activity.pcParametersSave.setData(StorageDeviceList[finalStoragePos].get("name"), StorageDeviceList[finalStoragePos]);
                    };
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(action);
                        }
                    };
                    timer.schedule(task, delay);
                    break;
                }
                case "for_ram": {
                    cmd.warn(activity.words.get("Installation of drivers for RAM started"));
                    for (int i = 0;i<ramList.length;i++) {
                        if (ramList[i] != null) {
                            delay += 100;
                            if(activity.pcParametersSave.ramValid(activity.pcParametersSave.getRam(i))) {
                                String driver = DriverInstaller.DriverForRAM + ramList[i];
                                if (extended) {
                                    driver += "\n" + DriverInstaller.EXTENDED_TYPE;
                                } else {
                                    driver += "\n" + DriverInstaller.BASE_TYPE;
                                }
                                if (!StorageDeviceList[finalStoragePos].get("Содержимое").contains(driver + ",")) {
                                    StorageDeviceList[finalStoragePos].put("Содержимое", StorageDeviceList[finalStoragePos].get("Содержимое") + driver + ",");
                                } else {
                                    cmd.output(activity.words.get("Drivers for RAM [slot n] are already installed").replace("n", String.valueOf(i + 1)));
                                }
                            }
                        }
                    }
                    action = () -> {
                        cmd.success(activity.words.get("Installation of drivers for RAM is complete"));
                        activity.pcParametersSave.setData(StorageDeviceList[finalStoragePos].get("name"), StorageDeviceList[finalStoragePos]);
                    };
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(action);
                        }
                    };
                    timer.schedule(task, delay);
                    break;
                }
                case "for_storage": {
                    cmd.warn(activity.words.get("Installation of drivers for drives started"));
                    delay += 1000;
                    for (int i = 0;i<dataList.length;i++){
                        if(dataList[i] != null) {
                            if (!StorageDeviceList[finalStoragePos].get("Содержимое").contains(DriverInstaller.DriverForStorageDevices + dataList[i] + ",")) {
                                StorageDeviceList[finalStoragePos].put("Содержимое", StorageDeviceList[finalStoragePos].get("Содержимое") + DriverInstaller.DriverForStorageDevices + dataList[i] + ",");
                            } else {
                                cmd.warn(activity.words.get("Drivers for drive [slot n] are already installed").replace("n", String.valueOf(i + 1)));
                            }
                        }
                    }
                    action = () -> {
                        cmd.success(activity.words.get("Installation of drivers for drives is complete"));
                        activity.pcParametersSave.setData(StorageDeviceList[finalStoragePos].get("name"), StorageDeviceList[finalStoragePos]);
                    };
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            activity.runOnUiThread(action);
                        }
                    };
                    timer.schedule(task, delay);
                    break;
                }
                default:
                    cmd.error(activity.words.get("The command was entered incorrectly"));
                    break;
            }
        }
        else if(command.startsWith("prepare.")){
            command = command.replace("prepare.","");
            if(command.startsWith("select_storage_id:")) {
                for (int i = 0; i < StorageDeviceList.length; i++) {
                    HashMap<String, String> buff = StorageDeviceList[i];
                    if (buff != null) {
                        if (command.equals(buff.get("name"))) {
                            currentStoragePos = i;
                            cmd.success(activity.words.get("Drive selected"));
                            break;
                        } else {
                            cmd.error(activity.words.get("Drive not found"));
                        }
                    }
                }
            }
            else if(command.startsWith("select_storage_slot:")) {
                int slot = Integer.parseInt(command.replace("select_storage_slot:",""));
                HashMap<String, String> buff = StorageDeviceList[slot];
                if (buff != null) {
                    currentStoragePos = slot;
                    cmd.success(activity.words.get("Drive selected"));
                } else {
                    cmd.error(activity.words.get("Drive not found"));
                }
            }
            else if(command.startsWith("extended:")){
                command = command.replace("extended:","");
                switch (command) {
                    case "true":
                        extended = true;
                        cmd.success(activity.words.get("The type of drivers for installation is set: Extended"));
                        break;
                    case "false":
                        extended = false;
                        cmd.success(activity.words.get("The type of driver for installation is set: Base"));
                        break;
                    case "":
                        if (extended) {
                            cmd.output(activity.words.get("Current driver type: Extended"));
                        } else {
                            cmd.output(activity.words.get("Current driver type: Base"));
                        }
                        break;
                    default:
                        cmd.error(activity.words.get("Invalid argument entered"));
                        break;
                }
            }
            else cmd.error(activity.words.get("The command was entered incorrectly"));
        }
    }
}
