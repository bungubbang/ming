package com.tokabout.ming.module;

import com.tokabout.ming.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 13. 8. 30.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
