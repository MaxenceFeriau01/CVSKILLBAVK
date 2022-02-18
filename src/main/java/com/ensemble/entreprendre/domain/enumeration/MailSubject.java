package com.ensemble.entreprendre.domain.enumeration;

import com.ensemble.entreprendre.config.mail.MailType;

public enum MailSubject {
		Password, ResetPassword, Invoice, PaymentDelay;

		
		public MailType getTemplate() {
			
			MailType mailType = new MailType();
			switch(this.toString()) {
			case "Password": 	  mailType.setPath("velocity/MailPasswordTemplate.vm");
								  mailType.setLabel("PasswordTemplate");
								  break;
			case "ResetPassword": mailType.setPath("velocity/MailResetPWDTemplate.vm");	
								  mailType.setLabel("ResetPwdTemplate");
								  break;
			case "PaymentDelay":  mailType.setPath("velocity/MailPaymentDelayTemplate.vm");
								  mailType.setLabel("PaymentDelayTemplate");
							  	  break;
			case "Invoice" : 	  mailType.setPath("velocity/MailInvoiceTemplate.vm");
								  mailType.setLabel("InvoiceTemplate");
							  	  break;
			
			}
			return mailType;
		}
	}


