package com.niksaen.pcsim;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.niksaen.pcsim.program.Benchmark;
import com.niksaen.pcsim.program.Browser;
import com.niksaen.pcsim.program.CPU_Tweaker;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.TemperatureViewer;
import com.niksaen.pcsim.program.ViewPowerSupplyLoad;
import com.niksaen.pcsim.program.checkIron.CheckIron;
import com.niksaen.pcsim.program.fileManager.FileManager;
import com.niksaen.pcsim.program.GPU_Overclocking;
import com.niksaen.pcsim.program.musicplayer.MusicPlayer;
import com.niksaen.pcsim.program.notepad.Notepad;
import com.niksaen.pcsim.program.paint.Paint;
import com.niksaen.pcsim.program.RAM_Overclocking;
import com.niksaen.pcsim.program.videoplayer.VideoPlayer;
import com.niksaen.pcsim.program.styleSettings.StyleSettings;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    Button button1,button2;
    LinearLayout toolbar;
    TextView greeting;
    RecyclerView desktop;

    PcParametersSave pcParametersSave;
    StyleSave styleSave;
    ConstraintLayout layout;

    Typeface font;
    int style = Typeface.BOLD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        font = Typeface.createFromAsset(getAssets(), "fonts/pixelFont.ttf");

        //init classes
        pcParametersSave = new PcParametersSave(this);
        styleSave = new StyleSave(this);

        initView();
        viewStyle();

        button1.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(),IronActivity.class);
            startActivity(intent);
            finish();
        });

        final int[] button2Click = {0};
        button2.setOnClickListener(v -> {
            if(button2Click[0] == 0) {
                if (pcParametersSave.getPcWork()) {
                    if (pcParametersSave.currentCpuTemperature() <= pcParametersSave.maxCpuTemperature()) {
                        if (pcParametersSave.psuEnoughPower()) {
                            button2.setText("ВКЛ");
                            button2.setTextColor(Color.GREEN);
                            pcWorkOn();
                        } else {
                            if(pcParametersSave.PSU != null) {
                                if (pcParametersSave.PSU.get("Защита").equals("нет")) {
                                    pcParametersSave.setPsu(pcParametersSave.Psu + "[Сломано]", null);
                                } else {
                                    button2.setTextColor(Color.BLUE);
                                }
                            }
                            button2.setTextColor(Color.RED);
                        }
                    }
                    else{
                        pcParametersSave.setCpu(pcParametersSave.Cpu+"[Сломано]",null);
                        button2.setTextColor(Color.CYAN);
                        button2.setTextColor(Color.RED);
                    }
                }
                button2Click[0] = 1;
            }
            else{
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {
                            pcWorkOff();
                            button2Click[0] = 0;
                        });
                    }
                };
                timer.schedule(timerTask,450);
            }
        });

        if(Language.getLanguage(this).equals("")){
            Language.ChangeLanguage(this);
        }
    }

    void initView(){
        layout = findViewById(R.id.monitor);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.on_off);
        layout = findViewById(R.id.monitor);
        toolbar =findViewById(R.id.toolbar);
        greeting = findViewById(R.id.greeting);
        desktop = findViewById(R.id.desktop);
    }

    private void updateDesktop(){

    }

    void viewStyle(){
        button1.setTypeface(font,style);
        button2.setTypeface(font,style);
        button2.setTextColor(Color.RED);
        greeting.setTypeface(font,style);
    }

    void pcWorkOn(){
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
                    test();
                });
            }
        };
        if(pcParametersSave.getMainDiskType().equals("SSD")){
            timer.schedule(timerTask,300);
        }
        else{
            timer.schedule(timerTask,900);
        }
    }

    ArrayList<Program> programArrayList = new ArrayList<>();
    public void pcWorkOff(){
        button2.setText("ВЫКЛ");
        button2.setTextColor(Color.RED);
        layout.setBackgroundColor(Color.BLACK);
        toolbar.setVisibility(View.GONE);
        desktop.setVisibility(View.GONE);

        for(Program program:programArrayList){
            if(program.status != -1) {
                program.closeProgram();
            }
        }
        programArrayList.clear();
    }

    private void test(){
        Spinner spinner = findViewById(R.id.apps);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:{
                        Browser program = new Browser(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 2:{
                        Benchmark benchmark = new Benchmark(getBaseContext(),pcParametersSave,layout,MainActivity.this);
                        programArrayList.add(benchmark);
                        benchmark.openProgram();
                        break;
                    }
                    case 3:{
                        CheckIron program = new CheckIron(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 4:{
                        CPU_Tweaker program = new CPU_Tweaker(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 5:{
                        GPU_Overclocking program = new GPU_Overclocking(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 6:{
                        RAM_Overclocking program = new RAM_Overclocking(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 7:{
                        FileManager program = new FileManager(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 8:{
                        Paint program = new Paint(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram(null);
                        break;
                    }
                    case 9:{
                        Notepad program = new Notepad(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram("");
                        break;
                    }
                    case 10:{
                        VideoPlayer program = new VideoPlayer(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram(null);
                        break;
                    }
                    case 11:{
                        MusicPlayer program = new MusicPlayer(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram(null,null);
                        break;
                    }
                    case 12:{
                        StyleSettings program = new StyleSettings(getBaseContext(),new View[]{layout,toolbar},pcParametersSave);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 13:{
                        TemperatureViewer program = new TemperatureViewer(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                    case 14:{
                        ViewPowerSupplyLoad program = new ViewPowerSupplyLoad(getBaseContext(),pcParametersSave,layout);
                        programArrayList.add(program);
                        program.openProgram();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}