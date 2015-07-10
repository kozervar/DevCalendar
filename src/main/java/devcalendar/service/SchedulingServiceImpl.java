package devcalendar.service;

import devcalendar.model.Attendee;
import devcalendar.model.Meeting;
import devcalendar.service.scheduler.Scheduler;
import devcalendar.service.scheduler.TimeSlotFinder;
import org.springframework.util.Assert;


/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
public class SchedulingServiceImpl implements SchedulingService {

    private TimeSlotFinder timeSlotFinder = new TimeSlotFinder();
    private Scheduler scheduler = new Scheduler();

    public void addAttendeeToMeeting(Meeting meeting, Attendee attendee) {
        Assert.notNull(meeting, "Meeting cannot be null");
        Assert.notNull(attendee, "Attendee list cannot be null");
        Assert.notNull(meeting.getMeetingAttendees(), "MeetingAttendee field cannot be null");
        Assert.notNull(attendee.getMeetingAttendees(), "MeetingAttendee field cannot be null");

        if (meeting
                .getMeetingAttendees()
                .stream()
                .filter(ma -> ma.getAttendee().equals(attendee))
                .findAny()
                .isPresent()) {
            throw new IllegalArgumentException("Attendee already assigned to meeting");
        }

        // TODO Implement
    }
}
