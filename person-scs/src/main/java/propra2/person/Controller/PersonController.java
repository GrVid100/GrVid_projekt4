package propra2.person.Controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import propra2.person.Model.Event;
import propra2.person.Model.Person;
import propra2.person.Model.PersonMitProjekten;
import propra2.person.Model.Projekt;
import propra2.person.PersonNichtVorhanden;
import propra2.person.Repository.EventReporitory;
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
    EventReporitory eventReporitory;

	@GetMapping("/")
	public String mainpage(Model model) {
		List<Person> persons = personRepository.findAll();
		Projekt[] projekts = getAllEntity(Projekt[].class);
		for(int i = 0; i < projekts.length; i++) {
		    projektRepository.save(projekts[i]);
        }
        List<PersonMitProjekten> personsWithProjects = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            PersonMitProjekten personWithProjects = new PersonMitProjekten();
            Person person = persons.get(i);
            personWithProjects.setPerson(person);
            List<Projekt> projects = new ArrayList<>();
            for (int j = 0; j < person.getProjekteId().length; j++) {
                projects.add(projektRepository.findAllById(person.getProjekteId()[j]));
            }
            personWithProjects.setProjekte(projects);
            personsWithProjects.add(personWithProjects);
        }


		model.addAttribute("persons",personsWithProjects);

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
        newPerson.setVorname(nachname);
        newPerson.setJahreslohn(jahreslohn);
        newPerson.setKontakt(kontaktdaten);
        newPerson.setSkills(skills);
        newPerson.setProjekteId(vergangeneProjekte);
        personRepository.save(newPerson);
        Event newEvent = new Event();
        newEvent.setEvent("create");
        newEvent.setPersonId(newPerson.getId());
        eventReporitory.save(newEvent);
        model.addAttribute("person", newPerson);

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

    @GetMapping("/projekte")
    public String index(Model model) {
        final Projekt[] projekte = getEntity(new Long(1), Projekt[].class);
        model.addAttribute("projekte", projekte);
        return "projektee";
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

    private List<Projekt> getEntities(Long[] vergangeneProjekte) {
	    List<Projekt> projekte = new ArrayList<>();
	    if (vergangeneProjekte.length == 0) {
	        return projekte;
        }
        for (int i = 0; i < vergangeneProjekte.length; i++) {
            Projekt projekt = getEntity(vergangeneProjekte[i], Projekt.class);
            projekte.add(projekt);
        }
        return projekte;
    }

    private static <T> T getAllEntity(final Class<T> type) {
        final Mono<T> mono = WebClient
                .create()
                .get()
                .uri("http://projekt:8080/api/all")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(type);

        return mono.block();
    }

}
