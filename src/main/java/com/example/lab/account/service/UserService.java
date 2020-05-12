package com.example.lab.account.service;


import com.example.lab.account.domain.LoginType;
import com.example.lab.account.domain.ThirdPartyAccountInfo;
import com.example.lab.account.domain.User;
import com.example.lab.account.exception.AccountBussinessException;
import com.example.lab.account.repository.UserRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class UserService {

    private static final int RANDOM_NUMBER_LENGTH = 6;

    @Autowired
    private UserRepository userRepository;

    private Set<AccountOrientedServiceEventHandler> accountOrientedServiceEventHandlers;

    /**
     * 根据id查询
     *
     * @param id 用户ID
     * @return
     */
    public User findById(String id) {
        return userRepository.findById(id).get();
    }

    public String findUserMobile(String userId) {
        User user = findById(userId);
        return getFirstMobile(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public String getFirstMobile(User user) {
        if (user == null) {
            return null;
        }
        Set<String> mobiles = user.getMobiles();
        if (!CollectionUtils.isEmpty(mobiles)) {
            return Lists.newArrayList(mobiles).get(0);
        }
        return null;
    }

    public Map<String, ThirdPartyAccountInfo> getThirdAccounts(User user) {
        Map<String, ThirdPartyAccountInfo> accounts = user.getThirdAccounts();
        return accounts;
    }

    /**
     * 手机用户注册
     *
     * @param mobile   手机号
     * @param password 密码
     * @param systemId 系统id
     * @return 注册的user
     */
    public User mobileRegisterInSystem(String mobile, String password, String systemId) {
        List<User> users = findByMobileAndSystemId(mobile, systemId);
        if (!CollectionUtils.isEmpty(users)) {
            return users.get(0); // 返回已存在的用户保持接口幂等性
        }
        User user = new User();
        user.getMobiles().add(mobile);
        generateUsernameAndNick(user);
        user.setPassword(password);
        encryptPasswordThenSetToUser(user);
        user.setSystemId(systemId);
        userRepository.save(user);

        return user;
    }

    /**
     * 用户密码修改
     *
     * @param userId 用户ID
     * @param newPwd 新密码
     * @param oldPwd 旧密码
     */
    public void changePassword(String userId, String newPwd, String oldPwd) {
        User user = userRepository.findById(userId).get();
        if (StringUtils.isBlank(user.getPassword())) {
            throw new AccountBussinessException("BIND_MOBILE_OR_EMAIL_FIRST",
                    "BIND_MOBILE_OR_EMAIL_FIRST");
        }
        boolean passwordMatch = passwordMatch(oldPwd, user.getPassword(), user.getSalt());
        if (!passwordMatch) {
            throw new AccountBussinessException("PASSWORD_ORIGINAL_NO_MATCH",
                    "PASSWORD_ORIGINAL_NO_MATCH");
        }
        user.setPassword(newPwd);
        encryptPasswordThenSetToUser(user);
        userRepository.save(user);
    }

//    /**
//     * 用户忘记密码
//     *
//     * @param mobile 手机号
//     */
//    public void findPassword(String mobile, String password, String systemId) {
//        List<User> users = findByMobileAndSystemId(mobile, systemId);
//        if (CollectionUtils.isEmpty(users)) {
//            throw new AccountBussinessException("USER_NO_FOUND", "USER_NO_FOUND:" + mobile);
//        }
//        User user = users.get(0);
//        // 重置密码
//        user.setPassword(password);
//        encryptPasswordThenSetToUser(user);
//        userRepository.save(user);
//    }
//
//    /**
//     * 根据邮箱重置用户密码
//     *
//     * @param email 手机号
//     */
//    public void resetPassword(String email, String password, String systemId) {
//        List<User> users = findByEmailAndSystemId(email, systemId);
//        if (CollectionUtils.isEmpty(users)) {
//            throw new AccountBussinessException("USER_NO_FOUND", "USER_NO_FOUND:" + email);
//        }
//        User user = users.get(0);
//        // 重置密码
//        user.setPassword(password);
//        encryptPasswordThenSetToUser(user);
//        userRepository.save(user);
//    }
//
//    /**
//     * 第三方账号绑定
//     *
//     * @param userId      用户id
//     * @param type        第三方账号类型
//     * @param thirdId     第三方账号ID
//     * @param nickName    昵称
//     * @param iconUrl     头像地址
//     * @param accessToken access token
//     */
//    public void thirdAccountBind(String userId, String type, String thirdId, String nickName,
//                                 String iconUrl, String accessToken, String systemId) {
//        User user = userRepository.findById(userId).get();
//        // 判断类型是否合法
//        boolean allow = ThirdAccountType.isAllowLoginType(type);
//        if (!allow) {
//            throw new UnsupportedThirdPartyAccountTypeException();
//        }
//        // 判断该用户是否已经绑定该类型账号
//        if (user.getThirdAccounts().containsKey(type)) {
//            ThirdPartyAccountInfo thirdPartyAccountInfo = user.getThirdAccounts().get(type);
//            if (null != thirdPartyAccountInfo
//                    && !thirdPartyAccountInfo.getUserId().equals(thirdId)) {
//                throw new AlreadyBindAccountTypeException(
//                        "User already bind with specified account type {0}.", type);
//            } else {
//                return;
//            }
//        }
//
//        // 判断账号是否已经被其他账户绑定
//        User userExists = userRepository.findByThirdAccountsAndSystemId(type, thirdId, systemId);
//        if (userExists != null && !userExists.getId().equals(userId)) {
//            throw new ThirdPartyAccountAlreadyBindException(
//                    "The third party (type:{0}) account bind with other user already.", type);
//        }
//
//        // 添加第三方账号
//        ThirdPartyAccountInfo acc =
//                new ThirdPartyAccountInfo(type, accessToken, thirdId, nickName, iconUrl);
//        user.getThirdAccounts().put(type, acc);
//        userRepository.save(user);
//    }
//
//    /**
//     * 第三方账号解绑
//     *
//     * @param userId 用户id
//     * @param type   第三方账号类型
//     */
//    public void thirdAccountUnbind(String userId, String type) {
//        User user = userRepository.findById(userId).get();
//        // 判断类型是否合法
//        boolean allow = ThirdAccountType.isAllowLoginType(type);
//        if (!allow) {
//            throw new UnsupportedThirdPartyAccountTypeException();
//        }
//        // 判断该用户是否已经绑定该类型账号
//        if (!user.getThirdAccounts().containsKey(type)) {
//            throw new UnbindAccountTypeException(
//                    "User unbind with specified account type {0}.", type);
//        }
//
//        // 删除第三方数据
//        user.getThirdAccounts().remove(type);
//        userRepository.save(user);
//    }
//
//    /**
//     * 绑定手机号
//     *
//     * @param mobile 手机号
//     */
//    public void mobileBind(String userId, String mobile, String password) {
//        User user = userRepository.findById(userId).get();
//        // 判断账号是否已经绑定手机
//        if (CollectionUtils.isNotEmpty(user.getMobiles())) {
//            throw new AccountBussinessException("USER_HAS_BIND_MOBILE", "USER_HAS_BIND_MOBILE");
//        }
//        // 判断手机号是否已经绑定
//        List users = findByMobileAndSystemId(mobile, user.getSystemId());
//        if (CollectionUtils.isNotEmpty(users)) {
//            throw new AccountBussinessException("MOBILE_HAS_BIND", "MOBILE_HAS_BIND");
//        }
//        user.getMobiles().add(mobile);
//        // 如果没有密码，则生成随机密码
//        if (StringUtils.isBlank(user.getPassword()) || user.isInitializedUUIDPassword()) {
//            log.info("set password when binding mobile for user {}", userId);
//            String pwd = null;
//            boolean sendRandomPwd = false;
//            if (StringUtils.isBlank(password)) {
//                pwd = randomNumber(RANDOM_NUMBER_LENGTH);
//                sendRandomPwd = true;
//            } else {
//                pwd = password;
//            }
//            user.setPassword(pwd);
//            encryptPasswordThenSetToUser(user);
//
//            // populate event then let handlers handle it
//            if (sendRandomPwd) {
//                AccountRandomPwdGeneratedEvent event =
//                        new AccountRandomPwdGeneratedEvent(user, pwd);
//                handleEvent(event);
//            }
//        } else {
//            log.info("ignore set password when binding mobile for user {}", userId);
//        }
//        userRepository.save(user);
//    }
//
//    /**
//     * 第三方id绑定
//     *
//     * @param user 用户
//     * @param type 第三类型
//     * @param id   第三方ID
//     */
//    public User thirdIdBind(User user, String id, String type) {
//        user.getThirdIds().put(type, id);
//        return userRepository.save(user);
//    }
//
//    private void handleEvent(AccountRandomPwdGeneratedEvent event) {
//        if (null != accountOrientedServiceEventHandlers) {
//            for (AccountOrientedServiceEventHandler handler : accountOrientedServiceEventHandlers) {
//                handler.handle(event);
//            }
//        }
//    }
//
//    /**
//     * 重新绑定手机号
//     *
//     * @param mobile 手机号
//     */
//    public void mobileReBind(String userId, String mobile) {
//        User user = userRepository.findById(userId).get();
//        // 判断手机号是否已经绑定
//        List users = findByMobileAndSystemId(mobile, user.getSystemId());
//        if (CollectionUtils.isNotEmpty(users)) {
//            throw new AccountBussinessException("MOBILE_HAS_BIND", "MOBILE_HAS_BIND");
//        }
//        user.getMobiles().clear();
//        user.getMobiles().add(mobile);
//        userRepository.save(user);
//    }

//    public List<User> findByMobile(String mobile) {
//        return userRepository.findByMobile(new String[] {mobile});
//    }

    public List<User> findByMobileAndSystemId(String mobile, String systemId) {
        return userRepository.findByMobileAndSystemId(new String[]{mobile}, systemId);
    }

    public List<User> findByEmailAndSystemId(String mobile, String systemId) {
        return userRepository.findByEmailAndSystemId(new String[]{mobile}, systemId);
    }

    /**
     * 从用户的password属性取出未加密的密码,用salt处理用户密码生成然后设置回去
     *
     * @param user 用户object
     */
    public void encryptPasswordThenSetToUser(User user) {
        String salt = RandomStringUtils.randomAscii(16);
        byte[] saltByte = CodecSupport.toBytes(salt);
        String hashedPasswordBase64 = new Sha512Hash(user.getPassword(), saltByte, 1024).toBase64();
        user.setSalt(salt);
        user.setPassword(hashedPasswordBase64);
    }

    /**
     * 判断用户密码是否正确
     *
     * @param pwd    密码
     * @param oriPwd 原始密码
     * @param salt   salt
     */
    public boolean passwordMatch(String pwd, String oriPwd, String salt) {
        byte[] saltByte = CodecSupport.toBytes(salt);
        String hashedPasswordBase64 = new Sha512Hash(pwd, saltByte, 1024).toBase64();
        return hashedPasswordBase64.equals(oriPwd);

    }

    /**
     * 生成随机用户ID
     *
     * @return 随机用户ID
     */
    public String randomUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    /**
     * 生成随机用户名与昵称
     *
     * @param user
     */
    public void generateUsernameAndNick(User user) {
        String username = RandomStringUtils.randomAlphanumeric(10);
        user.setUsername(username);
        user.setNickName("user_" + username);
    }

    /**
     * 随机生成指定长度的数字序列
     *
     * @param len 长度
     * @return 随机生成的数字序列
     */
    public String randomNumber(int len) {
        return RandomStringUtils.randomNumeric(len);
    }

    /**
     * 邮箱注册
     *
     * @param identity 邮箱地址
     * @param password 密码
     * @param systemId 系统id
     * @return 注册的用户
     */
    public User emailRegisterInSystem(String identity, String password, String systemId) {
        List<User> users = userRepository.findByEmailAndSystemId(new String[]{identity}, systemId);
        if (!CollectionUtils.isEmpty(users)) {
            throw new AccountBussinessException("USER_REGISTER_EMAIL_EXISTS",
                    "USER_REGISTER_EMAIL_EXISTS");
        }
        User user = new User();
        user.getEmails().add(identity);
        generateUsernameAndNick(user);
        user.setPassword(password);
        encryptPasswordThenSetToUser(user);
        user.setSystemId(systemId);
        userRepository.save(user);

        return user;
    }

    /**
     * 根据用户登录唯一凭证获取用户id
     *
     * @param identity 登录凭证
     * @param type     登录类型,默认的是{@code LoginType.USERNAME}
     * @param systemId 系统id
     * @return 如果用户存在, 则返回用户id, 否则返回<code>null</code>
     */
    public String getUserIdByIdentityInSystem(String identity, String type, String systemId) throws IllegalArgumentException {
        if (StringUtils.isBlank(systemId)) {
            throw new IllegalArgumentException("SystemId is required.");
        }

        LoginType loginType = LoginType.determinLoginType(type);
        List<User> users = null;
        User user = null;
        switch (loginType) {
            case EMAIL:
                users = userRepository.findByEmailAndSystemId(new String[]{identity}, systemId);
                user = getFirstUser(users);
                break;
            case MOBILE:
                users = userRepository.findByMobileAndSystemId(new String[]{identity}, systemId);
                user = getFirstUser(users);
                break;
            case WECHAT:
            case QQ:
            case WEIBO:
            case ALIPAY:
                user = userRepository.findByThirdAccountsAndSystemId(loginType.value(), identity, systemId);
                break;
            case USERID:
                user = userRepository.findByIdAndSystemId(identity, systemId);
                break;
            default: // default is UserName type
                user = findUserByIdentity(identity, systemId);
                break;
        }

        if (null != user) {
            return user.getId();
        }

        return null;
    }

    /**
     * 根据用户标示查询用户，用户标示可能是用户名或userId
     */
    private User findUserByIdentity(String identity, String systemId) {
        User user = userRepository.findByUsernameAndSystemId(identity, systemId);
        if (user == null) {
            user = userRepository.findByIdAndSystemId(identity, systemId);
        }
        return user;
    }

    /**
     * 返回第一个user
     *
     * @param users user的集合
     * @return 如果集合不为空, 则返回第一个user, 否则返回<code>null</code>
     */
    private User getFirstUser(Collection<User> users) {
        if (CollectionUtils.isNotEmpty(users)) {
            return (User) CollectionUtils.get((Object) users, 0);
        }
        return null;
    }

    /**
     * 根据用户邮箱或者手机号码获取用户信息，取第一个
     * @param value
     * @return
     */
    public User getUserByEmailOrMobile(String value) {
        List<User> users = userRepository.findByUsernameOrEmailOrMobile(value);
        return getFirstUser(users);
    }


}
