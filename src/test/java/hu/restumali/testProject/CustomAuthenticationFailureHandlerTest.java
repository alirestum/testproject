package hu.restumali.testProject;

import hu.restumali.testProject.config.CustomAuthenticationFailureHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomAuthenticationFailureHandlerTest {

    @TestConfiguration
    private static class AuthenticationFailureHandlerTestContextConfiguration {

        @Bean
        public CustomAuthenticationFailureHandler failureHandler(){ return new CustomAuthenticationFailureHandler(); }
    }

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Test
    public void whenAuthenticationFailure_thenSessionAttributeShouldBeOne(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        try {
            customAuthenticationFailureHandler.onAuthenticationFailure(request, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        assertThat(request.getSession().getAttribute("failedAuths")).isEqualTo(1);
    }

    @Test
    public void whenSecondAuthenticationFailure_thenSessionAttributeShouldBeTwo(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        try {
            customAuthenticationFailureHandler.onAuthenticationFailure(request, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        response = new MockHttpServletResponse();
        try {
            customAuthenticationFailureHandler.onAuthenticationFailure(request, response, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        assertThat(request.getSession().getAttribute("failedAuths")).isEqualTo(2);
    }
}
