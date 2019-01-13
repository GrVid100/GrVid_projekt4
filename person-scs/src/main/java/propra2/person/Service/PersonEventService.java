package propra2.person.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propra2.person.Model.Person;
import propra2.person.Model.PersonEvent;
import propra2.person.Repository.EventRepository;

@Service
public class PersonEventService {
    @Autowired
    EventRepository eventRepository;

    public void createEvent(Person newPerson) {
        PersonEvent newPersonEvent = new PersonEvent("create",newPerson.getId());
        eventRepository.save(newPersonEvent);
    }

    public void editEvent(Long id) {
        PersonEvent newPersonEvent = new PersonEvent("edit",id);
        eventRepository.save(newPersonEvent);
    }
}
