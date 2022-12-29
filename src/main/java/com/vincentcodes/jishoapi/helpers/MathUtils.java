package com.vincentcodes.jishoapi.helpers;

public class MathUtils {
    public static int getRandomInt(int min, int max){
        return (int)Math.floor(Math.random()*(max-min)+min);
    }
    public static int getRandomInt(int max){
        return getRandomInt(0, max);
    }
}
