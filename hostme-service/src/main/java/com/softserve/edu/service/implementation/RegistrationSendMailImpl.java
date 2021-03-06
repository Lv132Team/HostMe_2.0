package com.softserve.edu.service.implementation;

import com.softserve.edu.model.User;
import com.softserve.edu.service.RegistrationSendMail;
import com.softserve.edu.service.Settings;
import com.softserve.edu.service.SystemPropertiesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class RegistrationSendMailImpl implements RegistrationSendMail, Settings {
 	
    @Autowired
    SystemPropertiesService systemPropertiesService;

    private JavaMailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendWelcomeMail(User user) {
        String url = systemPropertiesService.getPropetyByName(BASE_SEND_URL);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(user.getEmail());
            helper.setSubject("Thank you " + user.getFirstName() + " "
                    + user.getLastName() + " for joining us!");
            String html = "Thank you " + user.getFirstName()
                    + " for joining us!"
                    + "<br> Please, activate your account with the following link: <br>"
                    + "<a href=\"" + url +
                    + user.getUserId() + "\">Activation Link</a>";
            helper.setText("UTF-8", html);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }
    
    @Override
    public void sendNewPassMail(User user, String newpass) {
        String url = systemPropertiesService.getPropetyByName(WEB_URL);
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(user.getEmail());
            helper.setSubject("Thank you " + user.getFirstName() + " "
                    + user.getLastName() + " for joining us!");
            String html = "Hi! " + user.getFirstName()
                    + " , yours new password will be: "
                    + newpass
                    + "<br> You can login with the following link: <br>"
                    + "<a href=\"" + url +
                    "\">Activation Link</a>";
            helper.setText("UTF-8", html);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }
}
