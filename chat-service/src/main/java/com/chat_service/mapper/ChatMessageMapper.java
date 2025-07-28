package com.chat_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.chat_service.dto.request.ChatMessageRequest;
import com.chat_service.dto.response.ChatMessageResponse;
import com.chat_service.entity.ChatMessage;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {
    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);

    ChatMessage toChatMessage(ChatMessageRequest chatMessageRequest);

    List<ChatMessageResponse> toChatMessageResponseList(List<ChatMessage> chatMessages);
}
