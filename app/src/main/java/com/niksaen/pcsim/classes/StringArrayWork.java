package com.niksaen.pcsim.classes;

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

    public static String[] randomizeList(String[] List){
        String[] strings = new String[1];
        for(String str:List){
            if(Others.random(1,2)==2){
                strings = add(strings,str);
            }
        }
        return strings;
    }
}
