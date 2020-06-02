package hu.restumali.testProject.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom class to add my own logic when there is an authentication failure. This class counts the failed login attempts.
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public CustomAuthenticationFailureHandler() {
        super("/user/login?error");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        int failedRequestsCnt;
        if (request.getSession().getAttribute("failedAuths") == null)
            failedRequestsCnt = 1;
        else
            failedRequestsCnt = Integer.parseInt(request.getSession().getAttribute("failedAuths").toString()) + 1;
        request.getSession().setAttribute("failedAuths", failedRequestsCnt);
        super.onAuthenticationFailure(request, response, exception);
    }
}
