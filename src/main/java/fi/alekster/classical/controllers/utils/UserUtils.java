package fi.alekster.classical.controllers.utils;

import fi.alekster.classical.db.tables.pojos.Credential;
import fi.alekster.classical.security.JwtTokenUtil;
import fi.alekster.classical.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by aleksandr on 16.11.2017.
 */

@Component
public class UserUtils {
    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserUtils (JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    public Credential getAuthenticatedCredential(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);

        return new Credential(
                jwtUser.getUsername(),
                jwtUser.getPassword()
        );
    }
}