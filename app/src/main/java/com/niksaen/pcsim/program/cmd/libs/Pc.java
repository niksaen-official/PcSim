package com.niksaen.pcsim.program.cmd.libs;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.program.cmd.CMD;

public class Pc {
    public static void start(MainActivity activity, String command){
        if(command.startsWith("off")){
            activity.pcWorkOff();
        }else if(command.startsWith("reload")){
            activity.reloadPc();
        }else if(command.startsWith("cpu")){
            CMD.outputCommand.add(CMD.SUCCESS + "CPU parameters:");
            CMD.outputCommand.add("Name: "+activity.pcParametersSave.Cpu);
            CMD.outputCommand.add("Streams: "+activity.pcParametersSave.CPU.get( "Кол-во потоков"));
            CMD.outputCommand.add("Cores: "+activity.pcParametersSave.CPU.get("Кол-во ядер"));
            CMD.outputCommand.add("Frequency: "+activity.pcParametersSave.CPU.get("Частота")+"MHz");
            CMD.outputCommand.add("Temperature: "+activity.pcParametersSave.currentCpuTemperature()+" C");
        }
        else if(command.startsWith("motherboard.")){
            if(command.contains("slots")) {
                CMD.outputCommand.add(CMD.SUCCESS + "Motherboard slot parameters:");
                CMD.outputCommand.add("Name: " + activity.pcParametersSave.Mobo);
                CMD.outputCommand.add("RAM slot count: " + activity.pcParametersSave.MOBO.get("Кол-во слотов"));
                CMD.outputCommand.add("Ports SATA count: " + activity.pcParametersSave.MOBO.get("Портов SATA"));
                CMD.outputCommand.add("PCI slot count: " + activity.pcParametersSave.MOBO.get("Слотов PCI"));
            }else if(command.contains("main")){
                CMD.outputCommand.add(CMD.SUCCESS + "Motherboard parameters:");
                CMD.outputCommand.add("Name: " + activity.pcParametersSave.Mobo);
                CMD.outputCommand.add("Socket: " + activity.pcParametersSave.MOBO.get("Сокет"));
                CMD.outputCommand.add("RAM type: " + activity.pcParametersSave.MOBO.get("Тип памяти"));
                CMD.outputCommand.add("Max. RAM value: " + activity.pcParametersSave.MOBO.get("Макс. объём") +"Gb");
                CMD.outputCommand.add("Min. RAM frequency: " + activity.pcParametersSave.MOBO.get("Мин. частота") + "MHz");
                CMD.outputCommand.add("Max. RAM frequency: " + activity.pcParametersSave.MOBO.get("Макс. частота") + "MHz");
            }else {
                CMD.outputCommand.add(CMD.ERROR + "Command not found");
            }
        }
        else {
            CMD.outputCommand.add(CMD.ERROR + "Command not found");
        }
    }
}
