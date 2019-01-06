package propra2.person;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
@Data
@Controller
public class PersonController {


	@Autowired
	PersonRepository personRepository;

	@GetMapping("/")
	public String mainpage(Model model) {
		List<Person> persons = personRepository.findAll();
		model.addAttribute("persons",persons);

		return "index";
	}

	@GetMapping("/addPerson")
    public String addPersonPage() {
	    return "addPerson";
    }

    @RequestMapping("/add")
    public String addToDatabase(@RequestParam("vorname") String vorname,
                                @RequestParam("nachvorname") String nachname,
                                @RequestParam("jahreslohn") String jahreslohn,
                                @RequestParam("kontaktdaten") String kontaktdaten,
                                @RequestParam("skills") String[] skills,
                                Model model) {
        Person newPerson = new Person();
        newPerson.setVorname(vorname);
        newPerson.setVorname(nachname);
        newPerson.setJahreslohn(jahreslohn);
        newPerson.setKontakt(kontaktdaten);
        newPerson.setSkills(skills);
        personRepository.save(newPerson);

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
        final Projekt[] projekte = getEntity("projekt", Projekt[].class);
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

    private static <T> T getEntity(final String entity, final Class<T> type) {
        final Mono<T> mono = WebClient
                .create()
                .get()
                .uri("http://projekt:8080/projekte-rest/all")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(type);

        return mono.block();
    }
}
