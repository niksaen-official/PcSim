package com.niksaen.pcsim.program.videoplayer;

import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.notepad.NotepadSpinnerAdapter;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.io.IOException;
import java.util.HashMap;

public class VideoPlayer extends Program implements SurfaceHolder.Callback {

    Context context;
    PcParametersSave pcParametersSave;
    ConstraintLayout layout;

    View mainWindow;
    MainActivity mainActivity;

    private SurfaceHolder surfaceHolder;
    private SurfaceView videoView;
    private MediaPlayer mediaPlayer;

    LayoutInflater layoutInflater;
    Typeface font;
    StyleSave styleSave;

    String VIDEO_PATH;

    public VideoPlayer(MainActivity activity){
        super(activity);
        this.Title = "Video player";
        mainActivity = activity;
        this.context = activity.getBaseContext();
        this.pcParametersSave = activity.pcParametersSave;
        this.layout = activity.layout;

        layoutInflater = LayoutInflater.from(context);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
        styleSave = new StyleSave(context);
    }

    TextView title;
    Spinner menu;
    Button close,fullScreen,rollUp;
    Button play_pause;
    SeekBar seekBarTime;
    TextView timeText;
    LinearLayout taskBar;

    private void initView(){
        title = mainWindow.findViewById(R.id.title);
        timeText = mainWindow.findViewById(R.id.textViewTime);
        seekBarTime = mainWindow.findViewById(R.id.seekBarTime);
        menu = mainWindow.findViewById(R.id.menu);
        close = mainWindow.findViewById(R.id.close);
        fullScreen = mainWindow.findViewById(R.id.fullscreenMode);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        play_pause = mainWindow.findViewById(R.id.pause);
        taskBar = mainWindow.findViewById(R.id.taskBar);
        videoView = mainWindow.findViewById(R.id.surfaceView);
    }

    HashMap<String,String> words;
    NotepadSpinnerAdapter spinnerAdapter;

    private void style(){
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        taskBar.setBackgroundColor(styleSave.ThemeColor1);
        taskBar.setVisibility(View.GONE);

        title.setTypeface(font,Typeface.BOLD);
        title.setTextColor(styleSave.TitleColor);

        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        fullScreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);

        play_pause.setBackgroundResource(styleSave.PlayButtonImage);

        timeText.setTypeface(font);
        timeText.setTextColor(styleSave.TextColor);

        seekBarTime.setProgressDrawable(context.getDrawable(styleSave.SeekBarProgressResource));
        seekBarTime.setThumb(context.getDrawable(styleSave.SeekBarThumbResource));

