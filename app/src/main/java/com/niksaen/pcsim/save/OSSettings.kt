package com.niksaen.pcsim.save

import android.content.Context
import android.content.SharedPreferences
import com.niksaen.pcsim.classes.StringArrayWork
import com.niksaen.pcsim.program.Program

class OSSettings(val context: Context) {
    private var data:SharedPreferences = context.getSharedPreferences("OS",Context.MODE_PRIVATE)
    val autoRunList:ArrayList<String> = ArrayList()
    init {
        data.getString("autoRun","")?.split(",")?.let { autoRunList.addAll(it) }
    }
    fun addToAutoRun(programId: String):Boolean{
        return if (!autoRunList.contains(programId)) {
            autoRunList.add(programId)
            saveAutoRunList()
            true
        }else{
            false
        }
    }
    fun removeAutoRunList(programId: String){
        autoRunList.remove(programId)
        saveAutoRunList()
    }
    fun clearAutoRunList(){
        autoRunList.clear()
        saveAutoRunList()
    }
    private fun saveAutoRunList(){
        data.edit().putString("autoRun",StringArrayWork.ArrayListToString(autoRunList)).apply()
    }
}