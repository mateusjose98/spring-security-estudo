package dev.security.filters;

import dev.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter implements Filter {

    @Autowired
    private AuthenticationManager manager;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String authorization = req.getHeader("Authorization");

    try {
        var auth = new CustomAuthentication(authorization, null);
        var result = manager.authenticate(auth);

        if(result.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(result);
            chain.doFilter(request, response);
        } else {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    }catch (AuthenticationException e){
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }


    }
}
