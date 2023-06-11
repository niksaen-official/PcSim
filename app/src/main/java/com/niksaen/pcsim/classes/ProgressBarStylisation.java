package com.niksaen.pcsim.classes;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.niksaen.pcsim.activities.MainActivity;

public class ProgressBarStylisation {
    public static void setStyle(ProgressBar progressBar, MainActivity activity){
        progressBar.setProgressDrawable(activity.getDrawable(activity.styleSave.ProgressBarResource));
        LayerDrawable progressBarBackground = (LayerDrawable) progressBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ProgressBarBgColor, PorterDuff.Mode.SRC_IN);
    }
    public static void setStyleHorizontal(ProgressBar progressBar, MainActivity activity){
        progressBar.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
        LayerDrawable progressBarBackground = (LayerDrawable) progressBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ProgressBarBgColor, PorterDuff.Mode.SRC_IN);
    }
    public static void setStyle(SeekBar seekBar,MainActivity activity){
        seekBar.setProgressDrawable(activity.getDrawable(activity.styleSave.SeekBarProgressResource));
        LayerDrawable progressBarBackground = (LayerDrawable) seekBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.SeekBarBgColor, PorterDuff.Mode.SRC_IN);
        seekBar.setThumb(activity.getDrawable(activity.styleSave.SeekBarThumbResource));
    }
}
