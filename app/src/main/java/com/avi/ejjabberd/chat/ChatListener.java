package com.avi.ejjabberd.chat;

import com.avi.ejjabberd.dto.Chat;

/**
 * Created by Avinash on 8/2/15.
 */
public interface ChatListener {

    public void processMessage(Chat chat);
}
