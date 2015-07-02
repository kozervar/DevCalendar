package devcalendar.repository;

import devcalendar.model.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Marcin Kozaczyk on 2015-07-02.
 */
public interface AttendeeRepository extends JpaRepository<Attendee,Long>, QueryDslPredicateExecutor<Attendee> {
}
