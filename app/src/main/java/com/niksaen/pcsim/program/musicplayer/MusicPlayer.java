package com.niksaen.pcsim.program.musicplayer;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niksaen.pcsim.R;
import com.niksaen.pcsim.classes.AssetFile;
import com.niksaen.pcsim.classes.FileUtil;
import com.niksaen.pcsim.classes.PortableView;
import com.niksaen.pcsim.program.notepad.NotepadSpinnerAdapter;
import com.niksaen.pcsim.save.Language;
import com.niksaen.pcsim.save.PcParametersSave;
import com.niksaen.pcsim.save.StyleSave;

import java.util.ArrayList;
import java.util.HashMap;

public class MusicPlayer{

    View mainWindow;
    PcParametersSave pcParametersSave;
    StyleSave styleSave;
    Context context;

    ConstraintLayout layout;

    Typeface font;

    public MusicPlayer(Context context, PcParametersSave pcParametersSave, ConstraintLayout layout){
        this.context = context;
        this.layout = layout;
        this.pcParametersSave = pcParametersSave;
        styleSave = new StyleSave(context);
        mainWindow = LayoutInflater.from(context).inflate(R.layout.program_musicplayer,null);
        font = Typeface.createFromAsset(context.getAssets(), "fonts/pixelFont.ttf");
    }

    TextView title,musicName;
    Button close,rollUp,fullscreen,buttonPlayStop,buttonNext,buttonPrev;
    Spinner menuSpinner;
    ListView songView;
    SeekBar seekBarTime;
    ConstraintLayout mainContent;
    LinearLayout taskbar;
    TextView timeText;

    NotepadSpinnerAdapter spinnerAdapter;

    public void initView(){
        buttonPlayStop = mainWindow.findViewById(R.id.play);
        seekBarTime = mainWindow.findViewById(R.id.seekBarTime);
        buttonPlayStop = mainWindow.findViewById(R.id.play);
        menuSpinner = mainWindow.findViewById(R.id.menu);
        songView = mainWindow.findViewById(R.id.songList);
        mainContent = mainWindow.findViewById(R.id.main);
        taskbar = mainWindow.findViewById(R.id.taskBar);
        musicName = mainWindow.findViewById(R.id.musicName);
        title = mainWindow.findViewById(R.id.title);
        close = mainWindow.findViewById(R.id.close);
        rollUp = mainWindow.findViewById(R.id.roll_up);
        fullscreen = mainWindow.findViewById(R.id.fullscreenMode);
        buttonPrev = mainWindow.findViewById(R.id.prev);
        buttonNext = mainWindow.findViewById(R.id.next);
        timeText = mainWindow.findViewById(R.id.timeText);
    }

    HashMap<String,String> words;
    public void style(){

        mainContent.setBackgroundColor(styleSave.ThemeColor1);
        mainWindow.setBackgroundColor(styleSave.ColorWindow);
        taskbar.setBackgroundColor(styleSave.ThemeColor1);

        musicName.setTypeface(font);
        musicName.setTextColor(styleSave.TextColor);

        title.setTypeface(font,Typeface.BOLD);
        title.setTextColor(styleSave.TitleColor);

        close.setBackgroundResource(styleSave.CloseButtonImageRes);
        rollUp.setBackgroundResource(styleSave.RollUpButtonImageRes);
        fullscreen.setBackgroundResource(styleSave.FullScreenMode2ImageRes);

        buttonPlayStop.setBackgroundResource(styleSave.PlayButtonImage);
        buttonNext.setBackgroundResource(styleSave.PrevOrNextImageRes);
        buttonPrev.setBackgroundResource(styleSave.PrevOrNextImageRes);

        timeText.setTypeface(font);
        timeText.setTextColor(styleSave.TextColor);

        seekBarTime.setProgressDrawable(context.getDrawable(styleSave.SeekBarProgressResource));
        seekBarTime.setThumb(context.getDrawable(styleSave.SeekBarThumbResource));

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
        menuSpinner.setAdapter(spinnerAdapter);
        title.setText(words.get("Music Player"));

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

        initView();style();
        if(pathFolder != null){
            CurrentMusic = pathMusic;
            FileUtil.listDir(pathFolder,folder);
            for(String file:folder) {
                if(file.endsWith(".mp3")){
                    musicFiles.add(file);
                }
            }
            // настройка и подключение адаптера
            musicListAdapter = new MusicListAdapter(context,0,musicFiles);
            musicListAdapter.ColorBackground = styleSave.ThemeColor1;
            musicListAdapter.ColorText = styleSave.TextColor;
            songView.setAdapter(musicListAdapter);
        }

        // изменение счетчика времени и позиции seekbar
        runnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null) {
                    seekBarTime.setProgress(mediaPlayer.getCurrentPosition());
                    timeText.setText(convertTime(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                }
            }
        };

