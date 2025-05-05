package com.yallaIg.yallaIg_backend.controller.core;

import com.yallaIg.yallaIg_backend.dto.request.BlogRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.ProductRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.ResourceRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.ResourceRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.*;
import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import com.yallaIg.yallaIg_backend.service.core.crud.BlogService;
import com.yallaIg.yallaIg_backend.service.core.crud.ResourceService;
import com.yallaIg.yallaIg_backend.service.core.crud.StudentResourceService;
import com.yallaIg.yallaIg_backend.service.core.enrollment.ResourceEnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yallaIg.yallaIg_backend.constants.SuccessConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;
    private final StudentResourceService studentResourceService;
    private final ResourceEnrollmentService registerResource;

    @GetMapping
    public ResponseEntity<GenericApiResponsePage<ResourceResponseDto>> getAllResources(@RequestParam(defaultValue = "0") Integer page,
                                                                                       @RequestParam(defaultValue = "10") Integer size){
        Page<ResourceResponseDto> resourceResponseDtos = resourceService.getAllResources(page,size);
        GenericApiResponsePage<ResourceResponseDto> genericApiResponsePage = new GenericApiResponsePage<>();
        genericApiResponsePage.setPayload(resourceResponseDtos);
        genericApiResponsePage.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponsePage,OK);
    }

    @GetMapping("/category")
    public ResponseEntity<GenericApiResponsePage<ResourceResponseDto>> getAllResourcesByCategory(@RequestParam("category") ResourcesCategoryEnum resourcesCategoryEnum,
                                                                                        @RequestParam(defaultValue = "0") Integer page,
                                                                                       @RequestParam(defaultValue = "10") Integer size){
        Page<ResourceResponseDto> resourceResponseDtos = resourceService.getAllResourcesByCategory(resourcesCategoryEnum,page,size);
        GenericApiResponsePage<ResourceResponseDto> genericApiResponsePage = new GenericApiResponsePage<>();
        genericApiResponsePage.setPayload(resourceResponseDtos);
        genericApiResponsePage.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponsePage,OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GenericApiResponse<ResourceResponseDto>> getResourceById(@PathVariable int id){
        ResourceResponseDto resourceResponseDto = resourceService.getResourceById(id);
        GenericApiResponse<ResourceResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(resourceResponseDto);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }

    @PostMapping
    public ResponseEntity<GenericApiResponse<Integer>> createResource(@Valid @ModelAttribute ResourceRequestDto resourceRequestDto){
        Integer id = resourceService.createResource(resourceRequestDto);
        GenericApiResponse<Integer> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(id);
        genericApiResponse.setMessage(SUCCESS_RESOURCE_CREATION);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericApiResponse> updateResource(@PathVariable Integer id,
                                                         @Valid @ModelAttribute ResourceRequestDto resourceRequestDto){
        resourceService.updateResource(id,resourceRequestDto);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_RESOURCE_UPDATION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericApiResponse> deleteResource(@PathVariable Integer id){
        resourceService.deleteResource(id);
        GenericApiResponse genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_RESOURCE_DELETION);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse, OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<GenericApiResponseList<ResourcesCategoryEnum>> getResourcesCategory(){
        List<ResourcesCategoryEnum> resourcesCategoryEnumList = resourceService.getResourceCategories();
        GenericApiResponseList<ResourcesCategoryEnum> genericApiResponseList = new GenericApiResponseList<>();
        genericApiResponseList.setPayload(resourcesCategoryEnumList);
        genericApiResponseList.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponseList,OK);
    }

    @PostMapping("/register")
    public ResponseEntity<GenericApiResponse<ResourceRegistrationResponseDto>> register(@RequestBody @Valid ResourceRegistrationRequestDto registrationRequestDto){
        ResourceRegistrationResponseDto resourceRegistrationResponseDto =  registerResource.registerResource(registrationRequestDto);
        GenericApiResponse<ResourceRegistrationResponseDto> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setMessage(SUCCESS_RESOURCE_REGISTRATION);
        genericApiResponse.setPayload(resourceRegistrationResponseDto);
        genericApiResponse.setStatusCode(CREATED.value());
        return new ResponseEntity<>(genericApiResponse,CREATED);
    }

    @GetMapping("/check-registration")
    public ResponseEntity<GenericApiResponse<Boolean>> checkIfUserRegisterdWithResource(@RequestParam Integer resourceId){
        boolean isRegistered = studentResourceService.checkIfResourceRegisteredByUser(resourceId);
        GenericApiResponse<Boolean> genericApiResponse = new GenericApiResponse<>();
        genericApiResponse.setPayload(isRegistered);
        genericApiResponse.setStatusCode(OK.value());
        return new ResponseEntity<>(genericApiResponse,OK);
    }
}
