package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.os.cmd.CMD;

public class Customization {
    public static void start(CMD cmd, String command){
        command = command.replace("cstm.","");
        if(command.equals("reset")){
            cmd.activity.styleSave.resetAllStyle();
            cmd.success(cmd.activity.words.get("Operating system styles reset"));
        }else {
            cmd.error(cmd.activity.words.get("The command was entered incorrectly"));
        }
    }
}
