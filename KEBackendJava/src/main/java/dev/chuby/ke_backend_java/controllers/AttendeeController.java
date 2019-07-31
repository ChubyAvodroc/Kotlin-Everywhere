package dev.chuby.ke_backend_java.controllers;

import dev.chuby.ke_backend_java.pojos.Attendee;
import dev.chuby.ke_backend_java.services.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttendeeController {

    @Autowired
    AttendeeService attendeeService;

    @PostMapping("/attendees")
    public Attendee registerAttendee(@RequestBody Attendee attendee) {
        Attendee result = attendeeService.saveAttendee(attendee);
        return result;
    }

    // GET
    // Returns JSON of Attendee
    @GetMapping("/attendees")
    public List<Attendee> retrieveAttendees() {
        List<Attendee> attendees = attendeeService.getAttendees();
        return attendees;
    }

    // GET
    // Returns JSON of Attendee
    @GetMapping("/attendees/{attendee_id}")
    public Attendee retrieveAttendee(@PathVariable Long attendee_id) {
        Attendee attendee = attendeeService.getAttendee(attendee_id);
        return attendee;
    }
}
