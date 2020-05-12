/*
 * Project Name: SoundBus-Open-Platform
 * Package Name: cn.soundbus.platform.core.account.service
 * Copyright: Copyright(C) 2015-2016 SoundBus Technologies, Co., LTD. All rights reserved.
 */
package com.example.lab.account.service;

import com.example.lab.account.domain.User;

/**
 * Account Random Pwd Generated Event
 *
 * @author <a href="mailto:jacky.wu@soundnet.com.cn">Jacky Wu</a>
 * @since 16/7/2 上午11:56
 */
public class AccountRandomPwdGeneratedEvent extends AbstractAccountServiceEvent {

    private String randomPwd;

    public AccountRandomPwdGeneratedEvent(User user) {
        super(user);
    }

    public AccountRandomPwdGeneratedEvent(User user, String randomPwd) {
        this(user);
        this.randomPwd = randomPwd;
    }

    public String getRandomPwd() {
        return randomPwd;
    }

    public void setRandomPwd(String randomPwd) {
        this.randomPwd = randomPwd;
    }
}
