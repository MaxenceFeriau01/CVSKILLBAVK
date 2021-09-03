package com.ensemble.entreprendre.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ensemble.entreprendre.security.helper.JwtTokenUtilBean;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private JwtTokenUtilBean jwtTokenUtilBean;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    	String username = null;
    	if(jwtTokenUtilBean.hasToken(req)) {
    		username = jwtTokenUtilBean.getUsername(req);
    		logger.debug("token with bearer found, username : " + username);
    	} else {
            logger.debug("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    		logger.debug("prepare user session -> load user details");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            logger.debug("details found");
            if (jwtTokenUtilBean.validateToken(req, userDetails)) {
            	logger.debug("token is valid !");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }

}