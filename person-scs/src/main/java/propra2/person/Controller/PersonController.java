package propra2.person.Controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propra2.person.Service.PersonEventService;
import propra2.person.Service.PersonenMitProjektenService;
import propra2.person.Service.ProjekteService;
import propra2.person.Model.*;
import propra2.person.PersonNichtVorhanden;
import propra2.person.Repository.EventRepository;
import propra2.person.Repository.PersonRepository;
import propra2.person.Repository.ProjektRepository;

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
	@Autowired
    ProjekteService projekteService;
	@Autowired
    PersonenMitProjektenService personenMitProjektenService;
    @Autowired
    PersonEventService personEventService;
    public PersonController(ProjektRepository projektRepository, PersonRepository personRepository, EventRepository eventRepository,ProjekteService projekteService,PersonenMitProjektenService personenMitProjektenService,PersonEventService personEventService) {
        this.projektRepository=projektRepository;
        this.personRepository=personRepository;
        this.eventRepository=eventRepository;
        this.projekteService=projekteService;
        this.personenMitProjektenService=personenMitProjektenService;
        this.personEventService=personEventService;
    }

    @GetMapping("/")
	public String mainpage(Model model) {
	    projekteService.updateProjekte();
	    List<PersonMitProjekten> personsMitProjekten = personenMitProjektenService.returnPersonenMitProjekten();
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
                                @RequestParam(value = "skills", defaultValue = "keine", required = false) String[] skills,
                                @RequestParam(value = "vergangeneProjekte", required = false) Long[] vergangeneProjekte,
                                Model model) {
        Person newPerson = new Person();
        List<Projekt> projekte = new ArrayList<>();
        newPerson.setVorname(vorname);
        newPerson.setNachname(nachname);
        newPerson.setJahreslohn(jahreslohn);
        newPerson.setKontakt(kontaktdaten);
        newPerson.setSkills(skills);
        if(vergangeneProjekte != null) {
            newPerson.setProjekteId(vergangeneProjekte);
            projekte = projekteService.getProjekte(vergangeneProjekte);
        }
        personRepository.save(newPerson);
        model.addAttribute("person", newPerson);
        personEventService.createEvent(newPerson);

        model.addAttribute("projekte", projekte);
	    return "confirmationAdd";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (!person.isPresent()) {
            throw new PersonNichtVorhanden();
        }
        else {
            List<Projekt> projekte = projektRepository.findAll();
            model.addAttribute("projekte", projekte);
            model.addAttribute("person", person);
        }

        return "edit";
	}

	@RequestMapping("/saveChanges/{id}")
    public String saveChanges(@RequestParam("vorname") String vorname,
                              @RequestParam("nachname") String nachname,
                              @RequestParam("jahreslohn") String jahreslohn,
                              @RequestParam("kontakt") String kontakt,
                              @RequestParam(value = "skills", defaultValue = "keine", required = false) String[] skills,
                              @RequestParam(value = "vergangeneProjekte", required = false) Long[] vergangeneProjekte,
                              @PathVariable Long id,
                              Model model) {
        Optional<Person> person = personRepository.findById(id);
        List<Projekt> projekts = new ArrayList<>();
        person.get().setVorname(vorname);
        person.get().setNachname(nachname);
        person.get().setJahreslohn(jahreslohn);
        person.get().setKontakt(kontakt);
        person.get().setSkills(skills);
        if(vergangeneProjekte != null) {
            person.get().setProjekteId(vergangeneProjekte);
            projekts = projekteService.getProjekte(vergangeneProjekte);
        }
        personRepository.save(person.get());
        personEventService.editEvent(id);
        model.addAttribute("projekte", projekts);
        model.addAttribute("person", person);

        return "confirmationEdit";
	}
}
