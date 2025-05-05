package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.response.OrderDetailsResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.ProductRegistrationResponseDto;
import com.yallaIg.yallaIg_backend.model.Order;
import com.yallaIg.yallaIg_backend.service.core.enrollment.models.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    ProductRegistrationResponseDto orderResponseToOrderResponseDto(OrderResponse orderResponse);

    OrderDetailsResponseDto orderToOrderDetailsResponseDto(Order order);
}
