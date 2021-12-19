package com.niksaen.pcsim.classes;

import java.util.HashMap;

public class ErrorCodeList {
    // ошибки категории АA - связаны с разгоном комплектующих
    // ошибки категории ВB - связаны с неисправностью или отсутствием комплектующих пк
    // ошибки категории СC - связаны с установкой драйверов
    // ошибки категории DD - это те ошибки которые связаны с работой дисковода
    // ошибки категории CL - это те ошибки которые связаны с работой калькулятора
    // ошибки категории CMD - это ошибки связанные с работой консоли

    public static HashMap<String,String> ErrorCodeText = new HashMap<>();
    static {
        ErrorCodeText.put("0xAA0001","Overheating of the processor due to its overclocking");
        ErrorCodeText.put("0xAA0002","Overheating of the video card due to its overclocking");
        ErrorCodeText.put("0xAA0003","Overheating of RAM due to its overclocking");
        ErrorCodeText.put("0xAA0004","The frequency of the RAM is too high");
        ErrorCodeText.put("0xAA0005","RAM frequency is too low");

        ErrorCodeText.put("0xBB0001","Power supply overload.");
        ErrorCodeText.put("0xBB0002","Overheating processor.");
        ErrorCodeText.put("0xBB0003","Fan not found.");
        ErrorCodeText.put("0xBB0004","Fan error.");

        ErrorCodeText.put("0xCL0001","The example does not have to start with an arithmetic sign.");
        ErrorCodeText.put("0xCL0002","The example must not end with an arithmetic sign.");
        ErrorCodeText.put("0xCL0003","Invalid number format.");

        ErrorCodeText.put("0xCMD0001","Package not found.");
        ErrorCodeText.put("0xCMD0002","Command not found.");
        ErrorCodeText.put("0xCMD0003","Storage device not change.");
        ErrorCodeText.put("0xCMD0004","Not enough space for installation.");
        ErrorCodeText.put("0xCMD0005","Installation completed.");

        ErrorCodeText.put("0xDD0001","Disk not found.");
    }
}
