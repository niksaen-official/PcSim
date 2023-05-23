package com.niksaen.pcsim.classes;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.widget.ProgressBar;

import com.niksaen.pcsim.activities.MainActivity;

public class ProgressBarStylisation {
    public static void setStyle(ProgressBar progressBar, MainActivity activity){
        progressBar.setProgressDrawable(activity.getDrawable(activity.styleSave.ProgressBarResource));
        LayerDrawable progressBarBackground = (LayerDrawable) progressBar.getProgressDrawable();
        progressBarBackground.getDrawable(0).setColorFilter(activity.styleSave.ThemeColor2, PorterDuff.Mode.SRC_IN);
    }
}
