/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.spring.boot.config;

import com.cloud.spring.boot.common.exception.ApplicationException;
import com.cloud.spring.boot.common.util.Constants;
import com.cloud.spring.boot.config.auth.AuthUser;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author Quy Duong
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final Log LOGGER = LogFactory.getLog(this.getClass());


    /**
     * Do filter all request, if any request don't have token => though error =>
     * Will enable it later when all API apply secure
     *
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String authToken = request.getHeader(Constants.HEADER_TOKEN);
        if (authToken != null) {
            try {
                // TOBE Update
                AuthUser user = new AuthUser(authToken, authToken, authToken, authToken, authToken, authToken, true, authToken, 0);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ApplicationException ex) {
                // token not found or expired
                LOGGER.debug("doFilterInternal", ex);
            }
        }
        chain.doFilter(request, response);
    }
}
