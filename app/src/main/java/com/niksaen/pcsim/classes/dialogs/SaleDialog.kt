package com.niksaen.pcsim.classes.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.niksaen.pcsim.R
import com.niksaen.pcsim.classes.AssetFile
import com.niksaen.pcsim.classes.pcComponents.PcComponent
import com.niksaen.pcsim.save.PcParametersSave
import com.niksaen.pcsim.save.Settings
import kotlin.math.roundToInt

class SaleDialog(val context: Context) {
    private var name: String? = null

    private lateinit var words: HashMap<String, String>
    private fun getLanguage() {
        val typeToken: TypeToken<HashMap<String, String>> = object : TypeToken<HashMap<String, String>>() {}
        words = Gson().fromJson(AssetFile(context).getText("language/" + Settings(context).Language + ".json"), typeToken.type)
    }

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private lateinit var buttonSale: Button
    private lateinit var ButtonCancel:Button
    lateinit var saleClickAction: View.OnClickListener
    lateinit var pcComponent: PcComponent
    fun create(pcComponent: PcComponent) {
        this.pcComponent = pcComponent
        getLanguage()
        name = pcComponent.Name
        builder = AlertDialog.Builder(context)
        val main: View = LayoutInflater.from(context).inflate(R.layout.dialog_shop, null)
        val Title = main.findViewById<TextView>(R.id.title)
        val Text = main.findViewById<TextView>(R.id.text)
        val Image = main.findViewById<ImageView>(R.id.image)
        buttonSale = main.findViewById(R.id.buy)
        ButtonCancel = main.findViewById(R.id.cancel_button)
        val font = Typeface.createFromAsset(context.assets, "fonts/pixelFont.ttf")
        Title.setTypeface(font, Typeface.BOLD)
        Text.typeface = font
        buttonSale.setTypeface(font, Typeface.BOLD)
        ButtonCancel.setTypeface(font, Typeface.BOLD)
        Title.text = pcComponent.Name
        Text.text = parser(pcComponent.Parameters, pcComponent.Type)
        Image.setImageDrawable(pcComponent.Textures)
        buttonSale.text = words["Sale"]
        ButtonCancel.text = words["Cancel"]
        main.setBackgroundColor(Color.parseColor("#111111"))
        Title.setTextColor(Color.WHITE)
        Text.setTextColor(Color.WHITE)
        buttonSale.setTextColor(Color.WHITE)
        buttonSale.setBackgroundColor(Color.parseColor("#1C1C1C"))
        ButtonCancel.setTextColor(Color.WHITE)
        ButtonCancel.setBackgroundColor(Color.parseColor("#1C1C1C"))
        builder.setView(main)
        builder.setCancelable(false)
    }

    fun dismiss() { dialog.dismiss() }

