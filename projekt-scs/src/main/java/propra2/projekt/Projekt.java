package propra2.projekt;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Projekt {
    @Id
    @GeneratedValue
    private Long id;
    private String titel;
    private String beschreibung;
    private String startdatum;
    private String laufzeit;
    private Long[] team;
}
