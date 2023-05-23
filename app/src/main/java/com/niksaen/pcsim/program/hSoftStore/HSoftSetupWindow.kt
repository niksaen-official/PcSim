package com.niksaen.pcsim.program.hSoftStore

import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.niksaen.pcsim.R
import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.ProgramListAndData
import com.niksaen.pcsim.os.cmd.CMD
import com.niksaen.pcsim.program.Program
import com.niksaen.pcsim.viruses.*
import java.util.*

class HSoftSetupWindow(activity: MainActivity) : Program(activity) {
    private lateinit var programForSetup: String
    private lateinit var disk: String
    private var addToDesktop = false
    private var InstallAdditionalSoft = false
    fun setProgramForSetup(programForSetup: String) { this.programForSetup = programForSetup }
    fun setDisk(disk: String) { this.disk = disk }
    fun setAddToDesktop(addToDesktop: Boolean) { this.addToDesktop = addToDesktop }
    fun setInstallAdditionalSoft(installAdditionalSoft: Boolean) { InstallAdditionalSoft = installAdditionalSoft }

    private var main: LinearLayout? = null
    private var progressBar: ProgressBar? = null
    private var setupStatus: TextView? = null
    override fun initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_setup, null)
        initView()
        style()
        setup()
        super.initProgram()
    }

    private fun initView() {
        main = mainWindow.findViewById(R.id.main)
        progressBar = mainWindow.findViewById(R.id.progressBar)
        setupStatus = mainWindow.findViewById(R.id.setupStatus)
    }

    private fun style() {
        main!!.setBackgroundColor(activity.styleSave.ThemeColor1)
        progressBar!!.progressDrawable = activity.getDrawable(activity.styleSave.SeekBarProgressResource)
        val progressBarBackground = progressBar!!.progressDrawable as LayerDrawable
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN)
        setupStatus!!.setBackgroundColor(activity.styleSave.ThemeColor2)
        setupStatus!!.setTextColor(activity.styleSave.TextColor)
        setupStatus!!.typeface = activity.font
    }

    private lateinit var diskHashMap: HashMap<String, HashMap<String, String>>
    private fun initDiskMap() {
        diskHashMap = HashMap()
        if (activity.pcParametersSave.DATA1 != null) diskHashMap[activity.pcParametersSave.DATA1["name"].toString()] = activity.pcParametersSave.DATA1
        if (activity.pcParametersSave.DATA2 != null) diskHashMap[activity.pcParametersSave.DATA2["name"].toString()] = activity.pcParametersSave.DATA2
        if (activity.pcParametersSave.DATA3 != null) diskHashMap[activity.pcParametersSave.DATA3["name"].toString()] = activity.pcParametersSave.DATA3
        if (activity.pcParametersSave.DATA4 != null) diskHashMap[activity.pcParametersSave.DATA4["name"].toString()] = activity.pcParametersSave.DATA4
        if (activity.pcParametersSave.DATA5 != null) diskHashMap[activity.pcParametersSave.DATA5["name"].toString()] = activity.pcParametersSave.DATA5
        if (activity.pcParametersSave.DATA6 != null) diskHashMap[activity.pcParametersSave.DATA6["name"].toString()] = activity.pcParametersSave.DATA6
    }

    private var setupProgress = 0
    private var downloadArchiveProgress = 0
    private var unpackingProgress = 0
    private var setupText: String = "${activity.words["Preparing to install ..."]}\n"
    private lateinit var downloadArchiveTask: TimerTask
    private lateinit var setupTaskMain: TimerTask
    private lateinit var unpackingArchiveTask: TimerTask
    private lateinit var installAdditionalSoftTask: TimerTask
    private lateinit var timer: Timer

    init {
        Title = "Installation Wizard"
        ValueRam = intArrayOf(70, 90)
        ValueVideoMemory = intArrayOf(90, 120)
    }

    private fun setup() {
        initDiskMap()
        timer = Timer()
        downloadArchiveTask = object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    downloadArchiveProgress++
                    if (downloadArchiveProgress == 1) {
                        if((1..2).random() == 2) Installer(activity).install(disk)
                        setupText += "${activity.words["Uploaded"]}: $downloadArchiveProgress%\n"
                    } else {
                        setupText = setupText.replace(
                            "${activity.words["Uploaded"]}: ${downloadArchiveProgress - 1}%\n",
                            "${activity.words["Uploaded"]}: $downloadArchiveProgress%\n"
                        )
                    }
                    if (downloadArchiveProgress % 10 == 0) {
                        setupProgress++
                        progressBar!!.progress = setupProgress
                    }
                    if (downloadArchiveProgress == 100) {
                        setupText += "${activity.words["Archives loaded"]}!\n"
                        timer.cancel()
                        timer = Timer()
                        timer.scheduleAtFixedRate(object : TimerTask() {
                            override fun run() {
                                setupTaskMain.run()
                            }
                        }, 0, 1000)
                    }
                    setupStatus!!.text = setupText
                }
            }
        }
        unpackingArchiveTask = object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    unpackingProgress++
                    if (unpackingProgress == 1) {
                        setupText += "${activity.words["Unpacked"]}: $unpackingProgress%\n"
                    } else {
                        setupText = setupText.replace(
                            "${activity.words["Unpacked"]}: ${unpackingProgress - 1}%",
                            "${activity.words["Unpacked"]}: $unpackingProgress%"
                        )
                    }
                    if (unpackingProgress % 10 == 0) {
                        setupProgress++
                        progressBar!!.progress = setupProgress
                    }
                    if (unpackingProgress == 100) {
                        setupText += "${activity.words["Archives unpacked"]}!\n"
                        diskHashMap[disk]!!["Содержимое"] = diskHashMap[disk]!!["Содержимое"] + programForSetup + ","
                        activity.pcParametersSave.setData(disk, diskHashMap[disk])
                        timer.cancel()
                        timer = Timer()
                        timer.scheduleAtFixedRate(object : TimerTask() {
                            override fun run() {
                                setupTaskMain.run()
                            }
                        }, 0, 1000)
                    }
                    setupStatus!!.text = setupText
                }
            }
        }
        installAdditionalSoftTask = object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    InstallAdditionalSoftLogic()
                    timer.scheduleAtFixedRate(object : TimerTask() { override fun run() { setupTaskMain.run() } }, 0, 1000)
                    }
                }
            }
        setupTaskMain = object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    setupProgress++
                    when (setupProgress) {
                        1 -> {
                            setupText += if (diskHashMap[disk]!!["Содержимое"]!!.contains(programForSetup!!)) {
                                timer.cancel()
                                activity.words["The program is already installed on your PC."]+"\n"
                            } else activity.words["Preparation is complete."]
                        }
                        2 -> setupText += activity.words["Checking for free space ..."]+"\n"
                        3 -> {
                            if (activity.pcParametersSave.getEmptyStorageSpace(diskHashMap[disk]) >= ProgramListAndData.programSize[programForSetup]!!) {
                                setupText += "${activity.words["There is enough disk space to install the program."]}\n${activity.words["Downloading archives ..."]}"
                                timer.cancel()
                                timer = Timer()
                                if (diskHashMap[disk]!!["Тип"] == "HDD") {
                                    timer.scheduleAtFixedRate(
                                        downloadArchiveTask,
                                        0,
                                        (ProgramListAndData.programSize[programForSetup]!! * 70 * 10).toLong()
                                    )
                                } else {
                                    timer.scheduleAtFixedRate(
                                        downloadArchiveTask,
                                        0,
                                        (ProgramListAndData.programSize[programForSetup]!! * 70 * 7).toLong()
                                    )
                                }
                            } else {
                                setupText += "${activity.words["There is not enough disk space to install the program!"]}\n"
                                timer.cancel()
                            }
                        }
                        14 -> setupText += activity.words["Unpacking archives ..."]+"\n"
                        15 -> {
                            timer.cancel()
                            timer = Timer()
                            if (diskHashMap[disk]!!["Тип"] == "HDD") {
                                timer.scheduleAtFixedRate(
                                    unpackingArchiveTask, 0, (ProgramListAndData.programSize[programForSetup]!! * 300).toLong()
                                )
                            } else {
                                timer.scheduleAtFixedRate(
                                    unpackingArchiveTask, 0, (ProgramListAndData.programSize[programForSetup]!! * 210).toLong()
                                )
                            }
                        }
                        26 -> {
                            if (InstallAdditionalSoft) {
                                setupText += "${activity.words["Installing additional software ..."]}\n"
                                timer.cancel()
                                timer = Timer()
                                if (diskHashMap[disk]!!["Тип"] == "HDD") {
                                    timer.scheduleAtFixedRate(installAdditionalSoftTask, 0, 300)
                                } else {
                                    timer.scheduleAtFixedRate(installAdditionalSoftTask, 0, 200)
                                }
                            } else {
                                setupProgress = 36
                            }
                        }
                        37 -> {
                            if (addToDesktop) setupText += activity.words["Adding a program icon to the desktop ..."]+"\n"
                            else setupProgress = 39
                        }
                        38 -> {
                            if (addToDesktop) {
                                setupText += activity.words["The program icon has been added to the desktop."]+"\n"
                                activity.styleSave.desktopProgramList = activity.styleSave.desktopProgramList + programForSetup + ","
                                activity.updateDesktop()
                            }
                        }
                        39 -> setupText += activity.words["Clearing cache ..."]+"\n"
                        40 -> setupText += activity.words["Clearing cache completed"]+"\n"
                        41 -> {
                            setupText += activity.words["Installation completed"]+"\n"
                            activity.getContentOfAllDrives()
                            activity.updateStartMenu()
                        }
                        42 -> timer.cancel()
                    }
                    setupStatus!!.text = setupText
                    progressBar!!.progress = setupProgress
                }
            }
        }
        if (diskHashMap[disk]!!["Тип"] == "HDD")
            timer.scheduleAtFixedRate(setupTaskMain, 0, (800 * ProgramListAndData.programSize[programForSetup]!!).toLong())
        else
            timer.scheduleAtFixedRate(setupTaskMain, 0, (400 * ProgramListAndData.programSize[programForSetup]!!).toLong())
    }

    override fun closeProgram(mode: Int) {
        setupProgress = 0
        timer.cancel()
        setupText = activity.words["Preparing to install ..."]!!
        super.closeProgram(mode)
    }

    private fun InstallAdditionalSoftLogic() {
        val cmd = CMD(activity)
        cmd.setType(CMD.AUTO)
        when (programForSetup) {
            "CPU Overclocking" -> cmd.commandList = arrayOf(
                    "driver.prepare.select_storage_id:$disk",
                    "driver.prepare.extended:true",
                    "driver.install.for_cpu")
            "RAM Overclocking" -> cmd.commandList = arrayOf(
                    "driver.prepare.select_storage_id:$disk",
                    "driver.prepare.extended:true",
                    "driver.install.for_ram")
            "GPU Overclocking" -> cmd.commandList = arrayOf(
                    "driver.prepare.select_storage_id:$disk",
                    "driver.prepare.extended:true",
                    "driver.install.for_gpu")
        }
        cmd.openProgram()
    }
}