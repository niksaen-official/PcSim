package com.niksaen.pcsim.program

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.StringArrayWork
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter
import com.niksaen.pcsim.databinding.ProgramInstallfdBinding
import com.niksaen.pcsim.os.cmd.CMD
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller

class InstallFD(activity: MainActivity?) : Program(activity) {
    var idDisk=""
    lateinit var ui:ProgramInstallfdBinding
    init {
        ValueRam[0] = 50
        ValueRam[1] = 80
        ValueVideoMemory[0] = 15
        ValueVideoMemory[1] = 30
        Title = "InstallFD"
    }
    fun style(){
        ui.main.setBackgroundColor(activity.styleSave.ThemeColor1)
        ui.installButton.setTextColor(activity.styleSave.TextButtonColor)
        ui.installButton.setBackgroundColor(activity.styleSave.ThemeColor2)
        ui.diskName.setTextColor(activity.styleSave.TextColor)
    }

    override fun initProgram() {
        ui = ProgramInstallfdBinding.inflate(activity.layoutInflater)
        style()
        ui.installButton.text = activity.words["Install"]
        val adapter = CustomListViewAdapter(activity, 0, getDiskList())
        adapter.BackgroundColor1 = activity.styleSave.ThemeColor1
        adapter.BackgroundColor2 = activity.styleSave.ThemeColor1
        adapter.TextColor = activity.styleSave.TextColor
        ui.diskList.adapter = adapter
        ui.diskList.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idDisk = getDiskList()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                idDisk = ""
            }
        }
        ui.diskName.text = activity.DiskInDrive
        ui.installButton.setOnClickListener {
            if (idDisk != "") {
                val cmd = CMD(activity)
                cmd.setType(CMD.AUTO)
                cmd.commandList = arrayOf(
                    "ifd.prepare.select_storage_id:$idDisk",
                    "ifd.prepare.get_disk",
                    "ifd.install"
                )
                cmd.openProgram()
            }
        }
        mainWindow = ui.root
        super.initProgram()
    }
    private fun getDiskList(): Array<String> {
        var diskList: Array<String> = arrayOf(activity.words["Select drive:"].toString())
        if (activity.pcParametersSave.DATA1 != null
            && StringArrayWork.ArrayListToString(activity.apps)
                .contains(DriverInstaller.DriverForStorageDevices + activity.pcParametersSave.Data1)
        ) {
            diskList = StringArrayWork.add(diskList, activity.pcParametersSave.DATA1["name"])
        }
        if (activity.pcParametersSave.DATA2 != null
            && StringArrayWork.ArrayListToString(activity.apps)
                .contains(DriverInstaller.DriverForStorageDevices + activity.pcParametersSave.Data2)
        ) {
            diskList = StringArrayWork.add(diskList, activity.pcParametersSave.DATA2["name"])
        }
        if (activity.pcParametersSave.DATA3 != null
            && StringArrayWork.ArrayListToString(activity.apps)
                .contains(DriverInstaller.DriverForStorageDevices + activity.pcParametersSave.Data3)
        ) {
            diskList = StringArrayWork.add(diskList, activity.pcParametersSave.DATA3["name"])
        }
        if (activity.pcParametersSave.DATA4 != null
            && StringArrayWork.ArrayListToString(activity.apps)
                .contains(DriverInstaller.DriverForStorageDevices + activity.pcParametersSave.Data4)
        ) {
            diskList = StringArrayWork.add(diskList, activity.pcParametersSave.DATA4["name"])
        }
        if (activity.pcParametersSave.DATA5 != null
            && StringArrayWork.ArrayListToString(activity.apps)
                .contains(DriverInstaller.DriverForStorageDevices + activity.pcParametersSave.Data5)
        ) {
            diskList = StringArrayWork.add(diskList, activity.pcParametersSave.DATA5["name"])
        }
        if (activity.pcParametersSave.DATA6 != null
            && StringArrayWork.ArrayListToString(activity.apps)
                .contains(DriverInstaller.DriverForStorageDevices + activity.pcParametersSave.Data6)
        ) {
            diskList = StringArrayWork.add(diskList, activity.pcParametersSave.DATA6["name"])
        }
        return diskList
    }
}