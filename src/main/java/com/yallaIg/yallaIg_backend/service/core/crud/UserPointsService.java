package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.enums.PointSettingEnum;
import com.yallaIg.yallaIg_backend.model.User;

public interface UserPointsService {
    void increaseUserPoint(User user, PointSettingEnum pointSettingEnum);

    void decreaseUserPoint(User user, PointSettingEnum pointSettingEnum);
}
