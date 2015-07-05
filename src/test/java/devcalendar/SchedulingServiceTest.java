package devcalendar;

import devcalendar.model.Attendee;
import devcalendar.model.Meeting;
import devcalendar.model.MeetingAttendee;
import devcalendar.service.SchedulingServiceImpl;
import devcalendar.service.SchedulingUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static devcalendar.service.SchedulingUtils.*;
/**
 * Created by Marcin Kozaczyk on 2015-07-05.
 */
@RunWith(JUnit4.class)
public class SchedulingServiceTest {

    @Test
    public void isAvailable(){
        SchedulingServiceImpl schedulingService = new SchedulingServiceImpl();

        Date workFrom = toDate(LocalDateTime.of(2015, 7, 2, 8, 0));
        Date workTo = toDate(LocalDateTime.of(2015, 7, 2, 16, 0));

        Date meetingFrom = toDate(LocalDateTime.of(2015, 7, 2, 10, 0));
        Date meetingTo = toDate(LocalDateTime.of(2015, 7, 2, 11, 0));

        Attendee attendee = Attendee.builder().workFrom(workFrom).workTo(workTo).meetingAttendees(new ArrayList<>()).build();
        Meeting meeting = Meeting.builder().startDate(meetingFrom).endDate(meetingTo).meetingAttendees(new ArrayList<>()).build();

        Assert.assertTrue(schedulingService.isAvailable(meeting, attendee));

        workTo = toDate(LocalDateTime.of(2015, 7, 2, 10, 30));
        attendee = Attendee.builder().workFrom(workFrom).workTo(workTo).meetingAttendees(new ArrayList<>()).build();
        Assert.assertFalse(schedulingService.isAvailable(meeting, attendee));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isAvailableButAlreadyAssigned(){
        SchedulingServiceImpl schedulingService = new SchedulingServiceImpl();

        Attendee attendee = new Attendee();
        Meeting meeting = new Meeting();

        MeetingAttendee meetingAttendee = new MeetingAttendee();
        meetingAttendee.setAttendee(attendee);
        meetingAttendee.setMeeting(meeting);
        meeting.getMeetingAttendees().add(meetingAttendee);

        schedulingService.isAvailable(meeting, attendee);
    }

    public static MeetingAttendee createMeetingAttendee(Attendee attendee, Meeting meeting) {
        MeetingAttendee meetingAttendee = new MeetingAttendee();
        meetingAttendee.setAttendee(attendee);
        meetingAttendee.setMeeting(meeting);

        return meetingAttendee;
    }

    @Test
    public void isAvailableMultipleMeetings(){
        SchedulingServiceImpl schedulingService = new SchedulingServiceImpl();

        Date workFrom = toDate(LocalDateTime.of(2015, 7, 2, 8, 0));
        Date workTo = toDate(LocalDateTime.of(2015, 7, 2, 16, 0));

        Attendee attendee = Attendee.builder().workFrom(workFrom).workTo(workTo).meetingAttendees(new ArrayList<>()).build();

        Date meetingFrom = toDate(LocalDateTime.of(2015, 7, 2, 10, 0));
        Date meetingTo = toDate(LocalDateTime.of(2015, 7, 2, 11, 0));

        Meeting meeting1 = Meeting.builder().startDate(meetingFrom).endDate(meetingTo).meetingAttendees(new ArrayList<>()).build();

        meetingFrom = toDate(LocalDateTime.of(2015, 7, 2, 12, 0));
         meetingTo = toDate(LocalDateTime.of(2015, 7, 2, 13, 0));
        Meeting meeting2 = Meeting.builder().startDate(meetingFrom).endDate(meetingTo).meetingAttendees(new ArrayList<>()).build();

        meetingFrom = toDate(LocalDateTime.of(2015, 7, 2, 14, 30));
        meetingTo = toDate(LocalDateTime.of(2015, 7, 2, 15, 0));
        Meeting meeting3 = Meeting.builder().startDate(meetingFrom).endDate(meetingTo).meetingAttendees(new ArrayList<>()).build();

        attendee.getMeetingAttendees().add(createMeetingAttendee(attendee, meeting1));
        attendee.getMeetingAttendees().add(createMeetingAttendee(attendee, meeting2));
        attendee.getMeetingAttendees().add(createMeetingAttendee(attendee, meeting3));

        /// ###########
        meetingFrom = toDate(LocalDateTime.of(2015, 7, 2, 11, 0));
        meetingTo = toDate(LocalDateTime.of(2015, 7, 2, 12, 0));
        Meeting meeting = Meeting.builder().startDate(meetingFrom).endDate(meetingTo).meetingAttendees(new ArrayList<>()).build();

        Assert.assertTrue(schedulingService.isAvailable(meeting, attendee));

        meetingFrom = toDate(LocalDateTime.of(2015, 7, 2, 10, 0));
        meetingTo = toDate(LocalDateTime.of(2015, 7, 2, 12, 0));
        meeting = Meeting.builder().startDate(meetingFrom).endDate(meetingTo).meetingAttendees(new ArrayList<>()).build();

        Assert.assertFalse(schedulingService.isAvailable(meeting, attendee));
    }
}
