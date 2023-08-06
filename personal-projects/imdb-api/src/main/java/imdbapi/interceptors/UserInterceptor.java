package imdbapi.interceptors;

import imdbapi.dao.User;
import imdbapi.exceptions.InvalidAuthKeyException;
import imdbapi.exceptions.InvalidHeaderException;
import imdbapi.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final String AUTH_KEY = "Auth-Key";

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws InvalidHeaderException, InvalidAuthKeyException {
        String authKey = request.getHeader(AUTH_KEY);
        if (StringUtils.isBlank(authKey)) {
            throw new InvalidHeaderException("Missing Auth-Key header in request.", "INVALID_HEADER");
        }
        User user = authenticationService.authenticateUserByAuthKey(authKey);
        request.setAttribute("principal", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        LOGGER.debug(request.getRequestURI() + " Ended at " + new LocalDateTime() + " TimeTaken: " + (System.currentTimeMillis() - (long) request.getAttribute("apiStartMillis")) + " millis");
    }
}
