package propra2.person.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import propra2.person.model.Person;
import propra2.person.repository.PersonRepository;

import java.util.List;

@RestController
public class PersonRestController {
    @Autowired
    PersonRepository personRepository;
    @RequestMapping("/getall")
    public List<Person> getAll(){
        System.out.println(personRepository.findAll());
        return personRepository.findAll();
    }
    @RequestMapping("/{id}")
    public Person getByID(@PathVariable("id") Long id){
        System.out.println("Fetching ID" + id);
        Person person = personRepository.findAllById(id);
        if(person == null){
            System.out.println("Id " + id + " not found");
        }
        System.out.println(person);
        return person;
    }
    @PostMapping("/add")
    public Person add(@RequestBody Person person){
        System.out.println("Add : " + person);
        personRepository.save(person);
        return person;
    }
    @DeleteMapping("/delete/{id}")
    public Person deleteByID(@PathVariable("id") Long id){
        Person person = personRepository.findAllById(id);
        if(person == null){
            System.out.println("Id " + id + " not found");
        }
        personRepository.deleteById(id);
        return person;
    }
    @PutMapping("/update")
    public Person update(@RequestBody Person personRequest){
        Person person = personRepository.findAllById(personRequest.getId());
        if(person != null){
            System.out.println("update id : " + person.getId());
            personRepository.save(personRequest);
            return personRequest;
        }
        System.out.println("Not exsit id!");
        return personRequest;
    }
}
