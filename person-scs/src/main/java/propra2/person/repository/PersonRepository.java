package propra2.person.repository;

import org.springframework.data.repository.CrudRepository;
import propra2.person.model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person,Long> {
    List<Person> findAll();
    Person findAllById(Long id);
}
