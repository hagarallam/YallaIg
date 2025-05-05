package com.yallaIg.yallaIg_backend.mapper;

import com.yallaIg.yallaIg_backend.dto.response.FileResponseDto;
import com.yallaIg.yallaIg_backend.model.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface FileMapper {


    @Mapping(target = "fileId",ignore = true)
    @Mapping(target = "name", source = "multipartFile", qualifiedByName = "fileName")
    @Mapping(target = "data", source = "multipartFile", qualifiedByName = "fileToBytes")
    @Mapping(target = "type", source = "multipartFile", qualifiedByName = "fileContentType")
    @Mapping(target = "size", source = "multipartFile", qualifiedByName = "fileSize")
    File multipartFileToFile(MultipartFile multipartFile);

    FileResponseDto fileToFileResponseDto(File file);


    // Custom mapping methods
    @Named("fileToBytes")
    default byte[] fileToBytes(MultipartFile file) throws IOException {
        return file.getBytes();
    }

    @Named("fileContentType")
    default String fileContentType(MultipartFile file) {
        return file.getContentType();
    }

    @Named("fileSize")
    default Long fileSize(MultipartFile file) {
        return file.getSize();
    }

    @Named("fileName")
    default String fileName(MultipartFile file) {
        return file.getOriginalFilename();
    }
}
