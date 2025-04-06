package com.ecdms.ecdms.enums;

public enum Status {

    ACTIVE,
    INACTIVE,
    SUSPENDED,
    VERIFIED,
    UNVERIFIED,
    LOCKED,
    DEACTIVATED,

    FROZEN,
    CLOSED,
    PENDING,
    LIMITED,

    // Transaction statuses
    SUCCESS,
    FAILED,
    CANCELLED,
    REFUNDED,
    REVERSED,
    ON_HOLD,

    // Loan/Investment statuses
    APPLIED,
    APPROVED,
    REJECTED,

    DISBURSED,
    ONGOING,
    DEFAULTED,

    // Payment statuses
    COMPLETED,
    DISPUTED,

    // Service statuses
    ENABLED,
    DISABLED,
    UNDER_MAINTENANCE,
    DEPRECATED,

    // Compliance/KYC statuses
    KYC_PENDING,
    KYC_VERIFIED,
    KYC_REJECTED,
    KYC_EXPIRED,
    KYC_SUSPENDED,

    // Notification/Alert statuses
    SENT,
    READ,
    UNREAD,
    ARCHIVED,

    // Custom statuses
    UNDER_REVIEW,
    ESCALATED,
    SETTLED,
    PARTIALLY_PAID,
    CHARGEBACK
}
