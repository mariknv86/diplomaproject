package ru.mariknv86.blog.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Utils {

    public static long convertLocalDateTimeToMillis(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

}
