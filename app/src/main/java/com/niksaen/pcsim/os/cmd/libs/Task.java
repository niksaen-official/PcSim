package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.program.Program;

public class Task {
    public static void start(String command, CMD cmd){
        command = command.replace("task.","");
        command = command.replace("#","");
        if(command.equals("list")){
            for(Program program:cmd.activity.programArrayList){
                cmd.output("Program: "+cmd.activity.words.get(program.Title)+"\nRAM use: "+program.CurrentRamUse+"Mb"+"\nVRAM use: "+program.CurrentVideoMemoryUse+"Mb");
            }
        }else if(command.startsWith("close:")){
            int pos = Integer.parseInt(command.replace("close:",""));
            cmd.activity.programArrayList.get(pos).closeProgram(1);
            cmd.activity.taskManager.update();
        }
    }
}
