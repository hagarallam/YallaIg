package com.yallaIg.yallaIg_backend.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public final class CommonUtil {

    private CommonUtil(){

    }

    public static Pageable getPageableObject(int page,int size){
     return PageRequest.of(page,size);
    }

}
