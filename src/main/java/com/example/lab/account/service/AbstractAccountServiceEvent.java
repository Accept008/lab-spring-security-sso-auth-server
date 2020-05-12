/*
 * Project Name: SoundBus-Open-Platform
 * Package Name: cn.soundbus.platform.core.account.service
 * Copyright: Copyright(C) 2015-2016 SoundBus Technologies, Co., LTD. All rights reserved.
 */
package com.example.lab.account.service;

import com.example.lab.account.domain.User;

/**
 * @author <a href="mailto:jacky.wu@soundnet.com.cn">Jacky Wu</a>
 * @since 16/7/2 上午11:57
 */
public abstract class AbstractAccountServiceEvent implements AccountOrientedServiceEvent {

    protected User user;

    public AbstractAccountServiceEvent(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }
}
