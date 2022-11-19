package antigravity.global.interceptor;

import antigravity.global.exception.BusinessException;
import antigravity.global.exception.InvalidHeaderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static antigravity.global.common.Constants.USER_ID_HEADER;
import static antigravity.global.exception.ErrorCode.INVALID_TYPE;
import static antigravity.global.exception.ErrorCode.NOT_EXISTS_HEADER;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        log.debug("userAuthInterceptor prehandle");
        checkValidation(request.getHeader(USER_ID_HEADER));
        return true;
    }

    private void checkValidation(String userId) {
        if(userId == null) {
            throw new InvalidHeaderException(NOT_EXISTS_HEADER);
        } else if(userId.isBlank()) {
            throw new InvalidHeaderException(NOT_EXISTS_HEADER);
        } else if (!userId.chars().allMatch(Character::isDigit)) {
            throw new InvalidHeaderException(INVALID_TYPE);
        }
        log.debug("유효한 Header 정보입니다.");
    }
}
