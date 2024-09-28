
package com.whomade.kycarrots.common.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whomade.kycarrots.common.service.WebCommonService;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.framework.common.util.EgovMessageSource;
import com.whomade.kycarrots.framework.common.util.RequestUtil;

@Controller
public class SnsController {

	private static Log log = LogFactory.getLog(SnsController.class);

	@Resource(name = "egovMessageSource")
	private EgovMessageSource egovMessageSource;
	
	@Resource(name = "webCommonService")
	private WebCommonService webCommonService;

	@RequestMapping(value = "/sns/kakao.do")
	public String useInfo(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		model.addAttribute("param", param);
		
		return "common/useInfo";
	}
	
	@RequestMapping(value = "/sns/kakaoAjax.do")
	public @ResponseBody void kakaoAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		// 엑세스 토큰을 이용하여 사용자 정보를 획득.
		// rest api 양식
		/*
			GET/POST /v1/user/me HTTP/1.1
			Host: kapi.kakao.com
			Authorization: Bearer {access_token}
			Content-type: application/x-www-form-urlencoded;charset=utf-8
		 */
		StringBuffer rtnData = new StringBuffer();
		
		try{
			URL url = new URL("https://kapi.kakao.com/v1/user/me");
			URLConnection httpConnection = url.openConnection();
			
			String auth= "Bearer " + param.getString("at");
			httpConnection.setRequestProperty ("Authorization", auth);
//			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			httpConnection.setUseCaches(false);
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
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", rtnData.toString());
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);
		
		response.setContentType("application/json; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	@RequestMapping(value = "/sns/faceBookAjax.do")
	public @ResponseBody void faceBookAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		// 엑세스 토큰을 이용하여 사용자 정보를 획득.
		StringBuffer rtnData = new StringBuffer();
		
		try{
			URL url = new URL("https://graph.facebook.com/me?access_token=" + param.getString("at"));
			URLConnection httpConnection = url.openConnection();
			httpConnection.setUseCaches(false);
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
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", rtnData.toString());
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);
		
		response.setContentType("application/json; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	@RequestMapping(value = "/sns/googleAjax.do")
	public @ResponseBody void googleAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		// 엑세스 토큰을 이용하여 사용자 정보를 획득.
		StringBuffer rtnData = new StringBuffer();
		
		try{
			URL url = new URL("https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + param.getString("at"));
			URLConnection httpConnection = url.openConnection();
			httpConnection.setUseCaches(false);
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
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", rtnData.toString());
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);
		
		response.setContentType("application/json; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	@RequestMapping(value = "/sns/twitterAuth.do")
	public String twitterAuth(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		// 엑세스 토큰을 이용하여 사용자 정보를 획득.
		StringBuffer rtnData = new StringBuffer();
		
//		try{
//			URL url = new URL("https://api.twitter.com/oauth/request_token?oauth_callback=http://SooHyun.Seo.test.co.kr/sns/kakao.do");
//			URLConnection httpConnection = url.openConnection();
//			
//			String auth= "OAuth oauth_consumer_key=\"OPRtlA2ssUrpOpW9WNKwSq84K\""
//					+ ", oauth_nonce=\"f899240bec61f824daf87cb47191ce19\""
//					+ ", oauth_signature=\"Q7HdvqvTMEsdFKG6bAOvC%2FvYdEU%3D\""
//					+ ", oauth_signature_method=\"HMAC-SHA1\""
//					+ ", oauth_timestamp=\"1449557448\""
//					+ ", oauth_token=\"1126577574-xsAgVIZk3gtMs0b82Z6nBpqeaooQGRC7hmxFwM9\""
//					+ ", oauth_version=\"1.0\"";
//			httpConnection.setRequestProperty ("Authorization", auth);
//			
//			httpConnection.setUseCaches(false);
//			BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
//			
//			String line = null;
//			while ((line = in.readLine()) != null) {
//				rtnData.append(line);
//			}
//			in.close();
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		System.out.println(rtnData.toString());
				
		model.addAttribute("param", param);
		
		return "common/twitterAuth";
	}
	
	@RequestMapping(value = "/sns/naverAjax.do")
	public @ResponseBody void naverAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

		DataMap param = RequestUtil.getDataMap(request);
		
		// 엑세스 토큰을 이용하여 사용자 정보를 획득.
		StringBuffer rtnData = new StringBuffer();
		
		try{
			URL url = new URL("https://openapi.naver.com/v1/nid/getUserProfile.xml");
			URLConnection httpConnection = url.openConnection();
			String auth= "Bearer " + param.getString("at");
			httpConnection.setRequestProperty("Authorization", auth);
			httpConnection.setUseCaches(false);
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
		
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(rtnData.toString());

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> jsonInMap = null;
		
		try {

			jsonInMap = mapper.readValue(json.toString(), new TypeReference<Map<String, Object>>() {
			});
			List<String> keys = new ArrayList<String>(jsonInMap.keySet());
			
			for (String key : keys) {
				System.out.println(key + ":" + jsonInMap.get(key));
			}

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("resultMap", jsonInMap);
		
		//return 상태
		DataMap resultStats = new DataMap();
		resultStats.put("resultCode", "ok");
		resultStats.put("resultMsg", "");
		resultJSON.put("resultStats", resultStats);
		
		response.setContentType("application/json; charset=utf-8");
		try {
			response.getWriter().write(resultJSON.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}
}
