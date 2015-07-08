package devcalendar.service.scheduler.data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode( of =
{ "start", "end" } )
public class TimeSlot implements Comparable<TimeSlot>
{
    private LocalDateTime start;
    private LocalDateTime end;
    
    public Duration duration()
    {
        return Duration.between( start, end );
    }

    @Override
    public String toString()
    {
        return "[ " + start + " - " + end + " ]";
    }

    @Override
    public int compareTo(TimeSlot ts) {
        LocalDateTime t1Start = this.getStart();
        LocalDateTime t2Start = ts.getStart();
        if (t1Start.isBefore(t2Start)) return -1;
        if (t2Start.isBefore(t1Start)) return 1;
        return 0;
    }
}
