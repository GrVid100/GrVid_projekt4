package propra2.person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import propra2.person.Controller.PersonController;
import propra2.person.Model.Person;
import propra2.person.Model.Projekt;
import propra2.person.Model.ProjektEvent;
import propra2.person.Repository.EventRepository;
import propra2.person.Repository.PersonRepository;
import propra2.person.Repository.ProjektRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration
public class MainPageTest {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private ProjektRepository projektRepository;
    @Mock
    private EventRepository eventRepository;
    private Projekt firstProjekt = new Projekt();
    private Person firstPerson = new Person("Tom","Stark","10000","tung@gmail.com", new String[]{"Java","Python"},new Long[]{1L});


    private ProjektEvent projektEvent=new ProjektEvent();
    private ArrayList<ProjektEvent> projektEvents =new ArrayList<>();

    private MockMvc mockMvc;

    @Before
    public void setUp(){

        //projektEvent
        projektEvent.setId(1L);
        projektEvent.setProjektId(1L);

        //first Person
        firstPerson.setVorname("Tom");
        firstPerson.setNachname("Stark");
        firstPerson.setJahreslohn("10000");
        firstPerson.setKontakt("tung@gmail.com");
        firstPerson.setSkills(new String[]{"Java","Python"});
        firstPerson.setProjekteId(new Long[]{1L, 2L});

        // personREPO
        when(personRepository.findAll()).thenReturn(Arrays.asList(firstPerson));
        when(personRepository.findById(	1L)).thenReturn(java.util.Optional.ofNullable(firstPerson));
        when(personRepository.save(Mockito.isA(Person.class))).thenReturn(firstPerson);


        // wichtig für get url
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        // first Projekt
        firstProjekt.setTitel("projekt4");
        firstProjekt.setBeschreibung("description");
        firstProjekt.setStartdatum("30.10.2018");
        firstProjekt.setLaufzeit("10 Monaten");

        // projektREPO
        when(projektRepository.findAll()).thenReturn(Arrays.asList(firstProjekt));
//		when(projektRepository.deleteById(1L)).thenReturn(projektDummyREPO=null);
//		when(projektRepository.save(any(Projekt.class))).thenReturn(projektDummyREPO.add(firstProjekt));

        // build mockmvc
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(projektRepository,personRepository, eventRepository))
                .setViewResolvers(viewResolver)
                .build();
    }
    @Test
    public void PROJEKT_EVENT_DELETE() throws Exception{
        projektEvent.setEvent("delete");

        projektEvents.add(projektEvent);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(print())
                .andExpect(model().attribute("persons",hasItem(
                        allOf(
                                hasProperty("vorname",is("Tom")),
                                hasProperty("nachname",is("Stark")),
                                hasProperty("jahreslohn",is("10000")),
                                hasProperty("kontakt",is("tung@gmail.com")),
                                //hasProperty("projekteId",is(new Long[]{Long.valueOf(1),Long.valueOf(2)})),
                                hasProperty("skills",is(new String[]{"Java","Python"}))
                        )
                )))
        ;
    }

}