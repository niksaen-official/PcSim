package com.niksaen.pcsim.program;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Benchmark {

    LayoutInflater inflater;
    Typeface font;
    View mainWindow;

    Context context;
    ConstraintLayout layout;
    PcParametersSave pcParametersSave;
    Activity activity;

    TextView title,cpu_bench,ram_bench,gpu_bench,data_bench,all_bench;
    ConstraintLayout content;
    Button start_bench,button,button2,button3;
    int button_2_1,button_2_2,button_1,button_3;
    ProgressBar progressBar;
    Timer timer;

    StyleSave styleSave;

    public Benchmark(Context context, PcParametersSave pcParametersSave, ConstraintLayout layout, Activity activity){
        inflater = LayoutInflater.from(context);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        this.context = context;
        this.layout = layout;
        this.activity = activity;
        this.pcParametersSave = pcParametersSave;
        mainWindow = inflater.inflate(R.layout.program_benchmark,null);
        styleSave = new StyleSave(context);
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        getLanguage();
        style();
    }

    // get language
    private HashMap<String,String> words;
    private void getLanguage(){
        TypeToken<HashMap<String,String>> typeToken = new TypeToken<HashMap<String,String>>(){};
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),typeToken.getType());
    }

    public void openProgram(){

        int[] buttonClicks = {0};

        button3.setOnClickListener(v -> {
            closeProgram();
        });
        button2.setOnClickListener(v->{
            if(buttonClicks[0] == 0){
                mainWindow.setScaleX(0.6f);
                mainWindow.setScaleY(0.6f);
                PortableView view = new PortableView(mainWindow);
                v.setBackgroundResource(button_2_1);
                buttonClicks[0]=1;
            }else{
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setOnTouchListener(null);
                mainWindow.setX(0);
                mainWindow.setY(0);
                v.setBackgroundResource(button_2_2);
                buttonClicks[0]=0;
            }
        });

        start_bench.setOnClickListener(v -> {
            v.setClickable(false);
            v.setVisibility(View.GONE);
            bench();
        });

        layout.addView(mainWindow, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
    }
    public void closeProgram(){
        mainWindow.setVisibility(View.GONE);
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        mainWindow = null;
    }

    private int getCpuBench(){
        int core = Integer.parseInt(pcParametersSave.CPU.get("Кол-во ядер"));
        int streams = Integer.parseInt(pcParametersSave.CPU.get("Кол-во потоков"));
        int technical_process = Integer.parseInt(pcParametersSave.CPU.get("Техпроцесс"));
        double cache = Double.parseDouble(pcParametersSave.CPU.get("Кэш"));
        int frequency = Integer.parseInt(pcParametersSave.CPU.get("Частота"));
        return (int) (((core*frequency*streams)/(technical_process/3)+(3000*cache))/2);
    }

    private int getRamBench(){
        int bench_ram1=0,bench_ram2=0,bench_ram3=0,bench_ram4=0;
        if(pcParametersSave.RAM1 != null && pcParametersSave.ramValid(pcParametersSave.RAM1)){
            int volume = Integer.parseInt(pcParametersSave.RAM1.get("Объём"));
            int frequency = Integer.parseInt(pcParametersSave.RAM1.get("Частота"));
            int throughput = Integer.parseInt(pcParametersSave.RAM1.get("Пропускная способность"));
            bench_ram1 = (volume*frequency+throughput)/3;
        }
        if(pcParametersSave.RAM2 != null && pcParametersSave.ramValid(pcParametersSave.RAM2)){
            int volume = Integer.parseInt(pcParametersSave.RAM2.get("Объём"));
            int frequency = Integer.parseInt(pcParametersSave.RAM2.get("Частота"));
            int throughput = Integer.parseInt(pcParametersSave.RAM2.get("Пропускная способность"));
            bench_ram2 = (volume*frequency+throughput)/3;
        }
        if(pcParametersSave.RAM3 != null && pcParametersSave.ramValid(pcParametersSave.RAM3)){
            int volume = Integer.parseInt(pcParametersSave.RAM3.get("Объём"));
            int frequency = Integer.parseInt(pcParametersSave.RAM3.get("Частота"));
            int throughput = Integer.parseInt(pcParametersSave.RAM3.get("Пропускная способность"));
            bench_ram3 = (volume*frequency+throughput)/3;
        }
        if(pcParametersSave.RAM4 != null && pcParametersSave.ramValid(pcParametersSave.RAM4)){
            int volume = Integer.parseInt(pcParametersSave.RAM4.get("Объём"));
            int frequency = Integer.parseInt(pcParametersSave.RAM4.get("Частота"));
            int throughput = Integer.parseInt(pcParametersSave.RAM4.get("Пропускная способность"));
            bench_ram4 = (volume*frequency+throughput)/3;
        }
        return  (int) (((bench_ram1+bench_ram2+bench_ram3+bench_ram4)*pcParametersSave.ramCanals)/4);
    }

    private int getGpuBench(){
        double gpu1_bench=0,gpu2_bench=0,gpu_i_bench=0;
        if(Objects.equals(pcParametersSave.CPU.get("Графическое ядро"), "+")){
            int model=gpu(pcParametersSave.CPU.get("Модель"));
            int frequency = Integer.parseInt(pcParametersSave.CPU.get("Частота GPU"));
            gpu_i_bench = (int) (model+(frequency*1.5));
        }
        if(pcParametersSave.GPU1 != null){
            int model = gpu(pcParametersSave.GPU1.get( "Графический процессор"));
            int count_chips = Integer.parseInt(pcParametersSave.GPU1.get("Кол-во видеочипов"));
            int frequency = Integer.parseInt(pcParametersSave.GPU1.get("Частота"));
            int volume = Integer.parseInt(pcParametersSave.GPU1.get("Объём видеопамяти"));
            Double throughput = Double.parseDouble(pcParametersSave.GPU1.get("Пропускная способность"));
            gpu1_bench = count_chips*frequency*volume*(throughput/4)+(model*2)/4;
        }
        if(pcParametersSave.GPU2 != null){
            int model = gpu(pcParametersSave.GPU2.get( "Графический процессор"));
            int count_chips = Integer.parseInt(pcParametersSave.GPU2.get("Кол-во видеочипов"));
            int frequency = Integer.parseInt(pcParametersSave.GPU2.get("Частота"));
            int volume = Integer.parseInt(pcParametersSave.GPU2.get("Объём видеопамяти"));
            double throughput = Double.parseDouble(pcParametersSave.GPU2.get("Пропускная способность"));
            gpu2_bench = count_chips*frequency*volume*(throughput/4)+(model*2)/4;
        }
        return (int)(gpu1_bench+gpu2_bench+gpu_i_bench);
    }

    private int gpu(String gpuName){
        switch (gpuName){
            case "Heforce GT 710": return 7143;
            case "Sadeon HD5450": return 4345;
            case "Sadeon S7": return 5690;
            case "Sadeon S5": return 4902;
            case "Jntel HD Graphics 610": return 3420;
            case "Jntel HD Graphics 630": return 4205;
            case "Jntel HD Graphics 510": return 2403;
            case "Jntel HD Graphics 530": return 3023;
            default: return 0;
        }
    }

    private int getDataBench(){
        HashMap<String,String>[] data = new HashMap[]{pcParametersSave.DATA1, pcParametersSave.DATA2, pcParametersSave.DATA3, pcParametersSave.DATA4, pcParametersSave.DATA5, pcParametersSave.DATA6};
        int data_bench = 0;
        for(int i=0;i<6;i++){
            if(data[i] != null){
                int speed;
                if(data[i].get("Тип").equals("SSD")){
                    speed = 14;
                }
                else{
                    speed = 6;
                }
                data_bench+=(speed*Integer.parseInt(data[i].get("Объём")));
            }
        }
        return data_bench;
    }

    private int[] bench = {0,0,0,0,0};
    private void bench(){

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
               activity.runOnUiThread(() -> {
                   if(bench[4]<=getCpuBench()+getRamBench()+getGpuBench()+getDataBench()) {
                       if (bench[0] <= getCpuBench()) {
                           cpu_bench.setText("CPU: " + bench[0]);
                           bench[0]++;
                           bench[4]++;
                       } else if (bench[1] <= getRamBench()) {
                           ram_bench.setText("RAM: " + bench[1]);
                           bench[1]++;
                           bench[4]++;
                       } else if (bench[2] <= getGpuBench()) {
                           gpu_bench.setText("GPU: " + bench[2]);
                           bench[2]++;
                           bench[4]++;
                       } else if (bench[3] <= getDataBench()) {
                           data_bench.setText("DATA: " + bench[3]);
                           bench[3]++;
                           bench[4]++;
                       }
                       progressBar.setProgress(bench[4]);
                       all_bench.setText(String.valueOf(bench[4]));
                   }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1);
    }

    private void style(){
        button_1 = styleSave.CloseButtonImageRes;
        button_2_1 = styleSave.FullScreenMode1ImageRes;
        button_2_2 =  styleSave.FullScreenMode2ImageRes;
        button_3 =  styleSave.RollUpButtonImageRes;

        title = mainWindow.findViewById(R.id.title);
        cpu_bench = mainWindow.findViewById(R.id.cpu);
        ram_bench = mainWindow.findViewById(R.id.ram);
        gpu_bench = mainWindow.findViewById(R.id.gpu);
        data_bench = mainWindow.findViewById(R.id.data);
        all_bench = mainWindow.findViewById(R.id.all);
        start_bench = mainWindow.findViewById(R.id.start);
        progressBar = mainWindow.findViewById(R.id.progressBar);
        content = mainWindow.findViewById(R.id.content);
        button = mainWindow.findViewById(R.id.roll_up);
        button2 = mainWindow.findViewById(R.id.fullscreenMode);
        button3 = mainWindow.findViewById(R.id.close);

        //style
        title.setTypeface(font,Typeface.BOLD);
        title.setTextColor(styleSave.TitleColor);
        cpu_bench.setTypeface(font,Typeface.BOLD);
        cpu_bench.setTextColor(styleSave.TextColor);
        ram_bench.setTypeface(font,Typeface.BOLD);
        ram_bench.setTextColor(styleSave.TextColor);
        gpu_bench.setTypeface(font,Typeface.BOLD);
        gpu_bench.setTextColor(styleSave.TextColor);
        data_bench.setTypeface(font,Typeface.BOLD);
        data_bench.setTextColor(styleSave.TextColor);
        all_bench.setTypeface(font,Typeface.BOLD);
        all_bench.setTextColor(styleSave.TextColor);

        start_bench.setTypeface(font,Typeface.BOLD);
        start_bench.setBackgroundColor(styleSave.ThemeColor2);
        start_bench.setTextColor(styleSave.TextButtonColor);

        content.setBackgroundColor(styleSave.ThemeColor1);
        progressBar.setProgressDrawable(context.getDrawable(styleSave.ProgressBarResource));

        //set button image and background
        button.setBackgroundResource(button_3);
        button2.setBackgroundResource(button_2_2);
        button3.setBackgroundResource(button_1);
        start_bench.setText(words.get("Start"));
    }
}
