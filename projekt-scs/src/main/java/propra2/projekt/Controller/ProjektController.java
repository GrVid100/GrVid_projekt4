package propra2.projekt.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import propra2.projekt.Model.ProjektEvent;
import propra2.projekt.Model.Projekt;
import propra2.projekt.ProjektNichtVorhanden;
import propra2.projekt.Respository.EventRepository;
import propra2.projekt.Respository.ProjektRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class ProjektController {

	@Autowired
    ProjektRepository projektRepository;

	@Autowired
    EventRepository eventRepository;

	@GetMapping("/")
	public String mainPage(Model model) {
		List<Projekt> projekts = projektRepository.findAll();
		model.addAttribute("projekts", projekts);

		return "index";
	}

	@GetMapping("/addProjekt")
    public String addProjektPage() {
	    return "addProjekt";
    }

    @RequestMapping("/add")
    public String addToDatabase(@RequestParam("titel") String titel,
                                @RequestParam("beschreibung") String beschreibung,
                                @RequestParam("startdatum") String startdatum,
                                @RequestParam("laufzeit") String laufzeit,
                                Model model) {
        Projekt newProjekt = new Projekt();
        newProjekt.setTitel(titel);
        newProjekt.setBeschreibung(beschreibung);
        newProjekt.setStartdatum(startdatum);
        newProjekt.setLaufzeit(laufzeit);
        projektRepository.save(newProjekt);

        model.addAttribute("projekt", newProjekt);

        ProjektEvent newProjektEvent = new ProjektEvent();
        newProjektEvent.setProjektId(newProjekt.getId());
        newProjektEvent.setEvent("create");
        eventRepository.save(newProjektEvent);

	    return "confirmationAdd";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        Optional<Projekt> projekt = projektRepository.findById(id);

        if (!projekt.isPresent()) {
            throw new ProjektNichtVorhanden();
        }
        model.addAttribute("projekt", projekt);
        return "edit";
	}

	@RequestMapping("/saveChanges/{id}")
    public String saveChanges(@RequestParam("titel") String name,
                              @RequestParam("beschreibung") String description,
                              @RequestParam("startdatum") String startdatum,
                              @RequestParam("laufzeit") String laufzeit,
                              @PathVariable Long id,
                              Model model) {
        Optional<Projekt> projekt = projektRepository.findById(id);

        projekt.get().setTitel(name);
        projekt.get().setBeschreibung(description);
        projekt.get().setStartdatum(startdatum);
        projekt.get().setLaufzeit(laufzeit);
        projektRepository.save(projekt.get());
        model.addAttribute("projekt", projekt);

        ProjektEvent newProjektEvent = new ProjektEvent();
        newProjektEvent.setProjektId(id);
        newProjektEvent.setEvent("edit");

        return "confirmationEdit";
	}
}
