package dev.chuby.ke_backend_java.dao;

import dev.chuby.ke_backend_java.pojos.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeDao extends JpaRepository<Attendee, Long> {

    @Query(value = "SELECT * FROM ATTENDEES WHERE id = :attendee_id", nativeQuery = true)
    Attendee getAttendee(@Param("attendee_id") Long attendee_id);
}
