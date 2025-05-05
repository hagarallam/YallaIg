package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.dto.request.ResourceRegistrationRequestDto;
import com.yallaIg.yallaIg_backend.dto.response.ResourceResponseDto;
import com.yallaIg.yallaIg_backend.dto.response.StudentResourceResponseDto;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.mapper.ResourceMapper;
import com.yallaIg.yallaIg_backend.model.*;
import com.yallaIg.yallaIg_backend.repository.OrderRepository;
import com.yallaIg.yallaIg_backend.repository.ResourceRepository;
import com.yallaIg.yallaIg_backend.repository.StudentResourceRepository;
import com.yallaIg.yallaIg_backend.util.CommonUtil;
import com.yallaIg.yallaIg_backend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StudentResourceServiceImpl implements StudentResourceService{

    private final ResourceRepository resourceRepository;
    private final OrderRepository orderRepository;
    private final StudentResourceRepository studentResourceRepository;
    private final UserService userService;
    private final ResourceMapper resourceMapper;

    @Override
    public Resource register(ResourceRegistrationRequestDto resourceRegistrationRequestDto, Integer orderId) {

        Optional<Resource> resource = resourceRepository.findById(resourceRegistrationRequestDto.getResourceId());
        if(resource.isEmpty()) throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);

        Optional<Order> order  = orderRepository.findById(orderId);
        if(order.isEmpty()) throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);;

        User student = userService.findByUserId(SecurityUtil.getCurrentUserId());

        StudentResource studentResource = createStudentResource(student,resource.get(),order.get());
        studentResourceRepository.save(studentResource);
        return resource.get();
    }

    public StudentResource createStudentResource(User student, Resource resource, Order order) {
        StudentResource studentResource = new StudentResource();
        studentResource.setStudentResourceId(new StudentResourceId(student.getUserId(),resource.getResourceId()));
        studentResource.setOrder(order);
        studentResource.setUser(student);
        studentResource.setResource(resource);
        return studentResource;
    }
    @Override
    public Page<StudentResourceResponseDto> getAllResourcesByUser(int page, int size) {
        Pageable pageable = CommonUtil.getPageableObject(page, size);
        Integer userId = SecurityUtil.getCurrentUserId();
        List<Integer> resourcesIds = studentResourceRepository.findAllByStudentId(userId);
        Page<Resource> resources = resourceRepository.findAllByResourceIdIn(resourcesIds,pageable);
        return resources.map(resourceMapper::resourceToStudentResourceResponseDto) ;
    }

    @Override
    public StudentResourceResponseDto getRegistredResourceById(Integer resourceId) {
        Integer userId = SecurityUtil.getCurrentUserId();
        Integer resourcesId = studentResourceRepository.findByStudentIdAndResourceId(userId,resourceId);
        Optional<Resource> resources = resourceRepository.findById(resourcesId);
        return resources.map(resourceMapper::resourceToStudentResourceResponseDto).orElseThrow( () -> new ItemNotFoundException(ERROR_ITEM_NOT_FOUND));
    }


    @Override
    public boolean checkIfResourceRegisteredByUser(Integer resourceId) {
        Integer userId = SecurityUtil.getCurrentUserId();
        Optional<StudentResource> studentResource = studentResourceRepository.findByStudentResourceIdStudentIdAndStudentResourceIdResourceId(userId,resourceId);
        return studentResource.isPresent();
    }


}
