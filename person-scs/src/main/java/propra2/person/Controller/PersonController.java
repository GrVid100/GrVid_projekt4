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
                                @RequestParam("skills") String[] skills,
                                @RequestParam("vergangeneProjekte") Long[] vergangeneProjekte,
                                Model model) {
        Person newPerson = new Person();
        newPerson.setVorname(vorname);
        newPerson.setNachname(nachname);
        newPerson.setJahreslohn(jahreslohn);
        newPerson.setKontakt(kontaktdaten);
        if (skills != null) {
            newPerson.setSkills(skills);
        }
        newPerson.setProjekteId(vergangeneProjekte);
        personRepository.save(newPerson);
        model.addAttribute("person", newPerson);

        List<Projekt> projekte = projekteService.getProjekte(vergangeneProjekte);
        model.addAttribute("projekte", projekte);
        personEventService.createEvent(newPerson);

	    return "confirmationAdd";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (!person.isPresent()) {
            throw new PersonNichtVorhanden();
        }
        List<Projekt> projekte = projektRepository.findAll();
        model.addAttribute("projekte", projekte);
        model.addAttribute("person", person);
        return "edit";
	}

	@RequestMapping("/saveChanges/{id}")
    public String saveChanges(@RequestParam("vorname") String vorname,
                              @RequestParam("nachname") String nachname,
                              @RequestParam("jahreslohn") String jahreslohn,
                              @RequestParam("kontakt") String kontakt,
                              @RequestParam("skills") String[] skills,
                              @RequestParam("vergangeneProjekte") Long[] vergangeneProjekte,
                              @PathVariable Long id,
                              Model model) {
        Optional<Person> person = personRepository.findById(id);
        person.get().setVorname(vorname);
        person.get().setNachname(nachname);
        person.get().setJahreslohn(jahreslohn);
        person.get().setKontakt(kontakt);
        if (skills != null) {
            person.get().setSkills(skills);
        }
        person.get().setProjekteId(vergangeneProjekte);
        personRepository.save(person.get());
        personEventService.editEvent(id);

        List<Projekt> projekts = projekteService.getProjekte(vergangeneProjekte);

        model.addAttribute("projekte", projekts);
        model.addAttribute("person", person);

        return "confirmationEdit";
	}
}
