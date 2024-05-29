package com.myproject.project_oop.constant;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ErrorConstant {

    public static final Integer UNEXPECTED_ERROR_CODE = 1;

    public static final String UNEXPECTED_ERROR = "Unexpected error occurred!";

    public static final Integer USERNAME_DUPLICATE_CODE = 2;

    public static final String USERNAME_DUPLICATE = "Username is duplicated!";

    public static final Integer USERNAME_PASSWORD_WRONG_CODE = 3;

    public static final String USERNAME_PASSWORD_WRONG = "Username or password incorrect!";

    public static final Integer ACCOUNT_NOT_VERIFIED_CODE = 4;

    public static final String ACCOUNT_NOT_VERIFIED = "Account is not verified!";

    public static final Integer OTP_WRONG_CODE = 5;

    public static final String OTP_WRONG = "OTP key incorrect!";

    public static final Integer EMAIL_SEND_FAILED_CODE = 6;

    public static final String EMAIL_SEND_FAILED = "An error while sending email occurred!";

    public static final Integer EMAIL_DUPLICATE_CODE = 7;

    public static final String EMAIL_DUPLICATE = "Email is duplicated!";

}
