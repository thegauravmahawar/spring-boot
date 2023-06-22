package imdbapi.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class NonUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        request.setAttribute("apiStartMillis", System.currentTimeMillis());
//        UUID requestId = UUID.randomUUID();
//        MDC.clear();
//        MDC.put("UUID", String.valueOf(requestId));
//        LOGGER.debug(request.getMethod() + " : " + request.getRequestURI() + " Started at " + new LocalDateTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        LOGGER.debug(request.getRequestURI() + " Ended at " + new LocalDateTime() + " TimeTaken: " + (System.currentTimeMillis() - (long) request.getAttribute("apiStartMillis")) + " millis");
    }
}
