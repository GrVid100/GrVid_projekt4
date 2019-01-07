package propra2.projekt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import propra2.projekt.Model.ProjektEvent;
import propra2.projekt.Model.Projekt;
import propra2.projekt.Respository.EventRepository;
import propra2.projekt.Respository.ProjektRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class ProjektRestController {
    @Autowired
    ProjektRepository projektRepository;
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/{id}")
    public Projekt getById(@PathVariable Long id){
        Optional<Projekt> projekt = projektRepository.findById(id);
        return projekt.get();
    }

    @GetMapping("/events")
    public List<ProjektEvent> getEvents() {
        List<ProjektEvent> projektEvents = eventRepository.findAll();
        eventRepository.deleteAll();
        return projektEvents;
    }
}
