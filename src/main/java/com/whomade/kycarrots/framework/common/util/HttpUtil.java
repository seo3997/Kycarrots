package com.whomade.kycarrots.framework.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.whomade.kycarrots.framework.common.object.DataMap;

public class HttpUtil {
	
	
	/*
	* url 요청결과를 string 으로 변환
	*/
	public static String getHttpRtnData(String urlData, String encodeType){
		StringBuffer rtnData = new StringBuffer();
		
		try{
			URL url = new URL(urlData);
			URLConnection conn = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encodeType));
			String line = null;
			
			while ((line = in.readLine()) != null) {
				rtnData.append(line);
			}
			in.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return rtnData.toString();
	}
	
	//String 값을 Map 형태로 변환
	public static Map jsonToMap(String str){
		 Map states = JSONObject.fromObject(str);
		 return states;
	}

	public static Map getXmlToMap(String url, String encodeType) {
		Map<String, Object> jsonInMap = null;
		String xmlData = getHttpRtnData(url, encodeType);
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(xmlData);

		ObjectMapper mapper = new ObjectMapper();

		try {
			jsonInMap = mapper.readValue(json.toString(), new TypeReference<Map<String, Object>>() {});
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
		return jsonInMap;
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : mapToQueryString
	 * 2. ClassName  : HttpUtil
	 * 3. Comment   : dataMap 형태를 queryString형태로 변경
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 4. 19. 오전 9:24:50
	 * </PRE>
	 *   @return String
	 *   @param map
	 *   @return
	 */
	public static String mapToQueryString(DataMap map){
		String qStr = "";
		
		Set key = map.keySet();
		
		for(Iterator iterator = key.iterator(); iterator.hasNext();){
			String keyName = (String) iterator.next();
			Object valueName = (Object) map.get(keyName);
			
			// 값이 리스트 형태인경우
			if(valueName.getClass().getName().equals(ArrayList.class.getName())){
				List valueList = (List)valueName;
				for(int i = 0; i < valueList.size(); i++){
					qStr += keyName + "=" + valueList.get(i) + "&";
				}
			}
			else {
				qStr += keyName + "=" + valueName + "&";
			}
		}
		
		// 맨마지막 & 은 자르고 넘겨줌
		if(qStr.length() > 0){
			qStr = qStr.substring(0, qStr.length() - 1);
		}
		
		return qStr;
	}
}
