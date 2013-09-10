package com.tokabout.ming.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 8/25/13
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String name;

    private String profile;

    private String email;

    private String fbid;
}
