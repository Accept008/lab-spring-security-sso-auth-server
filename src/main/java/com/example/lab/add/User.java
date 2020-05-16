package com.example.lab.add;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * 用户信息
 *
 * @author Miles.wang
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -6117108610171201352L;
    private String id;
    private Date createTime;
    private Date updateTime;
    private String password;
    private String salt;
    /** username is one of user identities which must be unique and can not be updated. */
    private String username;
    private String name;
    private String nickName;
    private String iconUrl;
    private String systemId;
    private Integer gender;
    private Integer status;
    private String roles;
    private Set<String> mobiles = new HashSet<String>(0);
    private Set<String> emails = new HashSet<String>(0);
    private Map<String, String> thirdIds = new HashMap<>();
    private long version;

}
