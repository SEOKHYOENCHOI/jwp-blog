package techcourse.myblog.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import techcourse.myblog.application.dto.UserResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkLoginBeforeSignup(request)) {
            return true;
        }

        UserResponse userSession = (UserResponse) request.getSession().getAttribute("user");

        if ("/login".equals(request.getRequestURI()) && userSession == null) {
            return true;
        }

        if ("/login".equals(request.getRequestURI()) && userSession != null) {
            response.sendRedirect("/");
            return false;
        }

        if (userSession == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }


    private boolean checkLoginBeforeSignup(HttpServletRequest request) {
        return request.getRequestURI().equals("/users") && request.getMethod().equals("POST");
    }
}
