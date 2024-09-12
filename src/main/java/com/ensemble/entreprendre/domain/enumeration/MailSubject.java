package com.ensemble.entreprendre.domain.enumeration;

import com.ensemble.entreprendre.config.mail.MailType;

public enum MailSubject {
    Password, ResetPassword, RegistrationConfirm, ApplyCompany, ApplyCompanyAdminTemplate, AdminRegistrationConfirm, AdminCompanyContact;

    public MailType getTemplate() {

        MailType mailType = new MailType();
        switch (this.toString()) {
            case "Password":
                mailType.setPath("velocity/MailPasswordTemplate.vm");
                mailType.setLabel("PasswordTemplate");
                break;
            case "ResetPassword":
                mailType.setPath("velocity/MailResetPWDTemplate.vm");
                mailType.setLabel("ResetPwdTemplate");
                break;
            case "RegistrationConfirm":
                mailType.setPath("velocity/MailRegistrationConfirmTemplate.vm");
                mailType.setLabel("RegistrationConfirmTemplate");
                break;
            case "ApplyCompany":
                mailType.setPath("velocity/ApplyCompanyTemplate.vm");
                mailType.setLabel("ApplyCompany");
                break;
            case "ApplyCompanyAdminTemplate":
                mailType.setPath("velocity/ApplyCompanyAdminTemplate.vm");
                mailType.setLabel("ApplyCompanyAdminTemplate");
                break;
            case "AdminRegistrationConfirm":
                mailType.setPath("velocity/MailNewUserAdminTemplate.vm");
                mailType.setLabel("MailNewUserAdminTemplate");
                break;
            case "AdminCompanyContact":
                mailType.setPath("velocity/AdminCompanyContactTemplate.vm");
                mailType.setLabel("AdminCompanyContactTemplate");
                break;

        }
        return mailType;
    }
}