        videoView.setZ(-1);
        taskBar.setVisibility(View.GONE);
        //настройка адаптеров и перевода
        words = new Gson().fromJson(new AssetFile(context).getText("language/"+ Language.getLanguage(context)+".json"),new TypeToken<HashMap<String,String>>(){}.getType());
        String[] fileWork = new String[]{
                words.get("File")+":",
                words.get("Open"),
                words.get("Exit")
        };
        spinnerAdapter = new NotepadSpinnerAdapter(context,R.layout.item_textview,fileWork,words.get("File"));
        spinnerAdapter.BackgroundColor = styleSave.ThemeColor2;
        spinnerAdapter.TextColor = styleSave.TextColor;
        menu.setAdapter(spinnerAdapter);
        title.setText(words.get("Video Player"));
    }

    Handler handler;
    int CurrentPos = 0;
    public void openProgram(final String PathOpen){
        super.openProgram();
        mainWindow = layoutInflater.inflate(R.layout.program_videoplayer,null);
        initView();style();

        final int[] buttonClickCount = {0,1};
        fullScreen.setOnClickListener(v -> {
            if(buttonClickCount[0]==0){
                fullScreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                mainWindow.setScaleX(0.7f);
                mainWindow.setScaleY(0.7f);
                PortableView portableView1 = new PortableView(mainWindow);
                buttonClickCount[0]++;
            }
            else{
                fullScreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setX(0);
                mainWindow.setY(0);
                mainWindow.setOnTouchListener(null);
                buttonClickCount[0]=0;
            }
        });
        close.setOnClickListener(v -> {
            closeProgram(1);
        });

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
            timeText.setText(convertTime(mediaPlayer.getCurrentPosition()));
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
            if(buttonClickCount[1] == 0){
                mediaPlayer.seekTo(CurrentPos);
                mediaPlayer.start();
                v.setBackgroundResource(styleSave.PauseButtonRes);
                buttonClickCount[1] = 1;
            }
            else{
                CurrentPos = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                v.setBackgroundResource(styleSave.PlayButtonImage);
                buttonClickCount[1] = 0;
            }
            new Handler().postDelayed(() -> {taskBar.setVisibility(View.GONE);menu.setVisibility(View.GONE);},1500);
        });

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    closeProgram(1);
                    new VideoOpenFile(mainActivity).openProgram();
                }
                else if(position == 2){
                    closeProgram(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(mainWindow.getParent() == null) {
            layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }else{
            mainWindow.setVisibility(View.VISIBLE);
        }
        mainActivity.programArrayList.add(this);
    }
    public void openProgram(){
        if(status == -1) {
            super.openProgram();
            mainWindow = layoutInflater.inflate(R.layout.program_videoplayer, null);
            initView();
            style();

            final int[] buttonClickCount = {0, 1};
            fullScreen.setOnClickListener(v -> {
                if (buttonClickCount[0] == 0) {
                    fullScreen.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                    mainWindow.setScaleX(0.7f);
                    mainWindow.setScaleY(0.7f);
                    PortableView portableView1 = new PortableView(mainWindow);
                    buttonClickCount[0]++;
                } else {
                    fullScreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                    mainWindow.setScaleX(1);
                    mainWindow.setScaleY(1);
                    mainWindow.setX(0);
                    mainWindow.setY(0);
                    mainWindow.setOnTouchListener(null);
                    buttonClickCount[0] = 0;
                }
            });
            close.setOnClickListener(v -> {
                closeProgram(1);
            });

            //показываем таскбар при нажатии на видео
            videoView.setOnClickListener(v -> {
                taskBar.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);
            });

            // если пользователь перематывает видео то таск бар не убирается
            seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    taskBar.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);
                    CurrentPos = progress;
                    new Handler().postDelayed(() -> {
                        taskBar.setVisibility(View.GONE);
                        menu.setVisibility(View.GONE);
                    }, 1500);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (mediaPlayer != null) {
                        mediaPlayer.seekTo(CurrentPos);
                        mediaPlayer.start();
                    }
                    new Handler().postDelayed(() -> {
                        taskBar.setVisibility(View.GONE);
                        menu.setVisibility(View.GONE);
                    }, 1500);
                }
            });

            //пауза
            play_pause.setOnClickListener(v -> {
                if (buttonClickCount[1] == 0) {
                    v.setBackgroundResource(styleSave.PauseButtonRes);
                    buttonClickCount[1] = 1;
                } else {
                    v.setBackgroundResource(styleSave.PlayButtonImage);
                    buttonClickCount[1] = 0;
                }
                new Handler().postDelayed(() -> {
                    taskBar.setVisibility(View.GONE);
                    menu.setVisibility(View.GONE);
                }, 1500);
            });

            menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 1) {
                        closeProgram(1);
                        new VideoOpenFile(mainActivity).openProgram();
                    } else if (position == 2) {
                        closeProgram(1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (mainWindow.getParent() == null) {
                layout.addView(mainWindow, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            } else {
                mainWindow.setVisibility(View.VISIBLE);
            }
        }
    }


    public void closeProgram(int mode){
        super.closeProgram(mode);
        mainWindow.setVisibility(View.GONE);
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private String convertTime(int milliSeconds){
        int second = milliSeconds/1000;
        int minute = second/60;
        int hour = minute/60;
        String strSecond = String.valueOf(second%60);
        if(strSecond.length() == 1){
            strSecond = "0"+strSecond;
        }

        String strMinute = String.valueOf(minute%60);
        if(strMinute.length() == 1){
            strMinute = "0"+strMinute;
        }
        String strHour = "00";
        if(hour > 0) {
            strHour = String.valueOf(hour);
            if(strHour.length()==1){
                strHour = "0"+strHour;
            }
            return strHour + ":" + strMinute + ":" + strSecond;
        }
        else{
            return strMinute + ":" + strSecond;
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
            play_pause.setBackgroundResource(styleSave.PauseButtonRes);
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
