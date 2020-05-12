/*
 * Project Name: SoundBus-Open-Platform
 * Package Name: cn.soundbus.platform.core.account.service
 * Copyright: Copyright(C) 2015-2016 SoundBus Technologies, Co., LTD. All rights reserved.
 */
package com.example.lab.account.service;

/**
 * The handler to handle {@link AccountOrientedServiceEvent}s.
 *
 * @author <a href="mailto:jacky.wu@soundnet.com.cn">Jacky Wu</a>
 * @since 16/7/2 下午12:01
 */
public interface AccountOrientedServiceEventHandler<T extends AccountOrientedServiceEvent> {

    /**
     * Handle {@link AccountOrientedServiceEvent}
     *
     * @param event the event to be handled
     */
    void handle(T event);

    /**
     * To estimate handler can handle the event or not.
     *
     * @param event the event to be estimated
     * @return <code>true</code>: if the handler can handle the event
     */
    boolean support(AccountOrientedServiceEvent event);
}
