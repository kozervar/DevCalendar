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
public class TimeSlotFinderTest {

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

        Set<TimeSlot> meetings = new HashSet<>(2);

        LocalDateTime m1From = LocalDateTime.of(2015, 7, 7, 12, 0, 0);
        LocalDateTime m1To = LocalDateTime.of(2015, 7, 7, 13, 0, 0);
        meetings.add(new TimeSlot(m1From, m1To));

        LocalDateTime m2From = LocalDateTime.of(2015, 7, 8, 12, 0, 0);
        LocalDateTime m2To = LocalDateTime.of(2015, 7, 8, 13, 0, 0);
        meetings.add(new TimeSlot(m2From, m2To));

        LocalDateTime m3From = LocalDateTime.of(2015, 7, 8, 14, 0, 0);
        LocalDateTime m3To = LocalDateTime.of(2015, 7, 8, 15, 0, 0);
        meetings.add(new TimeSlot(m3From, m3To));

        LocalDateTime m4From = LocalDateTime.of(2015, 7, 8, 15, 15, 0);
        LocalDateTime m4To = LocalDateTime.of(2015, 7, 8, 15, 50, 0);
        meetings.add(new TimeSlot(m4From, m4To));

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
