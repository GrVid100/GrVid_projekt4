package propra2.projekt.Respository;

import org.springframework.data.repository.CrudRepository;
import propra2.projekt.Model.ProjektEvent;

import java.util.List;

public interface EventRepository extends CrudRepository<ProjektEvent,Long> {
    List<ProjektEvent> findAll();
}
