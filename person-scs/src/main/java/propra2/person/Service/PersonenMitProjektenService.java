package propra2.person.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propra2.person.Model.Person;
import propra2.person.Model.PersonMitProjekten;
import propra2.person.Model.Projekt;
import propra2.person.Repository.PersonRepository;
import propra2.person.Repository.ProjektRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonenMitProjektenService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    ProjektRepository projektRepository;

    public List<PersonMitProjekten> returnPersonenMitProjekten() {
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
        return personsMitProjekten;
    }
}

