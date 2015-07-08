package devcalendar.service.scheduler;

import static devcalendar.service.scheduler.SchedulerUtils.areCompatible;
import static devcalendar.service.scheduler.SchedulerUtils.findAttendeeWithMinNumberOfSlots;
import static devcalendar.service.scheduler.SchedulerUtils.findShortestAttendeeTimeSlot;
import static devcalendar.service.scheduler.SchedulerUtils.flattenAttendeeTimeSlot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import devcalendar.service.scheduler.data.Attendee;
import devcalendar.service.scheduler.data.AttendeeTimeSlot;
import devcalendar.service.scheduler.data.TimeSlot;

/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
public class Scheduler
{
    public Scheduler()
    {
    }

    public Set< TimeSlot > findAvailableTimeSlots( Map< Attendee, List< TimeSlot >> timeSlots,
        Integer duration, Integer limit )
    {

        Entry< Attendee, List< TimeSlot >> attendeeWithMinSlots = SchedulerUtils.findAttendeeWithMinNumberOfSlots(timeSlots);
        Attendee attendee = attendeeWithMinSlots.getKey();
        List< TimeSlot > attendeeTimeSlots = attendeeWithMinSlots.getValue();

        Map< Attendee, List< TimeSlot >> reducedTimeSlots =
            timeSlots.entrySet().stream().filter( e -> !e.getKey().equals( attendee ) )
                .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ) );

        Set< AttendeeTimeSlot > matchingTimeSlots =
            getMatchingTimeSlots( attendee, attendeeTimeSlots, reducedTimeSlots, duration );

        matchingTimeSlots = getMatchingAndAdditionalTimeSlots( reducedTimeSlots, matchingTimeSlots, duration );

        // Each time slot should be the shortest one
        matchingTimeSlots =
            matchingTimeSlots
                .stream()
                .map(
                    mts -> {
                        Set< AttendeeTimeSlot > flatten = SchedulerUtils.flattenAttendeeTimeSlot(mts);
                        AttendeeTimeSlot ats = SchedulerUtils.findShortestAttendeeTimeSlot(flatten);
                        Set< AttendeeTimeSlot > compatible =
                            flatten.stream().filter( t -> !t.equals( ats ) ).collect( Collectors.toSet() );
                        ats.getCompatibleTimeSlots().addAll( compatible );
                        return ats;
                    } ).filter( ats -> ats.isCompatible( duration ) ).collect( Collectors.toSet() );

        if( matchingTimeSlots.size() == 0 && reducedTimeSlots.size() > 0 )
        {
            return findAvailableTimeSlots( reducedTimeSlots, duration, limit );
        }
        return matchingTimeSlots.stream().map( e -> e.getTimeSlot() ).limit( limit )
            .collect( Collectors.toSet() );
    }

    public Set< AttendeeTimeSlot > getMatchingTimeSlots( Attendee attendee, List< TimeSlot > timeSlots,
        Map< Attendee, List< TimeSlot >> attendees, Integer duration )
    {
        Set< AttendeeTimeSlot > attendeeTimeSlots = new HashSet<>();
        timeSlots.forEach( timeSlot -> {
            AttendeeTimeSlot attendeeTimeSlot = new AttendeeTimeSlot( attendee, timeSlot );

            attendees.entrySet().forEach(
                entry -> {
                    List< TimeSlot > currAttendeeTimeSlots = entry.getValue();
                    Set< AttendeeTimeSlot > compatibleTimeSlots =
                        currAttendeeTimeSlots.stream().filter( ts -> SchedulerUtils.areCompatible(timeSlot, ts, duration) )
                            .map( e -> new AttendeeTimeSlot( entry.getKey(), e ) )
                            .collect( Collectors.toSet() );

                    attendeeTimeSlot.getCompatibleTimeSlots().addAll( compatibleTimeSlots );
                } );
            if( attendeeTimeSlot.getCompatibleTimeSlots().size() >= attendees.size() )
            {
                attendeeTimeSlots.add( attendeeTimeSlot );
            }
        } );
        return attendeeTimeSlots;
    }

    public Set< AttendeeTimeSlot > getMatchingAndAdditionalTimeSlots(
        Map< Attendee, List< TimeSlot >> otherTimeSlots, Set< AttendeeTimeSlot > matchingTimeSlots,
        Integer duration )
    {
        List< AttendeeTimeSlot > additionalTimeSlots =
            matchingTimeSlots
                .stream()
                .filter( ats -> ats.getCompatibleTimeSlots().size() > otherTimeSlots.size() )
                // filter for additional time slots
                .flatMap(
                    ats -> {
                        List< AttendeeTimeSlot > additionalMatchingTimeSlots = new ArrayList<>();
                        Map< Attendee, List< AttendeeTimeSlot >> groups = groupByAttendees( ats );
                        groups
                            .entrySet()
                            .stream()
                            .filter( e -> e.getValue().size() > 1 )
                            // filter for multiple matching time slots
                            .forEach(
                                e -> {
                                    ats.getCompatibleTimeSlots().removeAll( e.getValue() );
                                    Set< AttendeeTimeSlot > compatibleTimeSlots =
                                        SchedulerUtils.flattenAttendeeTimeSlot(ats);

                                    List< AttendeeTimeSlot > matchingTS =
                                        createAdditionalTimeSlots( e.getValue(), compatibleTimeSlots,
                                            duration );

                                    additionalMatchingTimeSlots.addAll( matchingTS );
                                } );
                        return additionalMatchingTimeSlots.stream();
                    } ).collect( Collectors.toList() );
        // Filter out multiple time slots and Join additional time slots
        Set< AttendeeTimeSlot > collection =
            matchingTimeSlots.stream()
                .filter( e -> e.getCompatibleTimeSlots().size() == otherTimeSlots.size() )
                .collect( Collectors.toSet() );
        collection.addAll( additionalTimeSlots );
        return collection;
    }

    private List< AttendeeTimeSlot > createAdditionalTimeSlots( List< AttendeeTimeSlot > additionalTimeSlots,
        Set< AttendeeTimeSlot > compatibleTimeSlots, Integer duration )
    {
        return additionalTimeSlots.stream()
            // filter out incompatible time slots
            .filter( ats -> ats.isCompatible( duration ) )
            .map(
                additionalTimeSlot -> {
                    AttendeeTimeSlot temp =
                        new AttendeeTimeSlot( additionalTimeSlot.getAttendee(), additionalTimeSlot
                            .getTimeSlot() );
                    temp.getCompatibleTimeSlots().addAll( compatibleTimeSlots );
                    return temp;
                } ).collect( Collectors.toList() );
    }

    private Map< Attendee, List< AttendeeTimeSlot >> groupByAttendees( AttendeeTimeSlot ats )
    {
        Map< Attendee, List< AttendeeTimeSlot >> groups =
            ats.getCompatibleTimeSlots().stream()
                .collect( Collectors.groupingBy( AttendeeTimeSlot::getAttendee ) );
        return groups;
    }

}
