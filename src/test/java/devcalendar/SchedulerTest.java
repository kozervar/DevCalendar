package devcalendar;

import devcalendar.service.scheduler.Scheduler;
import devcalendar.service.scheduler.data.Attendee;
import devcalendar.service.scheduler.data.TimeSlot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
@RunWith(JUnit4.class)
public class SchedulerTest extends AbstractTest {
    private Scheduler scheduler;

    @Before
    public void before() {
        scheduler = new Scheduler();
    }

    @Test
    public void test0() {
        Map<Attendee, List<TimeSlot>> data = getData0();

        Set<TimeSlot> availableTimeSlots = scheduler.findAvailableTimeSlots(data, 30, 10);
        System.out.println("---------------FINAL-------------");
        availableTimeSlots.forEach(System.out::println);
        System.out.println("----------------------------");
    }

    @Test
    public void test1() {
        Map<Attendee, List<TimeSlot>> data = getData1();

        Set<TimeSlot> availableTimeSlots = scheduler.findAvailableTimeSlots(data, 30, 10);
        Assert.assertEquals(3, availableTimeSlots.size());
    }

    @Test
    public void test2() {
        Map<Attendee, List<TimeSlot>> data = getData2();

        Set<TimeSlot> availableTimeSlots = scheduler.findAvailableTimeSlots(data, 30, 10);
        Assert.assertEquals(3, availableTimeSlots.size());
        availableTimeSlots = scheduler.findAvailableTimeSlots(data, 30, 1);
        Assert.assertEquals(1, availableTimeSlots.size());
        availableTimeSlots = scheduler.findAvailableTimeSlots(data, 30, 2);
        Assert.assertEquals(2, availableTimeSlots.size());
    }

    @Test
    public void test3() {
        Map<Attendee, List<TimeSlot>> data = getData3();

        Set<TimeSlot> availableTimeSlots = scheduler.findAvailableTimeSlots(data, 30, 10);
        System.out.println("------------TS3----------------");
        availableTimeSlots.forEach( System.out::println );
        System.out.println("----------------------------");
        Assert.assertEquals(2, availableTimeSlots.size());
    }

    @Test
    public void test4() {
        Map<Attendee, List<TimeSlot>> data = getData4();

        Set<TimeSlot> availableTimeSlots =  scheduler.findAvailableTimeSlots(data, 30, 10);
//        System.out.println("------------TS4----------------");
//        availableTimeSlots.forEach(System.out::println);
//        System.out.println("----------------------------");
        Assert.assertEquals(2, availableTimeSlots.size());
    }

}


