package com.niksaen.pcsim.viruses

import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.Others
import com.niksaen.pcsim.classes.StringArrayWork
import com.niksaen.pcsim.os.cmd.CMD
import com.niksaen.pcsim.program.Program
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller

//Открывает командную строку 10 раз после выключает компьютер
class OCP10TASDC(activity: MainActivity):Program(activity){
    init {
        Type = BACKGROUND
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
        Type = BACKGROUND
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
        Type = BACKGROUND
    }

    override fun openProgram() {
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "installer.prepare.select_storage_slot:0",
            "installer.install:virus.faoyr",
            "installer.prepare.select_storage_slot:1",
            "installer.install:virus.faoyr",
            "installer.prepare.select_storage_slot:2",
            "installer.install:virus.faoyr",
            "installer.prepare.select_storage_slot:3",
            "installer.install:virus.faoyr",
            "installer.prepare.select_storage_slot:4",
            "installer.install:virus.faoyr",
            "installer.prepare.select_storage_slot:5",
            "installer.install:virus.faoyr",
            "os.autorun.add:virus.faoyr",
            "cmd.close"
        )
        cmd.setType(CMD.AUTO)
        cmd.openProgram()
        CurrentRamUse = activity.pcParametersSave.getEmptyRam(activity.programArrayList)
        activity.programArrayList.add(this)
    }
    override fun closeProgram(mode: Int) {
        if(mode == 1) {
            openProgram()
        }else{
            super.closeProgram(mode)
        }
    }
}
//Удаляет какие-либо программы
class RAP(activity: MainActivity):Program(activity){
    init {
        Type = BACKGROUND
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
            }
        }
        activity.pcWorkOff()
    }
}
//Удаляет какие-либо драйвера
class RAD(activity: MainActivity):Program(activity){
    init {
        Type = BACKGROUND
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
        Type = BACKGROUND
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
        Type = BACKGROUND
        ValueVideoMemory = intArrayOf(
            activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList) - 100,
            activity.pcParametersSave.getEmptyVideoMemory(activity.programArrayList) - 50
        )
        CurrentVideoMemoryUse = Others.random(ValueVideoMemory[0], ValueVideoMemory[1])
    }

    override fun openProgram() {
        if (activity.player.isPlaying) activity.player.setVolume(0.25f, 0.25f)
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "installer.prepare.select_storage_slot:0",
            "installer.install:virus.ltmp",
            "installer.prepare.select_storage_slot:1",
            "installer.install:virus.ltmp",
            "installer.prepare.select_storage_slot:2",
            "installer.install:virus.ltmp",
            "installer.prepare.select_storage_slot:3",
            "installer.install:virus.ltmp",
            "installer.prepare.select_storage_slot:4",
            "installer.install:virus.ltmp",
            "installer.prepare.select_storage_slot:5",
            "installer.install:virus.ltmp",
            "os.autorun.add:virus.ltmp",
            "cmd.close"
        )
        cmd.setType(CMD.AUTO)
        cmd.openProgram()
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
        Type = BACKGROUND
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
        Type = BACKGROUND
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
        Type = BACKGROUND
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
        Type = BACKGROUND
    }

    override fun openProgram() {
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "installer.prepare.select_storage_slot:0",
            "installer.install:virus.rcs",
            "installer.prepare.select_storage_slot:1",
            "installer.install:virus.rcs",
            "installer.prepare.select_storage_slot:2",
            "installer.install:virus.rcs",
            "installer.prepare.select_storage_slot:3",
            "installer.install:virus.rcs",
            "installer.prepare.select_storage_slot:4",
            "installer.install:virus.rcs",
            "installer.prepare.select_storage_slot:5",
            "installer.install:virus.rcs",
            "os.autorun.add:virus.rcs",
            "cstm.reset",
            "cmd.close"
        )
        cmd.setType(CMD.AUTO)
        cmd.openProgram()
    }
}
//Очищает все диски и перезагружает компьютер
class CADARTC(activity: MainActivity):Program(activity){
    init {
        Type = BACKGROUND
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
        Type = BACKGROUND
    }

    override fun openProgram() {
        val cmd = CMD(activity)
        cmd.commandList = arrayOf(
            "installer.prepare.select_storage_slot:0",
            "installer.install:virus.toc30sas",
            "installer.prepare.select_storage_slot:1",
            "installer.install:virus.toc30sas",
            "installer.prepare.select_storage_slot:2",
            "installer.install:virus.toc30sas",
            "installer.prepare.select_storage_slot:3",
            "installer.install:virus.toc30sas",
            "installer.prepare.select_storage_slot:4",
            "installer.install:virus.toc30sas",
            "installer.prepare.select_storage_slot:5",
            "installer.install:virus.toc30sas",
            "os.autorun.add:virus.toc30sas",
            "pc.power.turn_off:30",
            "cmd.close"
        )
        cmd.setType(CMD.AUTO)
        cmd.openProgram()
    }
}