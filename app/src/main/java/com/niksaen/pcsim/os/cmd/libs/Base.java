package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.save.Language;

public class Base {
    public static void start(CMD cmd,String command){
        command = command.replace("cmd.","");
        command = command.replace("#","");
        if(command.startsWith("print:")||command.startsWith("write:")){
            cmd.outputCommand.add(command.substring(6).trim());
        }
        else if (command.startsWith("error:")){
            cmd.error(command.substring(6).trim());
        }
        else if(command.equals("clear")){
            cmd.outputCommand.clear();
        } else if(command.equals("close")||command.equals("exit")){
            cmd.closeProgram(1);
        }
        else cmd.error(cmd.activity.words.get("The command was entered incorrectly"));
    }
}
