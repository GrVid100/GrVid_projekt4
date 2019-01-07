package propra2.projekt.Respository;

import org.springframework.data.repository.CrudRepository;
import propra2.projekt.Model.Projekt;

import java.util.List;

public interface ProjektRepository extends CrudRepository<Projekt,Long> {
    List<Projekt> findAll();
}
