package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.enums.PointSettingEnum;
import com.yallaIg.yallaIg_backend.exception.ItemNotFoundException;
import com.yallaIg.yallaIg_backend.model.PointSetting;
import com.yallaIg.yallaIg_backend.repository.PointSettingRepository;
import com.yallaIg.yallaIg_backend.service.redis.PointSettingCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.yallaIg.yallaIg_backend.constants.ErrorConstants.ERROR_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PointSettingServiceImpl implements PointSettingService{

    private final PointSettingCacheService pointSettingCacheService;
    private final PointSettingRepository pointSettingRepository;


    @Override
    public int getPointsForAction(PointSettingEnum pointSettingEnum) {
        PointSetting pointSetting = pointSettingCacheService.get(PointSettingCacheService.KEY_NAME,pointSettingEnum.toString());
        if(Objects.isNull(pointSetting)){
            PointSetting pointSettingFromDB = getPointSettingFromDB(pointSettingEnum);
            updateCache(pointSettingEnum, pointSettingFromDB);
            return pointSettingFromDB.getPointCount();
        }
        return pointSetting.getPointCount();
    }

    private void updateCache(PointSettingEnum pointSettingEnum, PointSetting pointSettingFromDB) {
        pointSettingCacheService.put(PointSettingCacheService.KEY_NAME, pointSettingEnum.toString(), pointSettingFromDB,24);
    }

    private PointSetting getPointSettingFromDB(PointSettingEnum pointSettingEnum) {
        Optional<PointSetting> optionalPointSetting = pointSettingRepository.findById(pointSettingEnum);
        if(optionalPointSetting.isPresent()){
            return optionalPointSetting.get();
        }
        throw new ItemNotFoundException(ERROR_ITEM_NOT_FOUND);
    }
}
