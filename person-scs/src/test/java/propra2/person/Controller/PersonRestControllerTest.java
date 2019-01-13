package propra2.person.Controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import propra2.person.Controller.PersonController;
import propra2.person.Controller.PersonRestController;
import propra2.person.Model.Person;
import propra2.person.Repository.EventRepository;
import propra2.person.Repository.PersonRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@RunWith(MockitoJUnitRunner.class)
////@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
//@WebAppConfiguration
//
//public class PersonRestControllerTest {
//    @Mock
//    PersonRepository personRepository;
//    @Mock
//    EventRepository eventRepository;
//    @Test
//    public void getById() throws Exception {
//        verify(personRepository,times(1)).findById(1L);
//    }
//
//    @Test
//    public void getEvents() {
//        verify(eventRepository,times(1)).findAll();
//        verify(eventRepository,times(1)).deleteAll();
//
//    }
//}