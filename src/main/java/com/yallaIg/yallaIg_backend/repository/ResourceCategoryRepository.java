package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ResourcesCategoryEnum;
import com.yallaIg.yallaIg_backend.model.ResourcesCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceCategoryRepository extends JpaRepository<ResourcesCategory,Integer>  {

    Optional<ResourcesCategory> findByResourcesCategoryEnum(ResourcesCategoryEnum resourcesCategoryEnum);

}
