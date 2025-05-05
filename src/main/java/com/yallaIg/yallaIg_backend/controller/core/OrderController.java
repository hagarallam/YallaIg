package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.response.GenericApiResponsePage;
import com.yallaIg.yallaIg_backend.dto.response.OrderDetailsResponseDto;
import com.yallaIg.yallaIg_backend.service.core.enrollment.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<GenericApiResponsePage<OrderDetailsResponseDto>> getAllOrderByUserName(@RequestParam Integer page ,
                                                                                                 @RequestParam Integer size){
        Page<OrderDetailsResponseDto> orderResponseDtos = orderService.getAllOrdersByUser(page,size);
        GenericApiResponsePage<OrderDetailsResponseDto> genericApiResponsePage = new GenericApiResponsePage<>();
        genericApiResponsePage.setPayload(orderResponseDtos);
        genericApiResponsePage.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponsePage,OK);
    }


}
