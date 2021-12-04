package com.niksaen.pcsim.activites;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
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
import com.niksaen.pcsim.classes.ErrorCodeList;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.classes.adapters.DesktopAdapter;
import com.niksaen.pcsim.classes.adapters.DrawerAdapter;
import com.niksaen.pcsim.classes.adapters.StartMenuAdapter;
import com.niksaen.pcsim.classes.adapters.ToolbarAdapter;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.ProgramListAndData;
import com.niksaen.pcsim.program.taskManager.TaskManager;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
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
    TextView greeting,startMenuTitle;
    RecyclerView desktop;
    RecyclerView appList;
    ListView allAppList;
    private View caseView;

    public PcParametersSave pcParametersSave;
    public StyleSave styleSave;
    public ConstraintLayout layout;

    private MediaPlayer player;
    public Typeface font;
    int style = Typeface.BOLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");

        if(new Settings(this).Language.equals("")){
            Language.ChangeLanguage(this);
        }else {
            getLanguage();
        }
        //init classes
        pcParametersSave = new PcParametersSave(this);
        styleSave = new StyleSave(this);

        initView();
        viewStyle();
        initDrawer();

        player = new MediaPlayer();
        buttonPC();
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
    private void getLanguage(){
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
                words.get("About me")
        };
        ListView drawer = findViewById(R.id.drawer);
        DrawerAdapter drawerAdapter = new DrawerAdapter(this,menuList);
        drawerAdapter.BackgroundColor = Color.parseColor("#111111");
        drawerAdapter.TextColor = Color.parseColor("#FFFFFF");
        drawer.setAdapter(drawerAdapter);

        drawer.setOnItemClickListener((parent, view, position, id) -> {
           if(position == 1){
               intent = new Intent(MainActivity.this, MainShopActivity.class);
               player.pause();
               startActivity(intent);
           }
            if(position == 2){
                intent = new Intent(MainActivity.this,IronActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public String[] apps;
    public void updateAppList(){
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
            updateAppList();
            startMenu.setVisibility(View.VISIBLE);
            updateStartMenu();
        }
        else{
            startMenu.setVisibility(View.GONE);
        }
    }
    private void updateStartMenu(){
        ProgramListAndData program = new ProgramListAndData();
        program.initHashMap(this);
        startMenu.setBackgroundColor(styleSave.StartMenuColor);
        startMenuTitle.setTextColor(styleSave.StartMenuTextColor);
        startMenuTitle.setTypeface(font,Typeface.BOLD);
        startMenuTitle.setText(words.get("Start"));
        StartMenuAdapter menuAdapter = new StartMenuAdapter(this, 0, apps);
        menuAdapter.setMainActivity(this);
        allAppList.setAdapter(menuAdapter);
        allAppList.setOnItemClickListener((parent, view, position, id) -> program.programHashMap.get(menuAdapter.getItem(position)).openProgram());
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
                            powerButton.setForeground(getDrawable(R.drawable.on));
                            pcWorkOn();
                        } else {
                            if(pcParametersSave.PSU != null) {
                                if (pcParametersSave.PSU.get("Защита").equals("-")) {
                                    pcParametersSave.setPsu(pcParametersSave.Psu + "[Сломано]", null);
                                    blackDeadScreen(new String[]{"0xBB0004"});
                                }
                            }
                            powerButton.setForeground(getDrawable(R.drawable.off));
                        }
                    }
                    else{
                        pcParametersSave.setCpu(pcParametersSave.Cpu+"[Сломано]",null);
                        blackDeadScreen(new String[]{"0xBB0002"});
                        powerButton.setForeground(getDrawable(R.drawable.off));
                    }
                }
                else{
                    if(pcParametersSave.Cooler == null){
                        blackDeadScreen(new String[]{"0xBB0003"});
                    }
                    if(pcParametersSave.Cooler != null && pcParametersSave.Cooler.contains("[Сломано]")){
                        blackDeadScreen(new String[]{"0xBB0004"});
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
        player.stop();
        player = MediaPlayer.create(this, R.raw.pc_work_end_sound);
        player.setLooping(false);
        player.start();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() ->{
                    PcWorkStatus = 0;
                    powerButton.setForeground(getDrawable(R.drawable.off));
                    layout.setBackgroundColor(Color.BLACK);
                    toolbar.setVisibility(View.GONE);
                    desktop.setVisibility(View.GONE);
                    startMenu.setVisibility(View.GONE);
                    for(Program program:programArrayList){
                        if(program.status != -1) {
                            program.closeProgram(0);
                        }
                    }
                    programArrayList.clear();
                } );
            }
        };
        if(pcParametersSave.getMainDiskType().equals("SSD")){
            timer.schedule(timerTask,1500);
        }
        else{
            timer.schedule(timerTask,3000);
        }
    }
    // включение пк
    public void pcWorkOn(){
        player = MediaPlayer.create(this, R.raw.pc_work_start_sound);
        player.setVolume(0.1f,0.1f);
        player.setLooping(false);
        player.start();

        PcWorkStatus = 1;
        styleSave.getStyle();
        greeting.setVisibility(View.VISIBLE);
        greeting.setTextColor(styleSave.GreetingColor);
        greeting.setText(styleSave.Greeting);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    greeting.setVisibility(View.GONE);
                    layout.setBackgroundResource(styleSave.BackgroundResource);
                    desktop.setVisibility(View.VISIBLE);
                    toolbar.setBackgroundColor(styleSave.ToolbarColor);
                    toolbar.setVisibility(View.VISIBLE);
                    updateDesktop();
                    updateAppList();
                    pcWorkSound();
                });
            }
        };
        if(pcParametersSave.getMainDiskType().equals("SSD")){
            timer.schedule(timerTask,1500);
        }
        else{
            timer.schedule(timerTask,3000);
        }
    }
    private void pcWorkSound(){
        player.stop();
        player = MediaPlayer.create(this, R.raw.pc_work_sound);
        player.setLooping(true);
        player.start();
    }
    // экран смерти
    public void blackDeadScreen(String[] errorCode){
        pcWorkOff();
        int count = 1;
        TextView textView = new TextView(this);
        textView.setPadding(30, 30, 30, 30);
        textView.setTextSize(27);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(font);
        textView.setGravity(-1);

        if(errorCode.length>1) {
            StringBuilder text = new StringBuilder("Fatal error\n");
            StringBuilder text2 = new StringBuilder("Error code\n");
            for (String code : errorCode) {
                text.append(count).append(". ").append(ErrorCodeList.ErrorCodeText.get(code)).append("\n");
                text2.append(count).append(". ").append(code).append("\n");
                count++;
            }
            String str = text.toString() + text2.toString();
            textView.setText(str);
        }
        else{
            String str = "Fatal error\n"+ErrorCodeList.ErrorCodeText.get(errorCode[0])+"\nError code: "+errorCode[0];
            textView.setText(str);
        }
        layout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        new Handler().postDelayed(() -> textView.setVisibility(View.GONE),1200);
    }
    //перезагрузка пк
    public void reloadPc(){
        pcWorkOff();
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
    protected void onResume() {
        if(player != null){
            player.start();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        if(player.isPlaying()){
            player.stop();
        }
        super.onStop();
    }
}