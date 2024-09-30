package com.project.shopapp.filters;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Override
    //Cho phep: nhu nao thi cho request di qua
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        if(isByPassToken(request)) {
            filterChain.doFilter(request, response);
        }
        //filterChain.doFilter(request, response);
    }
    private boolean isByPassToken(@NotNull HttpServletRequest request){
        final List<Pair<String, String>> byPassTokens = Arrays.asList(
                Pair.of("api/v1/categories", "GET"),
                Pair.of("api/v1/products", "GET"),
                Pair.of("api/v1/users/register", "POST"),
                Pair.of("api/v1/users/login", "POST")
        );
        for(Pair<String, String> byPassToken : byPassTokens) {
            if(request.getServletPath().contains(byPassToken.getFirst()) &&
                    request.getMethod().equals(byPassToken.getSecond())){
                return true;
            }
        }
        return false;
    }

}
