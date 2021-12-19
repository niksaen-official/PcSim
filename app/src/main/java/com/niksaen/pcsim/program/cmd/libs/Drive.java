package com.niksaen.pcsim.program.cmd.libs;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.classes.ErrorCodeList;
import com.niksaen.pcsim.program.cmd.CMD;

public class Drive{
    public static void start(MainActivity activity,String command){
        if(command.startsWith("inDrive")){
            if(activity.DiskInDrive!= null) {
                CMD.outputCommand.add(CMD.SUCCESS + "Disk in drive");
            }else {
                CMD.outputCommand.add(CMD.SUCCESS + "Disk not found!");
            }
        }
        else if(command.startsWith("inject")){
            if(activity.DiskInDrive!= null) {
                activity.DiskInDrive = null;
                CMD.outputCommand.add(CMD.SUCCESS + "Disk injected");
            }else {
                CMD.outputCommand.add(CMD.ERROR +"0xDD0001"+ ErrorCodeList.ErrorCodeText.get("0xDD0001"));
            }
        }
        else{
            CMD.outputCommand.add(CMD.ERROR +"0xCMD0002"+ ErrorCodeList.ErrorCodeText.get("0xCMD0002"));
        }
    }
}
