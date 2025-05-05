package com.yallaIg.yallaIg_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericApiResponseList<T> {

    private List<T> payload;
    private String message;
    private int statusCode ;

}
