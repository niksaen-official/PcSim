package com.niksaen.pcsim.program;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.activites.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.save.StyleSave;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Benchmark extends Program{
    MainActivity activity;

    TextView cpu_bench,ram_bench,gpu_bench,data_bench,all_bench;
    ConstraintLayout content;
    Button start_bench;
    ProgressBar progressBar;
    Timer timer;

    StyleSave styleSave;

    public Benchmark(MainActivity activity){
        super(activity);
        Title = "Benchmark";
        this.activity = activity;
        styleSave = activity.styleSave;
        ValueRam = new int[]{90,128};
        ValueVideoMemory = new int[]{200,324};
    }

    @Override
    public void closeProgram(int mode){
        super.closeProgram(mode);
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    public void initProgram(){
        mainWindow = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.program_benchmark,null);
        style();
        start_bench.setOnClickListener(v -> {
            v.setClickable(false);
            v.setVisibility(View.GONE);
            bench();
        });
        super.initProgram();
    }
    private int getCpuBench(){
        int core = Integer.parseInt(activity.pcParametersSave.CPU.get("Кол-во ядер"));
        int streams = Integer.parseInt(activity.pcParametersSave.CPU.get("Кол-во потоков"));
        int technical_process = Integer.parseInt(activity.pcParametersSave.CPU.get("Техпроцесс"));
        double cache = Double.parseDouble(activity.pcParametersSave.CPU.get("Кэш"));
        int frequency = Integer.parseInt(activity.pcParametersSave.CPU.get("Частота"));
        return (int) (((core*frequency*streams)/(technical_process/3)+(3000*cache))/2);
    }
    private int getRamBench(){
        int bench_ram1=0,bench_ram2=0,bench_ram3=0,bench_ram4=0;
        if(activity.pcParametersSave.RAM1 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM1)){
            int volume = Integer.parseInt(activity.pcParametersSave.RAM1.get("Объём"));
            int frequency = Integer.parseInt(activity.pcParametersSave.RAM1.get("Частота"));
            int throughput = Integer.parseInt(activity.pcParametersSave.RAM1.get("Пропускная способность"));
            bench_ram1 = (volume*frequency+throughput)/3;
        }
        if(activity.pcParametersSave.RAM2 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM2)){
            int volume = Integer.parseInt(activity.pcParametersSave.RAM2.get("Объём"));
            int frequency = Integer.parseInt(activity.pcParametersSave.RAM2.get("Частота"));
            int throughput = Integer.parseInt(activity.pcParametersSave.RAM2.get("Пропускная способность"));
            bench_ram2 = (volume*frequency+throughput)/3;
        }
        if(activity.pcParametersSave.RAM3 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM3)){
            int volume = Integer.parseInt(activity.pcParametersSave.RAM3.get("Объём"));
            int frequency = Integer.parseInt(activity.pcParametersSave.RAM3.get("Частота"));
            int throughput = Integer.parseInt(activity.pcParametersSave.RAM3.get("Пропускная способность"));
            bench_ram3 = (volume*frequency+throughput)/3;
        }
        if(activity.pcParametersSave.RAM4 != null && activity.pcParametersSave.ramValid(activity.pcParametersSave.RAM4)){
            int volume = Integer.parseInt(activity.pcParametersSave.RAM4.get("Объём"));
            int frequency = Integer.parseInt(activity.pcParametersSave.RAM4.get("Частота"));
            int throughput = Integer.parseInt(activity.pcParametersSave.RAM4.get("Пропускная способность"));
            bench_ram4 = (volume*frequency+throughput)/3;
        }
        return  (int) (((bench_ram1+bench_ram2+bench_ram3+bench_ram4)*activity.pcParametersSave.ramCanals)/4);
    }
    private int getGpuBench(){
        double gpu1_bench=0,gpu2_bench=0,gpu_i_bench=0;
        if(Objects.equals(activity.pcParametersSave.CPU.get("Графическое ядро"), "+")){
            int model=gpu(activity.pcParametersSave.CPU.get("Модель"));
            int frequency = Integer.parseInt(activity.pcParametersSave.CPU.get("Частота"));
            gpu_i_bench = (int) (model+(frequency*1.5));
        }
        if(activity.pcParametersSave.GPU1 != null){
            int model = gpu(activity.pcParametersSave.GPU1.get( "Графический процессор"));
            int count_chips = Integer.parseInt(activity.pcParametersSave.GPU1.get("Кол-во видеочипов"));
            int frequency = Integer.parseInt(activity.pcParametersSave.GPU1.get("Частота"));
            int volume = Integer.parseInt(activity.pcParametersSave.GPU1.get("Объём видеопамяти"));
            double throughput = Double.parseDouble(activity.pcParametersSave.GPU1.get("Пропускная способность"));
            gpu1_bench = count_chips*frequency*volume*(throughput/4)+(model*2)/4;
        }
        if(activity.pcParametersSave.GPU2 != null){
            int model = gpu(activity.pcParametersSave.GPU2.get( "Графический процессор"));
            int count_chips = Integer.parseInt(activity.pcParametersSave.GPU2.get("Кол-во видеочипов"));
            int frequency = Integer.parseInt(activity.pcParametersSave.GPU2.get("Частота"));
            int volume = Integer.parseInt(activity.pcParametersSave.GPU2.get("Объём видеопамяти"));
            double throughput = Double.parseDouble(activity.pcParametersSave.GPU2.get("Пропускная способность"));
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
        HashMap<String,String>[] data = new HashMap[]{activity.pcParametersSave.DATA1, activity.pcParametersSave.DATA2, activity.pcParametersSave.DATA3, activity.pcParametersSave.DATA4, activity.pcParametersSave.DATA5, activity.pcParametersSave.DATA6};
        int data_bench = 0;
        for(int i=0;i<6;i++){
            if(data[i] != null){
                int speed;
                if(data[i].get("Тип").equals("SSD")){
                    speed = 19;
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
                           gpu_bench.setText("Graphics card: " + bench[2]);
                           bench[2]++;
                           bench[4]++;
                       } else if (bench[3] <= getDataBench()) {
                           data_bench.setText("Storage device: " + bench[3]);
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
        cpu_bench = mainWindow.findViewById(R.id.cpu);
        ram_bench = mainWindow.findViewById(R.id.ram);
        gpu_bench = mainWindow.findViewById(R.id.gpu);
        data_bench = mainWindow.findViewById(R.id.data);
        all_bench = mainWindow.findViewById(R.id.all);
        start_bench = mainWindow.findViewById(R.id.start);
        progressBar = mainWindow.findViewById(R.id.progressBar);
        content = mainWindow.findViewById(R.id.content);

        //style
        cpu_bench.setTypeface(activity.font,Typeface.BOLD);
        cpu_bench.setTextColor(styleSave.TextColor);
        ram_bench.setTypeface(activity.font,Typeface.BOLD);
        ram_bench.setTextColor(styleSave.TextColor);
        gpu_bench.setTypeface(activity.font,Typeface.BOLD);
        gpu_bench.setTextColor(styleSave.TextColor);
        data_bench.setTypeface(activity.font,Typeface.BOLD);
        data_bench.setTextColor(styleSave.TextColor);
        all_bench.setTypeface(activity.font,Typeface.BOLD);
        all_bench.setTextColor(styleSave.TextColor);

        start_bench.setTypeface(activity.font,Typeface.BOLD);
        start_bench.setBackgroundColor(styleSave.ThemeColor2);
        start_bench.setTextColor(styleSave.TextButtonColor);

        content.setBackgroundColor(styleSave.ThemeColor1);
        progressBar.setProgressDrawable(activity.getDrawable(styleSave.ProgressBarResource));
        LayerDrawable progressBarBackground = (LayerDrawable) progressBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN);
        //translation
        start_bench.setText(activity.words.get("Start"));
    }
}
