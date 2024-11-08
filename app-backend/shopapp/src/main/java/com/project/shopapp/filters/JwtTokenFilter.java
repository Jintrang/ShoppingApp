package com.project.shopapp.filters;


import com.project.shopapp.components.JwtTokenUtil;
import com.project.shopapp.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    //Cho phep: nhu nao thi cho request di qua
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }

                final String token = authHeader.substring(7);
                final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
                if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User userDetails = (User)userDetailsService.loadUserByUsername(phoneNumber);
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null,
                                        userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            filterChain.doFilter(request, response);
        } catch(Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unAuthorized");
        }
    }



//    private boolean isByPassToken(@NotNull HttpServletRequest request){
//        final List<Pair<String, String>> byPassTokens = Arrays.asList(
//                Pair.of("api/v1/categories/**", "GET"),
//                Pair.of("api/v1/products/**", "GET"),
//                Pair.of("api/v1/users/register", "POST"),
//                Pair.of("api/v1/users/login", "POST")
//        );
//        for(Pair<String, String> byPassToken : byPassTokens) {
//            if(request.getServletPath().contains(byPassToken.getFirst()) &&
//                    request.getMethod().equals(byPassToken.getSecond())){
//                return true;
//            }
//        }
//        return false;
//    }
    private boolean isByPassToken(@NotNull HttpServletRequest request){
        final List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of("api/v1/categories", "GET"),
                Pair.of("api/v1/products", "GET"),
                Pair.of("api/v1/users/register", "POST"),
                Pair.of("api/v1/users/login", "POST")
        );
        for(Pair<String, String> byPassToken : byPassTokens) {
            if(request.getServletPath().matches(byPassToken.getFirst() + "(/.*)?") &&
                    request.getMethod().equals(byPassToken.getSecond())){
                return true;
            }
        }
        return false;
    }

}
