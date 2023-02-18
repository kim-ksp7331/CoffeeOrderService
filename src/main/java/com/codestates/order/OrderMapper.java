package com.codestates.order;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderPostDTOToOrder(OrderDTO.Post orderPostDTO);

    OrderDTO.Response orderToOrderResponseDTO(Order order);
}
