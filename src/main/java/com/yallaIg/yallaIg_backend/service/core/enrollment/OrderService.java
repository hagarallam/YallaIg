package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.dto.response.OrderDetailsResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.ProductRegistrationResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    Integer createOrder(double totalPrice);

    Page<OrderDetailsResponseDto> getAllOrdersByUser(int page, int size);
}
