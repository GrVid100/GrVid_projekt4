package propra2.person.Repository;

import org.springframework.data.repository.CrudRepository;
import propra2.person.Model.Projekt;

import java.util.List;

public interface ProjektRepository extends CrudRepository<Projekt,Long> {
    List<Projekt> findAll();
    Projekt findAllById(Long id);
}
