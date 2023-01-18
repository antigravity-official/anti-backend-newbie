package antigravity.config;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import antigravity.exception.payload.ErrorCode;
import antigravity.exception.custom.NotFoundParameter;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(UserId.class) != null && Integer.class.equals(
			parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String userId = webRequest.getHeader("X-USER-ID");

		if(!StringUtils.hasText(userId))
			throw new NotFoundParameter("not exist userId on header", ErrorCode.NOT_FOUND_HEADER);

		return Integer.parseInt(userId);
	}
}
