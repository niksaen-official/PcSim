package com.niksaen.pcsim.program.hSoftStore

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.niksaen.pcsim.R
import com.niksaen.pcsim.classes.AssetFile
import com.niksaen.pcsim.classes.ProgramListAndData
import com.niksaen.pcsim.save.Settings

class HSoftStoreAdapter(context: Context, resource: Int) : ArrayAdapter<String>(context, resource) {
    init { getLanguage() }

    lateinit var words: HashMap<String, String>
    private fun getLanguage() {
        val typeToken: TypeToken<HashMap<String, String>> = object : TypeToken<HashMap<String, String>>() {}
        words = Gson().fromJson(
            AssetFile(context).getText(
                "language/" + Settings(context).Language + ".json"
            ), typeToken.type
        )
    }

    override fun getCount(): Int { return ProgramListAndData.DontFreeAppList.size }

    var BackgroundColor = Color.parseColor("#ffffff")
    var TextColor = Color.parseColor("#000000")
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val main: View = LayoutInflater.from(context).inflate(R.layout.item_start_menu, null)
        val textView = main.findViewById<TextView>(R.id.app_name)
        textView.typeface = Typeface.createFromAsset(context.assets, "fonts/pixelFont.ttf")
        textView.setTextColor(TextColor)
        textView.setBackgroundColor(BackgroundColor)
        textView.text = words[ProgramListAndData.DontFreeAppList[position]]
        val image = main.findViewById<ImageView>(R.id.app_icon)
        image.setImageResource(ProgramListAndData.programIcon[ProgramListAndData.DontFreeAppList[position]]!!)
        return main
    }
}