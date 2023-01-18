package antigravity.common.login;

import antigravity.common.exception.InvalidLoginException;
import antigravity.common.exception.RequiredLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String USER_HEADER = "X-USER-ID";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String userInfo = Optional.ofNullable(request.getHeader(USER_HEADER))
                .orElseThrow(RequiredLoginException::new);

        return convertUserId(userInfo);
    }

    private Long convertUserId(String userInfo) {
        try {
            long userId = Long.parseLong(userInfo.trim());
            checkValidUserId(userId);
            return userId;
        } catch (Exception e) {
            throw new InvalidLoginException();
        }
    }

    private void checkValidUserId(Long userId) {
        if(userId <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
