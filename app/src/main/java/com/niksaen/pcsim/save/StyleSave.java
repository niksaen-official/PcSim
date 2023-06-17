package com.niksaen.pcsim.save;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.niksaen.pcsim.R;
import com.niksaen.pcsim.program.styleSettings.ColorList;

public class StyleSave {

    private final SharedPreferences preferences;

    public StyleSave(Context context){
        preferences = context.getSharedPreferences("style",Context.MODE_PRIVATE);
        getStyle();
    }

    public String getDesktopProgramList(){
        return preferences.getString("DesktopProgramList","");
    }
    public void setDesktopProgramList(String desktopProgramList ){
        preferences.edit().putString("DesktopProgramList", desktopProgramList).apply();
    }

    /** resource */     public int BackgroundResource;
    /** not resource */ public int ColorWindow;
    /** not resource */ public int TitleColor;
    /** resource */     public int CloseButtonImageRes;
    /** resource */     public int FullScreenMode1ImageRes;
    /** resource */     public int FullScreenMode2ImageRes;
    /** resource */     public int RollUpButtonImageRes;
    /** not resource */ public int ThemeColor1,ThemeColor2,ThemeColor3;
    /** not resource */ public int TextColor,TextButtonColor;

    /** not resource */ public int StartMenuColor;
    /** not resource */public int StartMenuTextColor;
    /** not resource */public boolean StartMenuAppIconVisible;
    /** not resource */public boolean StartMenuAppNameVisible;

    /** resource */     public int ProgressBarResource;
    /** resource */     public int SeekBarProgressResource;
    /** resource */     public int SeekBarThumbResource;
    /** not resource */ public int GreetingColor;
    /** not resource */ public String Greeting;
    /** resource */     public int ArrowButtonImage;
    /** resource */     public int PlayButtonImage;
    /** resource */     public int PauseButtonRes;
    /** resource */     public int PrevOrNextImageRes;

    /** not resource */ public int ToolbarColor;
    /** not resource */ public boolean ToolbarAppNameVisible;
    /** not resource */ public int DesktopTextColor;
    /** not resource */ public boolean DesktopAppNameVisible;
    /** not resource */ public  boolean ToolbarAppIconVisible;
    /** not resource */ public int ToolbarTextColor;
    /** not resource */ public int ProgressBarBgColor;
    /** not resource */ public int SeekBarBgColor;

