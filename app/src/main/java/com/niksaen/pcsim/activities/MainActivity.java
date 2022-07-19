package com.niksaen.pcsim.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.niksaen.pcsim.classes.dialogs.Dialog;
import com.niksaen.pcsim.databinding.ActivityMainBinding;
import com.niksaen.pcsim.os.LiriOS;
import com.niksaen.pcsim.os.MakOS;
import com.niksaen.pcsim.os.NapiOS;
import com.niksaen.pcsim.os.cmd.CMD;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.taskManager.TaskManager;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.PlayerData;
import com.niksaen.pcsim.save.Settings;
import com.niksaen.pcsim.save.StyleSave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import me.toptas.fancyshowcase.listener.DismissListener;

public class MainActivity extends AppCompatActivity{

    public LinearLayout toolbar;
    public LinearLayout startMenu;
    public TextView greeting,startMenuTitle;
    public RecyclerView desktop;
    public RecyclerView appList;
    public ListView allAppList;
    private View caseView;
    public Button startMenuOpener;

    public PcParametersSave pcParametersSave;
    public StyleSave styleSave;
    public ConstraintLayout layout;
    public String DiskInDrive;
    public PlayerData playerData;

    public MediaPlayer player;
    public Typeface font;
    int style = Typeface.BOLD;
    ActivityMainBinding binding;
    private boolean isWork = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");

        getTranslate();
        pcParametersSave = new PcParametersSave(this);
        styleSave = new StyleSave(this);
        playerData = new PlayerData(this);

        initView();
        viewStyle();
        initDrawer();
        player = new MediaPlayer();
        buttonPC();
        if(!playerData.tutorialComplete){
            Dialog dialog = new Dialog(this);
            dialog.setTitleVisible(true);
            dialog.setTitleText(words.get("Education"));
            dialog.setText(words.get("Skip tutorial?"));
            dialog.setCancelable(false);
            dialog.setButtonOkVisible(true);
            dialog.setButtonCancelVisible(true);
            dialog.setButtonOkText(words.get("Yes"));
            dialog.setButtonCancelText(words.get("No"));
            dialog.setButtonCancelOnClick(view -> {
                new FancyShowCaseView.Builder(MainActivity.this)
                    .focusOn(binding.drawer)
                    .title(words.get("There is a side menu here.\nTo open it, drag to the right."))
                    .titleSize(35,2)
                    .typeface(font)
                    .backgroundColor(getColor(R.color.tutorialBack))
                    .dismissListener(new DismissListener() {
                        @Override
                        public void onDismiss(@Nullable String s) {
                            binding.getRoot().openDrawer(binding.drawer);
                            new FancyShowCaseView.Builder(MainActivity.this)
                                    .delay(100)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .focusCircleAtPosition(
                                            binding.drawer.getChildAt(1).getLeft()+binding.drawer.getChildAt(1).getWidth()/3,
                                            binding.drawer.getChildAt(1).getTop()+binding.drawer.getChildAt(1).getHeight()/2,
                                            100
                                    )
                                    .title(words.get("This is a PC parts store."))
                                    .titleSize(35,2)
                                    .typeface(font)
                                    .backgroundColor(getColor(R.color.tutorialBack))
                                    .dismissListener(new DismissListener() {
                                        @Override
                                        public void onDismiss(@Nullable String s) {
                                            new FancyShowCaseView.Builder(MainActivity.this)
                                                    .delay(100)
                                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                                    .focusCircleAtPosition(
                                                            binding.drawer.getChildAt(2).getLeft()+binding.drawer.getChildAt(2).getWidth()/3,
                                                            binding.drawer.getChildAt(2).getTop()+binding.drawer.getChildAt(2).getHeight()/2,
                                                            100
                                                    )
                                                    .title(words.get("Here you can build a PC."))
                                                    .titleSize(35,2)
                                                    .typeface(font)
                                                    .backgroundColor(getColor(R.color.tutorialBack))
                                                    .dismissListener(new DismissListener() {
                                                        @Override
                                                        public void onDismiss(@Nullable String s) {
                                                            new FancyShowCaseView.Builder(MainActivity.this)
                                                                    .delay(100)
                                                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                                                    .focusCircleAtPosition(
                                                                            binding.drawer.getChildAt(4).getLeft()+binding.drawer.getChildAt(4).getWidth()/3,
                                                                            binding.drawer.getChildAt(4).getTop()+binding.drawer.getChildAt(4).getHeight()/2,
                                                                            100
                                                                    )
                                                                    .title(words.get("Additional guides for the game can be found here."))
                                                                    .titleSize(35,2)
                                                                    .typeface(font)
                                                                    .backgroundColor(getColor(R.color.tutorialBack))
                                                                    .dismissListener(new DismissListener() {
                                                                        @Override
                                                                        public void onDismiss(@Nullable String s) {
                                                                            new FancyShowCaseView.Builder(MainActivity.this)
                                                                                    .delay(100)
                                                                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                                                                    .focusCircleAtPosition(
                                                                                            binding.drawer.getChildAt(1).getLeft()+binding.drawer.getChildAt(1).getWidth()/3,
                                                                                            binding.drawer.getChildAt(1).getTop()+binding.drawer.getChildAt(1).getHeight()/2,
                                                                                            100
                                                                                    )
                                                                                    .title(words.get("Click here to go to the store."))
                                                                                    .titleSize(35,2)
                                                                                    .typeface(font)
                                                                                    .backgroundColor(getColor(R.color.tutorialBack))
                                                                                    .dismissListener(new DismissListener() {
                                                                                        @Override
                                                                                        public void onDismiss(@Nullable String s) {
                                                                                            playerData.tutorialComplete = true;
                                                                                            playerData.setAllData();
                                                                                            Intent intent = new Intent(MainActivity.this, MainShopActivity.class);
                                                                                            startActivity(intent);
                                                                                            finish();
                                                                                        }

                                                                                        @Override
                                                                                        public void onSkipped(@Nullable String s) {

                                                                                        }
                                                                                    })
                                                                                    .build()
                                                                                    .show();
                                                                        }

                                                                        @Override
                                                                        public void onSkipped(@Nullable String s) {

                                                                        }
                                                                    })
                                                                    .build()
                                                                    .show();
                                                        }

                                                        @Override
                                                        public void onSkipped(@Nullable String s) {

                                                        }
                                                    })
                                                    .build()
                                                    .show();
                                        }

                                        @Override
                                        public void onSkipped(@Nullable String s) {

                                        }
                                    })
                                    .build()
                                    .show();
                        }

                        @Override
                        public void onSkipped(@Nullable String s) {

                        }
                    })
                    .build()
                    .show();
                dialog.dismiss();
            });
            dialog.setButtonOkOnClick(view -> {
                playerData.tutorialComplete = true;
                playerData.tutorialShopComplete = true;
                playerData.tutorialEnd = true;
                playerData.setAllData();
                dialog.dismiss();
            });
            dialog.create();
            dialog.show();
        }
        if(playerData.tutorialShopComplete && playerData.tutorialComplete && !playerData.tutorialEnd){
            new FancyShowCaseView.Builder(MainActivity.this)
                    .delay(100)
                    .title(words.get("This was a basic guide to the game.\nHave a good game!"))
                    .titleSize(35,2)
                    .typeface(font)
                    .backgroundColor(getColor(R.color.tutorialBack))
                    .dismissListener(new DismissListener() {
                        @Override
                        public void onDismiss(@Nullable String s) {
                            playerData.tutorialEnd = true;
                            playerData.setAllData();
                        }

                        @Override
                        public void onSkipped(@Nullable String s) {

                        }
                    })
                    .build()
                    .show();
        }

