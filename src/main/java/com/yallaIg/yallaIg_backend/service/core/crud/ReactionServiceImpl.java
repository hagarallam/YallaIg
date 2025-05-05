package com.yallaIg.yallaIg_backend.service.core.crud;

import com.yallaIg.yallaIg_backend.enums.ReactableType;
import com.yallaIg.yallaIg_backend.enums.ReactionType;
import com.yallaIg.yallaIg_backend.model.Reaction;
import com.yallaIg.yallaIg_backend.model.User;
import com.yallaIg.yallaIg_backend.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
 public class ReactionServiceImpl implements ReactionService{

    private final ReactionRepository reactionRepository;

    @Override
    public Optional<Reaction> getReactionByReactableIdAndUserId(Integer reactableId, ReactableType reactableType,Integer userId) {
        return reactionRepository.findByReactableIdAndReactableTypeAndUserUserId(reactableId,reactableType,userId);
    }

    @Override
    public List<Integer> getReactablesIdByReactableTypeAndUserId(ReactableType reactableType, Integer userId, ReactionType reactionType) {
        return reactionRepository.findAllReactablesIdByUserId(reactableType,userId,reactionType);
    }

    @Override
    public void deleteReaction(Integer reactionId) {
        reactionRepository.deleteById(reactionId);
    }

    @Override
    public Reaction saveNewReaction(int reactableId, User user, ReactableType reactableType,ReactionType reactionType) {
        Reaction newReaction = createNewReaction(reactableId,user,reactableType,reactionType);
        return reactionRepository.save(newReaction);
    }

    public Reaction createNewReaction(int reactableId,User user, ReactableType reactableType, ReactionType reactionType) {
        Reaction reaction = new Reaction();
        reaction.setReactableId(reactableId);
        reaction.setUser(user);
        reaction.setReactionType(reactionType);
        reaction.setReactableType(reactableType);
        return reaction;
    }


}
