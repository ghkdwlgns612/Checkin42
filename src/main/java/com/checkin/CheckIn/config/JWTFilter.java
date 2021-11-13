package com.checkin.CheckIn.config;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JWTFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                cookies = new Cookie[0];
            }
            if (Arrays.stream(cookies).noneMatch(cookie -> cookie.getName().endsWith("token"))) {
//                this.logger.info("token is not set to cookie");
//                throw new NoSuchFieldError("There is no token");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new ServletException("Token filter only support HttpServlet");
        }
    }
}
