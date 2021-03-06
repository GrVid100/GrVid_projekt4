package propra2.person.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.Array;

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
    public Projekt(){
    }
    public Projekt(String titel, String beschreibung, String startdatum, String laufzeit) {
        this.titel=titel;
        this.beschreibung=beschreibung;
        this.startdatum=startdatum;
        this.laufzeit=laufzeit;
    }
}
