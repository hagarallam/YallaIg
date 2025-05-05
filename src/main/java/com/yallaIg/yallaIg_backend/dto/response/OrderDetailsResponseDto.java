package com.yallaIg.yallaIg_backend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsResponseDto {


    private Integer orderId;
    List<CourseResponseDto> registeredCourses ;
    private Date creationDate;
    private Double totalPrice;

}
