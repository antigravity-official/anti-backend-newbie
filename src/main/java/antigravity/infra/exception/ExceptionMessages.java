package antigravity.infra.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {

    ;

    private final String code;
    private final String message;

}
