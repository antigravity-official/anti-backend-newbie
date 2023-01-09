package antigravity.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import antigravity.entity.User;
import antigravity.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@DisplayName("사용자 찾기 테스트")
	@Test
	void given_UserId_when_FindById_then_DoesNotThrow() {
		//given
		Integer userId = 1;

		//mock
		User mockedUser = mock(User.class);

		//when
		when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(mockedUser));
		when(mockedUser.isDeleted()).thenReturn(false);

		//then
		assertDoesNotThrow(() -> userService.findUserById(userId));
	}

	@DisplayName("사용자 찾기 테스트 - userId로 조회한 사용자가 존재하지 않을 때")
	@Test
	void given_NonExistentUserId_when_FindById_then_ThrowsIllegalStateException() {
		//given
		Integer userId = 1;

		//when
		when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.empty());

		//then
		IllegalStateException e = assertThrows(IllegalStateException.class,
			() -> userService.findUserById(userId));
		assertEquals("User Not Found", e.getMessage());
	}

	@DisplayName("사용자 찾기 테스트 - userId로 조회한 사용자가 삭제된 상태일때")
	@Test
	void given_DeletedUserId_when_FindById_then_ThrowsIllegalStateException() {
		//given
		Integer userId = 1;

		//mock
		User mockedUser = mock(User.class);

		//when
		when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(mockedUser));
		when(mockedUser.isDeleted()).thenReturn(true);

		//then
		IllegalStateException e = assertThrows(IllegalStateException.class,
			() -> userService.findUserById(userId));
		assertEquals("Already User Deleted", e.getMessage());
	}

}
