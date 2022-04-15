package com.niksaen.pcsim.program.videoplayer;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.niksaen.pcsim.activities.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.program.driverInstaller.DriverInstaller;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.musicplayer.MusicPlayer;
import com.niksaen.pcsim.program.notepad.NotepadSpinnerAdapter;

import java.io.IOException;

public class VideoPlayer extends Program implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private SurfaceView videoView;
    private MediaPlayer mediaPlayer;

    String VIDEO_PATH;

    public VideoPlayer(MainActivity activity){
        super(activity);
        this.Title = "Video player";
        ValueRam = new int[]{40,80};
        ValueVideoMemory = new int[]{60,90};
    }

    Spinner menu;
    Button play_pause;
    SeekBar seekBarTime;
    TextView timeText;
    LinearLayout taskBar;

    private void initView(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_videoplayer,null);
        timeText = mainWindow.findViewById(R.id.textViewTime);
        seekBarTime = mainWindow.findViewById(R.id.seekBarTime);
        menu = mainWindow.findViewById(R.id.menu);
        play_pause = mainWindow.findViewById(R.id.pause);
        taskBar = mainWindow.findViewById(R.id.taskBar);
        videoView = mainWindow.findViewById(R.id.surfaceView);
    }
    NotepadSpinnerAdapter spinnerAdapter;

    private void style(){
        taskBar.setBackgroundColor(activity.styleSave.ThemeColor1);
        taskBar.setVisibility(View.GONE);
        play_pause.setBackgroundResource(activity.styleSave.PlayButtonImage);
        timeText.setTypeface(activity.font);
        timeText.setTextColor(activity.styleSave.TextColor);
        seekBarTime.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
        LayerDrawable progressBarBackground = (LayerDrawable) seekBarTime.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN);
        seekBarTime.setThumb(activity.getDrawable(activity.styleSave.SeekBarThumbResource));

        videoView.setZ(-1);
        taskBar.setVisibility(View.GONE);
        //настройка адаптеров и перевода
        String[] fileWork = new String[]{
                activity.words.get("File")+":",
                activity.words.get("Open"),
                activity.words.get("Exit")
        };
        spinnerAdapter = new NotepadSpinnerAdapter(activity,R.layout.item_textview,fileWork,activity.words.get("File"));
        spinnerAdapter.BackgroundColor = activity.styleSave.ThemeColor2;
        spinnerAdapter.TextColor = activity.styleSave.TextColor;
        menu.setAdapter(spinnerAdapter);
    }

    Handler handler;
    int CurrentPos = 0;
    private int buttonClickCount = 0;

    private void logic() {
        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    if (StringArrayWork.ArrayListToString(activity.apps).contains(DriverInstaller.AdditionalSoftPrefix + "File manager: OpenLibs")) {
                        closeProgram(1);
                        new VideoOpenFile(activity).openProgram();
                    }
                }
                else if(position == 2){
                    closeProgram(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void openProgram(final String PathOpen){
        initView();style();logic();
        if(PathOpen != null){
            menu.setVisibility(View.GONE);
            VIDEO_PATH = PathOpen;
            surfaceHolder = videoView.getHolder();
            surfaceHolder.addCallback(this);
        }
        //показываем таскбар при нажатии на видео
        videoView.setOnClickListener(v -> {
            taskBar.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);
            seekBarTime.setProgress(mediaPlayer.getCurrentPosition());
            timeText.setText(MusicPlayer.convertTime(mediaPlayer.getCurrentPosition()));
            new Handler().postDelayed(() -> {taskBar.setVisibility(View.GONE);menu.setVisibility(View.GONE);},1500);
        });

        // если пользователь перематывает видео то таск бар не убирается
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                taskBar.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);
                CurrentPos = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer.seekTo(CurrentPos);
                    mediaPlayer.start();
                }
                new Handler().postDelayed(() -> {taskBar.setVisibility(View.GONE);menu.setVisibility(View.GONE);},1500);
            }
        });

        //пауза
        play_pause.setOnClickListener(v -> {
            if(buttonClickCount == 0){
                mediaPlayer.seekTo(CurrentPos);
                mediaPlayer.start();
                v.setBackgroundResource(activity.styleSave.PauseButtonRes);
                buttonClickCount= 1;
            }
            else{
                CurrentPos = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                v.setBackgroundResource(activity.styleSave.PlayButtonImage);
                buttonClickCount = 0;
            }
            new Handler().postDelayed(() -> {taskBar.setVisibility(View.GONE);menu.setVisibility(View.GONE);},1500);
        });
        initProgram();
        super.openProgram();
    }
    public void openProgram(){
        initView();style();logic();
        initProgram();
        super.openProgram();
    }


    public void closeProgram(int mode){
        super.closeProgram(mode);
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(surfaceHolder);
        try {
            mediaPlayer.setDataSource(VIDEO_PATH);
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            play_pause.setBackgroundResource(activity.styleSave.PauseButtonRes);
            seekBarTime.setMax(mediaPlayer.getDuration());
            new Handler().postDelayed(() -> {taskBar.setVisibility(View.GONE);menu.setVisibility(View.GONE);},750);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) { }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) { }
}
