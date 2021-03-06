package university.project.roomserviceondemand.controllers.test;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import university.project.roomserviceondemand.application.RoomserviceondemandApplication;
import university.project.roomserviceondemand.controllers.AuthorizationController;
import university.project.roomserviceondemand.models.User;
import university.project.roomserviceondemand.repository.FeedbackRepository;
import university.project.roomserviceondemand.repository.RequestRepository;
import university.project.roomserviceondemand.repository.UserRepository;
import university.project.roomserviceondemand.services.UserService;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SpringBootTest
@ContextConfiguration(classes = {RoomserviceondemandApplication.class})
@RunWith(Parameterized.class)
public class SignInFieldIncorrectTest {

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Parameterized.Parameter(0)
	public String pass;
	@Parameterized.Parameter(1)
	public String email;

	@Autowired
	private AuthorizationController authorizationController;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Parameterized.Parameters(name = "{index}: Test with pass={0}, email={1} ")
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][]{{"", "email@mail.ru"}};
		return Arrays.asList(data);
	}

	@Before
	public void setUp() {
		feedbackRepository.deleteAll();
		requestRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	public void ShouldNotSign_SignIn_When_AtLeastOneFieldIsIncorrect() {
		final MockHttpSession mockSession = new MockHttpSession();
		User user = new User();
		user.setPassword(pass);
		user.setEmail(email);
		user.setName("test");
		mockSession.setAttribute("user", user);
		assertEquals("At least one of the fields in the form is incorrect!", authorizationController.signIn(user));
	}
}