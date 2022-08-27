package co.develhope.WebSocket02.controller;

import co.develhope.WebSocket02.entities.ClientMessageDTO;
import co.develhope.WebSocket02.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Notification {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/broadcast-message")
    public ResponseEntity broadcastMessage(@RequestParam String topic, @RequestBody MessageDTO payload) {
        if (payload == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            simpMessagingTemplate.convertAndSend(topic, payload);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @MessageMapping("/client-message")
    @SendTo("/topic/broadcast")
    public MessageDTO clientMessage(@RequestBody ClientMessageDTO message) {
        return new MessageDTO(
                message.getClientName() ,
                message.getClientAlert(),
                message.getClientMsg());
    }

}



