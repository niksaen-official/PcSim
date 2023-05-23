package com.niksaen.pcsim.program.antivirus

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.LayerDrawable
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.BaseAdapter
import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.ProgressBarStylisation
import com.niksaen.pcsim.classes.adapters.CustomListViewAdapter
import com.niksaen.pcsim.databinding.ProgramAntivirusBinding
import com.niksaen.pcsim.os.LiriOS
import com.niksaen.pcsim.os.NapiOS
import com.niksaen.pcsim.os.cmd.CMD
import com.niksaen.pcsim.os.cmd.libs.Installer
import com.niksaen.pcsim.program.Program
import com.niksaen.pcsim.program.window.AcceptWindow
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Antivirus(activity: MainActivity?) : Program(activity) {
    private var ui:ProgramAntivirusBinding
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
                    updateAnimation()
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
        ui.listview.visibility = View.INVISIBLE
        ui.button5.text = activity.words["Find threats"]
        ui.textView3.text = activity.words["List of detected threats"]
        ProgressBarStylisation.setStyle(ui.progressBar,activity)
    }
    private fun findThreats():ArrayList<Threats>{
        val list = ArrayList<Threats>()
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