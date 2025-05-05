package com.yallaIg.yallaIg_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericApiResponsePage<T> {

    private Page<T> payload;
    private String message;
    private int statusCode ;

}
