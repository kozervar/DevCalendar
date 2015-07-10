package devcalendar.service.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import devcalendar.service.scheduler.data.Attendee;
import devcalendar.service.scheduler.data.AttendeeTimeSlot;
import devcalendar.service.scheduler.data.TimeSlot;

/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
public class SchedulerUtils
{
    
    public static Map.Entry< Attendee, List< TimeSlot >> findAttendeeWithMinNumberOfSlots( Map< Attendee, List< TimeSlot >> timeSlots )
    {
        Entry< Attendee, List< TimeSlot >> attendeeWithMinSlots =
            timeSlots.entrySet().stream().findAny().get();
        int size = attendeeWithMinSlots.getValue().size();
        for( Map.Entry< Attendee, List< TimeSlot > > entry : timeSlots.entrySet() )
        {
            if( entry.getValue().size() < size )
            {
                size = entry.getValue().size();
                attendeeWithMinSlots = entry;
            }
        }
        return attendeeWithMinSlots;
    }
    
    public static AttendeeTimeSlot findShortestAttendeeTimeSlot( Set< AttendeeTimeSlot > flatten )
    {
        return flatten.stream().reduce( (t1,t2)->{
            if(t1.getTimeSlot().duration().toMinutes() <= t2.getTimeSlot().duration().toMinutes()) return t1;
            else return t2;
        } ).get();
    }
    
    public static Set< AttendeeTimeSlot > flattenAttendeeTimeSlot( AttendeeTimeSlot ats )
    {
        Set< AttendeeTimeSlot > compatibleTimeSlots = new HashSet<>();
        compatibleTimeSlots.add( new AttendeeTimeSlot( ats.getAttendee(), ats.getTimeSlot() ) );
        ats.getCompatibleTimeSlots().forEach( n -> {
            compatibleTimeSlots.add( new AttendeeTimeSlot( n.getAttendee(), n.getTimeSlot() ) );
        } );
        return compatibleTimeSlots;
    }

    public static boolean isCompatible(Integer numberOfAttendees, AttendeeTimeSlot attendeeTimeSlot) {
        Integer id = attendeeTimeSlot.getAttendee().getId();
        Set<Integer> collect = attendeeTimeSlot.getCompatibleTimeSlots().stream().map(e -> e.getAttendee().getId()).collect(Collectors.toSet());
        collect.add(id);
        return collect.size() == numberOfAttendees;
    }
    
    public static TimeSlot createMatchingTimeSlot( TimeSlot t1, TimeSlot t2 )
    {
        LocalDateTime t1Start = t1.getStart();
        LocalDateTime t1End = t1.getEnd();

        LocalDateTime t2Start = t2.getStart();
        LocalDateTime t2End = t2.getEnd();

        LocalDateTime start = t1Start.isBefore( t2Start ) ? t2Start : t1Start;
        LocalDateTime end = t1End.isBefore( t2End ) ? t1End : t2End;

        if( start.isAfter( end ) )
            return new TimeSlot( end, start );
        return new TimeSlot( start, end );
    }

    public static boolean areCompatible( TimeSlot t1, TimeSlot t2, Integer duration )
    {
        LocalDateTime t1Start = t1.getStart();
        LocalDateTime t1End = t1.getEnd();

        LocalDateTime t2Start = t2.getStart();
        LocalDateTime t2End = t2.getEnd();

        LocalDateTime start = t1Start.isBefore( t2Start ) ? t2Start : t1Start;
        LocalDateTime end = t1End.isBefore( t2End ) ? t1End : t2End;

        Duration slotDuration = Duration.between( start, end );

        return slotDuration.toMinutes() >= duration;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }

    public static Date toDate(LocalDateTime date) {
        return Date.from(date.toInstant(ZoneOffset.UTC));
    }
}


