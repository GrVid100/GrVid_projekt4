package propra2.person.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Long personId;
    private String event;
    private Person person;
}
