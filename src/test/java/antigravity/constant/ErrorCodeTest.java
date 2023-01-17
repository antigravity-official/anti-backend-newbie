package antigravity.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;


@SpringBootTest
@DisplayName("에러 테스트")
class ErrorCodeTest {

    @DisplayName("예외를 받으면, 예외 메세지가 포함된 메세지 출력")
    @ParameterizedTest(name = "[{index}] {0} ====> {1}")
    @MethodSource
    void givenExceptionWithMessage_whenGettingMessage_thenReturnsmessage(ErrorCode sut, String expected) {
        // Given
        Exception e = new Exception("This is test message.");

        // When
        String actual = sut.getMessage(e);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenExceptionWithMessage_whenGettingMessage_thenReturnsmessage() {
        return Stream.of(
                arguments(ErrorCode.OK, "OK - This is test message."),
                arguments(ErrorCode.BAD_REQUEST, "bad request - This is test message."),
                arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - This is test message."),
                arguments(ErrorCode.VALIDATION_ERROR, "Validation error - This is test message."),
                arguments(ErrorCode.NOT_FOUND, "Requested resource is not found - This is test message."),
                arguments(ErrorCode.INTERNAL_ERROR, "internal error - This is test message."),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected internal error - This is test message."),
                arguments(ErrorCode.DATA_ACCESS_ERROR, "Data access error - This is test message.")
        );
    }
}