package antigravity.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import antigravity.exception.AntigravityException;
import antigravity.exception.ErrorCode;

public class HeaderInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		if (request.getHeader("X-USER-ID") == null) {
			throw new AntigravityException(ErrorCode.HEADER_NULL);
		}

		return true;
	}

}
