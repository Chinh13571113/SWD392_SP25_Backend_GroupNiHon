package com.swd.pregnancycare.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    USER_EXIST(400,"user existed"),
    USER_NOT_EXIST(403,"user not existed"),
    UNCATEGORIZED_EXCEPTION(999,"uncategorize exception"),
    BLOG_SAVED_EXCEPTION(999,"blog save error"),
    INVALID_PASSWORD(400,"password must be at least 8"),
    BLOG_NOT_EXIST(401,"Blog not found"),
    GROUP_NOT_EXIST(403, "group not existed"),
    GROUP_HAS_USER_ALREADY(999, "Group has a user already"),
    ;

    private int code;
    private String message;

}
