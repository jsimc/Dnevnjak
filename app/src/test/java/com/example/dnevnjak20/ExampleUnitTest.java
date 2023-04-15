package com.example.dnevnjak20;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int day, month, year;
        LocalDate now = LocalDate.now();
        day = now.getDayOfMonth();
        month = now.getMonthValue();
        year = now.getYear();
        System.out.println("now: " + now);
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);


    }
}