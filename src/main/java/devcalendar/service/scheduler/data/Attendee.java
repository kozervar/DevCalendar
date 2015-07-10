package devcalendar.service.scheduler.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "timeSlot"})
public class Attendee implements Comparable<Attendee>
{
    private Integer id;

    private TimeSlot timeSlot;
    
    @Override
    public String toString() {
        return "[" + id + "]";
    }

    @Override
    public int compareTo(Attendee a) {
        if (this.getId() < a.getId()) return -1;
        if (this.getId() > a.getId()) return 1;
        return 0;
    }
}


