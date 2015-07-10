package devcalendar.service.scheduler.data;

import static devcalendar.service.scheduler.SchedulerUtils.areCompatible;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode( of =
{ "attendee", "timeSlot" } )
public class AttendeeTimeSlot
{
    private @NonNull Attendee attendee;
    private @NonNull TimeSlot timeSlot;

    private Set< AttendeeTimeSlot > compatibleTimeSlots = new HashSet<>();

    @Override
    public String toString()
    {
        return "[ " + attendee + " - " + timeSlot + " ]==" + compatibleTimeSlots.toString() + " ";
    }

    public boolean isCompatible( Integer duration )
    {
        Set< AttendeeTimeSlot > compatibleTimeSlots = this.getCompatibleTimeSlots();
        boolean compatible = true;
        for( AttendeeTimeSlot cts : compatibleTimeSlots )
        {
            compatible = areCompatible( this.getTimeSlot(), cts.getTimeSlot(), duration );
            if( !compatible )
                break;
        }
        return compatible;
    }
}
