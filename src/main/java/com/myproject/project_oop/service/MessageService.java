package com.myproject.project_oop.service;

import com.myproject.project_oop.model.Message;
import com.myproject.project_oop.request.message.MessageRequest;

public interface MessageService {

    Message save(Message message);

    Message saveNewMessage(MessageRequest request);

}
