package com.codestates.hello_world;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messagePostDTOToMessage(MessageDTO.Post messagePostDTO);
    MessageDTO.Response messageToMessageResponseDTO(Message message);
}
