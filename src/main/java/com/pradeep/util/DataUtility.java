package com.pradeep.util;

import java.util.Random;

public class DataUtility {

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
