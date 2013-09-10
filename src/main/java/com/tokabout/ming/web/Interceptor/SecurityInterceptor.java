package com.tokabout.ming.web.Interceptor;

import com.tokabout.ming.web.support.AuthenticationSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sungyong.jung@sk.com
 * @author ykpark.planet@sk.com
 */
@Slf4j
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthenticationSessionFactory sessionFactory;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(sessionFactory.getSession(request, response).getUserPrincipal() != null) {
            return true;
        }

        String targetUrl = request.getContextPath() + "/login";
        response.sendRedirect(targetUrl);

        log.debug("Redirecting to: {}", targetUrl);

        return false;
    }

}
