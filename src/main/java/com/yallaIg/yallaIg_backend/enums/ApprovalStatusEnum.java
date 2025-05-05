package com.yallaIg.yallaIg_backend.enums;

import lombok.Getter;

@Getter
public enum ApprovalStatusEnum {

    PENDING,
    APPROVED,
    REJECTED;

    public static boolean isApproved(ApprovalStatusEnum approvalStatusEnum) {
        return ApprovalStatusEnum.APPROVED.equals(approvalStatusEnum);
    }
}
