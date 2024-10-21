package me.crud_backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        System.out.println("Authorization --- " + authorizationHeader);

        String username = null;
        String jwtToken = null;

        // Extract JWT token from the Authorization header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            System.out.println("Token --- " + jwtToken);
            try {
                username = jwtHelper.extractUsername(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired");
            } catch (Exception e) {
                System.out.println("Error extracting JWT token: " + e.getMessage());
            }
        }
        // Validate token and set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("username ---- " + username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            System.out.println("userDetails.getUsername()--- " + userDetails.getUsername());
            Boolean validateToken = jwtHelper.validateToken(jwtToken, userDetails.getUsername());

            if (validateToken) {
//                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Validation fails !!");
            }
        }

        // Proceed with the next filter
        filterChain.doFilter(request, response);
    }
}
