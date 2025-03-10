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

    ROLE_NOT_FOUND(401,"Role does not exist" ),
    REGISTER_FAILED(400,"register failed" );

    private int code;
    private String message;

}
