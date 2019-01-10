package propra2.person.Repository;

import org.springframework.data.repository.CrudRepository;
import propra2.person.Model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person,Long> {
    List<Person> findAll();
    Optional<Person> findById(Long id);

}
