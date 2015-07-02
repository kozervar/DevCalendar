package devcalendar.repository;

import devcalendar.model.Meeting;
import devcalendar.model.MeetingAttendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
public interface MeetingAttendeeRepository extends JpaRepository<MeetingAttendee,Long>, QueryDslPredicateExecutor<MeetingAttendee> {
}
