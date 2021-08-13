package com.niksaen.pcsim;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.niksaen.pcsim.classes.BlackDeadScreen;
import com.niksaen.pcsim.classes.DesktopAdapter;
import com.niksaen.pcsim.program.Benchmark;
import com.niksaen.pcsim.program.Browser;
import com.niksaen.pcsim.program.CPU_Tweaker;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.TemperatureViewer;
import com.niksaen.pcsim.program.ViewPowerSupplyLoad;
import com.niksaen.pcsim.program.deviceManager.DeviceManager;
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
    public LinearLayout toolbar;
    TextView greeting;
    RecyclerView desktop;

    public PcParametersSave pcParametersSave;
    StyleSave styleSave;
    public ConstraintLayout layout;

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
        buttonPC();

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
        String[] apps = Program.programList;
        desktop.setLayoutManager(new GridLayoutManager(getBaseContext(),7));
        desktop.setAdapter(new DesktopAdapter(this,apps));
    }

    void viewStyle(){
        button1.setTypeface(font,style);
        button2.setTypeface(font,style);
        button2.setTextColor(Color.RED);
        greeting.setTypeface(font,style);
    }

    //список запущеных програм
    public ArrayList<Program> programArrayList = new ArrayList<>();

    //кнопка питания пк
    private void buttonPC(){
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
                                    blackDeadScreen(new String[]{"0xBB0004"});
                                } else {
                                    button2.setTextColor(Color.BLUE);
                                }
                            }
                            button2.setTextColor(Color.RED);
                        }
                    }
                    else{
                        pcParametersSave.setCpu(pcParametersSave.Cpu+"[Сломано]",null);
                        blackDeadScreen(new String[]{"0xBB0002"});
                        button2.setTextColor(Color.RED);
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
    }
    // выключение пк
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
    // включение пк
    public void pcWorkOn(){
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
                text.append(count).append(". ").append(BlackDeadScreen.ErrorCodeText.get(code)).append("\n");
                text2.append(count).append(". ").append(code).append("\n");
                count++;
            }
            String str = text.toString() + text2.toString();
            textView.setText(str);
            layout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }
        else{
            String str = "Fatal error\n"+BlackDeadScreen.ErrorCodeText.get(errorCode[0])+"\nError code: "+errorCode[0];
            textView.setText(str);
            layout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }
        new Handler().postDelayed(() -> textView.setVisibility(View.GONE),1200);
    }
}