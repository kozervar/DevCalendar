package devcalendar;

import devcalendar.service.scheduler.Scheduler;
import devcalendar.service.scheduler.TimeSlotFinder;
import devcalendar.service.scheduler.data.Attendee;
import devcalendar.service.scheduler.data.TimeSlot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Marcin Kozaczyk on 2015-07-09.
 */
@RunWith(JUnit4.class)
public class SchedulingServiceTest extends AbstractTest{

    private TimeSlotFinder timeSlotFinder;
    private Scheduler scheduler;

    @Before
    public void before(){
        timeSlotFinder = new TimeSlotFinder();
        scheduler = new Scheduler();
    }
    @Test
    public void test(){
        Map<Attendee, Set<TimeSlot>> attendeesWithMeetings = getAttendeesWithMeetings();

        LocalDateTime rangeFrom = LocalDateTime.now().minusDays(0).withHour(7).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime rangeTo = LocalDateTime.now().plusDays(0).withHour(16).withMinute(0).withSecond(0).withNano(0);
        TimeSlot rangeTimeSlot = new TimeSlot(rangeFrom, rangeTo);


        Map<Attendee, List<TimeSlot>> attendeesTimeSlots = attendeesWithMeetings.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey())).map(e -> {
            Attendee attendee = e.getKey();
            Set<TimeSlot> meetings = e.getValue();

            List<TimeSlot> workingTimeSlots = timeSlotFinder.getWorkingTimeSlots(rangeTimeSlot, attendee.getTimeSlot());
            List<TimeSlot> availableTimeSlots = timeSlotFinder.getAvailableTimeSlots(meetings, workingTimeSlots);
            System.out.println("");
            System.out.println("Attendee " + attendee + " free time slots");
            availableTimeSlots.forEach(System.out::println);
            return new AbstractMap.SimpleEntry<>(attendee, availableTimeSlots);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Set<TimeSlot> availableTimeSlots = scheduler.findAvailableTimeSlots(attendeesTimeSlots, 30, 10);

        System.out.println("");
        System.out.println("-------------------");
        availableTimeSlots.forEach(e->System.out.println(e));

    }
}
