package com.niksaen.pcsim.pcView;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import com.niksaen.pcsim.R;

public class MotherBoardView {
    String name;
    View caseImage,moboImage, cpuImage, coolerImage, ram1View, ram2View, ram3View, ram4View, gpu1View, gpu2View;

    float dip;

    public MotherBoardView(String name, View caseImage, Context context){
        this.name = name;
        this.caseImage = caseImage;

        dip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1,context.getResources().getDisplayMetrics());
        moboImage = caseImage.findViewById(R.id.moboImage);
        cpuImage = caseImage.findViewById(R.id.cpuImage);
        coolerImage = caseImage.findViewById(R.id.coolerImage);
        ram1View = caseImage.findViewById(R.id.ramImage1);
        ram2View = caseImage.findViewById(R.id.ramImage2);
        ram3View = caseImage.findViewById(R.id.ramImage3);
        ram4View = caseImage.findViewById(R.id.ramImage4);
        gpu1View = caseImage.findViewById(R.id.gpu1View);
        gpu2View = caseImage.findViewById(R.id.gpu2View);
        setMotherboard();
    }

    public void setMotherboard(){
        switch (name){
            case "BSRock H110M-DVS":{
                cpuImage.setScaleX(1);
                cpuImage.setScaleY(1);
                cpuImage.setX(67f*dip);
                cpuImage.setY(48*dip);

                coolerImage.setScaleX(1f);
                coolerImage.setScaleY(1f);
                coolerImage.setX(54f*dip);
                coolerImage.setY(34f*dip);

                ram1View.setY(17*dip);
                ram2View.setY(17*dip);
                ram1View.setX(128*dip);
                ram2View.setX(138*dip);
                ram3View.setVisibility(View.GONE);
                ram4View.setVisibility(View.GONE);

                gpu1View.setY(135*dip);
                gpu1View.setX(7*dip);
                gpu2View.setVisibility(View.GONE);

                break;
            }
            case "Ciostar Hi-Fi A70U3P":{
                cpuImage.setX(69*dip);
                cpuImage.setY(52*dip);
                cpuImage.setScaleX(1f);
                cpuImage.setScaleY(1f);

                coolerImage.setScaleX(1f);
                coolerImage.setScaleY(1f);
                coolerImage.setX(55f*dip);
                coolerImage.setY(40f*dip);

                ram1View.setY(13*dip);
                ram2View.setY(13*dip);
                ram1View.setX(128*dip);
                ram2View.setX(138*dip);
                ram1View.setScaleY(1.0f);
                ram2View.setScaleY(1.0f);
                ram3View.setVisibility(View.GONE);
                ram4View.setVisibility(View.GONE);

                gpu1View.setY(132*dip);
                gpu1View.setX(7*dip);
                gpu2View.setVisibility(View.GONE);
                break;
            }
            case "Ciostar A68MHE":{
                cpuImage.setScaleX(1.05f);
                cpuImage.setScaleY(1.05f);
                cpuImage.setX(70f*dip);
                cpuImage.setY(60f*dip);

                coolerImage.setScaleX(1f);
                coolerImage.setScaleY(1f);
                coolerImage.setX(57f*dip);
                coolerImage.setY(47*dip);

                ram1View.setY(17*dip);
                ram2View.setY(17*dip);
                ram1View.setX(130*dip);
                ram2View.setX(140*dip);
                ram1View.setScaleY(1.1f);
                ram2View.setScaleY(1.1f);
                ram3View.setVisibility(View.GONE);
                ram4View.setVisibility(View.GONE);

                gpu1View.setY(141*dip);
                gpu2View.setVisibility(View.GONE);
                break;
            }
            case "Nsi A68HM-E33 V2":{
                cpuImage.setScaleX(0.9f);
                cpuImage.setScaleY(0.9f);
                cpuImage.setX(63f*dip);
                cpuImage.setY(49.5f*dip);

                coolerImage.setScaleX(0.9f);
                coolerImage.setScaleY(0.9f);
                coolerImage.setX(50.5f*dip);
                coolerImage.setY(36.5f*dip);

                ram1View.setY(16*dip);
                ram2View.setY(16*dip);
                ram1View.setX(124*dip);
                ram2View.setX(134*dip);
                ram1View.setScaleY(0.96f);
                ram2View.setScaleY(0.96f);
                ram3View.setVisibility(View.GONE);
                ram4View.setVisibility(View.GONE);

                gpu1View.setY(135*dip);
                gpu1View.setX(7*dip);
                gpu2View.setVisibility(View.GONE);
                break;
            }
            case "BSRock H110M-DGS":{
                cpuImage.setScaleY(1f);
                cpuImage.setScaleX(1);
                cpuImage.setX(72f*dip);
                cpuImage.setY(56f*dip);

                coolerImage.setScaleX(0.95f);
                coolerImage.setScaleY(0.95f);
                coolerImage.setX(58f*dip);
                coolerImage.setY(43f*dip);

                ram1View.setY(35*dip);
                ram2View.setY(35*dip);
                ram1View.setX(132*dip);
                ram2View.setX(143*dip);
                ram1View.setScaleY(1);
                ram2View.setScaleY(1);
                ram3View.setVisibility(View.GONE);
                ram4View.setVisibility(View.GONE);

                gpu1View.setY(163*dip);
                gpu1View.setX(7*dip);
                gpu2View.setVisibility(View.GONE);
                break;
            }
            case "BSRock Fatality H270M Perfomance":{
                cpuImage.setScaleX(1f);
                cpuImage.setScaleY(1f);
                cpuImage.setX(64f*dip);
                cpuImage.setY(50f*dip);

                coolerImage.setScaleX(0.9f);
                coolerImage.setScaleY(0.9f);
                coolerImage.setX(50f*dip);
                coolerImage.setY(37f*dip);

                ram3View.setVisibility(View.VISIBLE);
                ram4View.setVisibility(View.VISIBLE);
                ram1View.setY(17*dip);
                ram2View.setY(17*dip);
                ram3View.setY(17*dip);
                ram4View.setY(17*dip);
                ram1View.setX(119*dip);
                ram2View.setX(126*dip);
                ram3View.setX(132*dip);
                ram4View.setX(139*dip);
                ram1View.setScaleY(0.8f);
                ram2View.setScaleY(0.8f);
                ram3View.setScaleY(0.8f);
                ram4View.setScaleY(0.8f);

                gpu1View.setY(117*dip);
                gpu1View.setX(7*dip);
                gpu2View.setY(158*dip);
                gpu2View.setX(7*dip);
                gpu2View.setVisibility(View.VISIBLE);
                break;
            }
            default:{
            }
        }
    }
}
