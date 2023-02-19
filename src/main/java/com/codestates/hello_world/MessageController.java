package com.codestates.hello_world;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper mapper;

    @PostMapping
    public ResponseEntity<MessageDTO.Response> postMessage(@Valid @RequestBody MessageDTO.Post messagePostDTO) {
        Message message = mapper.messagePostDTOToMessage(messagePostDTO);
        Message createdMessage = messageService.createMessage(message);
        MessageDTO.Response response = mapper.messageToMessageResponseDTO(createdMessage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
