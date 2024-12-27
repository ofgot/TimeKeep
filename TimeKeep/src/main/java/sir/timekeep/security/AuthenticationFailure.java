package sir.timekeep.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import sir.timekeep.security.model.LoginStatus;

import java.io.IOException;

public class AuthenticationFailure implements AuthenticationFailureHandler {

    private final ObjectMapper mapper;

    public AuthenticationFailure(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException {
        final LoginStatus status = new LoginStatus(false, false, null, e.getMessage());
        mapper.writeValue(httpServletResponse.getOutputStream(), status);
    }
}