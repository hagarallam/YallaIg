package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.PointSettingEnum;
import com.yallaIg.yallaIg_backend.model.PointSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointSettingRepository extends JpaRepository<PointSetting, PointSettingEnum> {

    
}
