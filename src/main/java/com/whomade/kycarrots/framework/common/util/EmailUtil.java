package com.whomade.kycarrots.framework.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import jakarta.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : EmailUtil.java
 * 3. Package  : egovframework.framework.common.util
 * 4. Comment  : 메일 보내기
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2014. 9. 17. 오후 3:11:19
 * </PRE>
 */ 
public class EmailUtil {

	protected Log log = LogFactory.getLog(this.getClass());
	
	private static String ADMIN_EMAIL = EgovPropertiesUtil.getProperty("mail.default.from");
	
	private JavaMailSender mailSender;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	
	/**
	 * <PRE>
	 * 1. MethodName : sendEmail
	 * 2. ClassName  : EmailUtil
	 * 3. Comment   : 메일 보내기
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 9. 25. 오후 6:15:31
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @param emailForm
	 *   @param to
	 */
	public void sendEmail(DataMap param, String emailForm, String to) {
		sendEmail(param, emailForm, to, null);
	}
	
	public void sendEmail(DataMap param, String emailForm, String[] to) {
		sendEmail(param, emailForm, to, null);
	}
	
	public void sendEmail(DataMap param, String emailForm, String to, List fileList) {
		String from = ADMIN_EMAIL;
		
		sendEmail(param, emailForm, to, fileList, from);
	}
	
	public void sendEmail(DataMap param, String emailForm, String[] to, List fileList) {
		String from = ADMIN_EMAIL;
		
		sendEmail(param, emailForm, to, fileList, from);
	}
	
	public void sendEmail(DataMap param, String emailForm, String to, List fileList, String from) {
		if(from == null){ from = ADMIN_EMAIL; }
		
		sendEmail(param, emailForm, to, fileList, from, null);
	}
	
	public void sendEmail(DataMap param, String emailForm, String[] to, List fileList, String from) {
		if(from == null){ from = ADMIN_EMAIL; }
		
		sendEmail(param, emailForm, to, fileList, from, null);
	}
	
	public void sendEmail(DataMap param, String emailForm, String to, List fileList, String from, String cc) {
		if(from == null){ from = ADMIN_EMAIL; }
		
		sendEmail(param, emailForm, to, fileList, from, cc, null);
	}
	
	public void sendEmail(DataMap param, String emailForm, String[] to, List fileList, String from, String[] cc) {
		if(from == null){ from = ADMIN_EMAIL; }
		
		sendEmail(param, emailForm, to, fileList, from, cc, null);
	}
	
	public void sendEmail(DataMap param, String emailForm, String to, List fileList, String from, String cc, String bcc) {
		if(from == null){ from = ADMIN_EMAIL; }
		
		sendConfirmationEmail(param, emailForm, to, fileList, from, cc, bcc);
	}
	
	public void sendEmail(DataMap param, String emailForm, String[] to, List fileList, String from, String[] cc, String[] bcc) {
		if(from == null){ from = ADMIN_EMAIL; }
		
		sendConfirmationEmail(param, emailForm, to, fileList, from, cc, bcc);
	}

	/**
	 * <PRE>
	 * 1. MethodName : sendConfirmationEmail
	 * 2. ClassName  : EmailUtil
	 * 3. Comment   : 메일 폼 생성
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 9. 25. 오후 6:15:39
	 * </PRE>
	 *   @return void
	 *   @param param
	 *   @param emailForm
	 *   @param to
	 *   @param fileList
	 *   @param from
	 *   @param cc
	 *   @param bcc
	 */
	private void sendConfirmationEmail(final DataMap param, final String emailForm, final String to, final List fileList, final String from, final String cc, final String bcc){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				try {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					// 받는사람
					message.setTo(to);
					// 보내는사람
					message.setFrom(from); // could be parameterized...
					if (cc != null) {
						// 참조
						message.setCc(cc);
					}
					if (bcc != null) {
						// 숨은 참조
						message.setBcc(bcc);
					}
					
					// 제목
					message.setSubject(param.getString("title"));
					
					// 양식 텍스트를 가져온다.
					String text = "";
					// 내용
					message.setText(text, true);
					
					// 첨부파일
					if(fileList != null){
						for(int i = 0; i < fileList.size(); i++){
							File f = (File) fileList.get(i);
							InputStream is = new FileInputStream(f);
							message.addAttachment(f.getName(), new ByteArrayResource(IOUtils.toByteArray(is)));
						}
					}
				} catch (Exception e) {
					log.debug(e.toString());
					throw new Exception(e.toString());
				}
			}
		};
		send(preparator);
	}
	
	private void sendConfirmationEmail(final DataMap param, final String emailForm, final String[] to, final List fileList, final String from, final String[] cc, final String[] bcc){
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				try {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					message.setTo(to);
					message.setFrom(from); // could be parameterized...
					if (cc != null) {
						message.setCc(cc);
					}
					if (bcc != null) {
						message.setBcc(bcc);
					}
					
					// 제목
					message.setSubject(param.getString("title"));
					
					// 양식 텍스트를 가져온다.
					String text =  "";
					message.setText(text, true);
					
					// 첨부파일
					if(fileList != null){
						for(int i = 0; i < fileList.size(); i++){
							File f = (File) fileList.get(i);
							InputStream is = new FileInputStream(f);
							message.addAttachment(f.getName(), new ByteArrayResource(IOUtils.toByteArray(is)));
						}
					}
				} catch (Exception e) {
					log.debug(e.toString());
					throw new Exception(e.toString());
				}
			}
		};
		send(preparator);
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : send
	 * 2. ClassName  : EmailUtil
	 * 3. Comment   : 메일 보내기
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2014. 9. 25. 오후 6:15:50
	 * </PRE>
	 *   @return void
	 *   @param preparator
	 */
	private void send(MimeMessagePreparator preparator){
		this.mailSender.send(preparator);
	}
	
}

