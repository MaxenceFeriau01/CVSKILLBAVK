package com.ensemble.entreprendre.domain.enumeration;

import com.ensemble.entreprendre.config.mail.MailType;

public enum MailSubject {
	Password, ResetPassword, RegistrationConfirm, ApplyConfirm, ApplyCompanyAdminTemplate;

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
		case "ApplyConfirm":
			mailType.setPath("velocity/ApplyConfirmTemplate.vm");
			mailType.setLabel("ApplyConfirm");
			break;
		case "ApplyCompanyAdminTemplate":
			mailType.setPath("velocity/ApplyCompanyAdminTemplate.vm");
			mailType.setLabel("	ApplyCompanyAdminTemplate");
			break;

		}
		return mailType;
	}
}