    public void resetAllStyle(){
        preferences.edit().putInt("Background",R.color.color8).apply();
        preferences.edit().putInt("ColorWindow",Color.parseColor("#0D47A1")).apply();
        preferences.edit().putInt("TitleWindow",Color.parseColor("#FFFFFF")).apply();
        preferences.edit().putInt("TextColor",Color.parseColor("#000000")).apply();
        preferences.edit().putInt("TextButtonColor",Color.parseColor("#000000")).apply();
        preferences.edit().putInt("CloseButtonImageRes",R.drawable.button_1_color17).apply();
        preferences.edit().putInt("FullScreenMode1ImageRes",R.drawable.button_2_1_color17).apply();
        preferences.edit().putInt("FullScreenMode2ImageRes",R.drawable.button_2_2_color17).apply();
        preferences.edit().putInt("RollUpButtonImageRes",R.drawable.button_3_color17).apply();
        preferences.edit().putInt("ThemeColor1",Color.parseColor(ColorList.ThemeColorList1[15])).apply();
        preferences.edit().putInt("ThemeColor2",Color.parseColor(ColorList.ThemeColorList2[15])).apply();
        preferences.edit().putInt("ThemeColor3",Color.parseColor(ColorList.ThemeColorList3[15])).apply();
        preferences.edit().putInt("ProgressBarBgColor",Color.parseColor(ColorList.ThemeColorList2[15])).apply();
        preferences.edit().putInt("SeekBarBgColor",Color.parseColor(ColorList.ThemeColorList2[15])).apply();
        preferences.edit().putInt("StartMenuColor",Color.parseColor("#2C3488")).apply();
        preferences.edit().putInt("ToolbarColor",Color.parseColor("#131A5E")).apply();
        preferences.edit().putInt("ProgressBarResource", R.drawable.progress_bar_circle_color5).apply();
        preferences.edit().putInt("SeekBarProgressResource",R.drawable.seek_progress_color5).apply();
        preferences.edit().putInt("SeekBarThumbResource",R.drawable.seek_thumb_color5).apply();
        preferences.edit().putString("Greeting","User").apply();
        preferences.edit().putInt("GreetingColor",Color.parseColor("#FFFFFF")).apply();
        preferences.edit().putInt("ArrowButtonImage",R.drawable.arrow_color16).apply();
        preferences.edit().putInt("PauseButtonRes",R.drawable.play_color16).apply();
        preferences.edit().putInt("PlayButtonImage",R.drawable.pause_color16).apply();
        preferences.edit().putInt("PrevOrNextImageRes",R.drawable.prev_or_next_color16).apply();
        preferences.edit().putBoolean("ToolbarAppIconVisible",true).apply();
        preferences.edit().putBoolean("ToolbarAppNameVisible",true).apply();
        preferences.edit().putInt("ToolbarTextColor",Color.parseColor("#FFFFFF")).apply();
        preferences.edit().putInt("StartMenuTextColor",Color.parseColor("#FFFFFF")).apply();
        preferences.edit().putBoolean("StartMenuAppIconVisible",true).apply();
        preferences.edit().putBoolean("StartMenuAppNameVisible",true).apply();
        preferences.edit().putString("DesktopProgramList", "").apply();
    }
    public void getStyle(){
        BackgroundResource = preferences.getInt("Background",R.color.color8);
        ColorWindow = preferences.getInt("ColorWindow",Color.parseColor("#0D47A1"));
        TitleColor = preferences.getInt("TitleWindow",Color.parseColor("#FFFFFF"));
        CloseButtonImageRes = preferences.getInt("CloseButtonImageRes",R.drawable.button_1_color17);
        FullScreenMode1ImageRes = preferences.getInt("FullScreenMode1ImageRes",R.drawable.button_2_1_color17);
        FullScreenMode2ImageRes = preferences.getInt("FullScreenMode2ImageRes",R.drawable.button_2_2_color17);
        RollUpButtonImageRes = preferences.getInt("RollUpButtonImageRes",R.drawable.button_3_color17);
        ThemeColor1 = preferences.getInt("ThemeColor1",Color.parseColor(ColorList.ThemeColorList1[15]));
        ThemeColor2 = preferences.getInt("ThemeColor2",Color.parseColor(ColorList.ThemeColorList2[15]));
        ThemeColor3 = preferences.getInt("ThemeColor3",Color.parseColor(ColorList.ThemeColorList3[15]));
        ProgressBarBgColor = preferences.getInt("ProgressBarBgColor",Color.parseColor(ColorList.ThemeColorList2[15]));
        SeekBarBgColor = preferences.getInt("SeekBarBgColor",Color.parseColor(ColorList.ThemeColorList2[15]));
        TextColor = preferences.getInt("TextColor",Color.parseColor("#000000"));
        TextButtonColor = preferences.getInt("TextButtonColor",Color.parseColor("#000000"));
        StartMenuColor = preferences.getInt("StartMenuColor",Color.parseColor("#2C3488"));
        ToolbarColor = preferences.getInt("ToolbarColor",Color.parseColor("#131A5E"));
        ProgressBarResource = preferences.getInt("ProgressBarResource", R.drawable.progress_bar_circle_color5);
        SeekBarProgressResource = preferences.getInt("SeekBarProgressResource",R.drawable.seek_progress_color5);
        SeekBarThumbResource = preferences.getInt("SeekBarThumbResource",R.drawable.seek_thumb_color5);
        Greeting = preferences.getString("Greeting","User");
        GreetingColor = preferences.getInt("GreetingColor",Color.parseColor("#FFFFFF"));
        ArrowButtonImage = preferences.getInt("ArrowButtonImage",R.drawable.arrow_color16);
        PlayButtonImage = preferences.getInt("PlayButtonImage",R.drawable.play_color16);
        PauseButtonRes = preferences.getInt("PauseButtonRes", R.drawable.pause_color16);
        PrevOrNextImageRes = preferences.getInt("PrevOrNextImageRes",R.drawable.prev_or_next_color16);
        ToolbarAppIconVisible = preferences.getBoolean("ToolbarAppIconVisible",true);
        ToolbarAppNameVisible = preferences.getBoolean("ToolbarAppNameVisible",true);
        ToolbarTextColor = preferences.getInt("ToolbarTextColor",Color.parseColor("#FFFFFF"));
        StartMenuTextColor = preferences.getInt("StartMenuTextColor",Color.parseColor("#FFFFFF"));
        StartMenuAppIconVisible = preferences.getBoolean("StartMenuAppIconVisible",true);
        StartMenuAppNameVisible = preferences.getBoolean("StartMenuAppNameVisible",true);
        DesktopTextColor = preferences.getInt("DesktopTextColor",Color.parseColor("#FFFFFF"));
        DesktopAppNameVisible = preferences.getBoolean("DesktopAppNameVisible",true);
    }

