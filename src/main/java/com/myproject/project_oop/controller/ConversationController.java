package com.myproject.project_oop.controller;

import com.myproject.project_oop.dto.request.conversation.CreateConversationRequest;
import com.myproject.project_oop.dto.response.BaseResponse;
import com.myproject.project_oop.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("${api.prefix}" + "${api.version}" + "/conversation")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

//    @GetMapping("/all")
//    public ResponseEntity<BaseResponse<?>> getUsersConversationsDetails() {
//        return ResponseEntity.ok(
//                BaseResponse.buildDataResponse(conversationService.getAllConversationDetails())
//        );
//    }

    @GetMapping("/all-message")
    public ResponseEntity<BaseResponse<?>> getConversation(
            @RequestParam(name = "conversationId") Integer conversationId
    ) {
        return ResponseEntity.ok(
                BaseResponse.buildDataResponse(conversationService.getConversationMessage(conversationId))
        );
    }

    @PostMapping("/new-group")
    public ResponseEntity<BaseResponse<?>> createNewGroup(
            @RequestBody CreateConversationRequest request
    ) {
        return ResponseEntity.ok(
                BaseResponse.buildDataResponse(
                        conversationService.createConversation(request)
                )
        );
    }

    @GetMapping("/all-direct-conversation")
    public ResponseEntity<BaseResponse<?>> getAllDirectConversation() {
        return ResponseEntity.ok(
                BaseResponse.buildDataResponse(conversationService.getAllDirectConversationDetails())
        );
    }

    @GetMapping("/all-group-conversation")
    public ResponseEntity<BaseResponse<?>> getAllGroupConversation() {
        return ResponseEntity.ok(
                BaseResponse.buildDataResponse(conversationService.getAllGroupConversationDetails())
        );
    }


}
