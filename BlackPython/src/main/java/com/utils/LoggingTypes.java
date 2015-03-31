package com.utils;

/**
 * Created by Snow on 3/22/2015.
 */
public enum LoggingTypes {
    FACEBOOK("Facebook", 0),
    FREE("Free", 1);

    private String stringValue;
    private int intValue;

    private LoggingTypes(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public int getIntValue(){
        return intValue;
    }
}
