package propra2.person.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ProjektEvent {
    @Id
    @GeneratedValue
    private Long id;
    private Long projektId;
    private String event;
    public ProjektEvent(){

    }
    public ProjektEvent(String event, long id) {
        this.event=event;
        this.id=id;
    }
}
