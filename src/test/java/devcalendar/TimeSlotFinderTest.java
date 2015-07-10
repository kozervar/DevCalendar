package devcalendar;

import devcalendar.service.scheduler.TimeSlotFinder;
import devcalendar.service.scheduler.data.TimeSlot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Marcin Kozaczyk on 2015-07-08.
 */
@RunWith(JUnit4.class)
public class TimeSlotFinderTest extends  AbstractTest{

    private TimeSlotFinder timeSlotFinder;

    @Before
    public void before(){
        timeSlotFinder = new TimeSlotFinder();
    }
    @Test
    public void test() {

//        LocalDateTime rangeFrom = LocalDateTime.of(2015, 7, 8, 7, 0, 0);

//        LocalDateTime rangeFrom = LocalDateTime.of(2015, 7, 7, 14, 0, 0);
//        LocalDateTime rangeTo = LocalDateTime.of(2015, 7, 9, 12, 0, 0);

        LocalDateTime rangeFrom = LocalDateTime.of(2015, 7, 7, 9, 0, 0);
        LocalDateTime rangeTo = LocalDateTime.of(2015, 7, 9, 14, 0, 0);

        TimeSlot rangeTimeSlot = new TimeSlot(rangeFrom, rangeTo);

        LocalDateTime workingFrom = LocalDateTime.of(2015, 7, 8, 8, 0, 0);
        LocalDateTime workingTo = LocalDateTime.of(2015, 7, 8, 16, 0, 0);

        TimeSlot workingTimeSlot = getWorkingTimeSlot(workingFrom, workingTo);

        Set<TimeSlot> meetings = getMeetings();

        List<TimeSlot> timeSlots = timeSlotFinder.getWorkingTimeSlots(rangeTimeSlot, workingTimeSlot);

        List<TimeSlot> results = timeSlotFinder.getAvailableTimeSlots(meetings, timeSlots);

        System.out.println("");
        System.out.println("-------------------------");
        System.out.println("");

        results.forEach(System.out::println);
    }

    private TimeSlot getWorkingTimeSlot(LocalDateTime workingFrom, LocalDateTime workingTo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startsAt = now.withHour(workingFrom.getHour()).withMinute(workingFrom.getMinute());
        LocalDateTime finishesAt = now.withHour(workingTo.getHour()).withMinute(workingTo.getMinute());
        return new TimeSlot(startsAt, finishesAt);
    }

}
