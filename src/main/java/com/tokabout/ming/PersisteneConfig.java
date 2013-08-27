package com.tokabout.ming;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 8/25/13
 */
@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.tokabout.ming.module")
public class PersisteneConfig {
}
