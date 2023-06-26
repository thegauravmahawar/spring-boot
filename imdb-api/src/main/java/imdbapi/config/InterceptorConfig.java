package imdbapi.config;

import imdbapi.interceptors.AdminInterceptor;
import imdbapi.interceptors.NonUserInterceptor;
import imdbapi.interceptors.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final UserInterceptor userInterceptor;
    private final NonUserInterceptor nonUserInterceptor;
    private final AdminInterceptor adminInterceptor;

    public InterceptorConfig(UserInterceptor userInterceptor, NonUserInterceptor nonUserInterceptor,
                             AdminInterceptor adminInterceptor) {
        this.userInterceptor = userInterceptor;
        this.nonUserInterceptor = nonUserInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns(USER_PATTERNS)
                .excludePathPatterns(NON_USER_PATTERNS)
                .excludePathPatterns(ADMIN_PATTERNS);
        registry.addInterceptor(nonUserInterceptor)
                .addPathPatterns(NON_USER_PATTERNS);
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns(ADMIN_PATTERNS);
    }

    private final String[] USER_PATTERNS = new String[]{
            "/**",
    };

    private final String[] NON_USER_PATTERNS = new String[]{
            "/",
            "/env",
            "/users/signup",
            "/users/auth-key",
    };

    private final String[] ADMIN_PATTERNS = new String[]{
            "/admin/**",
            "/title",
    };

}
