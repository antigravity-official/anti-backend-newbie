package antigravity.infra.exception;

public class ApplicationRuntimeException extends RuntimeException {

    ExceptionMessages exceptionMessages;

    public ApplicationRuntimeException(ExceptionMessages exceptionMessages) {
        super(exceptionMessages.getMessage());
        this.exceptionMessages = exceptionMessages;
    }

}
