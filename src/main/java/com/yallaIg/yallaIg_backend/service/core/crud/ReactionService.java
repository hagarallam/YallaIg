package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.model.Reaction;
import com.yallaIg.yallaIg_backend.model.User;

import java.util.List;
import java.util.Optional;

public interface ReactionService {

    Optional<Reaction> getReactionByReactableIdAndUserId(Integer reactableId, ReactableType reactableType,Integer userId);

    List<Integer> getReactablesIdByReactableTypeAndUserId(ReactableType reactableType, Integer userId,ReactionType reactionType);


    void deleteReaction(Integer reactionId);

    Reaction saveNewReaction(int reactableId, User user, ReactableType reactableType,ReactionType reactionType);
}
