/**
 *
 * 1. FileName : CaptchaController.java
 * 2. Package : egovframework.framework.security
 * 3. Comment : 
 * 4. 작성자  : SooHyun.Seo
 * 5. 작성일  : 2017.12.22. 오전 9:55:47
 * 6. 변경이력 : 
 *    이름     : 일자          : 근거자료   : 변경내용
 *    ------------------------------------------------------
 *    SooHyun.Seo : 2017.12.22. :            : 신규 개발.
 */

package com.whomade.kycarrots.common.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.CaptchaUtil;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : LoginController.java
 * 3. Package  : egovframework.framework.security
 * 4. Comment  : 
 * 5. 작성자   : SooHyun.Seo
 * 6. 작성일   : 2017.12.22. 오전 9:55:47
 * </PRE>
 */
@Controller
public class CaptchaController {

	private static Log log = LogFactory.getLog(CaptchaController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;

	@RequestMapping(value = "/common/captcha.do")
	public void captcha(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		log.debug("####" + this.getClass().getName() + " START ####");

		CaptchaUtil cu = new CaptchaUtil();
		//문자 정보
		cu.setCharType("N"); //N:number, A:alphabet
		cu.setTextLength(5); //문자열 길이

		//만들 이미지 정보
		cu.setFontFamilyName("");
		cu.setImageSize(75, 50); //imageWitdh, imageHeight
		cu.setTextSize(13); //textSize

		String captchaText = cu.createText();

		request.getSession().setAttribute("captchaText", captchaText);

		BufferedImage bImage = cu.createImage(captchaText);

		if (bImage == null) { //실패

			response.setContentType("text/html; charset=utf-8");

			String message = "Error! Please try later.";

			try {
				PrintWriter out = response.getWriter();
				out.println(message);
				out.flush();
			} catch (Exception e) {
			}

		} else { //성공

			try {
				//png 파일 설정
				response.setContentType("image/png; charset=utf-8");

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", baos);

				byte buffer[] = baos.toByteArray();

				ServletOutputStream sos = response.getOutputStream();

				sos.write(buffer);
				sos.flush();

			} catch (Exception e) {
			}
		}
	}

	@RequestMapping(value = "/common/captcharValidateAjax.do")
	public @ResponseBody
	void captcharValidateAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		log.debug("####" + this.getClass().getName() + " START ####");

		DataMap param = RequestUtil.getDataMap(request);

		JSONObject resultJSON = new JSONObject();
		DataMap resultStats = new DataMap();
		
		String ssCaptcharText = "";
		
		if(request.getSession().getAttribute("captchaText") != null){
			ssCaptcharText = (String)request.getSession().getAttribute("captchaText");
		}
		
		log.debug("ssCaptcharText:"+ssCaptcharText);
		log.debug("captchaText:"+param.getString("captchaText"));
		
		if(ssCaptcharText.equalsIgnoreCase(param.getString("captchaText"))){
			resultStats.put("resultCode", "ok");
			resultStats.put("resultMsg", "");			
		}
		else{
			resultStats.put("resultCode", "error");
			resultStats.put("resultMsg", egovMessageSource.getMessage("fail.common.captchar"));			
		}
		
		resultJSON.put("resultStats", resultStats);

		response.setContentType("text/html; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

}
