package com.spring.tutorial.user.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Component
@Slf4j
public class Helper {

    public static LocalDateTime getLocalUtcDateTime() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static Double toInch(Double cm) {
        return cm * 0.394;
    }

    public Date addHoursToJavaUtilDate(Date date, int hours, String addType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (addType.equals("HOUR"))
            calendar.add(Calendar.HOUR_OF_DAY, hours);
        if (addType.equals("DATE"))
            calendar.add(Calendar.DATE, hours);
        return calendar.getTime();
    }

}
