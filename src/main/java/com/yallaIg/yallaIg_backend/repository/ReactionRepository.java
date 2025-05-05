package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.model.ReactablesCounts;
import com.yallaIg.yallaIg_backend.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction,Integer> {

    Optional<Reaction> findByReactableIdAndReactableTypeAndUserUserId(Integer reactableId,ReactableType reactableType, Integer userId);

    @Query("SELECT r.reactableId FROM Reaction r WHERE " +
            "r.user.userId = :userId " +
            "AND r.reactableType = :reactableType " +
            "AND  r.reactionType  = :reactionType ")
    List<Integer> findAllReactablesIdByUserId(@Param("reactableType") ReactableType reactableType,
                                              @Param("userId") Integer userId,
                                              @Param("reactionType") ReactionType reactionType);
}
