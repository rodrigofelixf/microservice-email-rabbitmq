package io.ms.email.services;

import io.ms.email.enums.StatusEmail;
import io.ms.email.models.EmailModel;
import io.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }


    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        emailModel.setEmailFrom(emailFrom);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailModel.getEmailTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getText());

        try {
            javaMailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }

        return emailRepository.save(emailModel);
    }



}
