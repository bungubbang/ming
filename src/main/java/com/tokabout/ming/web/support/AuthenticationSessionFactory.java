package com.tokabout.ming.web.support;

import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ykpark.planet@sk.com
 */
public class AuthenticationSessionFactory {

    @Autowired
    private CacheManager cacheManager;


    public AuthenticationSession getSession(HttpServletRequest request, HttpServletResponse response) {
        return new AuthenticationSession(request, response, cacheManager);
    }

    public AuthenticationSession getSession(NativeWebRequest webRequest) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        return getSession(request, response);
    }

}
