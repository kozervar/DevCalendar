package devcalendar.repository;

import devcalendar.model.Attendee;
import devcalendar.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
public interface MeetingRepository extends JpaRepository<Meeting,Long>, QueryDslPredicateExecutor<Meeting> {
}
