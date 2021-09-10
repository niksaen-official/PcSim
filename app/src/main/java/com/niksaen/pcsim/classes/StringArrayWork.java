package com.niksaen.pcsim.classes;

import java.util.ArrayList;

public class StringArrayWork {
    public static String[] add(String[] strings,String str){
        String[] buff = new String[strings.length+1];
        for (int i = 0;i< buff.length;i++){
            if(i == buff.length-1){
                buff[i] = str;
            }
            else{
                buff[i] = strings[i];
            }
        }
        return buff;
    }
    public static String[] remove(String[] strings,int index){
        String[] buff = new String[strings.length-1];
        for(int i = 0;i<strings.length;i++){
            if(i==index){
                continue;
            }else {
                buff[i] = strings[i];
            }
        }
        return buff;
    }

    public static String[] randomizeArray(String[] List){
        String[] strings = new String[1];
        for(String str:List){
            if(Others.random(1,2)==2){
                strings = add(strings,str);
            }
        }
        return strings;
    }
    public static String ArrayListToString(ArrayList<String> arrayList){
        StringBuilder res = new StringBuilder();
        for(String item:arrayList){
            res.append(item);
            res.append(",");
        }
        return res.toString();
    }
    public static String[] ArrayListToArray(ArrayList<String> arrayList){
        String[] strings = new String[arrayList.size()];
        for(int i = 0;i<arrayList.size();i++){
            strings[i] = arrayList.get(i);
        }
        return strings;
    }
}
