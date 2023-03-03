package com.example.zdtest.util;

import java.util.regex.Pattern;

public class PatternUtils {
    private static final String regex = "^[a-zA-Z0-9]+$";

    public static boolean compileUserName(String userName){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(userName).matches();
    }

}