    public void setStyle(){
        preferences.edit().putInt("Background",BackgroundResource).apply();
        preferences.edit().putInt("ColorWindow",ColorWindow).apply();
        preferences.edit().putInt("TitleWindow",TitleColor).apply();
        preferences.edit().putInt("TextColor",TextColor).apply();
        preferences.edit().putInt("TextButtonColor",TextButtonColor).apply();
        preferences.edit().putInt("CloseButtonImageRes",CloseButtonImageRes).apply();
        preferences.edit().putInt("FullScreenMode1ImageRes",FullScreenMode1ImageRes).apply();
        preferences.edit().putInt("FullScreenMode2ImageRes",FullScreenMode2ImageRes).apply();
        preferences.edit().putInt("RollUpButtonImageRes",RollUpButtonImageRes).apply();
        preferences.edit().putInt("ThemeColor1",ThemeColor1).apply();
        preferences.edit().putInt("ThemeColor2",ThemeColor2).apply();
        preferences.edit().putInt("ThemeColor3",ThemeColor3).apply();
        preferences.edit().putInt("StartMenuColor",StartMenuColor).apply();
        preferences.edit().putInt("ToolbarColor",ToolbarColor).apply();
        preferences.edit().putInt("ProgressBarResource",ProgressBarResource).apply();
        preferences.edit().putInt("SeekBarProgressResource",SeekBarProgressResource).apply();
        preferences.edit().putInt("SeekBarThumbResource",SeekBarThumbResource).apply();
        preferences.edit().putString("Greeting",Greeting).apply();
        preferences.edit().putInt("GreetingColor",GreetingColor).apply();
        preferences.edit().putInt("ArrowButtonImage",ArrowButtonImage).apply();
        preferences.edit().putInt("PauseButtonRes",PauseButtonRes).apply();
        preferences.edit().putInt("PlayButtonImage",PlayButtonImage).apply();
        preferences.edit().putInt("PrevOrNextImageRes",PrevOrNextImageRes).apply();
        preferences.edit().putBoolean("ToolbarAppIconVisible",ToolbarAppIconVisible).apply();
        preferences.edit().putBoolean("ToolbarAppNameVisible",ToolbarAppNameVisible).apply();
        preferences.edit().putInt("ToolbarTextColor",ToolbarTextColor).apply();
        preferences.edit().putInt("StartMenuTextColor",StartMenuTextColor).apply();
        preferences.edit().putBoolean("StartMenuAppIconVisible",StartMenuAppIconVisible).apply();
        preferences.edit().putBoolean("StartMenuAppNameVisible",StartMenuAppNameVisible).apply();
        preferences.edit().putInt("DesktopTextColor",DesktopTextColor).apply();
        preferences.edit().putBoolean("DesktopAppNameVisible",DesktopAppNameVisible).apply();
        preferences.edit().putInt("ProgressBarBgColor",ProgressBarBgColor).apply();
        preferences.edit().putInt("SeekBarBgColor",SeekBarBgColor).apply();
    }
}
