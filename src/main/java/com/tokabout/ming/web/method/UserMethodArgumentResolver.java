package com.tokabout.ming.web.method;

import com.tokabout.ming.domain.User;
import com.tokabout.ming.web.support.AuthenticationException;
import com.tokabout.ming.web.support.AuthenticationSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by bungubbang
 * Email: bungubbang57@gmail.com
 */
@Slf4j
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private AuthenticationSessionFactory sessionFactory;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        User user = sessionFactory.getSession(webRequest).getUserPrincipal();
        if(user == null) {
            throw new AuthenticationException("authentication credential is not found.");
        }

        log.info("looking up authentication credential: {}", user);

        return user;
    }

}
