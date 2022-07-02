package by.tretiak.demo.config.security.jwt;

import by.tretiak.demo.exception.source.ExceptionMessageSource;
import by.tretiak.demo.service.security.SecurityFilterAssistant;
import by.tretiak.demo.service.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    SecurityFilterAssistant assistant;

    private static final String AUTH_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader(AUTH_HEADER);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String userName = jwtUtils.getUsernameFromJwt(jwt);

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);

                if (this.assistant.checkAuthorization(userDetails)) {


                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
           response.setStatus(500);
           response.getWriter().write(ExceptionMessageSource.getMessage(ExceptionMessageSource.SERVER_ERROR));
        }
        filterChain.doFilter(request, response);
    }

}
