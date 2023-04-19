package com.example.dnevnjak20;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.model.enums.ObligationPriority;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        int day, month, year;
//        LocalDate now = LocalDate.now();
//        day = now.getDayOfMonth();
//        month = now.getMonthValue();
//        year = now.getYear();
//        System.out.println("now: " + now);
//        System.out.println("day: " + day);
//        System.out.println("month: " + month);
//        System.out.println("year: " + year);

        LocalTime from1 = LocalTime.of(12, 0);
        LocalTime to1 = LocalTime.of(1, 15);
        LocalTime from2 = LocalTime.of(12, 0);
        LocalTime to2 = LocalTime.of(1, 46);
        DateItem dateItem = new DateItem(2023, 4, 18);
        assertTrue(dateItem.addPlan(new Plan("ime", ObligationPriority.LOW_PRIORITY, LocalDate.now(), from1, to1, "info1")));
        assertFalse(dateItem.addPlan(new Plan("ime2", ObligationPriority.LOW_PRIORITY, LocalDate.now(), from2, to2, "long info 2")));
/////////////////////////////////////////////////////////
//         from1 = LocalTime.of(12, 1);
//         to1 = LocalTime.of(13, 0);
//         from2 = LocalTime.of(11, 0);
//         to2 = LocalTime.of(11, 45);
//        assertTrue(dateItem.addPlan(new Plan("ime", ObligationPriority.LOW_PRIORITY, LocalDate.now(), from1, to1, "info3")));
//        assertFalse(dateItem.addPlan(new Plan("ime2", ObligationPriority.LOW_PRIORITY, LocalDate.now(), from2, to2, "long info 4")));
/////////////////////////////////////////////////////////
//
//        assertTrue(dateItem.addPlan(new Plan("imee", ObligationPriority.LOW_PRIORITY, LocalDate.of(2023, 4, 19), from1, to1, "")));
//
//        assertFalse(dateItem.addPlan(new Plan("imee", ObligationPriority.LOW_PRIORITY, LocalDate.of(2023, 4, 18), from1, to1, "")));



    }
    @Test
    public void test2() {
        List<String> lista = new ArrayList<>();
        lista.add("3");
        lista.add("4");
        assertEquals("3", lista.get(0));
        lista.remove(0);
        assertEquals("4", lista.get(0));
        assertEquals("4", !lista.isEmpty() ? lista.get(0) : null);
        lista.remove(0);
        assertEquals(null, !lista.isEmpty() ? lista.get(0) : null);
    }

    @Test
    public void test3() {
        Plan p1 = new Plan("name1", ObligationPriority.LOW_PRIORITY, LocalDate.now(),
                LocalTime.of(17, 00), LocalTime.of(18, 00), "");

        Plan p2 = new Plan("name2", ObligationPriority.MID_PRIORITY, LocalDate.now(),
                LocalTime.of(16, 00), LocalTime.of(16, 45), "");
        Plan p3 = new Plan("name1", ObligationPriority.LOW_PRIORITY, LocalDate.now(),
                LocalTime.of(17, 00), LocalTime.of(18, 00), "");
        List<Plan> planovi = new ArrayList<>();
        planovi.add(p1);
        planovi.add(p2);
        Collections.sort(planovi);
        System.out.println(planovi);
        assertFalse(p1.equals(p2));
        assertTrue(p1.equals(p3));
    }

    @Test
    public void test4() {
        LocalDate date = LocalDate.of(2023, 4, 15);
        LocalTime time = LocalTime.of(14, 55);
        String dateStr = date.toString();
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println(LocalDate.parse(dateStr));
        int b = 04;
        System.out.println(b);
    }
}