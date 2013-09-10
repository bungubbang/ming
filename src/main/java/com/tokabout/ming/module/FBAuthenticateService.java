package com.tokabout.ming.module;

import com.tokabout.ming.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 9/8/13
 */
@Service
public class FBAuthenticateService implements AuthenticateService {

    @Autowired UserRepository userRepository;

    @Override
    public User load(User user) {
        User byEmail = userRepository.findByEmail(user.getEmail());
        if(byEmail == null) {
            byEmail = userRepository.save(user);
        }

        return byEmail;
    }
}
