package propra2.person.Controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import propra2.person.Model.*;
import propra2.person.PersonNichtVorhanden;
import propra2.person.Repository.EventRepository;
import propra2.person.Repository.PersonRepository;
import propra2.person.Repository.ProjektRepository;
import reactor.core.publisher.Mono;

import java.util.*;

@Data
@Controller
public class PersonController {


	@Autowired
    PersonRepository personRepository;
	@Autowired
    ProjektRepository projektRepository;
	@Autowired
    EventRepository eventRepository;

	@GetMapping("/")
	public String mainpage(Model model) {
		try {
            ProjektEvent[] projektEvents = getProjektEvents(ProjektEvent[].class);
            for (int i = 0; i < projektEvents.length; i++) {
                String event = projektEvents[i].getEvent();
                Long projektId = projektEvents[i].getProjektId();
                if (event.equals("delete")) {
                    projektRepository.deleteById(projektId);
                }
                else {
                    Projekt changedProjekt = getEntity(projektId, Projekt.class);
                    projektRepository.save(changedProjekt);
                }
            }
        }
        catch (Exception e) { }

        List<Person> persons = personRepository.findAll();
        List<PersonMitProjekten> personsMitProjekten = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            PersonMitProjekten personMitProjekten = new PersonMitProjekten();
            Person person = persons.get(i);
            personMitProjekten.setPerson(person);
            List<Projekt> projekte = new ArrayList<>();
            for (int j = 0; j < person.getProjekteId().length; j++) {
                projekte.add(projektRepository.findAllById(person.getProjekteId()[j]));
            }
            personMitProjekten.setProjekte(projekte);
            personsMitProjekten.add(personMitProjekten);
        }

		model.addAttribute("persons", personsMitProjekten);
		return "index";
	}

	@GetMapping("/addPerson")
    public String addPersonPage(Model model) {
	    List<Projekt> projekte = projektRepository.findAll();
	    model.addAttribute("projekte", projekte);
	    return "addPerson";
    }

    @RequestMapping("/add")
    public String addToDatabase(@RequestParam("vorname") String vorname,
                                @RequestParam("nachname") String nachname,
                                @RequestParam("jahreslohn") String jahreslohn,
                                @RequestParam("kontaktdaten") String kontaktdaten,
                                @RequestParam("skills") String[] skills,
                                @RequestParam("vergangeneProjekte") Long[] vergangeneProjekte,
                                Model model) {
        Person newPerson = new Person();
        newPerson.setVorname(vorname);
        newPerson.setNachname(nachname);
        newPerson.setJahreslohn(jahreslohn);
        newPerson.setKontakt(kontaktdaten);
        newPerson.setSkills(skills);
        newPerson.setProjekteId(vergangeneProjekte);
        personRepository.save(newPerson);
        model.addAttribute("person", newPerson);

        List<Projekt> projekte = new ArrayList<>();
        for (int j = 0; j < vergangeneProjekte.length; j++) {
            projekte.add(projektRepository.findAllById(vergangeneProjekte[j]));
        }

        model.addAttribute("projekte", projekte);
        PersonEvent newPersonEvent = new PersonEvent();
        newPersonEvent.setEvent("create");
        newPersonEvent.setPersonId(newPerson.getId());
        eventRepository.save(newPersonEvent);

	    return "confirmationAdd";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (!person.isPresent()) {
            throw new PersonNichtVorhanden();
        }
        model.addAttribute("person", person);
        return "edit";
	}

	@RequestMapping("/saveChanges/{id}")
    public String saveChanges(@RequestParam("vorname") String vorname,
                              @RequestParam("nachname") String nachname,
                              @RequestParam("jahreslohn") String jahreslohn,
                              @RequestParam("kontakt") String kontakt,
                              @RequestParam("skills") String[] skills,
                              @PathVariable Long id,
                              Model model) {
        Optional<Person> person = personRepository.findById(id);
        person.get().setVorname(vorname);
        person.get().setVorname(nachname);
        person.get().setJahreslohn(jahreslohn);
        person.get().setKontakt(kontakt);
        person.get().setSkills(skills);

        personRepository.save(person.get());
        model.addAttribute("person", person);

        return "confirmationEdit";
	}

    private static <T> T getEntity(final Long id, final Class<T> type) {
        final Mono<T> mono = WebClient
                .create()
                .get()
                .uri("http://projekt:8080/api/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(type);

        return mono.block();
    }

    private static <T> T getProjektEvents(final Class<T> type) {
        final Mono<T> mono = WebClient
                .create()
                .get()
                .uri("http://projekt:8080/api/events")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(type);

        return mono.block();
    }
}
