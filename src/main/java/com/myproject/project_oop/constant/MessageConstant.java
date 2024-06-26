package com.myproject.project_oop.constant;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MessageConstant {

    public static final String REGISTER_SUCCESS = "Register successful!";

    public static final String VERIFY_SUCCESS = "User verified successfully!";

    public static final String RESET_PASSWORD_SUCCESS = "Password reset successfully!";

    public static final String EMAIL_RESEND_SUCCESS = "A new email verification token has been send to your email, please check and try again!";

    public static final String PASSWORD_OTP_SUCCESS = "A email with your password reset token has been send to your email, please check!";

    public static final String LOGOUT_SUCCESS = "Logout successfully!";

    public static final String FRIEND_REQUEST_SENT_SUCCESS = "Friend request sent successfully!";

    public static final String FRIEND_REQUEST_ACCEPTED = "Friend request accepted!";

    public static final String GROUP_CREATED_SUCCESSFULLY = "Group created successfully!";
}