        //работа кнопки паузы
        buttonPlayStop.setOnClickListener(v -> {
            if(buttonPlayPauseClickCount == 0) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(CurrentMusic);
                    mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                    mediaPlayer.prepare();
                    mediaPlayer.seekTo(CurrentPosition);
                    musicName.setText(CurrentMusic.substring(CurrentMusic.lastIndexOf("/")+1));
                    seekBarTime.setMax(mediaPlayer.getDuration());
                    handler.post(runnable);

                } catch (Exception ignored) {
                }
                buttonPlayPauseClickCount++;
                buttonPlayStop.setBackgroundResource(styleSave.PauseButtonRes);
            }else {
                mediaPlayer.pause();
                CurrentPosition = mediaPlayer.getCurrentPosition();
                buttonPlayPauseClickCount = 0;
                buttonPlayStop.setBackgroundResource(styleSave.PlayButtonImage);
            }
        });

        //перемотка
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null) {
                    CurrentPosition = progress;
                    timeText.setText(convertTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null)
                    mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer != null) {
                    mediaPlayer.seekTo(CurrentPosition);
                    mediaPlayer.start();
                }
            }
        });

        //переключение на следующую или предыдущую песню
        View.OnClickListener onNextOrPrevClick = v -> {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                CurrentPosition = 0;
                seekBarTime.setProgress(0);
                timeText.setText("");
                buttonPlayPauseClickCount = 1;
                buttonPlayStop.setBackgroundResource(styleSave.PauseButtonRes);

                switch (v.getId()) {
                    case R.id.next: {
                        if(musicFiles.indexOf(CurrentMusic) < musicFiles.size()-1) {
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
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                CurrentPosition = 0;
                seekBarTime.setProgress(0);
                timeText.setText("");
                buttonPlayPauseClickCount = 1;
                buttonPlayStop.setBackgroundResource(styleSave.PauseButtonRes);

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
                if(position == 1){
                    layout.addView(
                            new MusicPlayerOpenFile(context,new MusicPlayer(context,pcParametersSave,layout)).openFile(),
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    closeProgram();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        close.setOnClickListener(v -> closeProgram());

        final int[] buttonClick = {0};
        fullscreen.setOnClickListener(v -> {
            if(buttonClick[0] == 0){
                mainWindow.setScaleX(0.65f);
                mainWindow.setScaleY(0.65f);
                PortableView portableView = new PortableView(mainWindow);
                v.setBackgroundResource(styleSave.FullScreenMode1ImageRes);
                buttonClick[0] = 1;
            }
            else{
                mainWindow.setScaleX(1);
                mainWindow.setScaleY(1);
                mainWindow.setX(0);
                mainWindow.setY(0);
                mainWindow.setOnTouchListener(null);
                v.setBackgroundResource(styleSave.FullScreenMode2ImageRes);
                buttonClick[0] = 0;
            }
        });

        layout.addView(mainWindow,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }
    public void closeProgram(){
        mainWindow.setVisibility(View.GONE);
        mainWindow = null;
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            runnable = null;
            handler = null;
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