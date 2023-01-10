package antigravity.web;

import antigravity.application.exception.UserNotFoundException;
import antigravity.web.exception.InvalidUserInfoException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserInfo.class) && parameter.getParameterType()
            .equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String userId = webRequest.getHeader("X-USER");

        try {
            if (userId == null) {
                throw new NullPointerException();
            }

            return Long.parseLong(userId);
        } catch (NumberFormatException | NullPointerException e) {
            throw new InvalidUserInfoException();
        }
    }
}
