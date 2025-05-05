package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import com.yallaIg.yallaIg_backend.model.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {

    Page<Resource> findAllByCreatedBy(Integer userId, Pageable pageable);

    Page<Resource> findAllByResourcesCategoryResourcesCategoryEnum(ResourcesCategoryEnum resourcesCategoryEnum, Pageable pageable);

    Page<Resource> findAllByResourceIdIn(List<Integer> resourcesIds, Pageable pageable);
}
