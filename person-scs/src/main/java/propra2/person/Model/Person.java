package propra2.person.Model;

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
    private String jahreslohn;
    private String rolle;
    private String kontakt;
    private String[] skills;
    private Long[] projekteId;

    public Person(String vorname, String nachname, String jahreslohn, String kontaktdaten, String[] skills, Long[] vergangeneProjekte) {

        this.vorname=vorname;
        this.nachname=nachname;
        this.jahreslohn=jahreslohn;
        this.kontakt=kontaktdaten;
        this.skills=skills;
        this.projekteId=vergangeneProjekte;
    }
    public Person(String vorname, String nachname, String jahreslohn, String kontaktdaten,Long[] vergangeneProjekte) {
        this.vorname=vorname;
        this.nachname=nachname;
        this.jahreslohn=jahreslohn;
        this.kontakt=kontaktdaten;
        this.projekteId=vergangeneProjekte;

    }
    public Person(String vorname, String nachname, String jahreslohn, String kontaktdaten) {
        this.vorname=vorname;
        this.nachname=nachname;
        this.jahreslohn=jahreslohn;
        this.kontakt=kontaktdaten;

    }


}
