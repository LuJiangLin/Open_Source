/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  EmailUtils.java
 * PACKAGE      :  com.anssy.webcore.common
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.common;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 * @description Email工具类
 */
public class EmailUtils {

    /**
     * 注册时 发送激活邮件
     *
     * @param to_email 收件邮箱账号
     */
    public boolean sendActivateEmail(String to_email, Long userId, String sign) {
        boolean flag;
        try {
            String subject = "去创业APP邮箱激活";
            sendMail(BaseConstants.EMAIL_HOST, BaseConstants.EMAIL_USER, BaseConstants.EMAIL_PASSWD, to_email, subject, "尊敬的【去创业】App用户:<br>" + "感谢您使用湖北科技创业服务中心去创业App," + "<a href='" + BaseConstants.ACTIVATE_URL + "app/base/registerAction/emailActivate.action" + "?userId=" + userId + "&sign=" + sign + "' >点击激活邮箱</a>");
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 参与活动时 发送通知邮件
     *
     * @param to_email 收件邮箱账号
     */
    public boolean sendActivityEmail(String to_email, String nickname, String activityName
            , int joinNumber, String upperNumber) {
        boolean flag;
        try {
            String subject = "去创业活动通知";
            sendMail(BaseConstants.EMAIL_HOST, BaseConstants.EMAIL_USER, BaseConstants.EMAIL_PASSWD, to_email, subject, "尊敬的" + nickname + "女士/先生:<br>" + "已成功报名" + activityName + "活动," + "您是第" + joinNumber + "/" + upperNumber + "位报名者," + "请等待工作人员和您联系(注册电话/邮箱),感谢您的大力支持.");
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 发送邮件方法
     *
     * @param host     smtp域名
     * @param user     发件邮箱账号
     * @param password 发件邮箱密码
     * @param to_email 收件邮箱账号
     * @param subject  标题
     * @param text     内容
     */
    private void sendMail(String host, String user, String password, String to_email, String subject, String text) throws Exception {
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        // 发件人的账号
        props.put("mail.user", user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", password);

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);
        // 设置收件人
        InternetAddress to = new InternetAddress(to_email);
        message.setRecipient(RecipientType.TO, to);
        // 设置邮件标题
        message.setSubject(subject);
        // 设置邮件的内容体
        message.setContent(text, "text/html;charset=UTF-8");
        // 发送邮件
        Transport.send(message);
    }

}