package  com.example.markethome.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.markethome.Entities.Role;
import com.example.markethome.reponsitory.RoleRepository;
import com.example.markethome.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)

public class RoleRepositoryTests {
	@Autowired private RoleRepository repo;

	@Autowired private UserService userService;

	@Test
	public void testCreateRoles() {
		Role admin = new Role("ROLE_ADMIN");
		Role receptionist = new Role("ROLE_RECEPTIONIST");
		Role customer = new Role("ROLE_CUSTOMER");
		Role staff = new Role("ROLE_STAFF");
		
		repo.saveAll(List.of(admin, receptionist, customer, staff));
		
		long count = repo.count();
		assertEquals(4, count);
	}

//	@Test
//	public void testCreateUsers() {
//		User user = new User();
//		user.setName("lượng");
//		user.setPhone("03769101971");
//		user.setPassword("abc");
//		user.setEmail("abc@gmail.com");
//		User newUser =  userService.save(user);
//
//
//		assertEquals(newUser.getId(), 2);
//	}
}
