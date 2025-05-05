package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.constants.ErrorConstants;
import com.yallaIg.yallaIg_backend.dto.request.ResourceRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.request.ResourceRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceRegistrationResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceResponseDto;
import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import com.yallaIg.yallaIg_backend.exception.BusinessValidationException;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.FileMapper;
import com.yallaIg.yallaIg_backend.mapper.ResourceMapper;
import com.yallaIg.yallaIg_backend.model.Resource;
import com.yallaIg.yallaIg_backend.model.StudentResource;
import com.yallaIg.yallaIg_backend.repository.ResourceCategoryRepository;
import com.yallaIg.yallaIg_backend.repository.ResourceRepository;
import com.yallaIg.yallaIg_backend.repository.StudentResourceRepository;
import com.yallaIg.yallaIg_backend.service.core.enrollment.OrderService;
import com.yallaIg.yallaIg_backend.service.core.enrollment.models.EnrollmentResponse;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_REGISTER_RESOURCE;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final FileMapper fileMapper;
    private final OrderService orderService;
    private final UserWalletService walletService;
    private final StudentResourceService studentResourceService;
    private final ResourceCategoryRepository resourceCategoryRepository;

    @Override
    public Page<ResourceResponseDto> getAllResources(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Resource> resources = resourceRepository.findAll(pageable);
        return resources.map(resourceMapper::resourceToResourceResponseDto) ;
    }

    @Override
    public Page<ResourceResponseDto> getAllResourcesByCategory(ResourcesCategoryEnum resourcesCategoryEnum, int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page,size);
        Page<Resource> resources = resourceRepository.findAllByResourcesCategoryResourcesCategoryEnum(resourcesCategoryEnum,pageable);
        return resources.map(resourceMapper::resourceToResourceResponseDto) ;
    }

    @Override
    public Page<ResourceResponseDto> getAllResourcesByUser(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page, size);
        Integer userId = SecurityUtil.getCurrentUserId();
        Page<Resource> resources = resourceRepository.findAllByCreatedBy(userId,pageable);
        return resources.map(resourceMapper::resourceToResourceResponseDto) ;
    }

    @Override
    public ResourceResponseDto getResourceById(int id) {
        Optional<Resource> resourceOptional = resourceRepository.findById(id);
        return resourceOptional.map(resourceMapper::resourceToResourceResponseDto).orElseThrow( () -> new ItemNotFoundException(ErrorConstants.ERROR_ITEM_NOT_FOUND));
    }

    @Override
    public Integer createResource(ResourceRequestDto resourceRequestDto) {
        Resource resource = resourceMapper.resourceRequestDtoToResource(resourceRequestDto);
        resource.setResourcesCategory(resourceCategoryRepository.findByResourcesCategoryEnum(resourceRequestDto.getResourcesCategoryEnum()).get());
        return resourceRepository.save(resource).getResourceId();
    }

    @Override
    public void updateResource(Integer id, ResourceRequestDto resourceRequestDto) {
        Optional<Resource> resourceOptional = resourceRepository.findById(id);
        if(resourceOptional.isPresent()){
            resourceRepository.save(updateResourceData(resourceOptional.get(),resourceRequestDto));
            return;
        }
        throw new ItemNotFoundException(ErrorConstants.ERROR_ITEM_NOT_FOUND);
    }

    private Resource updateResourceData(Resource resource, ResourceRequestDto resourceRequestDto) {
        resource.setTitle(resourceRequestDto.getTitle());
        resource.setPrice(resourceRequestDto.getPrice());
        resource.setDescription(resourceRequestDto.getDescription());
        resource.setResourcesCategory(resourceCategoryRepository.findByResourcesCategoryEnum(resourceRequestDto.getResourcesCategoryEnum()).get());
        resource.setResourceFile(fileMapper.multipartFileToFile(resourceRequestDto.getResourceFile()));
        resource.setResourceImage(fileMapper.multipartFileToFile(resourceRequestDto.getResourceImage()));
        return resource;
    }

    @Override
    public void deleteResource(Integer id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public List<ResourcesCategoryEnum> getResourceCategories() {
        return List.of(ResourcesCategoryEnum.values());
    }


}
