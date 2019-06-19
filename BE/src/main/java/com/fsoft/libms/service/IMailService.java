package com.fsoft.libms.service;

public interface IMailService {

	public void sendMail(String[] listMailTo, String subject, String content);
}
