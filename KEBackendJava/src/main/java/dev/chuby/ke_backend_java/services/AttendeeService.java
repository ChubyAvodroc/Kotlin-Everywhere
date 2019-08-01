package dev.chuby.ke_backend_java.services;

import dev.chuby.ke_backend_java.dao.AttendeeDao;
import dev.chuby.ke_backend_java.pojos.Attendee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendeeService {

    @Autowired
    AttendeeDao attendeeDao;

    // Save new Attendee into DB
    public Attendee saveAttendee(Attendee attendee) {
        return attendeeDao.save(attendee);
    }

    // Get all Attendees
    public List<Attendee> getAttendees() {
        return attendeeDao.findAll();
    }

    // Get Attendee by id
    public Attendee getAttendee(Long id) throws NullPointerException {
        //Optional<Attendee> optionalAttendee = attendeeDao.findById(id);
        //return optionalAttendee.orElse(null);
        Attendee optionalAttendee = attendeeDao.getAttendee(id);
        return optionalAttendee;
    }

}
