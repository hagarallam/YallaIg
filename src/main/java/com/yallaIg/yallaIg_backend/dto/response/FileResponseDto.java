package com.yallaIg.yallaIg_backend.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileResponseDto {

    private String name ;
    private String type;
    private Long size;
    private byte[] data ;


}
