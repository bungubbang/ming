package com.tokabout.ming.web.support;

import com.google.common.base.Strings;
import com.tokabout.ming.domain.User;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author ykpark.planet@sk.com
 */
public class AuthenticationSession implements Serializable {

    private static final long serialVersionUID = 5291149528082949734L;

    public static final String COOKIE_NAME = "MSESSIONID";
    public static final String COOKIE_PATH = "/";
    public static final Integer COOKIE_MAX_AGE = 17280000;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Cache authenticationTokenCache;
    private CookieGenerator cookieGenerator;

    private String id;


    AuthenticationSession(HttpServletRequest request, HttpServletResponse response, CacheManager cacheManager) {
        this.request = request;
        this.response = response;

        this.authenticationTokenCache = cacheManager.getCache("login");

        this.cookieGenerator = new CookieGenerator();
        this.cookieGenerator.setCookieName(COOKIE_NAME);
        this.cookieGenerator.setCookiePath(COOKIE_PATH);
        this.cookieGenerator.setCookieMaxAge(COOKIE_MAX_AGE);

        this.id = extractSessionIdForCookie();
        if(Strings.isNullOrEmpty(id)) {
            this.id = generateSessionId();
        }
    }

    String extractSessionIdForCookie() {
        Cookie[] cookies = request.getCookies();
        if((cookies == null) || (cookies.length == 0)) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

    String generateSessionId() {
        String id = UUID.randomUUID().toString();
        cookieGenerator.addCookie(response, id);
        return id;
    }

    public void setUserPrincipal(User user) {
        authenticationTokenCache.put(new Element(id, user));
    }

    public User getUserPrincipal() {
        Element element = authenticationTokenCache.get(id);
        if(element == null) {
            return null;
        }

        return (User) element.getObjectValue();
    }

    public void invalidate() {
        authenticationTokenCache.remove(id);
        request.getSession().invalidate();
        cookieGenerator.removeCookie(response);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthenticationSession)) return false;

        return id.equals(((AuthenticationSession) o).id);
    }

    @Override
    public String toString() {
        return "AuthenticationSession{" + "id='" + id + '\'' + '}';
    }


}
