package com.app.task_api.filters;

import com.app.task_api.dto.response.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class CsrfTokenFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    public CsrfTokenFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
        try {
            String method = request.getMethod();

            if (request.getRequestURI().endsWith("/login") ||
                    request.getRequestURI().endsWith("/register") || request.getRequestURI().contains("/image") ) {
                chain.doFilter(request, response);
                return;
            }

            if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "GET".equalsIgnoreCase(method)) {

                String sessionToken = (String) request.getSession().getAttribute("CSRF_TOKEN");
                String requestToken = request.getHeader("x-csrf-token");

                if (sessionToken == null || !sessionToken.equals(requestToken)) {
                    throw new UnauthorizedException("No Autorizado");
                    //response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Invalido");
                    //return;
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
