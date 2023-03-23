package com.niksaen.pcsim.program.hSoftStore

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.niksaen.pcsim.R
import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.StringArrayWork
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter
import com.niksaen.pcsim.program.Program
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller

class HSoftPrepareForInstall(activity: MainActivity) : Program(activity) {
    lateinit var programForSetup: String

    private var setupWindow: HSoftSetupWindow
    init {
        Title = "Installation Wizard"
        ValueRam = intArrayOf(100, 150)
        ValueVideoMemory = intArrayOf(80, 100)
        setupWindow = HSoftSetupWindow(activity)
    }

    private lateinit var text: TextView
    private lateinit var main: ConstraintLayout
    private lateinit var addToDesktop: AppCompatCheckBox
    private lateinit var installAdditionalSoft:AppCompatCheckBox
    private lateinit var changeDisk: Spinner
    private lateinit var next: Button
    private lateinit var cancel: Button
    private lateinit var adapter: CustomListViewAdapter

    private lateinit var disk: String
    private lateinit var diskList: Array<String?>
    override fun initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_prepare_for_install, null)
        diskList = getDiskList()
        initView()
        viewStyle()
        text.text = activity.words["Select the drive to install and the required options"]
        next.text = activity.words["Next"] + " >"
        cancel.text = activity.words["Cancel"]
        addToDesktop.text = activity.words["Add an icon to the desktop"]
        installAdditionalSoft.text = activity.words["Install the required additional software"]
        changeDisk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (position > 0) {
                    disk = diskList[position].toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        next.setOnClickListener { v: View? ->
            if (disk != null) {
                setupWindow.setDisk(disk)
                setupWindow.setInstallAdditionalSoft(installAdditionalSoft.isChecked)
                setupWindow.setAddToDesktop(addToDesktop.isChecked)
                setupWindow.setProgramForSetup(programForSetup)
                setupWindow.openProgram()
                closeProgram(1)
            }
        }
        cancel.setOnClickListener { closeProgram(1) }
        super.initProgram()
    }

    private fun initView() {
        text = mainWindow.findViewById(R.id.text)
        main = mainWindow.findViewById(R.id.main)
        addToDesktop = mainWindow.findViewById(R.id.addToDesktop)
        installAdditionalSoft = mainWindow.findViewById(R.id.installAdditionalSoft)
        changeDisk = mainWindow.findViewById(R.id.changedDisk)
        next = mainWindow.findViewById(R.id.button2)
        cancel = mainWindow.findViewById(R.id.button3)
    }

    private fun viewStyle() {
        text.typeface = activity.font
        addToDesktop.typeface = activity.font
        installAdditionalSoft.typeface = activity.font
        next.setTypeface(activity.font, Typeface.BOLD)
        cancel.setTypeface(activity.font, Typeface.BOLD)
        text.setTextColor(activity.styleSave.TextColor)
        addToDesktop.setTextColor(activity.styleSave.TextColor)
        val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
        val colors = intArrayOf(activity.styleSave.ThemeColor3, activity.styleSave.ThemeColor3)
        addToDesktop.buttonTintList = ColorStateList(states, colors)
        installAdditionalSoft.setTextColor(activity.styleSave.TextColor)
        installAdditionalSoft.buttonTintList = ColorStateList(states, colors)
        next.setBackgroundColor(activity.styleSave.ThemeColor2)
        next.setTextColor(activity.styleSave.TextButtonColor)
        cancel.setBackgroundColor(activity.styleSave.ThemeColor2)
        cancel.setTextColor(activity.styleSave.TextButtonColor)
        main.setBackgroundColor(activity.styleSave.ThemeColor1)
        adapter = CustomListViewAdapter(activity, 0, diskList)
        adapter.BackgroundColor1 = activity.styleSave.ThemeColor1
        adapter.BackgroundColor2 = activity.styleSave.ThemeColor1
        adapter.TextColor = activity.styleSave.TextColor
        changeDisk.adapter = adapter
    }

    private fun getDiskList(): Array<String?> {
        var diskList = arrayOf(activity.words["Select drive:"])
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