package com.niksaen.pcsim.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.niksaen.pcsim.classes.AssetFile
import com.niksaen.pcsim.classes.ItemClickSupport
import com.niksaen.pcsim.classes.StringArrayWork
import com.niksaen.pcsim.classes.adapters.ShopAdapter
import com.niksaen.pcsim.classes.dialogs.SaleDialog
import com.niksaen.pcsim.classes.pcComponents.PcComponent
import com.niksaen.pcsim.databinding.ActivitySalingBinding
import com.niksaen.pcsim.save.PlayerData
import com.niksaen.pcsim.save.Settings

class SealingActivity : AppCompatActivity() {
    private lateinit var font: Typeface
    private lateinit var playerData: PlayerData
    private lateinit var ui:ActivitySalingBinding

    lateinit var baseAdapter: ShopAdapter
    private val adapters = ArrayList<ShopAdapter>()
    var words: HashMap<String, String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySalingBinding.inflate(layoutInflater)
        setContentView(ui.root)
        getLanguage()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        font = Typeface.createFromAsset(assets, "fonts/pixelFont.ttf")
        playerData = PlayerData(this)
        initAdapters()
        setTextAndStyle()

        ItemClickSupport.addTo(ui.main).setOnItemClickListener { recyclerView: RecyclerView?, position: Int, v: View? ->
                if (ui.main.adapter === baseAdapter) {
                    ui.main.adapter = adapters[position]
                } else {
                    val dialog = SaleDialog(this)
                    val item = (ui.main.adapter as ShopAdapter).getItem(position)
                    val buff = PcComponent(this, item, (ui.main.adapter as ShopAdapter).type)
                    dialog.saleClickAction = View.OnClickListener { v1: View? ->
                        saleItem(position,dialog.pcComponent)
                        dialog.dismiss()
                    }
                    dialog.create(buff)
                    dialog.show()
                }
            }
    }
    private fun getLanguage() {
        val typeToken: TypeToken<HashMap<String, String>> = object : TypeToken<HashMap<String, String>>() {}
        words = Gson().fromJson(AssetFile(this).getText("language/" + Settings(this).Language + ".json"), typeToken.type)
    }
    private fun setTextAndStyle(){
        ui.text.typeface = font
        ui.text.text = words?.get("You have nothing to sell") ?: "You have nothing to sell"
        ui.moneyView.setTypeface(font,Typeface.BOLD)
        ui.back.typeface = font
        ui.back.text = words?.get("Back") ?: "Back"
        ui.moneyView.text = playerData.Money.toString()+"R"
        ui.main.adapter = baseAdapter
    }
    private fun initAdapters() {
        playerData.getAllData()
        val strings = ArrayList<String>()
        adapters.clear()
        if(playerData.PcCaseList.isNotEmpty()){
            strings.add(PcComponent.CASE)
            adapters.add(ShopAdapter(this, playerData.PcCaseList.asList(), PcComponent.CASE))
        }
        if(playerData.MotherboardList.isNotEmpty()) {
            strings.add(PcComponent.Motherboard)
            adapters.add(ShopAdapter(this, playerData.MotherboardList.asList(), PcComponent.Motherboard))
        }
        if(playerData.CpuList.isNotEmpty()){
            strings.add(PcComponent.CPU)
            adapters.add(ShopAdapter(this, playerData.CpuList.asList(), PcComponent.CPU))
        }
        if(playerData.CoolerList.isNotEmpty()){
            strings.add(PcComponent.COOLER)
            adapters.add(ShopAdapter(this, playerData.CoolerList.asList(), PcComponent.COOLER))
        }
        if(playerData.RamList.isNotEmpty()){
            strings.add(PcComponent.RAM)
            adapters.add(ShopAdapter(this, playerData.RamList.asList(), PcComponent.RAM))
        }
        if(playerData.GraphicsCardList.isNotEmpty()) {
            strings.add(PcComponent.GPU)
            adapters.add(ShopAdapter(this, playerData.GraphicsCardList.asList(), PcComponent.GPU))
        }
        if(playerData.StorageDeviceList.isNotEmpty()){
            strings.add(PcComponent.StorageDevice)
            adapters.add(ShopAdapter(this, playerData.StorageDeviceList.asList(), PcComponent.StorageDevice))
        }
        if(playerData.PowerSupplyList.isNotEmpty()) {
            strings.add(PcComponent.PowerSupply)
            adapters.add(ShopAdapter(this, playerData.PowerSupplyList.asList(), PcComponent.PowerSupply))
        }
        if(playerData.DiskSoftList.isNotEmpty()) {
            strings.add(PcComponent.Disk)
            adapters.add(ShopAdapter(this, playerData.DiskSoftList.asList(), PcComponent.Disk))
        }
        if(strings.isNotEmpty()) {
            baseAdapter = ShopAdapter(this, strings, "icon")
            ui.text.visibility = View.GONE
        }
        else {
            baseAdapter = ShopAdapter(this, arrayListOf(), "icon")
            ui.text.visibility = View.VISIBLE
        }
    }
    private fun saleItem(pos: Int,pcComponent: PcComponent){
        when(pcComponent.Type){
            PcComponent.CASE->{ playerData.PcCaseList = StringArrayWork.remove(playerData.PcCaseList,pos) }
            PcComponent.CPU->{playerData.CpuList = StringArrayWork.remove(playerData.CpuList,pos)}
            PcComponent.Motherboard->{playerData.MotherboardList = StringArrayWork.remove(playerData.MotherboardList,pos)}
            PcComponent.RAM->{playerData.RamList = StringArrayWork.remove(playerData.RamList,pos)}
            PcComponent.COOLER->{playerData.CoolerList = StringArrayWork.remove(playerData.CoolerList,pos)}
            PcComponent.GPU->{playerData.GraphicsCardList = StringArrayWork.remove(playerData.GraphicsCardList,pos)}
            PcComponent.StorageDevice->{playerData.StorageDeviceList = StringArrayWork.remove(playerData.StorageDeviceList,pos)}
            PcComponent.PowerSupply->{playerData.PowerSupplyList = StringArrayWork.remove(playerData.PowerSupplyList,pos)}
            PcComponent.Disk->{playerData.DiskSoftList = StringArrayWork.remove(playerData.DiskSoftList,pos)}
        }
        playerData.Money += SaleDialog.getPrice(pcComponent)
        playerData.setAllData()
        ui.moneyView.text = playerData.Money.toString()+"R"
        initAdapters()
        ui.main.adapter = baseAdapter
    }
    fun BackButton(view: View?) { onBackPressed() }

    override fun onBackPressed() {
        if (ui.main.adapter !== baseAdapter) {
            ui.main.adapter = baseAdapter
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            super.onBackPressed()
        }
    }
}