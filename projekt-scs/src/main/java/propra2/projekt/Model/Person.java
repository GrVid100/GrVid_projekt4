package propra2.projekt.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    private String[] skills;
}
