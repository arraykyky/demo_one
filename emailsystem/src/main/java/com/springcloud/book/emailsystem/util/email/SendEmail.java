package com.springcloud.book.emailsystem.util.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmail {

    static Logger logger = LoggerFactory.getLogger(SendEmail.class);

    private static JavaMailSender sender;

    /**
     * 文本邮件多个发送
     * @param addressesArray     邮件接收人
     * @return
     * @throws Exception
     */
    public static boolean sendEmail(String port,String email,String password,Address[] addressesArray,String emailTitle,String emailContext)throws Exception{
        boolean flag = true;
        Transport ts = null;
        try {
            //获取session
            Session session = getSession(port);
            //获取Transport连接
            ts = getTransportConnect(session,email,password);
            //4、创建邮件(文本)
            Message message = createSimpleMail(session,addressesArray,emailTitle,emailContext,email);
            ts.sendMessage(message, message.getAllRecipients());
        }catch (Exception e){
            flag = false;
            logger.info(e.getMessage());
            throw e;
        }finally {
            closeTransportConnect(ts);
        }
        logger.info("********邮件发送状态:"+flag+"********");
        return flag;
    }

    /**
     * 文本邮件单个发送
     * @param recipient     邮件接收人
     * @param emailContext  邮件正文内容
     * @return
     * @throws Exception
     */
    public static boolean sendEmail(String port,String email,String password,String recipient,String emailTitle,String emailContext)throws Exception{
        boolean flag = true;
        Transport ts = null;
        try {
            //获取session
            Session session = getSession(port);
            //获取Transport连接
            ts = getTransportConnect(session,email,password);
            //4、创建邮件(文本)
            Message message = createSimpleMail(session,recipient,emailTitle,emailContext,email);
            ts.sendMessage(message, message.getAllRecipients());
        }catch (Exception e){
            flag = false;
            logger.info(e.getMessage());
            throw e;
        }finally {
            closeTransportConnect(ts);
        }
        logger.info("********邮件发送状态:"+flag+"********");
        return flag;
    }

    /**
     * 附件邮件发送
     * @param recipient
     * @param inputStreamSource
     * @return
     * @throws Exception
     */
    public static boolean sendEmail(String port,String email,String password,String recipient,InputStreamSource inputStreamSource,String attachName,String emailTitle,String emailContent)throws Exception{
        boolean flag = true;
        Transport ts = null;
        try {

            //获取session
            Session session = getSession(port);
            //获取Transport连接
            ts = getTransportConnect(session,email,password);
            //创建邮件(附件)
            Message message = createAttachMail(session,recipient,inputStreamSource,attachName,emailTitle,emailContent,email);
            //5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
        }catch (Exception e){
            flag = false;
            logger.info(e.getMessage());
            throw e;
        }finally {
            closeTransportConnect(ts);
        }
        logger.info("********邮件发送状态:"+flag+"********");
        return flag;
    }

    //关闭Transport连接
    public static void closeTransportConnect(Transport ts){
        if (ts != null){
            try {
                ts.close();
                logger.info("******************关闭Transport连接*******************");
            } catch (MessagingException e) {
                logger.info("******************关闭Transport连接失败*******************");
            }
        }
    }

    //获取Transport连接
    public static Transport getTransportConnect(Session session,String email,String password) throws Exception{
        //2、通过session得到transport对象
        Transport ts = session.getTransport("smtp");
        //3、使用邮箱的用户名和密码连上邮件服务器
        ts.connect(email,password);
        return ts;
    }

    //获取session
    public static Session getSession(String port){
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.medbooks.com.cn");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", port);
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(false);
        return session;
    }

    /**
     * 创建一封只包含文本的邮件
     * @param session
     * @return
     * @throws Exception
     * */
    public static MimeMessage createSimpleMail(Session session,Address[] addresses,String emailTitle,String emailContext,String email) throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
        //指明邮件的发件人
        message.setFrom(new InternetAddress(email));
        //指明邮件的收件人
        message.setRecipients(Message.RecipientType.TO, addresses);
        //邮件的标题
        message.setSubject(emailTitle);
        //邮件的文本内容
        message.setContent(emailContext, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }

    /**
     * 创建一封只包含文本的邮件
     * @param session
     * @return
     * @throws Exception
     * */
    public static MimeMessage createSimpleMail(Session session, String recipient,String emailTitle,String emailContent, String email) throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
        //指明邮件的发件人
        message.setFrom(new InternetAddress(email));
        //指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        //邮件的标题
        message.setSubject(emailTitle);
        //邮件的文本内容
        message.setContent(emailContent, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }

    /**
     * 创建一封带附件的邮件
     * @param session
     * @return
     * @throws Exception
     */
    public static MimeMessage createAttachMail(Session session, String recipient, InputStreamSource inputStreamSource,String attachName, String emailTitle, String emailContent, String email) throws Exception{
        MimeMessage message = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(message, true,"GB2312");
        helper.setFrom(email);
        helper.setTo(recipient);
        helper.setSubject(emailTitle);
        helper.setText(emailContent,true);
        helper.addAttachment(attachName,inputStreamSource);
        return message;
    }

}
