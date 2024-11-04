package com.shivam.instagram.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;


@Component
public class Time 
{
     public String getGMT_Time(String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date = Date.from(Instant.now());

        String dateTime = sdf.format(date);

        return dateTime;
    }


    public long getGmtDateInMilliSec()
    {

        long milliSec = Instant.now().toEpochMilli();

        return milliSec;
        
    }
}
