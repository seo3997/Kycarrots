/**
 * 
 *
 * 1. FileName : pageNavigationUtil.java
 * 2. Package : egovframework.framework.common.util
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2013. 8. 26. 오후 1:45:02
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2013. 8. 26. :            : 신규 개발.
 */

package com.whomade.kycarrots.framework.common.mail.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : pageNavigationUtil.java
 * 3. Package  : egovframework.framework.common.util
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2013. 8. 26. 오후 1:45:02
 * </PRE>
 */

 public class mailUtil {

	private static Log log = LogFactory.getLog(mailUtil.class);

	/**
	 * <PRE>
	 * 1. MethodName : mailUtil
	 * 2. ClassName  : mailUtil
	 * 3. Comment   : 
	 * 4. 작성자    : 박찬석
	 * 5. 작성일    : 2020.11.11
	 * </PRE>
	 */
	public mailUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void send(String pFrom, String pSubject, String pTo, String pType, String pContent) throws IOException {
		
		String host = "mw-002.cafe24.com";              //smtp mail server    
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth","true");

		Authenticator auth = new MyAuthentication();
		Session sess = Session.getInstance(props, auth);

		System.out.println("pFrom:["+pFrom+"]");
		System.out.println("pTo:["+pTo+"]");
		System.out.println("pSubject:["+pSubject+"]");
		System.out.println("pContent:["+pContent+"]");

		
		try {
		        Message msg = new MimeMessage(sess);
		        msg.setFrom(new InternetAddress(pFrom));
		        InternetAddress[] address = {new InternetAddress(pTo)};
		        msg.setRecipients(Message.RecipientType.TO, address);
		        msg.setSubject(pSubject);
		        msg.setSentDate(new Date());
		        msg.setText(pContent);

		        Transport.send(msg);
		        System.out.println("^_^");
		} catch (MessagingException mex) {
			System.out.println(mex.getMessage()+":");
			System.out.println("-_-;;");
		}
		
	}

 }
 
 class MyAuthentication extends Authenticator {
	    PasswordAuthentication pa;
	    public MyAuthentication(){
		      pa = new PasswordAuthentication("admin@planf.shop", "amway1122!@");  //ex) ID:cafe24@cafe24.com PASSWD:1234
	    }

	    public PasswordAuthentication getPasswordAuthentication() {
	        return pa;
	    }
}
