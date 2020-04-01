package com.ncu.springboot.provider.message.util;

import java.io.File;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.ncu.springboot.api.message.entity.TbMessageTo;
import com.ncu.springboot.provider.message.entity.MailInfo;
import com.ncu.springboot.provider.message.queue.MessageStatus;
import com.ncu.springboot.provider.message.service.MessageService;

@Component
public class MailUtil {

	private Logger logger = LoggerFactory.getLogger(MailUtil.class);

	@Value("${mail.from}")
	private String from;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Resource
	private MessageService messageService;

	public void sendSimpleMail(MailInfo mailInfo, Integer id) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(from);
		simpleMailMessage.setTo(mailInfo.getTo());
		simpleMailMessage.setSubject(mailInfo.getSubject());
		simpleMailMessage.setText(mailInfo.getContent());
		mailSender.send(simpleMailMessage);
		if (id != null) {
			TbMessageTo tbMessageTo = new TbMessageTo();
			tbMessageTo.setId(id);
			tbMessageTo.setStatus(MessageStatus.SENT);
			messageService.editTbMessageTo(tbMessageTo);
		}
	}

	public void sendHtmlMail(MailInfo mailInfo) throws MessagingException {
		MimeMessage mimeMailMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(mailInfo.getTo());
		mimeMessageHelper.setSubject(mailInfo.getSubject());
		mimeMessageHelper.setText(mailInfo.getContent(), true);
		mailSender.send(mimeMailMessage);
	}

	public void sendTemplateMail(MailInfo mailInfo) throws MessagingException {
		Context context = new Context();
		context.setVariable("code", mailInfo.getContent());
		String emailContent = templateEngine.process("mailCodeTemplate", context);

		mailInfo.setContent(emailContent);

		this.sendHtmlMail(mailInfo);
	}

	/**
	 * 发送带附件格式的邮件
	 *
	 * @param mailInfo
	 * @throws MessagingException 
	 */
	public void sendAttachmentMail(MailInfo mailInfo) throws MessagingException {
		MimeMessage mimeMailMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
		mimeMessageHelper.setFrom(from);
		mimeMessageHelper.setTo(mailInfo.getTo());
		mimeMessageHelper.setSubject(mailInfo.getSubject());
		
		Context context = new Context();
		context.setVariable("code", mailInfo.getContent());
		String emailContent = templateEngine.process("mailCodeTemplate", context);

		mimeMessageHelper.setText(emailContent, true);
		// 文件路径
		FileSystemResource file = new FileSystemResource(new File("src/main/resources/templates/mailCodeTemplate.html"));
		mimeMessageHelper.addAttachment("mailCodeTemplate.html", file);

		mailSender.send(mimeMailMessage);
	}

}
