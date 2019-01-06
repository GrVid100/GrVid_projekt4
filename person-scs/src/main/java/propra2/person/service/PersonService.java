package propra2.person.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propra2.person.model.Person;
import propra2.person.repository.PersonRepository;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public Iterable<Person> findAll(){
        return personRepository.findAll();
    }
}
