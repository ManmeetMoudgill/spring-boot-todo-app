package com.manmeet.moudgill.NewTodoApplication.config.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtHelper jwtService;

    private final CustomUserDetailService userDetailService;
    @Autowired
    public JwtAuthenticationFilter(JwtHelper jwtService, CustomUserDetailService userDetailService){
        this.jwtService=jwtService;
        this.userDetailService=userDetailService;
    }
    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        //need to get the token from the header and validate the jwt token
        String authHeader=request.getHeader("Authorization");
        System.out.println("header auth"+authHeader);
        String jwtToken="";
        final String username;



        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            //continue with another filter
            filterChain.doFilter(request,response);
            return;
        }


        /* GET THE TOKEN FROM THE HEADER IF AUTHORIZATION IS NOT NULL */
        jwtToken=authHeader.substring(7);

        /*
        NEED TO CALL USER DETAILS SERVICE TO CHECK IF USER EXIST OR NOT
        IN ORDER TO DO THAT WE NEED TO CALL THE JWT SERVICE TO EXTRACT THE INFORMATION FROM  THE GIVEN TOKEN
        */

        username=jwtService.extractUsername(jwtToken);



        //need to check if username is not null and it is already authenticated
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            //getting the userDetails from the database
            UserDetails userDetails=this.userDetailService.loadUserByUsername(username);

            //validating the token
            if(this.jwtService.isTokenValid(jwtToken,userDetails)){
                //if token is valid we need to create a instance of UsernamePasswordAuthenticationToken to pass the security context
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());


                //setting some extra details of request by creating an object of authenticationDetailSource and passing it the request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                //need to update the security Context holder
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }


        //calling the filter chain to hand to the next filter
        filterChain.doFilter(request,response);



    }
}
