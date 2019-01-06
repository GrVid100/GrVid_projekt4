package propra2.person.model;

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
    private String name;
    private String jahresLohn;
    private String kontaktDaten;
    private String skills;
    //private List<Long> projektId;
    //
    //public List<Long> getProjektId() {
    //    return projektId;
    //}
    //
    //public void setProjektId(List<Long> projektId) {
    //    this.projektId = projektId;
    //}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJahresLohn() {
        return jahresLohn;
    }

    public String getKontaktDaten() {
        return kontaktDaten;
    }

    public String getSkills() {
        return skills;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJahresLohn(String jahresLohn) {
        this.jahresLohn = jahresLohn;
    }

    public void setKontaktDaten(String kontaktDaten) {
        this.kontaktDaten = kontaktDaten;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}
