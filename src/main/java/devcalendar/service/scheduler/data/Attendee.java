package devcalendar.service.scheduler.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Marcin Kozaczyk on 8 lip 2015.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Attendee
{
    private Integer id;
    
    @Override
    public String toString() {
        return "[" + id + "]";
    }
}


