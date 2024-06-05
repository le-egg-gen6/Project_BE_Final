package com.myproject.project_oop.constant;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ErrorConstant {

    public static final String UNEXPECTED_ERROR = "Unexpected error occurred!";

    public static final String USERNAME_DUPLICATE = "Username is duplicated!";

    public static final String USERNAME_PASSWORD_WRONG = "Username or password incorrect!";

    public static final String ACCOUNT_NOT_VERIFIED = "Account is not verified!";

    public static final String OTP_WRONG = "OTP key incorrect!";

    public static final String EMAIL_SEND_FAILED = "An error while sending email occurred!";

    public static final String RESET_PASSWORD_FAILED = "An error occurred while reset password!";

    public static final String EMAIL_DUPLICATE = "Email is duplicated!";

}
