package propra2.person.Repository;

import org.springframework.data.repository.CrudRepository;
import propra2.person.Model.Event;

import java.util.List;

public interface EventReporitory extends CrudRepository<Event,Long> {
    List<Event> findAll();
}
