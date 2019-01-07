package propra2.person.Repository;

import org.springframework.data.repository.CrudRepository;
import propra2.person.Model.PersonEvent;

import java.util.List;

public interface EventRepository extends CrudRepository<PersonEvent,Long> {
    List<PersonEvent> findAll();
}
