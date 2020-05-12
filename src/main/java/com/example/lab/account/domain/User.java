package com.example.lab.account.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.*;

/**
 * 用户信息
 *
 * @author Miles.wang
 */
@Document(collection = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -6117108610171201352L;
    @Id
    private String id;
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
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
    private Map<String, ThirdPartyAccountInfo> thirdAccounts = new HashMap<>();
    private Map<String, String> thirdIds = new HashMap<>();
    @Version
    private long version;

    /* ---------------------------------------------- */

    public void initialUUIDPassword() {
        setPassword(genereateUUIDPassword());
    }

    private String genereateUUIDPassword() {
        return UUID.randomUUID().toString();
    }

    public boolean isInitializedUUIDPassword() {
        String uuidPassword = genereateUUIDPassword();
        if (password != null && password.length() == uuidPassword.length()) {
            if (uuidPassword.contains("-")) {
                return password.contains("-");
            } else {
                return true;
            }
        }
        return false;
    }

    /* ---------------------------------------------- */



    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(Set<String> mobiles) {
        this.mobiles = mobiles;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Map<String, ThirdPartyAccountInfo> getThirdAccounts() {
        return thirdAccounts;
    }

    public void setThirdAccounts(Map<String, ThirdPartyAccountInfo> thirdAccounts) {
        this.thirdAccounts = thirdAccounts;
    }

    public Map<String, String> getThirdIds() {
        return thirdIds;
    }

    public void setThirdIds(Map<String, String> thirdIds) {
        this.thirdIds = thirdIds;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
