package com.tokabout.ming.web;

import com.tokabout.ming.domain.User;
import com.tokabout.ming.module.AuthenticateService;
import com.tokabout.ming.module.UserRepository;
import com.tokabout.ming.web.support.AuthenticationSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 13. 8. 30.
 */
@Slf4j
@Controller
public class AuthenticationController {

    @Autowired UserRepository userRepository;
    @Autowired AuthenticateService authenticateService;
    @Autowired AuthenticationSessionFactory sessionFactory;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public User login(User token, NativeWebRequest webRequest) {
        User user = authenticateService.load(token);
        sessionFactory.getSession(webRequest).setUserPrincipal(user);

        return user;
    }
}
