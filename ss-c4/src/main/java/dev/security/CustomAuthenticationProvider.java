package dev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    // Here we implement the authentication logic
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        // 1 - request is authenticated you should return an fully authenticated Authetnication obj
        if (userDetails != null){
            if(encoder.matches(password, userDetails.getPassword())){
                // 3 - Authentication isn't supported by this Ap -> return null
                var auth = new UsernamePasswordAuthenticationToken(userName, password, userDetails.getAuthorities());
                return auth;
            }
        }

        // 2 - if not, you should throw AuthenticationException
        throw new BadCredentialsException("Error!");

    }

    @Override
    public boolean supports(Class<?> authType) {

        return UsernamePasswordAuthenticationToken.class.equals(authType);
    }
}
