package devcalendar;

import devcalendar.service.scheduler.data.Attendee;
import devcalendar.service.scheduler.data.TimeSlot;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
public class AbstractTest {

    public Attendee getAttendee(int id, int hFrom, int mFrom, int hTo, int mTo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime rangeFrom = now.withHour(hFrom).withMinute(mFrom).withSecond(0).withNano(0);
        LocalDateTime rangeTo = now.withHour(hTo).withMinute(mTo).withSecond(0).withNano(0);
        return new Attendee(id, new TimeSlot(rangeFrom, rangeTo));
    }

    public TimeSlot getMeeting(int hFrom, int mFrom, int hTo, int mTo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime rangeFrom = now.withHour(hFrom).withMinute(mFrom).withSecond(0).withNano(0);
        LocalDateTime rangeTo = now.withHour(hTo).withMinute(mTo).withSecond(0).withNano(0);
        return new TimeSlot(rangeFrom, rangeTo);
    }

    public Map<Attendee, Set<TimeSlot>> getAttendeesWithMeetings() {


        List<Attendee> attendees = new ArrayList<>(getAttendees());
        attendees.sort(Attendee::compareTo);

        Map<Attendee, Set<TimeSlot>> results = attendees.stream().sorted(Attendee::compareTo).collect(Collectors.toMap(e -> e, e -> new HashSet<TimeSlot>()));
        for (int i = 0, pos = 0; i < attendees.size(); i++, pos++) {
            Attendee attendee = attendees.get(i);
            switch (pos) {
                case 0: {
                    results.get(attendee).add(getMeeting(9, 0, 10, 0));
                    results.get(attendee).add(getMeeting(13, 0, 14, 0));
                    results.get(attendee).add(getMeeting(15, 30, 16, 0));
                    break;
                }
                case 1: {
                    results.get(attendee).add(getMeeting(11, 0, 12, 0));
                    results.get(attendee).add(getMeeting(15, 30, 16, 0));
                    break;
                }
                case 2: {
                    results.get(attendee).add(getMeeting(10, 30, 11, 30));
                    results.get(attendee).add(getMeeting(14, 0, 15, 0));
                    results.get(attendee).add(getMeeting(15, 30, 17, 0));
                    break;
                }
            }
        }

        return results;
    }

    public Set<Attendee> getAttendees() {
        Set<Attendee> attendees = new HashSet<>(3);

        attendees.add(getAttendee(1, 8, 0, 16, 0));
        attendees.add(getAttendee(2, 9, 0, 15, 30));
        attendees.add(getAttendee(3, 10, 0, 17, 0));

        return attendees;
    }

    public Set<TimeSlot> getMeetings() {
        Set<TimeSlot> meetings = new HashSet<>(4);

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
        return meetings;
    }

    public TimeSlot createTimeSlot(int sh, int sm, int eh, int em) {
        LocalDateTime start = LocalDateTime.of(2015, 7, 2, sh, sm);
        LocalDateTime end = LocalDateTime.of(2015, 7, 2, eh, em);
        return new TimeSlot(start, end);
    }

    protected Map<Attendee, List<TimeSlot>> getData0() {
        Map<Attendee, List<TimeSlot>> slots = new HashMap<>();

        List<TimeSlot> temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 0, 9, 0));
        temp.add(createTimeSlot(10, 0, 10, 30));
        temp.add(createTimeSlot(11, 30, 13, 0));
        slots.put(new Attendee(1, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 30, 9, 0));
        temp.add(createTimeSlot(9, 30, 11, 0));
        temp.add(createTimeSlot(11, 30, 12, 0));
        temp.add(createTimeSlot(12, 30, 13, 0));
        slots.put(new Attendee(2, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(11, 0, 11, 30));
        slots.put(new Attendee(3, null), temp);

        return slots;
    }

    protected Map<Attendee, List<TimeSlot>> getData1() {
        Map<Attendee, List<TimeSlot>> slots = new HashMap<>();

        List<TimeSlot> temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 0, 9, 0));
        temp.add(createTimeSlot(10, 0, 10, 30));
        temp.add(createTimeSlot(11, 30, 13, 0));
        slots.put(new Attendee(1, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 30, 9, 0));
        temp.add(createTimeSlot(9, 30, 11, 0));
        temp.add(createTimeSlot(11, 30, 12, 0));
        temp.add(createTimeSlot(12, 30, 13, 0));
        slots.put(new Attendee(2, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(9, 30, 10, 30));
        temp.add(createTimeSlot(11, 0, 13, 0));
        slots.put(new Attendee(3, null), temp);

        return slots;
    }

    protected Map<Attendee, List<TimeSlot>> getData2() {
        Map<Attendee, List<TimeSlot>> slots = new HashMap<>();

        List<TimeSlot> temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 0, 9, 0));
        temp.add(createTimeSlot(10, 0, 10, 30));
        temp.add(createTimeSlot(11, 30, 13, 0));
        temp.add(createTimeSlot(13, 30, 14, 0));
        slots.put(new Attendee(1, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(9, 30, 11, 0));
        temp.add(createTimeSlot(11, 30, 12, 0));
        temp.add(createTimeSlot(12, 30, 13, 0));
        temp.add(createTimeSlot(13, 30, 14, 30));
        slots.put(new Attendee(2, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(9, 30, 10, 30));
        temp.add(createTimeSlot(12, 0, 13, 0));
        temp.add(createTimeSlot(13, 30, 14, 0));
        slots.put(new Attendee(3, null), temp);

        return slots;
    }

    protected Map<Attendee, List<TimeSlot>> getData3() {
        Map<Attendee, List<TimeSlot>> slots = new HashMap<>();

        List<TimeSlot> temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 0, 9, 0));
        temp.add(createTimeSlot(10, 0, 10, 30));
        temp.add(createTimeSlot(11, 30, 13, 0));
        temp.add(createTimeSlot(13, 30, 14, 0));
        slots.put(new Attendee(1, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(9, 30, 11, 0));
        temp.add(createTimeSlot(11, 30, 12, 0));
        temp.add(createTimeSlot(12, 30, 13, 0));
        slots.put(new Attendee(2, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(9, 30, 10, 30));
        temp.add(createTimeSlot(11, 0, 13, 0));
        slots.put(new Attendee(3, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 0, 10, 00));
        temp.add(createTimeSlot(10, 30, 18, 0));
        slots.put(new Attendee(4, null), temp);

        return slots;
    }

    protected Map<Attendee, List<TimeSlot>> getData4() {
        Map<Attendee, List<TimeSlot>> slots = new HashMap<>();

        List<TimeSlot> temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 0, 9, 0));
        temp.add(createTimeSlot(10, 0, 10, 30));
        temp.add(createTimeSlot(11, 30, 13, 0));
        temp.add(createTimeSlot(13, 30, 14, 0));
        slots.put(new Attendee(1, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(9, 30, 11, 0));
        temp.add(createTimeSlot(11, 30, 13, 0));
        slots.put(new Attendee(2, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(9, 30, 10, 30));
        temp.add(createTimeSlot(11, 0, 13, 0));
        slots.put(new Attendee(3, null), temp);

        temp = new ArrayList<>();
        temp.add(createTimeSlot(8, 0, 11, 00));
        temp.add(createTimeSlot(11, 30, 18, 0));
        slots.put(new Attendee(4, null), temp);

        return slots;
    }
}
