package devcalendar.service;

import devcalendar.model.Attendee;
import devcalendar.model.Meeting;
import devcalendar.model.MeetingAttendee;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static devcalendar.service.SchedulingUtils.isTimeInRange;
import static devcalendar.service.SchedulingUtils.isTimeOverlapingExclusive;
import static devcalendar.service.SchedulingUtils.toLocalDateTime;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
public class SchedulingServiceImpl implements SchedulingService {

    public boolean isAvailable(Meeting meeting, Attendee attendee) {
        Assert.notNull(meeting, "Meeting cannot be null");
        Assert.notNull(attendee, "Attendee cannot be null");

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

        boolean meetingInWorkingHours = isMeetingInWorkingHours(meeting, attendee);

        List<Meeting> attendeeMeetings = attendee
                .getMeetingAttendees()
                .stream()
                .map(MeetingAttendee::getMeeting)
                .collect(Collectors.toList());

        boolean meetingCollides = attendeeMeetings
                .stream()
                .filter(m -> doesMeetingsCollide(meeting, m))
                .findAny()
                .isPresent();

        return meetingInWorkingHours && !meetingCollides;
    }

    public boolean isMeetingInWorkingHours(Meeting meeting, Attendee attendee) {
        Assert.notNull(meeting, "Meeting cannot be null");
        Assert.notNull(attendee, "Attendee cannot be null");

        LocalTime meetingStart = toLocalDateTime(meeting.getStartDate()).toLocalTime();
        LocalTime meetingEnd = toLocalDateTime(meeting.getEndDate()).toLocalTime();

        LocalTime workFrom = toLocalDateTime(attendee.getWorkFrom()).toLocalTime();
        LocalTime workTo = toLocalDateTime(attendee.getWorkTo()).toLocalTime();

        return isTimeInRange(meetingStart, meetingEnd, workFrom, workTo);
    }

    public boolean doesMeetingsCollide(Meeting meeting1, Meeting meeting2) {
        Assert.notNull(meeting1, "Meeting cannot be null");
        Assert.notNull(meeting2, "Meeting cannot be null");

        LocalTime meeting1Start = toLocalDateTime(meeting1.getStartDate()).toLocalTime();
        LocalTime meeting1End = toLocalDateTime(meeting1.getEndDate()).toLocalTime();
        LocalTime meeting2Start = toLocalDateTime(meeting2.getStartDate()).toLocalTime();
        LocalTime meeting2End = toLocalDateTime(meeting2.getEndDate()).toLocalTime();

        return isTimeOverlapingExclusive(meeting1Start, meeting1End, meeting2Start, meeting2End);
    }
}
