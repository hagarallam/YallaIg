package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.enums.PointSettingEnum;
import com.yallaIg.yallaIg_backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class UserPointsServiceImpl implements UserPointsService{

    private final PointSettingService pointSettingService;
    private final UserService userService;

    @Override
    public void increaseUserPoint(User user, PointSettingEnum pointSettingEnum) {
        updateUserPoint(user,pointSettingEnum, (currentPoint , newPoint) -> currentPoint + newPoint);
    }

    @Override
    public void decreaseUserPoint(User user, PointSettingEnum pointSettingEnum) {
        updateUserPoint(user,pointSettingEnum, (currentPoint , newPoint) -> currentPoint - newPoint);
    }

    private void updateUserPoint( User user, PointSettingEnum pointSettingEnum , BinaryOperator<Integer> function){
        int pointCountPerAction = pointSettingService.getPointsForAction(pointSettingEnum);
        int newUserPoints = function.apply(user.getUserPoints(),pointCountPerAction);
        user.setUserPoints(newUserPoints);
        userService.updateUser(user);

    }
}
