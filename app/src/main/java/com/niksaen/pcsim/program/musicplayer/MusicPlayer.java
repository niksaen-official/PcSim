package com.niksaen.pcsim.program.musicplayer;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.StringArrayWork;
import com.niksaen.pcsim.fileWorkLib.FileUtil;
import com.niksaen.pcsim.program.Program;
import com.niksaen.pcsim.program.notepad.NotepadSpinnerAdapter;

import java.util.ArrayList;

public class MusicPlayer extends Program {

    public MusicPlayer(MainActivity activity){
        super(activity);
        this.Title = "Music player";
        ValueRam = new int[]{55,80};
        ValueVideoMemory = new int[]{30,40};
    }

    TextView musicName;
    Button buttonPlayStop,buttonNext,buttonPrev;
    Spinner menuSpinner;
    ListView songView;
    SeekBar seekBarTime;
    ConstraintLayout mainContent;
    LinearLayout taskbar;
    TextView timeText;

    NotepadSpinnerAdapter spinnerAdapter;

    public void initView(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_musicplayer,null);
        buttonPlayStop = mainWindow.findViewById(R.id.play);
        seekBarTime = mainWindow.findViewById(R.id.seekBarTime);
        buttonPlayStop = mainWindow.findViewById(R.id.play);
        menuSpinner = mainWindow.findViewById(R.id.menu);
        songView = mainWindow.findViewById(R.id.songList);
        mainContent = mainWindow.findViewById(R.id.main);
        taskbar = mainWindow.findViewById(R.id.taskBar);
        musicName = mainWindow.findViewById(R.id.musicName);
        buttonPrev = mainWindow.findViewById(R.id.prev);
        buttonNext = mainWindow.findViewById(R.id.next);
        timeText = mainWindow.findViewById(R.id.timeText);
    }

    public void style(){

        mainContent.setBackgroundColor(activity.styleSave.ThemeColor1);
        mainWindow.setBackgroundColor(activity.styleSave.ColorWindow);
        taskbar.setBackgroundColor(activity.styleSave.ThemeColor1);

        musicName.setTypeface(activity.font);
        musicName.setTextColor(activity.styleSave.TextColor);

        buttonPlayStop.setBackgroundResource(activity.styleSave.PlayButtonImage);
        buttonNext.setBackgroundResource(activity.styleSave.PrevOrNextImageRes);
        buttonPrev.setBackgroundResource(activity.styleSave.PrevOrNextImageRes);

        timeText.setTypeface(activity.font);
        timeText.setTextColor(activity.styleSave.TextColor);

        seekBarTime.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
        LayerDrawable progressBarBackground = (LayerDrawable) seekBarTime.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN);
        seekBarTime.setThumb(activity.getDrawable(activity.styleSave.SeekBarThumbResource));

        //настройка адаптеров и перевода
        String[] fileWork = new String[]{
                activity.words.get("File")+":",
                activity.words.get("Open"),
                activity.words.get("Exit")
        };
        spinnerAdapter = new NotepadSpinnerAdapter(activity.getBaseContext(),R.layout.item_textview,fileWork,activity.words.get("File"));
        spinnerAdapter.BackgroundColor = activity.styleSave.ThemeColor2;
        spinnerAdapter.TextColor = activity.styleSave.TextColor;
        menuSpinner.setAdapter(spinnerAdapter);
    }

    MediaPlayer mediaPlayer;
    MusicListAdapter musicListAdapter;
    ArrayList<String> folder = new ArrayList<>(),musicFiles = new ArrayList<>();
    Handler handler = new Handler();
    Runnable runnable;
    int buttonPlayPauseClickCount = 0;
    int CurrentPosition = 0;
    String CurrentMusic;

    public void openProgram(String pathMusic,String pathFolder){
        initView();
        style();
        CurrentMusic = pathMusic;
        FileUtil.listDir(pathFolder, folder);
        for (String file : folder) {
            if (file.endsWith(".mp3")) {
                musicFiles.add(file);
            }
        }
        // настройка и подключение адаптера
        musicListAdapter = new MusicListAdapter(activity.getBaseContext(), 0, musicFiles);
        musicListAdapter.ColorBackground = activity.styleSave.ThemeColor1;
        musicListAdapter.ColorText = activity.styleSave.TextColor;
        songView.setAdapter(musicListAdapter);
        // изменение счетчика времени и позиции seekbar
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBarTime.setProgress(mediaPlayer.getCurrentPosition());
                    timeText.setText(convertTime(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                }
            }
        };
        //работа кнопки паузы
        buttonPlayStop.setOnClickListener(v -> {
            if (buttonPlayPauseClickCount == 0) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(CurrentMusic);
                    mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(CurrentPosition);
                    musicName.setText(CurrentMusic.substring(CurrentMusic.lastIndexOf("/") + 1));
                    seekBarTime.setMax(mediaPlayer.getDuration());
                    handler.post(runnable);
                } catch (Exception ignored) { }
                buttonPlayPauseClickCount++;
                buttonPlayStop.setBackgroundResource(activity.styleSave.PauseButtonRes);
            } else {
                mediaPlayer.pause();
                CurrentPosition = mediaPlayer.getCurrentPosition();
                buttonPlayPauseClickCount = 0;
                buttonPlayStop.setBackgroundResource(activity.styleSave.PlayButtonImage);
            }
        });
        //перемотка
        if(mediaPlayer != null) {
            seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    CurrentPosition = progress;
                    timeText.setText(convertTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.pause();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(CurrentPosition);
                    mediaPlayer.start();
                }
            });
        }
        //переключение на следующую или предыдущую песню
        View.OnClickListener onNextOrPrevClick = v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                CurrentPosition = 0;
                seekBarTime.setProgress(0);
                timeText.setText("");
                buttonPlayPauseClickCount = 1;
                buttonPlayStop.setBackgroundResource(activity.styleSave.PauseButtonRes);
                switch (v.getId()) {
                    case R.id.next: {
                        if (musicFiles.indexOf(CurrentMusic) < musicFiles.size() - 1) {
                            CurrentMusic = musicFiles.get(musicFiles.indexOf(CurrentMusic) + 1);
                        }
                        break;
                    }
                    case R.id.prev: {
                        if (musicFiles.indexOf(CurrentMusic) > 0) {
                            CurrentMusic = musicFiles.get(musicFiles.indexOf(CurrentMusic) - 1);
                        }
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(CurrentMusic);
                    mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(CurrentPosition);
                    musicName.setText(CurrentMusic.substring(CurrentMusic.lastIndexOf("/") + 1));
                    seekBarTime.setMax(mediaPlayer.getDuration());
                    handler.post(runnable);
                } catch (Exception ignored) { }
            }
        };
        buttonNext.setOnClickListener(onNextOrPrevClick);
        buttonPrev.setOnClickListener(onNextOrPrevClick);

        //выбор песни
        songView.setOnItemClickListener((parent, view, position, id) -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                CurrentPosition = 0;
                seekBarTime.setProgress(0);
                timeText.setText("");
                buttonPlayPauseClickCount = 1;
                buttonPlayStop.setBackgroundResource(activity.styleSave.PauseButtonRes);
                CurrentMusic = musicFiles.get(position);
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(CurrentMusic);
                    mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(CurrentPosition);
                    musicName.setText(CurrentMusic.substring(CurrentMusic.lastIndexOf("/") + 1));
                    seekBarTime.setMax(mediaPlayer.getDuration());
                    handler.post(runnable);
                } catch (Exception ignored) { }
            }
        });
        //меню
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1) {
                    new MusicPlayerOpenFile(activity).openProgram();
                    closeProgram(1);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        initProgram();
        super.openProgram();
    }
    public void openProgram(){
        initView();
        style();
        //меню
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    if (StringArrayWork.ArrayListToString(activity.apps).contains(Program.AdditionalSoftPrefix + "File manager: OpenLibs")) {
                        new MusicPlayerOpenFile(activity).openProgram();
                        closeProgram(1);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        initProgram();
        super.openProgram();
    }

    public void closeProgram(int mode){
        super.closeProgram(mode);
        if(mediaPlayer != null){
            mediaPlayer.stop();
            CurrentPosition = 0;
            mediaPlayer.seekTo(CurrentPosition);
            seekBarTime.setProgress(0);
            timeText.setText("00:00");
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
}