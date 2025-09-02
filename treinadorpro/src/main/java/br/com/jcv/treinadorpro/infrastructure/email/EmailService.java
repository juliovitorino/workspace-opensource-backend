package br.com.jcv.treinadorpro.infrastructure.email;

import br.com.jcv.treinadorpro.infrastructure.config.EmailConfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService {
    private final EmailConfig emailConfig;
    private final JavaMailSender mailSender;

    public EmailService(EmailConfig emailConfig, JavaMailSender mailSender) {
        this.emailConfig = emailConfig;
        this.mailSender = mailSender;
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent, Map<String, String> variables) throws MessagingException {
        String htmlBody = replaceTags(htmlContent, variables);


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(emailConfig.getFromMail());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }

    private String replaceTags(String template, Map<String, String> variables) {
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }
}
