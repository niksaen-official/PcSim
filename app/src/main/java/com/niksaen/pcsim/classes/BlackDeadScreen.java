package com.niksaen.pcsim.classes;

import java.util.HashMap;

public class BlackDeadScreen {
    // ошибки категории А - связаны с разгоном комплектующих
    // ошибки категории В - связаны с неисправностью или отсутствием комплектующих пк
    // ошибки категории С - связаны с установкой драйверов
    // ошибки категории D - это те ошибки которые не подходят ни под одну из категорий
    public static HashMap<String,String> ErrorCodeText = new HashMap<>();
    static {
        ErrorCodeText.put("0xAA0001","Overheating of the processor due to its overclocking");
        ErrorCodeText.put("0xAA0002","Overheating of the video card due to its overclocking");
        ErrorCodeText.put("0xAA0003","Overheating of RAM due to its overclocking");
        ErrorCodeText.put("0xAA0004","The frequency of the RAM is too high");
        ErrorCodeText.put("0xAA0005","RAM frequency is too low");

        ErrorCodeText.put("0xBB0001","Power supply overload");
        ErrorCodeText.put("0xBB0002","Overheating processor");
        ErrorCodeText.put("0xBB0003","Fan not found");
        ErrorCodeText.put("0xBB0004","Fan error");
    }
}
