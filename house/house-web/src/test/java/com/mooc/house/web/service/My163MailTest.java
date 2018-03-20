package com.mooc.house.web.service;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.mooc.house.HouseApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=HouseApplication.class)
public class My163MailTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Test
    public void testSendSimple() throws MessagingException {
    	SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo("754576021@qq.com");
        message.setSubject("aaaa");
        message.setText("bbbbb");

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }

    }
}