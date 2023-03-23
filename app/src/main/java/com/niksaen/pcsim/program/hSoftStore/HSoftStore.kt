package com.niksaen.pcsim.program.hSoftStore

import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.niksaen.pcsim.R
import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.classes.ProgramListAndData
import com.niksaen.pcsim.program.Program
import com.niksaen.pcsim.program.appDownloader.AcceptPolitic
import com.niksaen.pcsim.program.appDownloader.AppDownloaderListAdapter

class HSoftStore(activity: MainActivity) : Program(activity) {
    private var hSoftPrepareForInstall: HSoftPrepareForInstall
    init{
        Title = "HSoftStore"
        ValueRam = intArrayOf(100, 150)
        ValueVideoMemory = intArrayOf(80, 100)
        hSoftPrepareForInstall = HSoftPrepareForInstall(activity)
    }

    private lateinit var appList: ListView
    override fun initProgram() {
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_temperature_viewer, null)
        initAdapter()
        initViewAndStyle()
        appList.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                hSoftPrepareForInstall.programForSetup = ProgramListAndData.DontFreeAppList[position]
                hSoftPrepareForInstall.openProgram()
                closeProgram(1)
            }
        super.initProgram()
    }

    lateinit var adapter: HSoftStoreAdapter
    private fun initAdapter() {
        adapter = HSoftStoreAdapter(activity.baseContext, 0)
        adapter.BackgroundColor = activity.styleSave.ThemeColor1
        adapter.TextColor = activity.styleSave.TextColor
    }

    private fun initViewAndStyle() {
        appList = mainWindow.findViewById(R.id.main)
        appList.setBackgroundColor(activity.styleSave.ThemeColor1)
        appList.adapter = adapter
    }
}