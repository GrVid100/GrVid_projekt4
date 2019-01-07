package propra2.person.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class PersonEvent {
    @Id
    @GeneratedValue
    private Long id;
    private Long personId;
    private String event;
}