    fun show() {
        dialog = builder.create()
        buttonSale.setOnClickListener(saleClickAction)
        ButtonCancel.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }
    fun parser(parameters:HashMap<String,String>,type:String):String{
        var res = ""
        when(type){
            PcComponent.CASE ->{
                res = """
                    ${words["Ports"]} SATA: ${parameters["DATA"]}
                    ${words["Color"]}: ${parameters["Цвет"]}
                    """
            }
            PcComponent.Motherboard ->{
                res = """${words["Socket"]}: ${parameters["Сокет"]}
                    ${words["Memory type"]}: ${parameters["Тип памяти"]}
                    ${words["Number of channels"]}: ${parameters["Кол-во каналов"]}
                    ${words["Number of slots"]}: ${parameters["Кол-во слотов"]}
                    ${words["Minimum frequency"]}: ${parameters["Мин. частота"]}MHz
                    ${words["Maximum frequency"]}: ${parameters["Макс. частота"]}MHz
                    ${words["Maximum volume"]}: ${parameters["Макс. объём"]}Gb
                    ${words["Ports"]} SATA: ${parameters["Портов SATA"]}
                    ${words["Slots"]} PCI: ${parameters["Слотов PCI"]}
                    ${words["Power"]}: ${parameters["Мощность"]}W
                    """
            }
            PcComponent.CPU ->{
                res = """
                    ${words["Socket"]}: ${parameters["Сокет"]}
                    ${words["Technological process"]}: ${parameters["Техпроцесс"]}Nm
                    ${words["Number of cores"]}: ${parameters["Кол-во ядер"]}
                    ${words["Number of threads"]}: ${parameters["Кол-во потоков"]}
                    ${words["Cache"]}: ${parameters["Кэш"]}Mb
                    ${words["Frequency"]}: ${parameters["Частота"]}MHz
                    ${words["Overclocking capability"]}: ${parameters["Возможность разгона"]}
                    ${words["Power"]}: ${parameters["Мощность"]}W
                    ${words["Heat dissipation"]}: ${parameters["TDP"]}W
                    
                    ${words["RAM characteristics"]}
                    ${words["Memory type"]}: ${parameters["Тип памяти"]}
                    ${words["Maximum volume"]}: ${parameters["Макс. объём"]}Gb
                    ${words["Number of channels"]}: ${parameters["Кол-во каналов"]}
                    ${words["Minimum frequency"]}: ${parameters["Мин. частота"]}MHz
                    ${words["Maximum frequency"]}: ${parameters["Макс. частота"]}MHz
                    
                    ${words["Integrated graphics core"]}: ${parameters["Графическое ядро"]}
                    """.trimIndent()
                if (parameters["Графическое ядро"] == "+") {
                    res += """
                        ${words["Model"]}: ${parameters["Модель"]}
                        ${words["Frequency"]}: ${parameters["Частота GPU"]}MHz
                        """.trimIndent()
                }
            }
            PcComponent.COOLER ->{
                res = """
                    ${words["Power dissipation"]}: ${parameters["TDP"]}W
                    ${words["Power"]}: ${parameters["Мощность"]}W
                    """.trimIndent()
            }
            PcComponent.RAM ->{
                res = """
                    ${words["Memory type"]}: ${parameters["Тип памяти"]}
                    ${words["Volume"]}: ${parameters["Объём"]}Gb
                    ${words["Frequency"]}: ${parameters["Частота"]}MHz
                    ${words["Throughput"]}: ${parameters["Пропускная способность"]}PC
                    ${words["Power"]}: ${PcParametersSave.RamPower(parameters)}W
                    """.trimIndent()
            }
            PcComponent.GPU ->{
                res = """
                    ${words["GPU"]}: ${parameters["Графический процессор"]}
                    ${words["Number of video chips"]}: ${parameters["Кол-во видеочипов"]}
                    ${words["Frequency"]}: ${parameters["Частота"]}MHz
                    ${words["Memory type"]}:${parameters["Тип памяти"]}
                    ${words["Video memory size"]}: ${parameters["Объём видеопамяти"]}Gb
                    ${words["Throughput"]}: ${parameters["Пропускная способность"]}Gb/s
                    ${words["Cooling type"]}: ${words[parameters["Тип охлаждения"]]}
                    ${words["Power"]}: ${PcParametersSave.GpuPower(parameters)}W
                    """.trimIndent()
            }
            PcComponent.StorageDevice ->{
                res = """
                    ${words["Volume"]}: ${parameters["Объём"]}Gb
                    ${words["Type"]}: ${parameters["Тип"]}
                    ${words["Power"]}: ${parameters["Мощность"]}W
                    """.trimIndent()
            }
            PcComponent.PowerSupply ->{
                res = """
                    ${words["Power"]}: ${parameters["Мощность"]}W
                    ${words["Protection"]}: ${parameters["Защита"]}
                    """.trimIndent()
            }
            PcComponent.Disk ->{
                res = """
                    ${words["$name:Description"]}
                    """.trimIndent()
            }
        }
        res += """
            ${words["Price"]}: ${setPrice(parameters["Цена"]?.toInt() ?: 0)}R
            """.trimIndent()
        return res
    }
    private fun setPrice(price:Int):Int{
        return (price * 0.75f).toInt()
    }
    companion object{
        fun getPrice(pcComponent: PcComponent):Int{ return ((pcComponent.Parameters["Цена"]?.toInt() ?: 0) * 0.75).roundToInt() }
    }
}