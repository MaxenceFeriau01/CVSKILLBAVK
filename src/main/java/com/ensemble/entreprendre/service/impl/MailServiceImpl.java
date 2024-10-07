package com.ensemble.entreprendre.service.impl;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import com.ensemble.entreprendre.domain.User;
import com.ensemble.entreprendre.domain.enumeration.MailSubject;
import com.ensemble.entreprendre.domain.enumeration.RoleEnum;
import com.ensemble.entreprendre.dto.CompanyEmailDto;
import com.ensemble.entreprendre.exception.ApiException;
import com.ensemble.entreprendre.exception.ApiNotFoundException;
import com.ensemble.entreprendre.repository.IUserRepository;
import com.ensemble.entreprendre.service.IMailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MailServiceImpl implements IMailService {

    @Value("${spring.mail.from:noreply@jobexplorer.eedk.fr}")
    private String from;

    @Value("${spring.mail.replyto:noreply@jobexplorer.eedk.fr}")
    private String replyTo;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private IUserRepository userRepository;

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

            HashMap<String, Object> params = mail.getParams();
            params.forEach((k, v) -> {
                context.put(k, v);
            });

            StringWriter msgContent = new StringWriter();
            template.merge(context, msgContent);
    
            if (mail.getFrom() == null) {
                helper.setFrom(from);
            } else {
                helper.setFrom(mail.getFrom());
            }

            helper.setReplyTo(replyTo);
            helper.setText(msgContent.toString(), true);
            helper.setSubject(mail.getTitle());
            helper.addTo(mail.getRecipient());
            if (mail.getAttachments() != null) {
                for (Resource a : mail.getAttachments()) {
                    helper.addAttachment(a.getFilename(), a);
                }
            }
            this.emailSender.send(message);

        }
    }

    @Override
    public void prepareMail(MailSubject subject, String title, String recipient, HashMap<String, Object> params,
            Optional<Collection<Resource>> attachments)
            throws EntityNotFoundException, ApiNotFoundException, ParseException, MessagingException {
        Mail mail = new Mail();
        mail.setSubject(subject);
        mail.setTitle(title);
        mail.setRecipient(recipient);
        mail.setParams(params);
        if (attachments != null && attachments.isPresent()) {
            mail.setAttachments(attachments.get());
        }
        this.sendMail(mail);
    }

    @Override
    public void sendEmailCompanyToEEDK(CompanyEmailDto email) throws ApiException, EntityNotFoundException, MessagingException {
        if (email == null) {
            throw new ApiNotFoundException("Unable to find email details for sending.");
        } else if (email.getFullName() == null) {
            throw new ApiNotFoundException("Unable to find full name for sending.");
        } else if (email.getCompany() == null) {
            throw new ApiNotFoundException("Unable to find company name for sending.");
        } else if (email.getEmail() == null) {
            throw new ApiNotFoundException("Unable to find email address for sending.");
        } else if (email.getPhone() == null) {
            throw new ApiNotFoundException("Unable to find phone number for sending.");
        } else if (email.getContent() == null) {
            throw new ApiNotFoundException("Unable to find email content for sending.");
        } else {
            Collection<User> users = userRepository.findByRoles_Role(RoleEnum.ROLE_ADMIN);

            for (User user : users) {
                if (user.getActivated()) {
                    Mail mail = new Mail();
                    mail.setTitle("[" + email.getCompany() + "] Demande de contact depuis la plateforme JobExplorer");
                    mail.setFrom(email.getFullName() + " <" + email.getEmail() + ">");
                    mail.setRecipient(user.getFirstName() + " " + user.getName() + "<" + user.getEmail() + ">");
                    mail.setSubject(MailSubject.AdminCompanyContact);

                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("fullName", email.getFullName());
                    params.put("company", email.getCompany());
                    params.put("email", email.getEmail());
                    params.put("phone", email.getPhone());
                    params.put("content", email.getContent().replace("\n", "<br />"));
                    mail.setParams(params);

                    this.sendMail(mail);
                }
            }
        }
    }
}
