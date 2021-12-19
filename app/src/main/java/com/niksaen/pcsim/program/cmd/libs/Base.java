package com.niksaen.pcsim.program.cmd.libs;

import com.niksaen.pcsim.classes.ErrorCodeList;
import com.niksaen.pcsim.program.cmd.CMD;

public class Base {
    public static void start(String command){
        if(command.startsWith("print:")||command.startsWith("write:")){
            CMD.outputCommand.add(command.substring(6).trim());
        }
        else if(command.startsWith("clear")){
            CMD.outputCommand.clear();
        }
        else CMD.outputCommand.add(CMD.ERROR +"0xCMD0002"+ ErrorCodeList.ErrorCodeText.get("0xCMD0002"));
    }
}
