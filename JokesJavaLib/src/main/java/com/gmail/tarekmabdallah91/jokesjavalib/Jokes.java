package com.gmail.tarekmabdallah91.jokesjavalib;

import java.util.Random;

public class Jokes {

    private static String[] jokes = new String[]{"joke 0","joke 1","joke 2","joke 3","joke 4"};

    public static String getJoke (){
        int num = new Random().nextInt(jokes.length);
        return jokes[num];
    }
}
