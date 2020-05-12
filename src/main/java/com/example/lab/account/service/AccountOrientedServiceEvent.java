/*
 * Project Name: SoundBus-Open-Platform
 * Package Name: cn.soundbus.platform.core.account
 * Copyright: Copyright(C) 2015-2016 SoundBus Technologies, Co., LTD. All rights reserved.
 */
package com.example.lab.account.service;

import com.example.lab.account.domain.User;

/**
 * Account Oriented Event
 * <p>
 * A abstract of events related with account during the service processing
 *
 * @author <a href="mailto:jacky.wu@soundnet.com.cn">Jacky Wu</a>
 * @since 16/7/2 上午11:48
 */
public interface AccountOrientedServiceEvent {

    User getUser();

}
