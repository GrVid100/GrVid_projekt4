package propra2.person.Model;

import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.List;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String vorname;
    private String nachname;
    private String jahreslohn;
    private String rolle;
    private String kontakt;
    private String[] skills;
    private Long[] projekteId;
}
