package devcalendar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
@Entity
@Data
@EqualsAndHashCode( callSuper = false, of = {"name", "email", "workFrom", "workTo" })
public class Attendee extends Identifiable {

    @Column(name="name", nullable=false)
    @NotNull
    private String name;

    @Column(name="email", nullable=false)
    @NotNull
    private String email;

    @Column(name="workFrom", nullable=false)
    @NotNull
    private Date workFrom;

    @Column(name="workTo", nullable=false)
    @NotNull
    private Date workTo;

    @OneToMany(mappedBy="attendee")
    private List<MeetingAttendee> meetingAttendees;
}
