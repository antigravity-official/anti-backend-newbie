package antigravity.exception;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends Exception{
    public CustomBadRequestException(String message){
        super(message);
    }
}
