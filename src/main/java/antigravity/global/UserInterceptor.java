package antigravity.global;

import antigravity.global.exception.AntiException;
import antigravity.user.exception.UserErrorCode;
import antigravity.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod)handler;

        UserAnnotation user = method.getMethodAnnotation(UserAnnotation.class);

        if(user == null) {
            return true;
        }

        try {
            Integer userId = Integer.parseInt(request.getHeader("X-USER-ID"));
            userService.validateExistUser(userId);
        }catch (NumberFormatException e) {
            throw new AntiException(UserErrorCode.USER_ID_NOT_EXIST);
        } catch (Exception e) {
            throw new AntiException(UserErrorCode.USER_NOT_EXIST);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
