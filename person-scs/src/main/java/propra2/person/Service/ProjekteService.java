package propra2.person.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import propra2.person.Model.Projekt;
import propra2.person.Model.ProjektEvent;
import propra2.person.Repository.ProjektRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjekteService {

    @Autowired
    ProjektRepository projektRepository;

    public void updateProjekte() {
        try {
            ProjektEvent[] projektEvents = getProjektEvents(ProjektEvent[].class);
            for (int i = 0; i < projektEvents.length; i++) {
                String event = projektEvents[i].getEvent();
                Long projektId = projektEvents[i].getProjektId();
                if (event.equals("delete")) {
                    projektRepository.deleteById(projektId);
                }
                else {
                    Projekt changedProjekt = getEntity(projektId, Projekt.class);
                    projektRepository.save(changedProjekt);
                }
            }
        }
        catch (Exception e) { }
    }

    public List<Projekt> getProjekte(Long[] vergangeneProjekte) {
        List<Projekt> projekts = new ArrayList<>();
        for (int i = 0; i < vergangeneProjekte.length; i++) {
            projekts.add(projektRepository.findAllById(vergangeneProjekte[i]));
        }
        return projekts;
    }

    private static <T> T getProjektEvents(final Class<T> type) {
        final Mono<T> mono = WebClient
                .create()
                .post()
                .uri("http://projekt:8080/api/events")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(type);

        return mono.block();
    }

    private static <T> T getEntity(final Long id, final Class<T> type) {
        final Mono<T> mono = WebClient
                .create()
                .get()
                .uri("http://projekt:8080/api/" + id)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .bodyToMono(type);

        return mono.block();
    }

}
