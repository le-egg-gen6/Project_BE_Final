package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.PasswordResetToken;
import com.myproject.project_oop.model.User;
import com.myproject.project_oop.repository.PasswordResetTokenRepository;
import com.myproject.project_oop.request.password.ResetPasswordRequest;
import com.myproject.project_oop.service.PasswordResetService;
import com.myproject.project_oop.service.PasswordResetTokenService;
import com.myproject.project_oop.service.UserService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final JavaMailSender mailSender;

    private final PasswordEncoder passwordEncoder;

    private final PasswordResetTokenService passwordResetTokenService;

    private final UserService userService;

    @Override
    public String generatePasswordResetToken(User user) {
        long id = (long) user.getId();
        Random rand = new Random(new Date().getTime());
        long key = rand.nextInt();
        StringBuilder otp = new StringBuilder(String.valueOf(Math.abs(id * key)));
        int length = otp.length();
        if (length <= 6) {
            otp.append("0".repeat(Math.max(0, 6 - length)));
            return otp.toString();
        } else {
            return otp.substring(0, 6);
        }
    }

    @Override
    public boolean resetPassword(ResetPasswordRequest request) {
        var user = userService.findByEmail(request.getEmail());
        if (user == null) {
            return false;
        }
        user = userService.findByIdAndEPasswordResetToken(user.getId(), request.getOtp());
        if (user == null) {
            return false;
        }
        if (request.getPassword().equals(request.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userService.save(user);
            var current_otp = passwordResetTokenService.findByToken(request.getOtp());
            current_otp.setExpired(1);
            passwordResetTokenService.save(current_otp);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendResetPasswordToken(String email) {
        var user = userService.findByEmail(email);
        if (user != null) {
            passwordResetTokenService.revokeAllPasswordResetToken(user.getId());
            String otp = generatePasswordResetToken(user);
            PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                    .token(otp)
                    .user(user)
                    .expired(0)
                    .build();
            passwordResetTokenService.save(passwordResetToken);
            String fromAddress = "wibulord.forever.love.wibu@gmail.com";
            String senderName = "Wjbu Lord Company";
            String subject = "Please reset your password";
            String content = """
                    <!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional //EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
                                    
                    <head>
                      <!--[if gte mso 9]>
                    <xml>
                      <o:OfficeDocumentSettings>
                        <o:AllowPNG/>
                        <o:PixelsPerInch>96</o:PixelsPerInch>
                      </o:OfficeDocumentSettings>
                    </xml>
                    <![endif]-->
                      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                      <meta name="viewport" content="width=device-width, initial-scale=1.0">
                      <meta name="x-apple-disable-message-reformatting">
                      <!--[if !mso]><!-->
                      <meta http-equiv="X-UA-Compatible" content="IE=edge">
                      <!--<![endif]-->
                      <title></title>
                                    
                      <style type="text/css">
                        @media only screen and (min-width: 620px) {
                          .u-row {
                            width: 600px !important;
                          }
                          .u-row .u-col {
                            vertical-align: top;
                          }
                          .u-row .u-col-100 {
                            width: 600px !important;
                          }
                        }
                       \s
                        @media (max-width: 620px) {
                          .u-row-container {
                            max-width: 100% !important;
                            padding-left: 0px !important;
                            padding-right: 0px !important;
                          }
                          .u-row .u-col {
                            min-width: 320px !important;
                            max-width: 100% !important;
                            display: block !important;
                          }
                          .u-row {
                            width: 100% !important;
                          }
                          .u-col {
                            width: 100% !important;
                          }
                          .u-col>div {
                            margin: 0 auto;
                          }
                        }
                       \s
                        body {
                          margin: 0;
                          padding: 0;
                        }
                       \s
                        table,
                        tr,
                        td {
                          vertical-align: top;
                          border-collapse: collapse;
                        }
                       \s
                        p {
                          margin: 0;
                        }
                       \s
                        .ie-container table,
                        .mso-container table {
                          table-layout: fixed;
                        }
                       \s
                        * {
                          line-height: inherit;
                        }
                       \s
                        a[x-apple-data-detectors='true'] {
                          color: inherit !important;
                          text-decoration: none !important;
                        }
                       \s
                        table,
                        td {
                          color: #000000;
                        }
                       \s
                        #u_body a {
                          color: #0000ee;
                          text-decoration: underline;
                        }
                      </style>
                                    
                                    
                                    
                      <!--[if !mso]><!-->
                      <link href="https://fonts.googleapis.com/css?family=Cabin:400,700" rel="stylesheet" type="text/css">
                      <!--<![endif]-->
                                    
                    </head>
                                    
                    <body class="clean-body u_body" style="margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #f9f9f9;color: #000000">
                      <!--[if IE]><div class="ie-container"><![endif]-->
                      <!--[if mso]><div class="mso-container"><![endif]-->
                      <table id="u_body" style="border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #f9f9f9;width:100%" cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr style="vertical-align: top">
                            <td style="word-break: break-word;border-collapse: collapse !important;vertical-align: top">
                              <!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td align="center" style="background-color: #f9f9f9;"><![endif]-->
                                    
                                    
                              <div class="u-row-container" style="padding: 0px;background-color: transparent">
                                <div class="u-row" style="Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #ffffff;">
                                  <div style="border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;">
                                    <!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding: 0px;background-color: transparent;" align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:600px;"><tr style="background-color: #ffffff;"><![endif]-->
                                    
                                    <!--[if (mso)|(IE)]><td align="center" width="600" style="width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;" valign="top"><![endif]-->
                                    <div class="u-col u-col-100" style="max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;">
                                      <div style="height: 100%;width: 100% !important;">
                                        <!--[if (!mso)&(!IE)]><!-->
                                        <div style="box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;">
                                          <!--<![endif]-->
                                    
                                          <table style="font-family:'Cabin',sans-serif;" role="presentation" cellpadding="0" cellspacing="0" width="100%" border="0">
                                            <tbody>
                                              <tr>
                                                <td style="overflow-wrap:break-word;word-break:break-word;padding:20px;font-family:'Cabin',sans-serif;" align="left">
                                    
                                                  <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                                    <tr>
                                                      <td style="padding-right: 0px;padding-left: 0px;" align="center">
                                                        <a href="https://sateeq.com" target="_blank">
                                                          <img align="center" border="0" src="https://cdn.donmai.us/original/b7/5e/__aris_blue_archive_drawn_by_donmin_h__b75ea043abdeca98f3e1ac7372140406.png" alt="Logo" title="Logo" style="outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 19%;max-width: 106.4px;"
                                                            width="106.4" />
                                                        </a>
                                                      </td>
                                                    </tr>
                                                  </table>
                                    
                                                </td>
                                              </tr>
                                            </tbody>
                                          </table>
                                    
                                          <!--[if (!mso)&(!IE)]><!-->
                                        </div>
                                        <!--<![endif]-->
                                      </div>
                                    </div>
                                    <!--[if (mso)|(IE)]></td><![endif]-->
                                    <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->
                                  </div>
                                </div>
                              </div>
                                    
                                    
                                    
                              <div class="u-row-container" style="padding: 0px;background-color: transparent">
                                <div class="u-row" style="Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #e5eaf5;">
                                  <div style="border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;">
                                    <!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding: 0px;background-color: transparent;" align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:600px;"><tr style="background-color: #e5eaf5;"><![endif]-->
                                    
                                    <!--[if (mso)|(IE)]><td align="center" width="600" style="width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;" valign="top"><![endif]-->
                                    <div class="u-col u-col-100" style="max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;">
                                      <div style="height: 100%;width: 100% !important;">
                                        <!--[if (!mso)&(!IE)]><!-->
                                        <div style="box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;">
                                          <!--<![endif]-->
                                    
                                          <table style="font-family:'Cabin',sans-serif;" role="presentation" cellpadding="0" cellspacing="0" width="100%" border="0">
                                            <tbody>
                                              <tr>
                                                <td style="overflow-wrap:break-word;word-break:break-word;padding:55px;font-family:'Cabin',sans-serif;" align="left">
                                    
                                                  <div style="color: #ffffff; line-height: 140%; text-align: left; word-wrap: break-word;">
                                                    <p style="font-size: 14px; line-height: 140%;"><span style="color: #000000; line-height: 19.6px;">Hello <font size="+1"><b>[[name]]</b></h2></span></p>
                                                    <p style="font-size: 14px; line-height: 140%;"> </p>
                                                    <p style="font-size: 14px; line-height: 140%;"><span style="color: #000000; line-height: 19.6px;">OTP for reset password is <font size="+3"><b>[[otp]]</b></font></span><br /><br /><span style="color: #000000; line-height: 19.6px;">Note: This OTP is valid for next 10 mins.</span></p>
                                                    <p style="font-size: 14px; line-height: 140%;"><br /><br /><span style="color: #000000; line-height: 19.6px;">Thanks &amp; Regards</span><br /><span style="color: #000000; line-height: 19.6px;">Team 18</span></p>
                                                  </div>
                                    
                                                </td>
                                              </tr>
                                            </tbody>
                                          </table>
                                    
                                          <!--[if (!mso)&(!IE)]><!-->
                                        </div>
                                        <!--<![endif]-->
                                      </div>
                                    </div>
                                    <!--[if (mso)|(IE)]></td><![endif]-->
                                    <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->
                                  </div>
                                </div>
                              </div>
                                    
                                    
                                    
                              <div class="u-row-container" style="padding: 0px;background-color: transparent">
                                <div class="u-row" style="Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;">
                                  <div style="border-collapse: collapse;display: table;width: 100%;height: 100%;background-color: transparent;">
                                    <!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding: 0px;background-color: transparent;" align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:600px;"><tr style="background-color: transparent;"><![endif]-->
                                    
                                    <!--[if (mso)|(IE)]><td align="center" width="600" style="width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;" valign="top"><![endif]-->
                                    <div class="u-col u-col-100" style="max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;">
                                      <div style="height: 100%;width: 100% !important;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;">
                                        <!--[if (!mso)&(!IE)]><!-->
                                        <div style="box-sizing: border-box; height: 100%; padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;border-radius: 0px;-webkit-border-radius: 0px; -moz-border-radius: 0px;">
                                          <!--<![endif]-->
                                    
                                          <table style="font-family:'Cabin',sans-serif;" role="presentation" cellpadding="0" cellspacing="0" width="100%" border="0">
                                            <tbody>
                                              <tr>
                                                <td style="overflow-wrap:break-word;word-break:break-word;padding:48px;font-family:'Cabin',sans-serif;" align="left">
                                    
                                                  <div style="color: #958d8d; line-height: 170%; text-align: center; word-wrap: break-word;">
                                                    <p style="font-size: 14px; line-height: 170%;">Love from Team 18</p>
                                                    <p style="font-size: 14px; line-height: 170%;">For any assistance and queries write to us at <a rel="noopener" href="mailto:support@codingmonk.in?" target="_blank">nguyenle.workspace@gmail.com</a><br /><br /></p>
                                                  </div>
                                    
                                                </td>
                                              </tr>
                                            </tbody>
                                          </table>
                                    
                                          <!--[if (!mso)&(!IE)]><!-->
                                        </div>
                                        <!--<![endif]-->
                                      </div>
                                    </div>
                                    <!--[if (mso)|(IE)]></td><![endif]-->
                                    <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->
                                  </div>
                                </div>
                              </div>
                                    
                                    
                              <!--[if (mso)|(IE)]></td></tr></table><![endif]-->
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <!--[if mso]></div><![endif]-->
                      <!--[if IE]></div><![endif]-->
                    </body>
                                    
                    </html>
                    """;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            try {
                helper.setFrom(fromAddress, senderName);
                helper.setTo(user.getEmail());
                helper.setSubject(subject);

                content = content.replace("[[name]]", user.getFullName());

                content = content.replace("[[otp]]", otp);

                helper.setText(content, true);

                mailSender.send(message);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
