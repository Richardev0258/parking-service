package com.parking.service.util;

import java.util.regex.Pattern;


public class PlateValidator {
    private static final Pattern CAR_PATTERN = Pattern.compile("^[A-Z]{3}[0-9]{3}$");
    private static final Pattern MOTOR_PATTERN = Pattern.compile("^[A-Z]{3}[0-9]{2}[A-Z]$");


    public static boolean isValid(String plate) {
        if (plate == null) return false;
        String p = plate.trim().toUpperCase();
        return CAR_PATTERN.matcher(p).matches() || MOTOR_PATTERN.matcher(p).matches();
    }
}
