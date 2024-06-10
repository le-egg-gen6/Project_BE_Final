package com.myproject.project_oop.controller.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.myproject.project_oop.dto.request.message.MessageRequest;
import com.myproject.project_oop.dto.response.message.MessageResponse;
import com.myproject.project_oop.service.MessageService;
import com.myproject.project_oop.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketEventHandler {

    private static final Map<Integer, SocketIOClient> clients = new ConcurrentHashMap<>();

    private final SocketIOServer socketIOServer;

    private final MessageService messageService;

    private final ConversationService roomService;

    @Autowired
    public SocketEventHandler(
            SocketIOServer socketIOServer,
            MessageService messageService,
            ConversationService roomService
    ) {
        this.socketIOServer = socketIOServer;
        this.messageService = messageService;
        this.roomService = roomService;
        socketIOServer.addListeners(this);
        socketIOServer.start();
    }

    @OnConnect
    public void onConnected(SocketIOClient client) {
        var userId = getUserIdFromHandshakeData(client);
        System.out.println("User " + userId + " connected");
        System.out.println(client);
        clients.put(userId, client);
        System.out.println("Number of client " + clients.size());
    }

    @OnDisconnect
    public void onDisconnected(SocketIOClient client) {
        var userId = getUserIdFromHandshakeData(client);
        System.out.println("User " + userId + " disconnected");
        clients.remove(userId);
    }

    private Integer getUserIdFromHandshakeData(SocketIOClient client) {
        return Integer.parseInt(client.getHandshakeData().getSingleUrlParam("userId"));
    }

    @OnEvent("send_message")
    public void onMessageReceived(SocketIOClient client, MessageRequest payload) {
        var responsePayload = saveAndConvertMessage(payload);
        var userId = getUserIdFromHandshakeData(client);
        if (responsePayload != null && userId.equals(payload.getSenderId())) {
            List<Integer> participants = roomService.getParticipantsId(userId);
            for (Integer id : participants) {
                var receiverClient = clients.get(id);
                if (receiverClient != null) {
                    receiverClient.sendEvent("new_message", responsePayload);
                }
            }
        }
    }

    private MessageResponse saveAndConvertMessage(MessageRequest payload) {
        var savedMessage = messageService.saveNewMessage(payload);
        if (savedMessage != null) {
            return MessageResponse.buildFromMessage(savedMessage);
        }
        return null;
    }

//    @OnEvent("send_friend_request")
//    public void onFriendRequestReceived(SocketIOClient client, FriendRequestRequest payload) {
//
//    }
}
