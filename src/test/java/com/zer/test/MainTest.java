package com.zer.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author zer
 * @create 2016-12-02 10:35
 */
public class MainTest {

    @Test
    public void testCalendar() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2002-01-22"));
        calendar.add(Calendar.MONTH, -1);
        int i = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        System.out.println(i);
        System.out.println(month);
    }
}
