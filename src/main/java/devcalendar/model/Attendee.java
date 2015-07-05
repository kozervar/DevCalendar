package devcalendar.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"name", "email", "workFrom", "workTo"})
public class Attendee extends Identifiable {

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "email", nullable = false)
    @NotNull
    private String email;

    @Column(name = "workFrom", nullable = false)
    @NotNull
    private Date workFrom;

    @Column(name = "workTo", nullable = false)
    @NotNull
    private Date workTo;

    @OneToMany(mappedBy = "attendee")
    private List<MeetingAttendee> meetingAttendees = new ArrayList<>();
}
