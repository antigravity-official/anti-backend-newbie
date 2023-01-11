package antigravity.common;

import antigravity.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String USER_ID = "X-USER-ID";
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(MemberID.class) != null
                && parameter.hasParameterAnnotation(MemberID.class);
    }

    @Override
    public Long resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return authService.validateId(request.getHeader(USER_ID));
    }
}
