package devcalendar.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin Kozaczyk on 2015-07-05.
 */
public class SchedulingUtils {
    private SchedulingUtils() {
    }

    public static boolean isTimeInRange(LocalTime timeFrom, LocalTime timeTo, LocalTime rangeFrom, LocalTime rangeTo) {
        return (timeFrom.equals(rangeFrom) || timeFrom.isAfter(rangeFrom)) &&
                (timeTo.equals(rangeTo) || timeTo.isBefore(rangeTo));
    }

    public static boolean isTimeOverlaping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.equals(end2) || start1.isBefore(end2)) &&
                (start2.equals(end1) || start2.isBefore(end1));
    }

    public static boolean isTimeOverlapingExclusive(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.isBefore(end2)) &&
                (start2.isBefore(end1));
    }

    public static boolean isTimeInRanges(LocalTime timeFrom, LocalTime timeTo, List<AbstractMap.SimpleEntry<LocalTime, LocalTime>> list) {
        return !list
                .stream()
                .filter(
                        se -> !isTimeInRange(timeFrom, timeTo, se.getKey(), se.getValue())
                ).findAny()
                .isPresent();
    }

    public static boolean doesTimeCollides(LocalTime meetingFrom, LocalTime meetingTo, List<AbstractMap.SimpleEntry<LocalTime, LocalTime>> list) {
        return list
                .stream().filter(
                        se -> isTimeOverlaping(meetingFrom, meetingTo, se.getKey(), se.getValue())
                ).findAny().isPresent();
    }

    public static boolean doesTimeCollidesExclusive(LocalTime meetingFrom, LocalTime meetingTo, List<AbstractMap.SimpleEntry<LocalTime, LocalTime>> list) {
        return list
                .stream().filter(
                        se -> isTimeOverlapingExclusive(meetingFrom, meetingTo, se.getKey(), se.getValue())
                ).findAny().isPresent();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }

    public static Date toDate(LocalDateTime date) {
        return Date.from(date.toInstant(ZoneOffset.UTC));
    }
}
