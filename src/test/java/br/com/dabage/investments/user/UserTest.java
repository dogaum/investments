package br.com.dabage.investments.user;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.dabage.investments.repositories.RoleRepository;
import br.com.dabage.investments.repositories.UserRepository;

@ContextConfiguration
(
  {
   "file:src/main/webapp/WEB-INF/mongo-config.xml",
   "file:src/main/webapp/WEB-INF/spring-config.xml",
   "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml"
  }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Test
	public void testFindAllUsers() {
		List<UserTO> users = userRepository.findAll();
		for (UserTO userTO : users) {
			System.out.println(userTO);
		}
	
	}

	@Test
	public void testFindAllRoles() {
		List<RoleTO> roles = roleRepository.findAll();
		for (RoleTO roleTO : roles) {
			System.out.println(roleTO);
		}
	
	}

	@Test
	public void testAddRole() {
		RoleTO roleAdmin = new RoleTO();
		roleAdmin.setName("ROLE_USUARIO");
		roleAdmin.setDescription("Usuário normal do sistema");
		//roleRepository.save(roleAdmin);
	}
	
	@Test
	public void testAddNewUser() {

		UserTO user = new UserTO();
		user.setAge("30");
		user.setEmail("dogaum@gmail.com");
		user.setName("Douglas");
		user.setPassword("dogaun");
		user.setSurname("Araujo");
		user.setUsername("dogaum");
		RoleTO roleAdmin = roleRepository.findByName("ROLE_ADMIN");
		user.setRoles(Collections.singletonList(roleAdmin));
		
		//userRepository.save(user);
	}
	
	@Test
	public void testEditUser() {
		UserTO user = userRepository.findByEmail("dogaum@gmail.com");
		user.setActivated(Boolean.TRUE);
		user.setAddDate(new Date());
		user.setActivateDate(new Date());
		//userRepository.save(user);
	}
}
