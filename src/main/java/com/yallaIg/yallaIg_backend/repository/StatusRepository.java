package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.StatusEnum;
import com.yallaIg.yallaIg_backend.enums.SubjectEnum;
import com.yallaIg.yallaIg_backend.model.Status;
import com.yallaIg.yallaIg_backend.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status,Integer>  {

    Optional<Status> findByStatusEnum(StatusEnum statusEnum);


}
