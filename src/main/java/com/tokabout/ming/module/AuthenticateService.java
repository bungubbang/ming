package com.tokabout.ming.module;

import com.tokabout.ming.domain.User;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 9/8/13
 */
public interface AuthenticateService {
    User load(User user);
}
