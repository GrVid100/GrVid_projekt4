//package propra2.person;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.stubbing.Answer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import propra2.person.Model.Person;
//import propra2.person.Repository.PersonRepository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
//import static org.hamcrest.Matchers.any;
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
//
//
//public class PersonScSApplicationIntegrationTest {
//	private static final String EXPECTED_SUBJECT = "[{name:Tom,jahresLohn:10000,kontakDaten: tom@example,skills: java}]";
//	@Autowired
//	private MockMvc mockMvc;
//	@MockBean
//	private PersonRepository personRepository;
//	private long PersonId = 5;
//	private String requestURL = "/";
//	@Test
//	public void contextLoads() {
//	}
//
//	@Before
//	public void setup(){
//		List<Person> personList = getPersonList();
//		when(personRepository.findAll()).thenReturn(personList);
//		when(personRepository.save(ArgumentMatchers.any(Person.class))).thenAnswer((Answer<Person>) invocationOnMock -> {
//			Person arg = invocationOnMock.getArgument(0);
//			arg.setId(PersonId);
//			return arg;
//		});
//	}
//
////	@After
////	public void deletePersons() {
////		PersonRepository.deleteAll();
////	}
//
////	@Test
////	public void findAllPerson() throws Exception {
////		mockMvc.perform(get(requestURL))
////				.andExpect(status().isOk())
////				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
////				.andExpect((ResultMatcher) jsonPath("$[0].subject", is(EXPECTED_SUBJECT)));
////	}
////	public List<Person> getPersonList() {
////		List<Person> list = new ArrayList<>();
////		Person person1 = new Person();
////		person1.setName("Tom");
////		person1.setJahresLohn("10000");
////		person1.setKontaktDaten("tom@example");
////		person1.setSkills("java");
////		list.add(person1);
////		return list;
////	}
//
//}
//
