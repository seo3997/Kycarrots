package com.whomade.kycarrots.framework.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.whomade.kycarrots.framework.common.object.DataMap;

/**
 * <PRE>
 * 1. ClassName 	: FcmSend 
 * 2. FileName  	: FcmSend.java
 * 3. Package  		: egovframework.framework.common.util
 * 4. Comment  		: FCM PUSH 보내기
 * 5. 작성자   		: SooHyun.Seo
 * 6. 작성일   		: 2014. 9. 17. 오후 3:11:19
 * </PRE>
 */ 
public class FcmPush {

	protected Log log = LogFactory.getLog(this.getClass());
	private String msgTitle;
	private String msgCont;
	private String msgUrl;
	
	public FcmPush() {
		
	}
	
	public FcmPush(String msgTitle,String msgCont,String msgUrl) {
		this.msgTitle	=msgTitle;
		this.msgCont	=msgCont;
		this.msgUrl		=msgUrl;
	}

	public void sendPush() {
		String serverKey = "AAAAdu6j0tY:APA91bGRbIg9fM0AbVm-i5HwuD8rPonR2C5Di2E9Wg3jUuUJq6c5lomAM5nhEhPNIOl6fR6Y7-zNCTlg7T9F48XUP7l-c7ugLVJbmV37IP5ddM-Io9pi-rSNBO9IE5N05hqflZRfBJa-";
		// FCM connection server 에 푸시 전송 요청
		// rest api 양식
		/*
			GET/POST https://fcm.googleapis.com/fcm/send
			Content-Type:application/json [JSON] OR Content-Type:application/x-www-form-urlencoded;charset=utf-8 [일반텍스트]
			Authorization:key={serverkey}
		 */
		StringBuffer rtnData = new StringBuffer();
		DataMap mesg = new DataMap();
		mesg.put("title"	, this.msgTitle);
		mesg.put("msg"		, this.msgCont);
		mesg.put("url"		, this.msgUrl);
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("to", "/topics/project");
		//jsonObj.put("to", "eAi6WeTdYOE:APA91bFNEI3rNk51eY-OcaupwXscJs_0ZjELbrczUBGeOElYUziGPM7UQkaegvDMLSmN6Zq34CY-OMMes8c0fbrvKRCuGlFaKTOWki6tMdDQ7AL_fQTHCk5sfxswoSSWfikCw2SM2qXV");
		jsonObj.put("data", mesg);
		String jsonss = jsonObj.toString();
		System.out.println(jsonss);
		
		try{
			URL url = new URL("https://fcm.googleapis.com/fcm/send");
			URLConnection httpConnection = url.openConnection();
			
			String auth= "key=" + serverKey;
			httpConnection.setRequestProperty ("Authorization", auth);
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setUseCaches(false);
			httpConnection.setDoOutput(true);
			
			// 파라미터 설정
			OutputStream out = httpConnection.getOutputStream();
			out.write(jsonss.getBytes());
			out.flush();
			out.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
			
			String line = null;
			while ((line = in.readLine()) != null) {
				rtnData.append(line);
			}
			in.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(rtnData.toString());
	}
	
}

