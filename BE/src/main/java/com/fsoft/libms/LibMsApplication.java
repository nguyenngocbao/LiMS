package com.fsoft.libms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( { "com.fsoft.libms" } )
public class LibMsApplication{
//	 @Autowired
//	    private JavaMailSender javaMailSender;
    public static void main( String[] args ) {
	SpringApplication.run( LibMsApplication.class, args );
    }
    
//    @Override
//    public void run(String... args) {
//
//        System.out.println("Sending Email...");
//
//        sendEmail();
//		//sendEmailWithAttachment();
//
//        System.out.println("Done");
//
//    }
    
//    void sendEmail(String[] emails, String subject, String content) {
//
//        SimpleMailMessage msg = new SimpleMailMessage();
////        msg.setTo("14130337@st.hcmuaf.edu.vn",  "2@yahoo.com");
////        String[] c = {emails};
//        msg.setTo(emails);
//
//        msg.setSubject("Testing from Spring Boot");
//        msg.setText("Hello World \n Spring Boot Email");
//
//        javaMailSender.send(msg);
//
//    }


}