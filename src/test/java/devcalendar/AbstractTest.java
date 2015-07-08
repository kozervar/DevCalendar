package devcalendar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import devcalendar.service.scheduler.data.Attendee;
import devcalendar.service.scheduler.data.AttendeeTimeSlot;
import devcalendar.service.scheduler.data.TimeSlot;


/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
public class AbstractTest
{

    public TimeSlot createTimeSlot( int sh, int sm, int eh, int em )
    {
        LocalDateTime start = LocalDateTime.of( 2015, 7, 2, sh, sm );
        LocalDateTime end = LocalDateTime.of( 2015, 7, 2, eh, em );
        return new TimeSlot( start, end );
    }

    protected Map< Attendee, List< TimeSlot >> getData0()
    {
        Map< Attendee, List< TimeSlot >> slots = new HashMap<>();

        List< TimeSlot > temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 0, 9, 0 ) );
        temp.add( createTimeSlot( 10, 0, 10, 30 ) );
        temp.add( createTimeSlot( 11, 30, 13, 0 ) );
        slots.put( new Attendee( 1 ), temp );

        temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 30, 9, 0 ) );
        temp.add( createTimeSlot( 9, 30, 11, 0 ) );
        temp.add( createTimeSlot( 11, 30, 12, 0 ) );
        temp.add( createTimeSlot( 12, 30, 13, 0 ) );
        slots.put( new Attendee( 2 ), temp );

        temp = new ArrayList<>();
        temp.add( createTimeSlot( 11, 0, 11, 30 ) );
        slots.put( new Attendee( 3 ), temp );

        return slots;
    }
    
    protected Map< Attendee, List< TimeSlot >> getData1()
    {
        Map< Attendee, List< TimeSlot >> slots = new HashMap<>();

        List< TimeSlot > temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 0, 9, 0 ) );
        temp.add( createTimeSlot( 10, 0, 10, 30 ) );
        temp.add( createTimeSlot( 11, 30, 13, 0 ) );
        slots.put( new Attendee( 1 ), temp );

        temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 30, 9, 0 ) );
        temp.add( createTimeSlot( 9, 30, 11, 0 ) );
        temp.add( createTimeSlot( 11, 30, 12, 0 ) );
        temp.add( createTimeSlot( 12, 30, 13, 0 ) );
        slots.put( new Attendee( 2 ), temp );

        temp = new ArrayList<>();
        temp.add( createTimeSlot( 9, 30, 10, 30 ) );
        temp.add( createTimeSlot( 11, 0, 13, 0 ) );
        slots.put( new Attendee( 3 ), temp );

        return slots;
    }
    
    protected Map< Attendee, List< TimeSlot >> getData2()
    {
        Map< Attendee, List< TimeSlot >> slots = new HashMap<>();

        List< TimeSlot > temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 0, 9, 0 ) );
        temp.add( createTimeSlot( 10, 0, 10, 30 ) );
        temp.add( createTimeSlot( 11, 30, 13, 0 ) );
        temp.add( createTimeSlot( 13, 30, 14, 0 ) );
        slots.put( new Attendee( 1 ), temp );

        temp = new ArrayList<>();
        temp.add( createTimeSlot( 9, 30, 11, 0 ) );
        temp.add( createTimeSlot( 11, 30, 12, 0 ) );
        temp.add( createTimeSlot( 12, 30, 13, 0 ) );
        temp.add( createTimeSlot( 13, 30, 14, 30 ) );
        slots.put( new Attendee( 2 ), temp );

        temp = new ArrayList<>();
        temp.add( createTimeSlot( 9, 30, 10, 30 ) );
        temp.add( createTimeSlot( 12, 0, 13, 0 ) );
        temp.add( createTimeSlot( 13, 30, 14, 0 ) );
        slots.put( new Attendee( 3 ), temp );

        return slots;
    }
    protected Map< Attendee, List< TimeSlot >> getData3()
    {
        Map< Attendee, List< TimeSlot >> slots = new HashMap<>();
        
        List< TimeSlot > temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 0, 9, 0 ) );
        temp.add( createTimeSlot( 10, 0, 10, 30 ) );
        temp.add( createTimeSlot( 11, 30, 13, 0 ) );
        temp.add( createTimeSlot( 13, 30, 14, 0 ) );
        slots.put( new Attendee( 1 ), temp );
        
        temp = new ArrayList<>();
        temp.add( createTimeSlot( 9, 30, 11, 0 ) );
        temp.add( createTimeSlot( 11, 30, 12, 0 ) );
        temp.add( createTimeSlot( 12, 30, 13, 0 ) );
        slots.put( new Attendee( 2 ), temp );
        
        temp = new ArrayList<>();
        temp.add( createTimeSlot( 9, 30, 10, 30 ) );
        temp.add( createTimeSlot( 11, 0, 13, 0 ) );
        slots.put( new Attendee( 3 ), temp );

        temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 0, 10, 00 ) );
        temp.add( createTimeSlot( 10, 30, 18, 0 ) );
        slots.put( new Attendee( 4 ), temp );
        
        return slots;
    }
    protected Map< Attendee, List< TimeSlot >> getData4()
    {
        Map< Attendee, List< TimeSlot >> slots = new HashMap<>();
        
        List< TimeSlot > temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 0, 9, 0 ) );
        temp.add( createTimeSlot( 10, 0, 10, 30 ) );
        temp.add( createTimeSlot( 11, 30, 13, 0 ) );
        temp.add( createTimeSlot( 13, 30, 14, 0 ) );
        slots.put( new Attendee( 1 ), temp );
        
        temp = new ArrayList<>();
        temp.add( createTimeSlot( 9, 30, 11, 0 ) );
        temp.add( createTimeSlot( 11, 30, 13, 0 ) );
        slots.put( new Attendee( 2 ), temp );
        
        temp = new ArrayList<>();
        temp.add( createTimeSlot( 9, 30, 10, 30 ) );
        temp.add( createTimeSlot( 11, 0, 13, 0 ) );
        slots.put( new Attendee( 3 ), temp );
        
        temp = new ArrayList<>();
        temp.add( createTimeSlot( 8, 0, 11, 00 ) );
        temp.add( createTimeSlot( 11, 30, 18, 0 ) );
        slots.put( new Attendee( 4 ), temp );
        
        return slots;
    }
}
