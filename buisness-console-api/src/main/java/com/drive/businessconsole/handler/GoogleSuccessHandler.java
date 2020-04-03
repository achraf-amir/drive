package com.drive.businessconsole.handler;

import com.drive.businessconsole.user.UserAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class GoogleSuccessHandler implements AuthenticationSuccessHandler {

 /*   @Autowired
    private JwtAuthenticationService tokenService;*/

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HashMap<String, String> userDetailsMap = (HashMap<String, String>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();

        UserAuthentication user = getUserAuthentication(userDetailsMap);

        //String jwtToken = tokenService.getAuthenticationToken(user);

        response.setHeader("Authorization", "jwtToken");
        response.getWriter().write("achrarf");
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect("/user");
    }

    private UserAuthentication getUserAuthentication(HashMap<String, String> userDetailsMap) {
        String externalId = userDetailsMap.get("id");
        String email = userDetailsMap.get("email");
        String givenName = userDetailsMap.get("first_name");
        String surname = userDetailsMap.get("last_name");

        return new UserAuthentication("google", externalId, givenName, surname, email);
    }
}
