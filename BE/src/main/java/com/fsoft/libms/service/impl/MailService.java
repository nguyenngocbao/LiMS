package com.fsoft.libms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fsoft.libms.service.IMailService;

@Service
public class MailService implements IMailService{

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String[] listMailTo, String subject, String content) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(listMailTo);
		msg.setSubject(subject);
		msg.setText(content);
		javaMailSender.send(msg);
	}
}
