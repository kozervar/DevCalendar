package devcalendar.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
@Entity
@Data
@EqualsAndHashCode( callSuper = false, of = {"name", "length", "startDate", "endDate" })
public class Meeting extends Identifiable {

    @Column(name="name", nullable=false)
    @NotNull
    private String name;

    @Column(name="length", nullable=false)
    @NotNull
    private Integer length = 60;

    @Column(name="startDate", nullable=false)
    @NotNull
    private Date startDate;

    @Column(name="endDate")
    private Date endDate;

    @OneToMany(mappedBy="meeting")
    private List<MeetingAttendee> meetingAttendees;

}
