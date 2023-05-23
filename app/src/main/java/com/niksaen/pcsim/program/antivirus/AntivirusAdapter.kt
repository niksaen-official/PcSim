package com.niksaen.pcsim.program.antivirus

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AntivirusAdapter(context: Context,resourceId:Int,val list: ArrayList<Threats>) : ArrayAdapter<Threats>(context,resourceId,list) {
    override fun getCount(): Int {
        return list.size
    }

    var backgroundColor = Color.parseColor("#ffffff")
    var textColor = Color.parseColor("#000000")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textView = TextView(context)
        textView.typeface = Typeface.createFromAsset(context.assets, "fonts/pixelFont.ttf")
        textView.setTextColor(textColor)
        textView.setBackgroundColor(backgroundColor)
        textView.setPadding(12, 12, 12, 12)
        textView.textSize = 27f
        textView.text = list[position].packageName
        return textView
    }
}