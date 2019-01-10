package propra2.projekt.Respository;

import org.springframework.data.repository.CrudRepository;
import propra2.projekt.Model.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person,Long> {
    List<Person> findAll();
}
