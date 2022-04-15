package com.niksaen.pcsim.os.cmd.libs;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.os.cmd.CMD;

public class Drive{
    public static void start(MainActivity activity,CMD cmd,String command){
        command = command.replace("drive.","");
        command = command.replace("#","");
        if(command.startsWith("inDrive")){
            if(activity.DiskInDrive!= null) {
                cmd.output("Disk in the drive");
            }else {
                cmd.output("Disk not found");
            }
        }
        else if(command.startsWith("inject")){
            if(activity.DiskInDrive!= null) {
                activity.DiskInDrive = null;
                cmd.output("The disc has been removed from the drive");
            }else {
                cmd.error("Disk not found");
            }
        }
        else cmd.error("The command was entered incorrectly");
    }
}
