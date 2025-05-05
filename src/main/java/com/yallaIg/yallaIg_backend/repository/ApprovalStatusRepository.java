package com.yallaIg.yallaIg_backend.repository;

import com.yallaIg.yallaIg_backend.enums.ApprovalStatusEnum;
import com.yallaIg.yallaIg_backend.enums.ExamSessionEnum;
import com.yallaIg.yallaIg_backend.model.ApprovalStatus;
import com.yallaIg.yallaIg_backend.model.ExamSession;
import com.yallaIg.yallaIg_backend.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus,Integer>  {

    Optional<ApprovalStatus> findByApprovalStatusEnum(ApprovalStatusEnum approvalStatusEnum);

}
