package antigravity.global;

import antigravity.global.exception.AntiException;
import antigravity.user.exception.UserErrorCode;
import antigravity.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Integer userId = Integer.parseInt(request.getHeader("X-USER-ID"));
            userService.validateExistUser(userId);
        }catch (Exception e) {
            throw new AntiException(UserErrorCode.USER_NOT_EXIST);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
