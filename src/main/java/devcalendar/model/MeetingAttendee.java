package devcalendar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
@Entity
@Data
@EqualsAndHashCode( callSuper = false, of = {"meeting", "attendee"})
public class MeetingAttendee extends Identifiable {

    @ManyToOne(optional = false)
    @NotNull
    private Meeting meeting;

    @ManyToOne(optional = false)
    @NotNull
    private Attendee attendee;
}
