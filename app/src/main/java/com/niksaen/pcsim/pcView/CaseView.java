package com.niksaen.pcsim.pcView;

import android.widget.RelativeLayout;

public class CaseView {

    String name;
    RelativeLayout caseImage;

    public int dataCount;

    public CaseView(String name, RelativeLayout caseImage){
        this.name = name;
        this.caseImage = caseImage;
    }
}
