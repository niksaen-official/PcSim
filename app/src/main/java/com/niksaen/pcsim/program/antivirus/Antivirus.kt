package com.niksaen.pcsim.program.antivirus

//noinspection SuspiciousImport
import android.R
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.ProgressBarStylisation
import com.niksaen.pcsim.classes.StringArrayWork
import com.niksaen.pcsim.databinding.ProgramAntivirusBinding
import com.niksaen.pcsim.os.cmd.CMD
import com.niksaen.pcsim.program.Program
import com.niksaen.pcsim.program.window.AcceptWindow
import java.util.*

class Antivirus(activity: MainActivity?) : Program(activity) {
    private var ui:ProgramAntivirusBinding
    private var list=ArrayList<Threats>()
    init{
        Title = "Antivirus"
        ValueRam = intArrayOf(100, 150)
        ValueVideoMemory = intArrayOf(80, 100)
        ui = ProgramAntivirusBinding.inflate(activity!!.layoutInflater)
    }

    override fun initProgram() {
        setStyle()
        ui.button5.setOnClickListener {
            findAnimation()
        }
        ui.listview.setOnItemClickListener { _, _, i,_ ->
            run {
                val window = AcceptWindow(activity)
                window.okBtnText = activity.words["Delete"].toString()
                window.cancelBtnText = activity.words["Cancel"].toString()
                window.message = activity.words["Confirm deletion"].toString()
                window.okBtnClickAction = View.OnClickListener {
                    val threats = findThreats()[i]
                    val cmd = CMD(activity)
                    cmd.commandList = arrayOf(
                        "os.autorun.remove:${threats.packageName}",
                        "pc.storage.remove_from_drive:${threats.storagePos},${threats.packageName}",
                        "cmd.close"
                    )
                    cmd.setType(CMD.AUTO)
                    cmd.openProgram()
                    window.closeProgram(1)
                }
                window.openProgram()
            }
        }
        mainWindow = ui.root

        super.initProgram()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setStyle(){
        ui.button5.setBackgroundColor(activity.styleSave.ThemeColor2)
        ui.button5.setTextColor(activity.styleSave.TextButtonColor)
        ui.main.setBackgroundColor(activity.styleSave.ThemeColor1)
        ui.textView3.setTextColor(activity.styleSave.TextColor)
        ui.autoRemoveEnable.setTextColor(activity.styleSave.TextColor)
        val states = arrayOf(intArrayOf(R.attr.state_checked), intArrayOf())
        val colors = intArrayOf(activity.styleSave.ThemeColor3, activity.styleSave.ThemeColor3)
        ui.autoRemoveEnable.buttonTintList = ColorStateList(states, colors)
        ui.listview.visibility = View.INVISIBLE
        ui.button5.text = activity.words["Find threats"]
        ui.textView3.text = activity.words["List of detected threats"]
        ui.autoRemoveEnable.text = activity.words["Automatic removal of threats"]
        ProgressBarStylisation.setStyle(ui.progressBar,activity)
    }
    private fun findThreats():ArrayList<Threats>{
        list = ArrayList()
        val driveList = ArrayList<HashMap<String,String>>()
        if(activity.pcParametersSave.DATA1 != null) driveList.add(activity.pcParametersSave.DATA1)
        if(activity.pcParametersSave.DATA2 != null) driveList.add(activity.pcParametersSave.DATA2)
        if(activity.pcParametersSave.DATA3 != null) driveList.add(activity.pcParametersSave.DATA3)
        if(activity.pcParametersSave.DATA4 != null) driveList.add(activity.pcParametersSave.DATA4)
        if(activity.pcParametersSave.DATA5 != null) driveList.add(activity.pcParametersSave.DATA5)
        if(activity.pcParametersSave.DATA6 != null) driveList.add(activity.pcParametersSave.DATA6)
        for (drivePos in (0 until driveList.size)){
            for(program in driveList[drivePos]["Содержимое"]!!.split(",")){
                if(program.startsWith("virus.")){
                    list.add(Threats(drivePos,program))
                }
            }
        }
        return list
    }
    private fun findAnimation(){
        var progress = 1
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    ui.listview.visibility = View.GONE
                    ui.progressBar.visibility = View.VISIBLE
                    ui.progressBar.progress = progress
                    progress++
                    if(progress >= 100){
                        if(list.size > 0) {
                            if (ui.autoRemoveEnable.isChecked) {
                                findThreats()
                                val window = AcceptWindow(activity)
                                window.okBtnText = activity.words["Delete"].toString()
                                window.cancelBtnText = activity.words["Cancel"].toString()
                                window.message = activity.words["Confirm deletion"].toString()
                                window.okBtnClickAction = View.OnClickListener {
                                    val cmd = CMD(activity)
                                    val commandList = ArrayList<String>()
                                    for (threat in list) {
                                        commandList.add("os.autorun.remove:${threat.packageName}")
                                        commandList.add("pc.storage.remove_from_drive:${threat.storagePos},${threat.packageName}")
                                    }
                                    commandList.add("cmd.close")
                                    cmd.commandList = StringArrayWork.ArrayListToArray(commandList)
                                    cmd.setType(CMD.AUTO)
                                    cmd.openProgram()
                                    window.closeProgram(1)
                                    updateAnimation()
                                }
                                window.openProgram()
                            }
                        }
                        ui.progressBar.visibility = View.GONE
                        updateList()
                        ui.listview.visibility = View.VISIBLE
                        progress = 0
                        timer.cancel()
                    }
                }
            }
        }
        timer.scheduleAtFixedRate(task,0,(activity.apps.size*25).toLong())
    }
    private fun updateAnimation(){
        var progress = 1
        val timer = Timer()
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                activity.runOnUiThread {
                    ui.listview.visibility = View.GONE
                    ui.progressBar.visibility = View.VISIBLE
                    ui.progressBar.progress = progress
                    progress++
                    if(progress >= 100){
                        ui.progressBar.visibility = View.GONE
                        updateList()
                        ui.listview.visibility = View.VISIBLE
                        progress = 0
                        timer.cancel()
                    }
                }
            }
        }
        timer.scheduleAtFixedRate(task,0,(25).toLong())
    }
    private fun updateList(){
        val adapter = AntivirusAdapter(activity,0,findThreats())
        adapter.backgroundColor = activity.styleSave.ThemeColor1
        adapter.textColor = activity.styleSave.TextColor
        ui.listview.adapter = adapter
    }
}