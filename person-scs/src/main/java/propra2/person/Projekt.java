package propra2.person;

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
    private String name;
    private String description;
    private String budget;
    private String startdatum;
    private String laufzeit;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String description() {
//        return description;
//    }
//
//    public String getBudget() {
//        return budget;
//    }
//
//    public String getStartdatum() {
//        return startdatum;
//    }
//
//    public String getLaufzeit() {
//        return laufzeit;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setBudget(String budget) {
//        this.budget = budget;
//    }
//
//    public void setStartdatum(String startdatum) {
//        this.startdatum = startdatum;
//    }
//
//    public void setLaufzeit(String laufzeit) {
//        this.laufzeit = laufzeit;
//    }
}
