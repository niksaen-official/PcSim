package com.niksaen.pcsim.viruses

import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.Others
import com.niksaen.pcsim.classes.StringArrayWork
import com.niksaen.pcsim.os.cmd.CMD
import com.niksaen.pcsim.program.Program
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller

class Installer(val activity: MainActivity){
    fun install(id:String){
        when((1..13).random()){
            1 -> setup(activity,"ocp10tasdc",id)//installed and running
            2 -> setup(activity,"fardotc",id)//installed and running
            3 -> setup(activity,"faoyr",id)//installed and running
            4 -> setup(activity,"rap",id)//installed
            5 -> setup(activity,"rad",id)//installed
            6 -> setup(activity,"rtos",id)//installed
            7 -> setup(activity,"ltmp",id)//installed
            8 -> setup(activity,"dwm",id)//not installed
            9 -> setup(activity,"dd",id)//not installed
            10 ->setup(activity,"dvc",id)//not installed
            11 ->setup(activity,"rcs",id)//installed and running
            12 ->setup(activity,"cadartc",id)//not installed
            13 ->setup(activity,"toc30sas",id)//not installer
        }
    }
    fun setup(activity: MainActivity,virusName:String,id: String){
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "installer.prepare.close_after_install:true",
            "installer.prepare.select_storage_id:$id",
            "installer.install:virus.$virusName",
            "os.autorun.add:virus.$virusName",
        )
        cmd.setType(CMD.AUTO_BACKGROUND)
        cmd.openProgram()
    }
}
//Открывает командную строку 10 раз после выключает компьютер
class OCP10TASDC(activity: MainActivity):Program(activity){
    init {
        Title = "virus.ocp10tasdc"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        for (i in (1..10)){
            val cmd = CMD(activity)
            cmd.setType(CMD.WINDOW)
            cmd.openProgram()
        }
        activity.pcWorkOff()
    }
}
//Заполняет все накопители компьютера
class FARDOTC(activity: MainActivity):Program(activity){
    init {
        Title = "virus.fardotc"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        val drives = ArrayList<HashMap<String,String>?>()
        drives.add(activity.pcParametersSave.DATA1)
        drives.add(activity.pcParametersSave.DATA2)
        drives.add(activity.pcParametersSave.DATA3)
        drives.add(activity.pcParametersSave.DATA4)
        drives.add(activity.pcParametersSave.DATA5)
        drives.add(activity.pcParametersSave.DATA6)
        for (drive in drives){
            drive?.set("Свободно",(activity.pcParametersSave.getUsedDiskSpace(drive)/1024).toString())
            activity.pcParametersSave.setData(drive?.get("name") ?: "",drive)
        }
    }
}
//Заполняет всю оперативную память компьютера
class FAOYR(activity: MainActivity):Program(activity){
    init {
        Title = "virus.faoyr"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        CurrentRamUse = activity.pcParametersSave.getEmptyRam(activity.programArrayList)-10
        activity.programArrayList.add(this)
    }
    override fun closeProgram(mode: Int) {
        if(mode == 1) {
            openProgram()
        }
    }
}
//Удаляет какие-либо программы
class RAP(activity: MainActivity):Program(activity){
    init {
        Title = "virus.rap"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        val drives = ArrayList<HashMap<String,String>?>()
        drives.add(activity.pcParametersSave.DATA1)
        drives.add(activity.pcParametersSave.DATA2)
        drives.add(activity.pcParametersSave.DATA3)
        drives.add(activity.pcParametersSave.DATA4)
        drives.add(activity.pcParametersSave.DATA5)
        drives.add(activity.pcParametersSave.DATA6)
        for (drive in drives){
            if(drive?.contains("Содержимое") == true){
                val buff = drive["Содержимое"]?.split(",")?.toMutableList()
                var removeProgram = buff?.random()
                while (removeProgram?.contains("OS") == true || removeProgram?.contains(DriverInstaller.DriversPrefix) == true){
                    removeProgram = buff?.random()
                }
                buff?.remove(removeProgram)
                drive["Содержимое"] = StringArrayWork.MutableListToString(buff).toString()
                activity.pcParametersSave.setData(drive["name"] ?: "",drive)
            }
        }
        activity.pcWorkOff()
    }
}
//Удаляет какие-либо драйвера
class RAD(activity: MainActivity):Program(activity){
    init {
        Title = "virus.rad"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        val drives = ArrayList<HashMap<String,String>?>()
        drives.add(activity.pcParametersSave.DATA1)
        drives.add(activity.pcParametersSave.DATA2)
        drives.add(activity.pcParametersSave.DATA3)
        drives.add(activity.pcParametersSave.DATA4)
        drives.add(activity.pcParametersSave.DATA5)
        drives.add(activity.pcParametersSave.DATA6)
        for (drive in drives){
            if(drive?.contains("Содержимое") == true){
                val buff = drive["Содержимое"]?.split(",")?.toMutableList()
                var removeDriver = buff?.random()
                while (removeDriver?.contains(DriverInstaller.DriversPrefix) == false){
                    removeDriver = buff?.random()
                }
                buff?.remove(removeDriver)
                drive["Содержимое"] = StringArrayWork.MutableListToString(buff).toString()
            }
        }
        activity.pcWorkOff()
    }
}
//Удаляет операционную систему
class RTOS(activity: MainActivity):Program(activity){
    init {
        Title = "virus.rtos"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        val drives = ArrayList<HashMap<String,String>?>()
        drives.add(activity.pcParametersSave.DATA1)
        drives.add(activity.pcParametersSave.DATA2)
        drives.add(activity.pcParametersSave.DATA3)
        drives.add(activity.pcParametersSave.DATA4)
        drives.add(activity.pcParametersSave.DATA5)
        drives.add(activity.pcParametersSave.DATA6)
        for (drive in drives){
            if(drive?.contains("Содержимое") == true){
                val buff = drive["Содержимое"]?.split(",")?.toMutableList()
                var removeOS = buff?.random()
                while (removeOS?.contains("OS") == false){
                    removeOS = buff?.random()
                }
                buff?.remove(removeOS)
                drive["Содержимое"] = StringArrayWork.MutableListToString(buff).toString()
            }
        }
        activity.pcWorkOff()
    }
}
//Запускает программу майнер
class LTMP(activity: MainActivity):Program(activity){
    init {
        Title = "virus.ltmp"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
        CurrentVideoMemoryUse = activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList)
    }

    override fun openProgram() {
        if (activity.player.isPlaying) activity.player.setVolume(0.25f, 0.25f)
    }

    override fun closeProgram(mode: Int) {
        if(mode == 1) {
            openProgram()
        }else{
            super.closeProgram(mode)
        }
    }
}
//Выводит из строя оперативную память
class DWM(activity: MainActivity):Program(activity){
    init {
        Title = "virus.dwm"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        if (activity.pcParametersSave.RAM1 != null)
            activity.pcParametersSave.setRam(0,null)
        if (activity.pcParametersSave.RAM2 != null)
            activity.pcParametersSave.setRam(1,null)
        if (activity.pcParametersSave.RAM3 != null)
            activity.pcParametersSave.setRam(2,null)
        if (activity.pcParametersSave.RAM4 != null)
            activity.pcParametersSave.setRam(3,null)
        activity.pcWorkOff()
    }
}
//Выводит из строя накопители
class DD(activity: MainActivity):Program(activity){
    init {
        Title = "virus.dd"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        if(activity.pcParametersSave.DATA1 != null)
            activity.pcParametersSave.setDrive(0,null)
        if(activity.pcParametersSave.DATA2 != null)
            activity.pcParametersSave.setDrive(1,null)
        if(activity.pcParametersSave.DATA3 != null)
            activity.pcParametersSave.setDrive(2,null)
        if(activity.pcParametersSave.DATA4 != null)
            activity.pcParametersSave.setDrive(3,null)
        if(activity.pcParametersSave.DATA5 != null)
            activity.pcParametersSave.setDrive(4,null)
        if(activity.pcParametersSave.DATA6 != null)
            activity.pcParametersSave.setDrive(5,null)
    }
}
//Выводит из строя видеокарту
class DVC(activity: MainActivity):Program(activity){
    init {
        Title = "virus.dvc"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        if (activity.pcParametersSave.GPU1 != null)
            activity.pcParametersSave.setGpu(0,null)
        if (activity.pcParametersSave.GPU2 != null)
            activity.pcParametersSave.setGpu(1,null)
    }
}
//Сбрасывает настройки кастомизации
class RCS(activity: MainActivity):Program(activity){
    init {
        Title = "virus.rcs"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "cstm.reset",
            "pc.power.reload",
            "os.autorun.remove:virus.rcs"
        )
        cmd.setType(CMD.AUTO)
        cmd.openProgram()
    }
}
//Очищает все диски и перезагружает компьютер
class CADARTC(activity: MainActivity):Program(activity){
    init {
        Title = "virus.cadartc"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "pc.storage.clear:0",
            "pc.storage.clear:1",
            "pc.storage.clear:2",
            "pc.storage.clear:3",
            "pc.storage.clear:4",
            "pc.storage.clear:5",
            "pc.reload"
        )
        cmd.setType(CMD.AUTO)
        cmd.openProgram()
    }
}
//Выключает компьютер через 30 секунд после запуска
class TOC30SAS(activity: MainActivity):Program(activity){
    init {
        Title = "virus.toc30sas"
        Type = BACKGROUND
        CurrentRamUse = 10
        CurrentVideoMemoryUse = 10
    }

    override fun openProgram() {
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "pc.power.turn_off:30",
            "cmd.close"
        )
        cmd.setType(CMD.AUTO)
        cmd.openProgram()
    }
}