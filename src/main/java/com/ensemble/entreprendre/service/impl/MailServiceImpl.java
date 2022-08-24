package com.ensemble.entreprendre.service.impl;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ensemble.entreprendre.config.mail.MailType;
import com.ensemble.entreprendre.domain.Mail;
import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.service.IMailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MailServiceImpl implements IMailService {

    @Value("${spring.mail.from:noreply-entreprendre@ensemble.com}")
    private String from;

    @Value("${spring.mail.replyto:noreply-entreprendre@ensemble.com}")
    private String replyTo;

    @Autowired
    public JavaMailSender emailSender;

    private void sendMail(final Mail mail) throws MessagingException, EntityNotFoundException, ApiNotFoundException {
        if (mail == null) {
            throw new ApiNotFoundException("Unable to find the mail for sending");
        } else if (mail.getRecipient() == null) {
            throw new ApiNotFoundException("Unable to find the recipient for sending");
        } else if (mail.getSubject() == null) {
            throw new ApiNotFoundException("Unable to find the mail subject");
        } else if (mail.getParams() == null) {
            throw new ApiNotFoundException("Unable to find essential informations for sending this mail");
        } else if (mail.getTitle() == null) {
            throw new ApiNotFoundException("Unable to find the mail title");
        }

        else

        {
            MimeMessage message = this.emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            Properties props = new Properties();
            props.setProperty(RuntimeConstants.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
            props.setProperty("resource.loader", "class");
            props.setProperty("class.resource.loader.class",
                    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            Velocity.init(props);

            VelocityContext context = new VelocityContext();
            MailType type = mail.getSubject().getTemplate();
            Template template = Velocity.getTemplate(type.getPath());
            System.out.println(mail.getRecipient());

            HashMap<String, Object> params = mail.getParams();
            params.forEach((k, v) -> {
                context.put(k, v);
            });

            StringWriter msgContent = new StringWriter();
            template.merge(context, msgContent);
            helper.setFrom(from);
            helper.setReplyTo(replyTo);
            helper.setText(msgContent.toString(), true);
            helper.setSubject(mail.getTitle());
            helper.addTo(mail.getRecipient());
            if (mail.getAttachments() != null) {
                for (Resource a : mail.getAttachments()) {
                    helper.addAttachment(a.getFilename(), a);
                }
            }
            log.debug(msgContent.toString());
            this.emailSender.send(message);

        }
    }

    @Override
    public void prepareMail(MailSubject subject, String title, String recipient, HashMap<String, Object> params,
            Optional<Collection<Resource>> attachments)
            throws EntityNotFoundException, ApiNotFoundException, ParseException, MessagingException {
        log.debug("on est dans le prepare Mail !!");
        Mail mail = new Mail();
        mail.setSubject(subject);
        mail.setTitle(title);
        mail.setRecipient(recipient);
        mail.setParams(params);
        log.debug("attachments");
        if (attachments != null && attachments.isPresent()) {
            mail.setAttachments(attachments.get());
        }
        this.sendMail(mail);
    }

}
