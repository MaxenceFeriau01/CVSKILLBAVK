package com.ensemble.entreprendre.domain.enumeration;

import com.ensemble.entreprendre.config.mail.MailType;

public enum MailSubject {
	Password, ResetPassword, RegistrationConfirm;

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

		}
		return mailType;
	}
}
