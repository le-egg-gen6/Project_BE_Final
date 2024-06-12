package com.myproject.project_oop.controller.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.myproject.project_oop.dto.request.message.MessageRequest;
import com.myproject.project_oop.dto.response.message.MessageResponse;
import com.myproject.project_oop.service.MessageService;
import com.myproject.project_oop.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SocketEventHandler {

    private static final Map<Integer, SocketIOClient> clients = new HashMap<>();

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
        socketIOServer.addConnectListener(this.onConnected());
        socketIOServer.addDisconnectListener(this.onDisconnected());
        socketIOServer.addEventListener("send_message", MessageRequest.class, this.onMessageReceived());
        socketIOServer.start();
    }

    private ConnectListener onConnected() {
        return (client) -> {
            var userId = getUserIdFromHandshakeData(client);
            System.out.println("User " + userId + " connected");
            System.out.println(client);
            clients.put(userId, client);
            client.joinRoom("global");
            System.out.println("Number of client " + clients.size());
        };

    }

    private DisconnectListener onDisconnected() {
        return (client) -> {
            var userId = getUserIdFromHandshakeData(client);
            System.out.println("User " + userId + " disconnected");
            clients.remove(userId);
        };
    }

    public DataListener<MessageRequest> onMessageReceived() {
        return (client, data, ackSender) -> {
            var responsePayload = saveAndConvertMessage(data);
            var userId = getUserIdFromHandshakeData(client);
            if (responsePayload != null && userId.equals(data.getSenderId())) {
                System.out.println(responsePayload.getContent());
                List<Integer> participants = roomService.getParticipantsId(responsePayload.getConversationId());
                for (Integer id : participants) {
                    var receiverClient = clients.get(id);
                    if (receiverClient != null) {
                        receiverClient.sendEvent("new_message", responsePayload);
                    }
                }
            }
        };
    }

    private Integer getUserIdFromHandshakeData(SocketIOClient client) {
        return Integer.parseInt(client.getHandshakeData().getSingleUrlParam("userId"));
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
