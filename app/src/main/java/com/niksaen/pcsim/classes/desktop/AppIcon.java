package com.niksaen.pcsim.classes.desktop;

import com.niksaen.pcsim.R;

import java.util.HashMap;
import java.util.Map;

public class AppIcon {
    public static final HashMap<String,Integer> icon = new HashMap<String,Integer>();
    static {
        icon.put("Benchmark",0);
        icon.put("CPU Overclocking",R.drawable.icon_cputweaker);
        icon.put("RAM Overclocking",R.drawable.icon_ramoverclocking);
        icon.put("GPU Overclocking",R.drawable.icon_gpuiverclocking);
        icon.put("View Power Supply Load",R.drawable.icon_viewpsuload);
        icon.put("Temperature Viewer",R.drawable.icon_temperatureviewer);
        icon.put("Video Player",R.drawable.icon_videoplayer);
        icon.put("Music Player",R.drawable.icon_musicplayer);
        icon.put("Browser",R.drawable.icon_browser);
    }
}
