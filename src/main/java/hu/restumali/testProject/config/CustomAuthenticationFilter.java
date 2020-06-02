package hu.restumali.testProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${google.recaptcha.secret}")
    private String GOOGLE_RECAPTCHA_SECRET;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getSession().getAttribute("failedAuths") != null){
            if (Integer.parseInt(request.getSession().getAttribute("failedAuths").toString()) >= 3){
                String captchaResponse = request.getParameter("g-recaptcha-response");
                String remoteAddr = request.getRemoteAddr();
                Boolean captchaValidationSuccess = verifyRecaptcha(captchaResponse, remoteAddr);
                if (!captchaValidationSuccess)
                    throw new AuthenticationServiceException("Google reCaptcha validation failed");

                logger.info("Google reCaptcha validation success");
            }
        }
        return super.attemptAuthentication(request,response);
    }

    private Boolean verifyRecaptcha(String clientResponse, String remoteAddr){
        Map<String, String> body = new HashMap<>();
        body.put("secret", GOOGLE_RECAPTCHA_SECRET);
        body.put("response", clientResponse);
        body.put("remoteip", remoteAddr);
        logger.debug(String.format("Request body for recaptcha: {}", body));

        ResponseEntity<Map> recaptchaResponseEntity = restTemplateBuilder.build()
                .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL + "?secret={secret}&response={response}&remoteip={remoteip}", body, Map.class, body);

        logger.debug(String.format("Response from recaptcha: {}",
                recaptchaResponseEntity));

        Map<String, Object> responseBody = recaptchaResponseEntity.getBody();

        return Boolean.valueOf(String.valueOf(responseBody.get("success")));
    }
}
