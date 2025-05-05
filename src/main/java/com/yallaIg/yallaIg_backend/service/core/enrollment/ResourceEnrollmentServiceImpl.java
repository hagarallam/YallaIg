package com.yallaIg.yallaIg_backend.service.core.enrollment;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.dto.request.ResourceRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceRegistrationResponseDto;
import com.yallaIg.yallaIg_backend.exception.BusinessValidationException;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.model.Resource;
import com.yallaIg.yallaIg_backend.repository.ResourceRepository;
import com.yallaIg.yallaIg_backend.service.core.crud.StudentResourceService;
import com.yallaIg.yallaIg_backend.service.core.crud.UserWalletService;
import com.yallaIg.yallaIg_backend.service.core.enrollment.models.EnrollmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_REGISTER_RESOURCE;

@Service
@RequiredArgsConstructor
public class ResourceEnrollmentServiceImpl implements ResourceEnrollmentService{

    private final StudentResourceService studentResourceService;
    private final OrderService orderService;
    private final UserWalletService walletService;
    private final ResourceRepository resourceRepository;
    private final FileMapper fileMapper;


    @Override
    public ResourceRegistrationResponseDto registerResource(ResourceRegistrationRequestDto resourceRegistrationRequestDto) {

        if(studentResourceService.checkIfResourceRegisteredByUser(resourceRegistrationRequestDto.getResourceId()))
            throw new BusinessValidationException(ERROR_REGISTER_RESOURCE);

        double price = calculatePrice(resourceRegistrationRequestDto.getResourceId());

        Integer orderId = orderService.createOrder(price);

        walletService.updateUserWallet(-price);

        Resource resource = studentResourceService.register(resourceRegistrationRequestDto, orderId);
        return createResourceRegistrationResponseDto(resource);
    }

    private ResourceRegistrationResponseDto createResourceRegistrationResponseDto(Resource resource) {
        ResourceRegistrationResponseDto resourceRegistrationResponseDto = new ResourceRegistrationResponseDto();
        resourceRegistrationResponseDto.setResourceFile(fileMapper.fileToFileResponseDto(resource.getResourceFile()));
        return resourceRegistrationResponseDto;
    }


    private double calculatePrice(Integer resourceId) {
        Optional<Resource> resource = resourceRepository.findById(resourceId);
        return resource.map(Resource::getPrice).orElseThrow(() -> new ItemNotFoundException(ErrorConstants.ERROR_ITEM_NOT_FOUND));
    }
}
