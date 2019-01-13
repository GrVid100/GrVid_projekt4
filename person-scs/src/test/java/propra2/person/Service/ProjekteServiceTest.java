package propra2.person.Service;

import org.assertj.core.api.Java6BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import propra2.person.Controller.PersonController;
import propra2.person.Model.Projekt;
import propra2.person.Model.ProjektEvent;
import propra2.person.Repository.ProjektRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class ProjekteServiceTest {
    @Mock
    private ProjektRepository projektRepository;
    private List<Projekt> projektList=new ArrayList<>();
    private ProjektEvent[] projektEventList=new ProjektEvent[10];

    @Mock
    private ProjekteService projekteService;


    @SuppressWarnings("unchecked")
    @Before
    public void setup(){
        // wichtig für get url
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        projektList.add(new Projekt("titel","beschreibung","10.08.2018","10 Monaten"));


        this.projekteService=new ProjekteService(projektRepository);


        when((Iterable<? extends Publisher<?>>) ProjekteService.getProjektEvents(any())).thenReturn(projektEventList);
       // when((Iterable<? extends Publisher<?>>) projekteService.getEntity(any(),any())).thenReturn(projektList);
        projektEventList[0]=new ProjektEvent("delete",1L);
        projektList.add(new Projekt("titel","beschreibung","10.08.2018","10 Monaten"));
    }
//    @Test
//    public void updateProjekte_Fall_delete() {
//        projektRepository=Mockito.mock(ProjektRepository.class);
//        Mockito.doAnswer((Answer) (projektEventList[0]=null)).when(projektRepository).deleteById(any());
//    }

    @Test
    public void getProjekte() {
    }
}