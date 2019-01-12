package propra2.projekt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/events")
    public List<ProjektEvent> getEvents() {
        List<ProjektEvent> projektEvents = eventRepository.findAll();
        eventRepository.deleteAll();
        return projektEvents;
    }
}
