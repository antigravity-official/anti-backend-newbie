package antigravity.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import antigravity.repository.UserRepository;

@SpringBootTest
public class UserTest {

	@Autowired
	private UserRepository userRepository;

	@DisplayName("equals 재정의 테스트")
	@Test
	void equalsOverrideTest() {
		User user1 = userRepository.findById(2L).get();
		User user2 = userRepository.findById(2L).get();
		User user3 = userRepository.findById(2L).get();

		//반사성
		assertTrue(user1.equals(user1));

		//대칭성
		assertTrue(user1.equals(user2) == user2.equals(user1));

		//추이성
		assertTrue((user1.equals(user2) == user2.equals(user3)) == user3.equals(user1));

		//일관성
		for (int i = 0; i < 10; i++) {
			assertTrue(user1.equals(user2));
		}

		//null 이 아님
		assertFalse(user1.equals(null));
		assertFalse(user2.equals(null));
		assertFalse(user3.equals(null));
	}

	@DisplayName("hashCode 재정의 테스트")
	@Test
	void hashCodeOverrideTest(){
		User user1 = userRepository.findById(2L).get();
		User user2 = userRepository.findById(2L).get();
		User user3 = userRepository.findById(2L).get();

		assertTrue(user1.hashCode() == user2.hashCode());
		assertTrue(user2.hashCode() == user3.hashCode());
		assertTrue(user3.hashCode() == user1.hashCode());
	}

}
