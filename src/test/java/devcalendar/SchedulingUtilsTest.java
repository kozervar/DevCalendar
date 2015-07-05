package devcalendar;

import devcalendar.service.SchedulingServiceImpl;
import devcalendar.service.SchedulingUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin Kozaczyk on 2015-07-05.
 */
@RunWith(JUnit4.class)
public class SchedulingUtilsTest {

    @Test
    public void isTimeInWorkingHours() {
        SchedulingServiceImpl schedulingService = new SchedulingServiceImpl();

        LocalTime meetingStart = LocalTime.of(10, 0);
        LocalTime meetingEnd = LocalTime.of(11, 0);

        LocalTime workFrom = LocalTime.of(10, 0);
        LocalTime workTo = LocalTime.of(11, 0);

        Assert.assertTrue(SchedulingUtils.isTimeInRange(meetingStart, meetingEnd, workFrom, workTo));
    }

    @Test
    public void areAllInRange() {
        LocalTime meetingStart = LocalTime.of(10, 0);
        LocalTime meetingEnd = LocalTime.of(13, 0);

        List<AbstractMap.SimpleEntry<LocalTime, LocalTime>> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LocalTime workFrom = LocalTime.of(9 + i, 0);
            LocalTime workTo = LocalTime.of(16 + i, 0);
//            System.out.println("Adding attende workign hours: " + workFrom.toString() + " - " + workTo.toString());
            list.add(new AbstractMap.SimpleEntry<>(workFrom, workTo));
        }

        Assert.assertTrue(SchedulingUtils.isTimeInRanges(meetingStart, meetingEnd, list));
    }

    @Test
    public void meetingCollides() {
        LocalTime meetingStart = LocalTime.of(10, 0);
        LocalTime meetingEnd = LocalTime.of(13, 0);

        List<AbstractMap.SimpleEntry<LocalTime, LocalTime>> list = new ArrayList<>();

        list.add(new AbstractMap.SimpleEntry<>(LocalTime.of(9, 0), LocalTime.of(10, 0)));
        Assert.assertTrue(SchedulingUtils.doesTimeCollides(meetingStart, meetingEnd, list));
        Assert.assertFalse(SchedulingUtils.doesTimeCollidesExclusive(meetingStart, meetingEnd, list));

        list.add(new AbstractMap.SimpleEntry<>(LocalTime.of(12, 0), LocalTime.of(14, 0)));
        Assert.assertTrue(SchedulingUtils.doesTimeCollidesExclusive(meetingStart, meetingEnd, list));
    }
}
