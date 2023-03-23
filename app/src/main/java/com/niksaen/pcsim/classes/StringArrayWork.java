package com.niksaen.pcsim.classes;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.internal.markers.KMutableList;

public class StringArrayWork {
    public static String[] add(String string,String[] strings){
        ArrayList<String> list = new ArrayList<>();
        for (int i = -1;i< strings.length;i++){
            if(i == -1){
                list.add(string);
            }
            else{
                list.add(strings[i]);
            }
        }
        return StringArrayWork.ArrayListToArray(list);
    }
    public static String[][] add(String[][] array,String[] strings){
        String[][] res = new String[array.length+1][];
        for(int i = 0;i< res.length;i++){
            if(i< res.length-1) {
                res[i] = array[i];
            }
            else {
                res[i] = strings;
            }
        }
        return res;
    }
    public static String[] concatAll(String[][] strings){
        String res = "";
        for(String[] itemStrings: strings){
            for (String item: itemStrings){
                if(!res.contains(item)){
                    res+=item+",";
                }
            }
        }
        return res.split(",");
    }
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

    public static String ArrayListToString(String[] arrayList) {
        StringBuilder res = new StringBuilder();
        for(String item:arrayList){
            res.append(item);
            res.append(",");
        }
        return res.toString();
    }

    public static String[] clearEmpty(String[] array) {
        return ArrayListToString(array).replaceAll("null","").replaceAll(",,",",").split(",");
    }

    @Nullable
    public static String MutableListToString(@Nullable List<String> buff) {
        StringBuilder res = new StringBuilder();
        for(String item:buff){
            res.append(item);
            res.append(",");
        }
        return res.toString();
    }
}
