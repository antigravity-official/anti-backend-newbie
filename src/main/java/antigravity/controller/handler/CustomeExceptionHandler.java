package antigravity.controller.handler;


import antigravity.payload.ResponseDto;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomeExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseDto<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
    String errorMessage = exception.getBindingResult()
        .getAllErrors()
        .get(0)
        .getDefaultMessage();

    return ResponseDto.fail(CustomError.INVALID_MEMBER.name(),
            CustomError.INVALID_MEMBER.getMessage());
  }

}
