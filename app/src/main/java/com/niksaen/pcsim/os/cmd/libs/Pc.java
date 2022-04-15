package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;

import java.util.HashMap;

public class Pc {
    public static void start(MainActivity activity,CMD cmd, String command) {
        command = command.replace("#","");
        command = command.replace("pc.","");
        String[] StorageDeviceModelList = {
                activity.pcParametersSave.Data1,
                activity.pcParametersSave.Data2,
                activity.pcParametersSave.Data3,
                activity.pcParametersSave.Data4,
                activity.pcParametersSave.Data5,
                activity.pcParametersSave.Data6,
        };
        HashMap<String, String>[] StorageDeviceList = new HashMap[]{
                activity.pcParametersSave.DATA1,
                activity.pcParametersSave.DATA2,
                activity.pcParametersSave.DATA3,
                activity.pcParametersSave.DATA4,
                activity.pcParametersSave.DATA5,
                activity.pcParametersSave.DATA6
        };
        if (command.startsWith("off")) {
            activity.pcWorkOff();
        }
        else if (command.startsWith("reload")) {
            activity.reloadPc();
        }
        else if(command.startsWith("parameters.")) {
            command = command.replace("parameters.","");
            if (command.equals("cpu")) {
                String buff = " " + activity.words.get("Model") + ": " + activity.pcParametersSave.Cpu +
                        "\n " + activity.words.get("Number of cores") + ": " + activity.pcParametersSave.CPU.get("Кол-во ядер") +
                        "\n " + activity.words.get("Number of threads") + ": " + activity.pcParametersSave.CPU.get("Кол-во потоков") +
                        "\n " + activity.words.get("Cache") + ": " + activity.pcParametersSave.CPU.get("Кэш") + "Mb" +
                        "\n " + activity.words.get("Frequency") + ": " + activity.pcParametersSave.CPU.get("Частота") + "MHz" +
                        "\n " + activity.words.get("Overclocking capability") + ": " + activity.pcParametersSave.CPU.get("Возможность разгона") +
                        "\n" + activity.words.get("RAM characteristics") + ": " +
                        "\n    " + activity.words.get("Memory type") + ": " + activity.pcParametersSave.CPU.get("Тип памяти") +
                        "\n    " + activity.words.get("Maximum volume") + ": " + activity.pcParametersSave.CPU.get("Макс. объём") + "Gb" +
                        "\n    " + activity.words.get("Number of channels") + ": " + activity.pcParametersSave.CPU.get("Кол-во каналов") +
                        "\n    " + activity.words.get("Minimum frequency") + ": " + activity.pcParametersSave.CPU.get("Мин. частота") + "MHz" +
                        "\n    " + activity.words.get("Maximum frequency") + ": " + activity.pcParametersSave.CPU.get("Макс. частота") + "MHz" +
                        "\n" + activity.words.get("Integrated graphics core") + ": " + activity.pcParametersSave.CPU.get("Графическое ядро");

                cmd.success(activity.words.get("Processor specifications") + ": ");
                if (activity.pcParametersSave.CPU.get("Графическое ядро").equals("+")) {
                    cmd.output(buff +
                            "\n    " + activity.words.get("Model") + ": " + activity.pcParametersSave.CPU.get("Модель") +
                            "\n    " + activity.words.get("Frequency") + ": " + activity.pcParametersSave.CPU.get("Частота Graphics card") + "MHz" +
                            "\nTemperature: " + activity.pcParametersSave.currentCpuTemperature() + " C");
                } else {
                    cmd.output(buff +
                            "\nTemperature: " + activity.pcParametersSave.currentCpuTemperature() + " C");
                }
            }
            else if (command.equals("motherboard")) {
                cmd.success(activity.words.get("Motherboard specifications") + ": ");
                cmd.output(" " + activity.words.get("Model") + ": " + activity.pcParametersSave.Mobo +
                        "\n " + activity.words.get("Ports") + " SATA: " + activity.pcParametersSave.MOBO.get("Портов SATA") +
                        "\n " + activity.words.get("Slots") + " PCI: " + activity.pcParametersSave.MOBO.get("Слотов PCI") +
                        "\n" + activity.words.get("RAM characteristics") + ": " +
                        "\n    " + activity.words.get("Memory type") + ": " + activity.pcParametersSave.MOBO.get("Тип памяти") +
                        "\n    " + activity.words.get("Number of channels") + ": " + activity.pcParametersSave.MOBO.get("Кол-во каналов") +
                        "\n    " + activity.words.get("Minimum frequency") + ": " + activity.pcParametersSave.MOBO.get("Мин. частота") + "MHz" +
                        "\n    " + activity.words.get("Maximum frequency") + ": " + activity.pcParametersSave.MOBO.get("Макс. частота") + "MHz" +
                        "\n    " + activity.words.get("Maximum volume") + ": " + activity.pcParametersSave.MOBO.get("Макс. объём") + "Gb");
            }
            else if (command.equals("storage")) {
                for (int i = 0; i < StorageDeviceList.length; i++) {
                    HashMap<String, String> buff = StorageDeviceList[i];
                    if (buff != null) {
                        cmd.success(activity.words.get("Drive characteristics") + " (" + activity.words.get("Slot") + " " + i + "):");
                        cmd.output("\n " + activity.words.get("Model") + ": " + StorageDeviceModelList[i] +
                                "\n " + activity.words.get("Volume") + ": " + buff.get("Объём") + "Gb" +
                                "\n " + activity.words.get("Type") + ": " + buff.get("Тип") +
                                "\n" + activity.words.get("Available") + ": " + activity.pcParametersSave.getEmptyStorageSpace(buff) + "Mb || " + activity.pcParametersSave.getEmptyStorageSpace(buff) / 1024 + "Gb\n" +
                                "\n" + activity.words.get("Total") + ": " + Integer.parseInt(buff.get("Объём")) * 1024 + "Mb || " + buff.get("Объём") + "Gb");
                    }
                }
            }
            else if (command.startsWith("storage.id:")) {
                command = command.replace("storage.id:", "");
                for (int i = 0; i < StorageDeviceList.length; i++) {
                    HashMap<String, String> buff = StorageDeviceList[i];
                    if (buff != null) {
                        if (command.equals(buff.get("name"))) {
                            cmd.success(activity.words.get("Drive characteristics") + " (" + activity.words.get("ID") + " " + command + "):");
                            cmd.output(activity.words.get("Slot") + " " + i +
                                    "\n " + activity.words.get("Model") + ": " + StorageDeviceModelList[i] +
                                    "\n " + activity.words.get("Volume") + ": " + buff.get("Объём") + "Gb" +
                                    "\n " + activity.words.get("Type") + ": " + buff.get("Тип") +
                                    "\n" + activity.words.get("Available") + ": " + activity.pcParametersSave.getEmptyStorageSpace(buff) + "Mb || " + activity.pcParametersSave.getEmptyStorageSpace(buff) / 1024 + "Gb\n" +
                                    "\n" + activity.words.get("Total") + ": " + Integer.parseInt(buff.get("Объём")) * 1024 + "Mb || " + buff.get("Объём") + "Gb");
                            break;
                        } else cmd.error(activity.words.get("Drive not found"));
                    }
                }
            }
            else if (command.startsWith("storage.slot:")) {
                command = command.replace("storage.slot:", "");
                int slot = Integer.parseInt(command);
                HashMap<String, String> buff = StorageDeviceList[slot];
                if (buff != null) {
                    cmd.success(activity.words.get("Drive characteristics") + " (" + activity.words.get("Slot") + " " + slot + "):");
                    cmd.output(activity.words.get("ID") + " " + buff.get("name") +
                            "\n " + activity.words.get("Model") + ": " + StorageDeviceModelList[slot] +
                            "\n " + activity.words.get("Volume") + ": " + buff.get("Объём") + "Gb" +
                            "\n " + activity.words.get("Type") + ": " + buff.get("Тип") +
                            "\n" + activity.words.get("Available") + ": " + activity.pcParametersSave.getEmptyStorageSpace(buff) + "Mb || " + activity.pcParametersSave.getEmptyStorageSpace(buff) / 1024 + "Gb\n" +
                            "\n" + activity.words.get("Total") + ": " + Integer.parseInt(buff.get("Объём")) * 1024 + "Mb || " + buff.get("Объём") + "Gb");
                } else cmd.error(activity.words.get("Drive not found"));
            }
        }
        else if(command.startsWith("storage.")) {
            command = command.replace("storage.","");
            if (command.equals("list")) {
                cmd.success(activity.words.get("List of drives:"));
                for (int i = 0; i < StorageDeviceModelList.length; i++) {
                    if (StorageDeviceList[i] != null) {
                        String model = StorageDeviceModelList[i];
                        cmd.output("[" + activity.words.get("Slot") + ": " + i + "]\n" +
                                activity.words.get("Model") + ": " + model + "\n"
                                + activity.words.get("ID") + ": " + StorageDeviceList[i].get("name"));
                    }
                }
            }
            else if (command.startsWith("content:")) {
                command = command.replace("content:", "");
                int slot = Integer.parseInt(command);
                HashMap<String, String> buff = StorageDeviceList[slot];
                if (buff != null) {
                    cmd.success(activity.words.get("Storage device content:"));
                    for (String str : buff.get("Содержимое").split(",")) {
                        if (str.startsWith(DriverInstaller.DriversPrefix)) {
                            str = str.replace(DriverInstaller.DriverForCPU, activity.words.get(DriverInstaller.DriverForCPU));
                            str = str.replace(DriverInstaller.DriverForGPU, activity.words.get(DriverInstaller.DriverForGPU));
                            str = str.replace(DriverInstaller.DriverForMotherboard, activity.words.get(DriverInstaller.DriverForMotherboard));
                            str = str.replace(DriverInstaller.DriverForStorageDevices, activity.words.get(DriverInstaller.DriverForStorageDevices));
                            str = str.replace(DriverInstaller.DriverForRAM, activity.words.get(DriverInstaller.DriverForRAM));
                            str = str.replace(DriverInstaller.BASE_TYPE, activity.words.get(DriverInstaller.BASE_TYPE));
                            str = str.replace(DriverInstaller.EXTENDED_TYPE, activity.words.get(DriverInstaller.EXTENDED_TYPE));
                            cmd.output(str);
                        } else {
                            cmd.output(activity.words.get(str));
                        }
                    }
                } else {
                    cmd.error(activity.words.get("Drive not found"));
                }
            }
            else if (command.startsWith("clear:")) {
                int finalStoragePos = Integer.parseInt(command.replace("clear:", ""));
                StorageDeviceList[finalStoragePos].put("Содержимое", "");
                activity.pcParametersSave.setData(StorageDeviceList[finalStoragePos].get("name"), StorageDeviceList[finalStoragePos]);
                if (StorageDeviceList[finalStoragePos].get("MainDisk") == "true") {
                    activity.styleSave.resetAllStyle();
                }
                cmd.success(activity.words.get("Drive cleaned"));
            }
        }
        else if(command.startsWith("resource.")){
            command = command.replace("resource.","");
            if(command.equals("get_ram")){
                cmd.success(activity.words.get("RAM"));
                cmd.output(activity.words.get("Total")+": "+activity.pcParametersSave.getAllRam()+"Mb");
                cmd.output(activity.words.get("Used")+": "+ (activity.pcParametersSave.getAllRam() - activity.pcParametersSave.getEmptyRam(activity.programArrayList))+"Mb");
                cmd.output(activity.words.get("Free")+": "+activity.pcParametersSave.getEmptyRam(activity.programArrayList)+"Mb");
            }
            else if (command.equals("get_video_memory")){
                cmd.success(activity.words.get("Video memory"));
                cmd.output(activity.words.get("Total")+": "+activity.pcParametersSave.getAllVideoMemory()+"Mb");
                cmd.output(activity.words.get("Used")+": "+ (activity.pcParametersSave.getAllVideoMemory() - activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList))+"Mb");
                cmd.output(activity.words.get("Free")+": "+activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList)+"Mb");
            }
        }
        else {
            cmd.error(activity.words.get("The command was entered incorrectly"));
        }
    }
}