        startMenuOpener.setOnLongClickListener(v -> {
            CMD cmd = new CMD(MainActivity.this);
            cmd.setType(CMD.WINDOW);
            cmd.openProgram();
            return false;
        });
    }

    void initView(){
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
            binding.onOff.setClickable(false);
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
        DrawerAdapter drawerAdapter = new DrawerAdapter(this,menuList);
        drawerAdapter.BackgroundColor = Color.parseColor("#111111");
        drawerAdapter.TextColor = Color.parseColor("#FFFFFF");
        binding.drawer.setAdapter(drawerAdapter);

        binding.drawer.setOnItemClickListener((parent, view, position, id) -> {
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
        binding.onOff.setOnClickListener(v -> {
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
        binding.onOff.setClickable(false);
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
                    binding.onOff.setForeground(getDrawable(R.drawable.off));
                    binding.onOff.setClickable(true);
                    player.stop();
                    isWork = false;
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
        isWork = true;
        if(StringArrayWork.ArrayListToString(apps).contains("NapiOS,")){
            NapiOS os = new NapiOS(this);
            os.openProgram();
        }else if(StringArrayWork.ArrayListToString(apps).contains("LiriOS,")){
            LiriOS os = new LiriOS(this);
            os.openProgram();
        }else if(StringArrayWork.ArrayListToString(apps).contains(MakOS.TITLE+",")){
            MakOS makOS = new MakOS(this);
            makOS.openProgram();
        }
        else{
            CMD cmd = new CMD(this);
            cmd.openProgram();
        }
        pcWorkSound();
        PcWorkStatus = 1;
        binding.onOff.setForeground(getDrawable(R.drawable.on));
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
        diskList = playerData.DiskSoftList;
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
            if(DiskInDrive.startsWith(MakOS.TITLE)) {
                if (isWork) {
                    programArrayList.get(0).closeProgram(0);
                    CMD cmd = new CMD(this);
                    cmd.setType(CMD.SEMI_AUTO_OS);
                    cmd.commandList = new String[]{"cmd.print: test"};
                    if (pcParametersSave.Cpu.startsWith("Jntel")) {
                        int drivePos = 0;
                        for (int i = 0; i < 6; i++) {
                            HashMap<String, String> disk = pcParametersSave.getDrive(i);
                            if (disk != null) {
                                drivePos = i;
                                break;
                            }
                        }
                        cmd.commandList = new String[]{
                                "pc.storage.clear:"+drivePos,
                                "driver.prepare.select_storage_slot:" + drivePos,
                                "driver.install.all",
                                "ifd.prepare.get_disk",
                                "ifd.prepare.select_storage_slot:"+drivePos,
                                "ifd.install",
                                "installer.prepare.select_storage_slot:"+drivePos,
                                "installer.install: App Downloader"
                        };
                    } else {
                        cmd.commandList = new String[]{
                                "cmd.error: "+words.get("You current CPU is not supported")
                        };
                    }
                    cmd.openProgram();
                }
            }
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
        binding.onOff.setForeground(getDrawable(R.drawable.off));
        binding.onOff.setClickable(true);
    }
}