package propra2.person.Controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import propra2.person.Model.Person;
import propra2.person.Model.PersonEvent;
import propra2.person.Model.Projekt;
import propra2.person.Repository.EventRepository;
import propra2.person.Repository.PersonRepository;
import propra2.person.Repository.ProjektRepository;
import propra2.person.Service.PersonEventService;
import propra2.person.Service.PersonenMitProjektenService;
import propra2.person.Service.ProjekteService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
@WebAppConfiguration

public class PersonControllerJunitTests {

    private Person firstPerson = new Person("Tom", "Stark", "10000",
            "tung@gmail.com", new String[]{"Java", "Python"}, new Long[]{1L});
    private Projekt firstProjekt = new Projekt("projekt4","description","30.10.2018","10 Monaten");
    private List<Projekt> vergangeneProjekt = new ArrayList<>();

    private PersonEvent newPersonevent= new PersonEvent("create", 1L);

    private MockMvc mockMvc;
    @Mock
    PersonRepository personRepository;
    @Mock
    ProjektRepository projektRepository;
    @Mock
    EventRepository eventRepository;
    @Mock
    ProjekteService projekteService;
    @Mock
    PersonenMitProjektenService personenMitProjektenService;
    @Mock
    PersonEventService personEventService;
    @Before
    public void setUp() {
        vergangeneProjekt.add(firstProjekt);
        //Person erzeugen
        firstPerson.setId(2L);
        when(personRepository.findById(2L)).thenReturn(java.util.Optional.ofNullable(firstPerson));
        when(personRepository.save(Mockito.isA(Person.class))).thenReturn(firstPerson);


        // wichtig für get url
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        // Projekt erzeugen
        firstProjekt.setId(1L);
        when(projektRepository.findAll()).thenReturn(Arrays.asList(firstProjekt));

        // build mockmvc
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(projektRepository, personRepository,eventRepository,projekteService,personenMitProjektenService,personEventService))
                .setViewResolvers(viewResolver)
                .build();
    }
    @After
    public void clear(){
        projektRepository.deleteAll();
        personRepository.deleteAll();
    }
    @Test
    public void MainPAGE_TEST() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
        verify(projekteService,times(1)).updateProjekte();
        verify(personenMitProjektenService,times(1)).returnPersonenMitProjekten();
    }
    @Test
    public void AddPersonPageTEST() throws Exception {
        mockMvc.perform(get("/addPerson"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attribute("projekte", hasItem(
                        allOf(
                                hasProperty("titel", is("projekt4")),
                                hasProperty("beschreibung", is("description")),
                                hasProperty("startdatum", is("30.10.2018")),
                                hasProperty("laufzeit", is("10 Monaten"))
                        )
                )));
        verify(projektRepository, times(1)).findAll();
        verifyNoMoreInteractions(projektRepository);
    }

    @Test
    public void AddToDatabaseTEST_fullInfo() throws Exception {
        Long[] vergangeneProjekte =new Long[]{1L};
        vergangeneProjekt.add(firstProjekt);
        when(projekteService.getProjekte(any())).thenReturn((vergangeneProjekt));

        mockMvc.perform(get("/add")

                .param("vorname", "Tom")
                .param("nachname", "Stark")
                .param("jahreslohn", "10000")
                .param("kontakt", "tung@gmail.com")
                .param("skills", "Java", "Python")
                .param("vergangeneProjekte", "1")
        )
                .andDo(print())
                .andExpect(model().attribute("person",
                        allOf(
                                hasProperty("projekteId", is(vergangeneProjekte)),
                                hasProperty("skills", is(new String[]{"Java", "Python"})),
                                hasProperty("kontakt", is("tung@gmail.com")),
                                hasProperty("jahreslohn", is("10000")),
                                hasProperty("nachname", is("Stark")),
                                hasProperty("vorname", is("Tom"))
                        )
                ))
                .andExpect(model().attribute("projekte",hasItem(
                        allOf(
                                //hasProperty("id", is(vergangeneProjekte)),// nicht automatik erzeugt ???
                                hasProperty("titel", is("projekt4")),
                                hasProperty("beschreibung", is("description")),
                                hasProperty("startdatum", is("30.10.2018")),
                                hasProperty("laufzeit", is("10 Monaten"))
                                //,hasProperty("team", is("1"))
                        )
                )))
        ;
        verify(personRepository, times(1)).save(isA(Person.class));
        verify(projekteService, times(1)).getProjekte(any());
        verify(personEventService, times(1)).createEvent(any());

    }

    @Test
    public void AddToDatabaseTEST_no_Projekt() throws Exception {
        vergangeneProjekt.add(firstProjekt);
        mockMvc.perform(get("/add")

                .param("vorname", "Tom")
                .param("nachname", "Stark")
                .param("jahreslohn", "10000")
                .param("kontakt", "tung@gmail.com")

        )
                .andDo(print())
                .andExpect(model().attribute("person",
                        allOf(
                                hasProperty("kontakt", is("tung@gmail.com")),
                                hasProperty("jahreslohn", is("10000")),
                                hasProperty("nachname", is("Stark")),
                                hasProperty("vorname", is("Tom"))
                        )
                ))
                .andExpect(model().size(1))

        ;
        verify(personRepository, times(1)).save(isA(Person.class));
        verify(personEventService, times(1)).createEvent(any());

    }
    //status muss OK und Exception
    @Test
    public void editTEST_PersonnichtVorhanden() throws Exception {
        mockMvc.perform(get("/edit/{id}", 3L))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;

        verify(personRepository, times(1)).findById(anyLong());
        verify(projektRepository, times(0)).findAll();
        verifyZeroInteractions(personRepository);
    }

    @Test
    public void editTEST_PersonVorhanden() throws Exception {

        when(personRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(firstPerson));

            mockMvc.perform(get("/edit/{id}", 1L))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("edit"))
                    .andExpect(request().attribute("projekte", hasItem(
                            allOf(
                                    hasProperty("titel", is("projekt4")),
                                    hasProperty("beschreibung", is("description")),
                                    hasProperty("startdatum", is("30.10.2018")),
                                    hasProperty("laufzeit", is("10 Monaten"))
                            )
                    )))
            ;
            verify(personRepository, times(1)).findById(1L);
            verifyZeroInteractions(personRepository);
    }
       @Test
    public void saveChanges_TEST() throws Exception {

        mockMvc.perform(get("/saveChanges/{id}", 2L)
                .param("vorname", "Tom")
                .param("nachname", "Stark")
                .param("jahreslohn", "10000")
                .param("kontakt", "tung@gmail.com")
                .param("skills", "Java", "Python")
                .param("vergangeneProjekte", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirmationEdit"))
                .andDo(print())

        ;
        verify(personRepository, times(1)).findById(anyLong());
        verify(personRepository, times(1)).save(any());
        verify(personEventService, times(1)).editEvent(any());
        verify(projekteService, times(1)).getProjekte(any());

    }



}