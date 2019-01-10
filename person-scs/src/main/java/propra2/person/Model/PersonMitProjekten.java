package propra2.person.Model;

import lombok.Data;

import java.util.List;

@Data
public class PersonMitProjekten {
    private Person person;
    private List<Projekt> projekte;
}
