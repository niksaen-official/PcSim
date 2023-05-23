package com.niksaen.pcsim.program.window

import android.graphics.Typeface
import android.view.View
import com.niksaen.pcsim.activities.MainActivity
import com.niksaen.pcsim.databinding.WarningWindowBinding
import com.niksaen.pcsim.program.Program

class AcceptWindow(activity: MainActivity) : Program(activity) {
    var message:String  = "Message"
    var okBtnClickAction:View.OnClickListener = View.OnClickListener { v: View? -> this.closeProgram(1)}
    var okBtnText:String = "Ok"
    var cancelBtnClickAction:View.OnClickListener = View.OnClickListener { v: View? -> this.closeProgram(1)}
    var cancelBtnText:String  = "Cancel"
    private val ui: WarningWindowBinding

    init {
        Title = "Confirm"
        ValueRam = intArrayOf(10, 25)
        ValueVideoMemory = intArrayOf(10, 20)
        ui = WarningWindowBinding.inflate(activity.layoutInflater)
    }

    override fun initProgram() {
        mainWindow = ui.root
        style()
        super.initProgram()
        ui.ok.setOnClickListener(okBtnClickAction)
        ui.cancel.setOnClickListener { cancelBtnClickAction }
        buttonClose.isClickable = false
        buttonRollUp.visibility = View.GONE
        buttonFullscreenMode.visibility = View.GONE
        mainWindow.scaleX = 0.5f
        mainWindow.scaleY = 0.5f
        ui.message.text = message
        ui.ok.text = okBtnText
        ui.cancel.text = cancelBtnText
    }

    fun style() {
        ui.background.setBackgroundColor(activity.styleSave.ThemeColor1)
        ui.cancel.setBackgroundColor(activity.styleSave.ThemeColor2)
        ui.ok.setBackgroundColor(activity.styleSave.ThemeColor2)
        ui.cancel.setTypeface(activity.font, Typeface.BOLD)
        ui.ok.setTypeface(activity.font, Typeface.BOLD)
        ui.message.setTypeface(activity.font, Typeface.BOLD)
        ui.ok.setTextColor(activity.styleSave.TextButtonColor)
        ui.cancel.setTextColor(activity.styleSave.TextButtonColor)
        ui.message.setTextColor(activity.styleSave.TextColor)
    }
}