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
@EqualsAndHashCode(callSuper = false, of = {"name", "length", "startDate", "endDate"})
public class Meeting extends Identifiable {

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    @Column(name = "length", nullable = false)
    @NotNull
    private Integer length = 60;

    @Column(name = "startDate", nullable = false)
    @NotNull
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @OneToMany(mappedBy = "meeting")
    private List<MeetingAttendee> meetingAttendees = new ArrayList<>();
}
