package propra2.person.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import propra2.person.Model.PersonApi;
import propra2.person.Model.PersonEvent;
import propra2.person.Model.Person;
import propra2.person.Repository.EventRepository;
import propra2.person.Repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class PersonRestController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/{id}")
    public PersonApi getById(@PathVariable Long id){
        Optional<Person> person = personRepository.findById(id);
        PersonApi personApi = new PersonApi(person.get());
        return personApi;
    }

    @PostMapping("/events")
    public List<PersonEvent> getEvents() {
        List<PersonEvent> personEvents = eventRepository.findAll();
        eventRepository.deleteAll();
        return personEvents;
    }
}
