# Spring Security

## Introduction

**Short answer**: Spring Security is a bunch of servlet filters that help you add authentication and authorization to your web application.

## Web Application Security: 101

**Authentication**: Authentication refers to the process of verifying the identity of a user, based on provided credentials.

**Authorization**: Authorization refers to the process of determining if a user has proper permission to perform a particular action or read particular data, assuming that the user is successfully authenticated.

**Principal**: Principal refers to the currently authenticated user.

**Granted Authority**: Refers to the permission of the authenticated user.

**Role**: Refers to a group of permissions of the authenticated user.

**Servlet Filters**:

There is no security hardcoded into the `DispatcherServlet`. Optimally, the authentication and authorization should be done before a request hits your @Controller.

There is a way to do it: you can put filters in front of servlets, which means you could think about writing a SecurityFilter and configure it in your Tomcat to filter every incoming HTTP request before it hits your servlet.

![Security Filter Example](../assets/security-filter-example.png)

A naive SecurityFilter

```java
import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityServletFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordToken token = extractUsernameAndPasswordFromRequest(request);

        if (notAuthenticated(token)) {
            // either no or wrong username/password
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401
            return;
        }

        if (notAuthorized(token, request)) {
            // you are logged in, but don't have the proper rights
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403
            return;
        }

        // allow the request to go to Spring's DispatcherServlet and @RestController/@Controller
        chain.doFilter(request, response);
    }

    private UsernamePasswordToken extractUsernameAndPasswordFromRequest(HttpServletRequest request) {
        return checkVariousLoginOptions(request);
    }

    private boolean notAuthenticated(UsernamePasswordToken token) {
        // compare the token with what you have in your database...or in-memory...
        return false;
    }

    private boolean notAuthorized(UsernamePasswordToken token, HttpServletRequest request) {
        // check if currently authenticated user has the permission/role to access this request's URI.
        // e.g. /admin needs a ROLE_ADMIN, /callcenter needs ROLE_CALLCENTER, etc.
        return false;
    }

}
```

1. First, the filter needs to extract a username/password from the request. It could be via a Basic Auth HTTP Header, or form fields, or a cookie, etc.
2. Then the filter needs to validate that username/password combination against something, like a database.
3. The filter needs to check, after successful authentication, that the user is authorized to access the requested URI.
4. If the request survives all the checks, then the filter can let the request go through to your DispatcherServlet, i.e. your @Controllers.

**FilterChains**