package com.niksaen.pcsim.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Others {
    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static ArrayList<String> randomizeList(ArrayList<String> arrayList){
        ArrayList<String> strings = new ArrayList<>();
        for(String str:arrayList){
            if(random(1,2)==2){
                strings.add(str);
            }
        }
        return strings;
    }
    public static ArrayList<String> randomizeList1(String[] arrayList){
        ArrayList<String> strings = new ArrayList<>();
        for(String str:arrayList){
            if(random(1,2)==2){
                strings.add(str);
            }
        }
        return strings;
    }

    public static ArrayList<String> filter(ArrayList<String > arrayList){
        ArrayList<String> res = new ArrayList<>();
        for(String item:arrayList){
            res.add(item.replaceAll("\\s{2,}", " ").trim());
        }
        return res;
    }
    public static ArrayList<String> clearEmpty(ArrayList<String> arrayList){
        arrayList.removeAll(Collections.singleton(null));
        arrayList.removeAll(Collections.singleton(""));
        return arrayList;
    }
    public static String ArrayToString(String[] list){
        String str = "";
        for(int i = 0;i< list.length;i++){
            str += list[i];
            if(i != list.length-1){
                str += ",";
            }
        }
        return str;
    }
}
