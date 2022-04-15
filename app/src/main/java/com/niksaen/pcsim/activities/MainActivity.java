package com.niksaen.pcsim.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PopuListView.PopupListView;
import com.niksaen.pcsim.classes.ProgramListAndData;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.DesktopAdapter;
import com.niksaen.pcsim.classes.adapters.DiskChangeAdapter;
import com.niksaen.pcsim.classes.adapters.DrawerAdapter;
import com.niksaen.pcsim.classes.adapters.StartMenuAdapter;
import com.niksaen.pcsim.classes.adapters.ToolbarAdapter;
import com.niksaen.pcsim.databinding.ActivityMainBinding;
import com.niksaen.pcsim.os.LiriOS;
import com.niksaen.pcsim.os.NapiOS;
import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.taskManager.TaskManager;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.PlayerData;
import com.niksaen.pcsim.save.Settings;
import com.niksaen.pcsim.save.StyleSave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    Button powerButton;
    public LinearLayout toolbar;
    public LinearLayout startMenu;
    public TextView greeting,startMenuTitle;
    public RecyclerView desktop;
    public RecyclerView appList;
    public ListView allAppList;
    private View caseView;
    public Button startMenuOpener;
    ListView drawer;

    public PcParametersSave pcParametersSave;
    public StyleSave styleSave;
    public ConstraintLayout layout;
    public String DiskInDrive;

    public MediaPlayer player;
    public Typeface font;
    int style = Typeface.BOLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");

        getTranslate();
        pcParametersSave = new PcParametersSave(this);
        styleSave = new StyleSave(this);

        initView();
        viewStyle();
        initDrawer();

        player = new MediaPlayer();
        buttonPC();

        startMenuOpener.setOnLongClickListener(v -> {
            CMD cmd = new CMD(MainActivity.this);
            cmd.setType(CMD.WINDOW);
            cmd.openProgram();
            return false;
        });
    }

    void initView(){
        powerButton = findViewById(R.id.on_off);
        layout = findViewById(R.id.monitor);
        toolbar =findViewById(R.id.toolbar);
        greeting = findViewById(R.id.greeting);
        desktop = findViewById(R.id.desktop);
        appList = findViewById(R.id.app_list);

        startMenu = findViewById(R.id.startMenu);
        startMenuTitle = findViewById(R.id.startMenuTitle);
        allAppList = findViewById(R.id.allAppList);
        caseView = findViewById(R.id.linearLayout2);
        startMenuOpener = findViewById(R.id.menu);
    }

    void viewStyle(){
        greeting.setTypeface(font,style);
        //case color
        if(pcParametersSave.CASE != null) {
            caseView.setBackgroundColor(Color.parseColor(pcParametersSave.CASE.get("Color")));
        }else{
            caseView.setVisibility(View.INVISIBLE);
            powerButton.setClickable(false);
        }
    }

    public HashMap<String,String> words;
    private void getTranslate(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(this).getText("language/"+ new Settings(this).Language+".json"),typeToken.getType());
    }

    private Intent intent = new Intent();
    private void initDrawer(){
        String[] menuList = new String[]{
                words.get("Menu")+":",
                words.get("Shop"),
                words.get("PC assembly"),
                words.get("Settings"),
                words.get("Tutorial"),
                words.get("About me")
        };
        drawer = findViewById(R.id.drawer);
        DrawerAdapter drawerAdapter = new DrawerAdapter(this,menuList);
        drawerAdapter.BackgroundColor = Color.parseColor("#111111");
        drawerAdapter.TextColor = Color.parseColor("#FFFFFF");
        drawer.setAdapter(drawerAdapter);

        drawer.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 1:{
                    intent = new Intent(MainActivity.this, MainShopActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case 2:{
                    intent = new Intent(MainActivity.this,IronActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case 3:{
                    intent = new Intent(MainActivity.this,SettingsActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case 4:{
                    intent = new Intent(MainActivity.this,TutorialActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case 5:{
                    intent = new Intent(MainActivity.this,AboutMeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }
        });
    }

    public String[] apps;
    public void getContentOfAllDrives(){
        String[][] allDiskAppList = new String[0][];
        if(pcParametersSave.DATA1 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,pcParametersSave.DATA1.get("Содержимое").split(","));
        }
        if(pcParametersSave.DATA2 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,pcParametersSave.DATA2.get("Содержимое").split(","));
        }
        if(pcParametersSave.DATA3 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,pcParametersSave.DATA3.get("Содержимое").split(","));
        }
        if(pcParametersSave.DATA4 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,pcParametersSave.DATA4.get("Содержимое").split(","));
        }
        if(pcParametersSave.DATA5 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,pcParametersSave.DATA5.get("Содержимое").split(","));
        }
        if(pcParametersSave.DATA6 != null){
            allDiskAppList = StringArrayWork.add(allDiskAppList,pcParametersSave.DATA6.get("Содержимое").split(","));
        }
        apps = StringArrayWork.concatAll(allDiskAppList);
    }
    public void updateDesktop() {
        desktop.setLayoutManager(new GridLayoutManager(getBaseContext(), 6));
        if(styleSave.getDesktopProgramList()!="") {
            desktop.setAdapter(new DesktopAdapter(this, styleSave.getDesktopProgramList().split(",")));
        }
    }
    public void StartMenu(View view){
        if(startMenu.getVisibility() == View.GONE){
            getContentOfAllDrives();
            startMenu.setVisibility(View.VISIBLE);
            updateStartMenu();
        }
        else{
            startMenu.setVisibility(View.GONE);
        }
    }
    public void updateStartMenu(){
        ProgramListAndData program = new ProgramListAndData();
        program.initHashMap(this);
        startMenu.setBackgroundColor(styleSave.StartMenuColor);
        startMenuTitle.setTextColor(styleSave.StartMenuTextColor);
        startMenuTitle.setTypeface(font,Typeface.BOLD);
        startMenuTitle.setText(words.get("Start"));
        StartMenuAdapter menuAdapter = new StartMenuAdapter(this, 0, apps);
        menuAdapter.setMainActivity(this);
        allAppList.setAdapter(menuAdapter);
        allAppList.setOnItemClickListener((parent, view, position, id) -> {
            Program program1 = program.programHashMap.get(menuAdapter.getItem(position));
            program1.openProgram();
        });
    }
    public void updateToolbar(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        appList.setLayoutManager(layoutManager);
        appList.setAdapter(new ToolbarAdapter(this));
    }

    //список запущеных програм
    public ArrayList<Program> programArrayList = new ArrayList<>();
    public TaskManager taskManager = new TaskManager(this);

    //кнопка питания пк
    private int PcWorkStatus = 0;
    private void buttonPC(){
        powerButton.setOnClickListener(v -> {
            if(PcWorkStatus == 0) {
                if (pcParametersSave.getPcWork()) {
                    if (pcParametersSave.currentCpuTemperature() <= pcParametersSave.maxCpuTemperature()) {
                        if (pcParametersSave.psuEnoughPower()) {
                            pcWorkOn();
                        } else {
                            if(pcParametersSave.PSU != null) {
                                if (pcParametersSave.PSU.get("Защита").equals("-")) {
                                    pcParametersSave.setPsu(pcParametersSave.Psu + "[Сломано]", null);
                                }
                            }
                            blackDeadScreen(new String[]{words.get("The power supply is overloaded")});
                        }
                    }
                    else{
                        pcParametersSave.setCpu(pcParametersSave.Cpu+"[Сломано]",null);
                        blackDeadScreen(new String[]{words.get("Processor overheating")});
                    }
                }
                else{
                    if(pcParametersSave.COOLER == null){
                        blackDeadScreen(new String[]{words.get("There is no cooler")});
                    }
                }
            }
            else{
                pcWorkOff();
            }
        });
    }
    // выключение пк
    public void pcWorkOff(){
        powerButton.setClickable(false);
        player.stop();
        player.release();
        player = MediaPlayer.create(this, R.raw.pc_work_end_sound);
        player.setVolume(0.1f,0.1f);
        player.setLooping(false);
        player.start();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->{
                    PcWorkStatus = 0;
                    if(DeadScreen != null){
                        DeadScreen.setVisibility(View.GONE);
                    }
                    for(int i = programArrayList.size()-1;i>=0;i--){
                        Program program = programArrayList.get(i);
                        program.closeProgram(0);
                    }
                    programArrayList.clear();
                    powerButton.setForeground(getDrawable(R.drawable.off));
                    powerButton.setClickable(true);
                    player.stop();
                });
            }
        };
        if (pcParametersSave.getMainDiskType().equals("SSD")) {
            timer.schedule(timerTask, 1500);
        } else {
            timer.schedule(timerTask, 3000);
        }
    }
    // включение пк
    public void pcWorkOn(){
        player = MediaPlayer.create(this, R.raw.pc_work_start_sound);
        player.setVolume(0.1f,0.1f);
        player.setLooping(false);
        player.start();

        pcParametersSave.setAllRamFrequency();
        getContentOfAllDrives();
        if(StringArrayWork.ArrayListToString(apps).contains("NapiOS,")){
            NapiOS os = new NapiOS(this);
            os.openProgram();
        }else if(StringArrayWork.ArrayListToString(apps).contains("LiriOS,")){
            LiriOS os = new LiriOS(this);
            os.openProgram();
        }
        else{
            CMD cmd = new CMD(this);
            cmd.openProgram();
        }
        pcWorkSound();
        PcWorkStatus = 1;
        powerButton.setForeground(getDrawable(R.drawable.on));
    }
    public void pcWorkSound(){
        player.stop();
        player = MediaPlayer.create(this, R.raw.pc_work_sound);
        player.setVolume(0.1f,0.1f);
        player.setLooping(true);
        player.start();
    }

    String[] diskList;
    //выбор диска
    public void changeCD(View view){
        DiskChangeAdapter adapter;
        diskList = new PlayerData(this).DiskSoftList;
        if(diskList.length == 0){
            diskList = StringArrayWork.add(diskList,words.get("You have not purchased any discs"));
            adapter = new DiskChangeAdapter(this,diskList);
            adapter.imageEnabled = false;
        }else {
            adapter = new DiskChangeAdapter(this,diskList);
        }
        ((TextView)view).setText(words.get("Change disk")+":");
        PopupListView listView = new PopupListView((TextView) view,MainActivity.this);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            DiskInDrive = diskList[position];
            listView.dismiss();
        });
        listView.setAdapter(adapter);
        listView.show();
    }
    // экран смерти
    public TextView DeadScreen;
    public void blackDeadScreen(String[] errorCode){
        greeting.setVisibility(View.GONE);
        int count = 1;
        DeadScreen = new TextView(this);
        DeadScreen.setPadding(30, 30, 30, 30);
        DeadScreen.setTextSize(27);
        DeadScreen.setTextColor(Color.WHITE);
        DeadScreen.setTypeface(font);
        DeadScreen.setGravity(-1);

        StringBuilder text2 = new StringBuilder();
        for (String code : errorCode) {
            text2.append(count).append(". ").append(code).append("\n");
            count++;
        }
        String str =  text2.toString();
        DeadScreen.setText(str);
        layout.addView(DeadScreen, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }
    //перезагрузка пк
    public void reloadPc(){
        pcWorkOff();
        player.release();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()->{
                    pcWorkOn();
                });
            }
        };
        timer.schedule(task,1000);
    }

    @Override
    protected void onStop() {
        if(player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
        }
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
        PcWorkStatus = 0;
        if(DeadScreen != null){
            DeadScreen.setVisibility(View.GONE);
        }
        for(int i = programArrayList.size()-1;i>=0;i--){
            Program program = programArrayList.get(i);
            program.closeProgram(0);
        }
        programArrayList.clear();
        powerButton.setForeground(getDrawable(R.drawable.off));
        powerButton.setClickable(true);
    }
}