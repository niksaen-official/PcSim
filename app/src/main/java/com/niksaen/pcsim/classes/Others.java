package com.niksaen.pcsim.classes;

import java.util.Random;

public class Others {
    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
