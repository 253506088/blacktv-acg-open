package blacktv.tvacg.database.service;

import blacktv.tvacg.tool.GetProperty;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 用于发送邮件的工具类
 */
@Service
public class SendEmail {
    private Properties properties;
    private Session session;
    private String to;// 收件人邮箱
    private String from;// 发件人邮箱
    private String host;// 邮箱服务器

    /**
     * 初始化
     */
    public SendEmail() throws GeneralSecurityException {
        // 读取配置文件
        properties = new GetProperty().getPropertyByFileName("email.properties");
        from = properties.getProperty("from");
        host = properties.getProperty("host");
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");

        //开启ssl加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        // 获取默认session对象
        session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("username"),
                        properties.getProperty("password")); // 发件人邮件用户名、密码
            }
        });
        session.setDebug(true);
    }

    /**
     * 发送一封带有消息体的html的邮件
     *
     * @param to    收件人
     * @param title 标题
     * @param msg   消息体
     * @param html  html代码
     * @return
     */
    public Boolean sendHtmlAndMessageEmail(String to, String title, String msg, String html) {
        Boolean flag = true;
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 邮件标题
            message.setSubject(title, "utf-8");
            // 设置消息体
            message.setText(msg, "utf-8");
            // 发送 HTML邮件, 可以插入html标签
            message.setContent(html, "text/html;charset=utf-8");
            // 发送消息
            Transport.send(message);
            System.out.println("发送完毕");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            flag = false;
            System.out.println("邮件发送异常");
        }
        return flag;
    }

    /**
     * 发送一封html邮件
     *
     * @param to    收件人
     * @param title 标题
     * @param html  html代码
     * @return
     */
    public Boolean sendHtmlEmail(String to, String title, String html) {
        Boolean flag = true;
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(title, "utf-8");
            message.setContent(html, "text/html;charset=utf-8");
            Transport.send(message);
            System.out.println("发送完毕");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            flag = false;
            System.out.println("邮件发送异常");
        }
        return flag;
    }

    /**
     * 发送一封普通的消息邮件
     *
     * @param to
     * @param title
     * @param msg
     * @return
     */
    public Boolean sendEmail(String to, String title, String msg) {
        Boolean flag = true;
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(title, "utf-8");
            message.setText(msg, "utf-8");
            Transport.send(message);
            System.out.println("发送完毕");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            flag = false;
            System.out.println("邮件发送异常");
        }
        return flag;
    }
}
