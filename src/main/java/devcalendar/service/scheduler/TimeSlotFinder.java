package devcalendar.service.scheduler;

import devcalendar.service.scheduler.data.TimeSlot;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Marcin Kozaczyk on 2015-07-09.
 */
public class TimeSlotFinder {

    public static boolean isTimeOverlaping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return (start1.equals(end2) || start1.isBefore(end2)) &&
                (start2.equals(end1) || start2.isBefore(end1));
    }

    public static boolean isTimeOverlaping(TimeSlot t1, TimeSlot t2) {
        return isTimeOverlaping(t1.getStart(), t1.getEnd(), t2.getStart(), t2.getEnd());
    }

    public List<TimeSlot> getAvailableTimeSlots(Set<TimeSlot> meetings, List<TimeSlot> timeSlots) {
        List<TimeSlot> sorted = meetings.stream().sorted(TimeSlot::compareTo).collect(Collectors.toList());

        List<TimeSlot> results = new ArrayList<>();

        Set<TimeSlot> dailyTimeSlots = new HashSet<>(meetings.size());
        sorted.stream().forEach(meeting -> {
            dailyTimeSlots.addAll(
                    timeSlots.stream().filter(timeSlot -> isTimeOverlaping(timeSlot, meeting)).collect(Collectors.toList())
            );
        });

        for(TimeSlot timeSlot : dailyTimeSlots) {

            List<TimeSlot> slotsPerRange = sorted.stream().filter(e -> isTimeOverlaping(timeSlot, e)).sorted(TimeSlot::compareTo).collect(Collectors.toList());

            TimeSlot first = slotsPerRange.stream().sorted(TimeSlot::compareTo).findFirst().get();
            TimeSlot last = slotsPerRange.stream().sorted(TimeSlot::compareTo).reduce((previous, current) -> current).get();

            if(first.getStart().isBefore(timeSlot.getStart())) first.setStart(first.getStart().with(timeSlot.getStart().toLocalTime()));
            if(last.getEnd().isAfter(timeSlot.getEnd())) last.setEnd(last.getEnd().with(timeSlot.getEnd().toLocalTime()));

            LocalDateTime t1Start = timeSlot.getStart();
            LocalDateTime t1End = first.getStart();
            TimeSlot ts1 = new TimeSlot(t1Start, t1End);

            LocalDateTime t2Start = last.getEnd();
            LocalDateTime t2End = timeSlot.getEnd();
            TimeSlot ts2 = new TimeSlot(t2Start, t2End);
            results.add(ts1);
            results.add(ts2);

            TimeSlot previous = null;
            for (TimeSlot ts : slotsPerRange) {
                if(previous!= null) {
                    TimeSlot slot = new TimeSlot(previous.getEnd(), ts.getStart());
                    results.add(slot);
                    previous = ts;
                } else {
                    previous = ts;
                }
            }
        }
        results = results.stream().filter(e->!e.getStart().equals(e.getEnd())).collect(Collectors.toList());
        results = results.stream().sorted(TimeSlot::compareTo).collect(Collectors.toList());
        return results;
    }

    public List<TimeSlot> getWorkingTimeSlots(TimeSlot rangeTimeSlot, TimeSlot workingTimeSlot) {

        LocalDateTime rangeStart = rangeTimeSlot.getStart();
        LocalDateTime rangeEnd = rangeTimeSlot.getEnd();
        if (rangeTimeSlot.getStart().getHour() < workingTimeSlot.getStart().getHour()) {
            rangeStart = rangeTimeSlot.getStart().with(workingTimeSlot.getStart().toLocalTime()).withSecond(0).withNano(0);
        }
        if (rangeTimeSlot.getEnd().getHour() > workingTimeSlot.getEnd().getHour()) {
            rangeEnd = rangeTimeSlot.getEnd().with(workingTimeSlot.getEnd().toLocalTime()).withSecond(0).withNano(0);
        }

        Duration workingDuration = Duration.between(workingTimeSlot.getStart(), workingTimeSlot.getEnd());
        Duration rangeDuration = Duration.between(rangeStart, rangeEnd);

        List<TimeSlot> timeSlots = new ArrayList<>();
        if (workingDuration.toMinutes() < rangeDuration.toMinutes()) {
            // w included in range
            double times = Math.ceil(rangeDuration.toMinutes() / workingDuration.toMinutes());
            for (int i = 0; i < times; i++) {
                LocalDateTime start;
                if (i == 0 && rangeStart.toLocalTime().isAfter(workingTimeSlot.getStart().toLocalTime()))
                    start = rangeStart;
                else
                    start = rangeStart.with(workingTimeSlot.getStart().toLocalTime()).withSecond(0).withNano(0);
                start = start.plusDays((long) i);
                LocalDateTime end = start.with(workingTimeSlot.getEnd().toLocalTime()).withSecond(0).withNano(0);
                if (end.isAfter(rangeEnd)) {
                    end = start.with(rangeEnd.toLocalTime()).withSecond(0).withNano(0);
                    timeSlots.add(new TimeSlot(start, end));
                    break;
                }
                timeSlots.add(new TimeSlot(start, end));
            }
        } else {
            timeSlots.add(new TimeSlot(rangeStart, rangeEnd));
        }

        return timeSlots;
    }
}
