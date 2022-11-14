package antigravity.global;

import antigravity.exception.InvalidHeaderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private static final String X_USER_ID_HEADER = "X-USER-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        existUserIdFromHeader(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void existUserIdFromHeader (HttpServletRequest request) {
        String userId = request.getHeader(X_USER_ID_HEADER);
        validFormatUserId(userId);
    }

    private void validFormatUserId(String userId) {
        log.info(String.format("[REQUEST HEADER] X-USER-ID=%s", userId));
        if(Strings.isBlank(userId)) {
            throw new InvalidHeaderException("Add header then retry...");
        }
        if(!userId.chars().allMatch( Character::isDigit )) {
            throw new InvalidHeaderException("Request header format invalid");
        }
    }
}
