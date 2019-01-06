package propra2.person;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.Array;
import java.util.List;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String vorname;
    private String nachname;
    private String rolle;
    private String kontakt;
    private Array<String> skills;
    private Array<Long> projekte;
}
