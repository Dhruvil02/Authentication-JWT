package com.example.jwt_auth_api.configs;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.jwt_auth_api.services.JwtService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter (HandlerExceptionResolver handlerExceptionResolver,UserDetailsService userDetailsService,
    JwtService jwtService){
        this.handlerExceptionResolver=handlerExceptionResolver;
        this.jwtService=jwtService;
        this.userDetailsService=userDetailsService;
    }

    //Overriding required abstract method of OncePerRequestFilter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse responce, FilterChain filterChain)
    throws ServletException, IOException {
        final String authToken = request.getHeader("Authorization");
        if(authToken==null || !authToken.startsWith("Bearer ")){
            filterChain.doFilter(request, responce);
            return;
        }
        try{
            final String jwtBody = authToken.substring(7);
            final String username = jwtService.extractUsername(jwtBody);

            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

            //If the token is valid, extract the username, find the related user in the database
            if(username!=null && authentication==null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if(jwtService.isTokenValid(jwtBody,userDetails)){
                    UsernamePasswordAuthenticationToken authToken1 = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                    //set it in the authentication context so you can access it in any application layer.3
                    authToken1.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken1);
                }
            }
            filterChain.doFilter(request, responce);
        } catch (Exception exception){
            handlerExceptionResolver.resolveException(request, responce, null, exception);
        }
    }
    //Custom Authentication is ready
    //Define Criteria does incoming requests to have befor forwording
    // to application middelware in SecurityConfig.js
}
