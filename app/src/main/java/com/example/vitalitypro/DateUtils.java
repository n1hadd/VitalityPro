package com.example.vitalitypro;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class DateUtils {
    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }
}


