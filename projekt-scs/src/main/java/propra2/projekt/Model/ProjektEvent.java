package propra2.projekt.Model;

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
}
