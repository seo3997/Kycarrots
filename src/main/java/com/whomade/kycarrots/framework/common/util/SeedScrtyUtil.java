package com.whomade.kycarrots.framework.common.util;

import com.whomade.kycarrots.framework.common.constant.Const;
import com.whomade.kycarrots.framework.security.seed.KISA_SEED_ECB;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : SeedScrtyUtil.java
 * 3. Package  : egovframework.framework.common.util
 * 4. Comment  : SEED 알고리즘 암복호화
 * 5. 작성자   : 박재현
 * 6. 작성일   : 2014. 10. 17. 오전 9:41:03
 * 7. 2017.04.07 소스 수정 SooHyun.Seo
 * > 기존의 소스가 암호화 복호화 할경우 1개의 문자를 암호화할경우 빈칸이 들어가는 오류 발생
 * > KISA에서 새로 seed 암호화 소스 받아서 SEED-ECB 방식으로 변경
 * </PRE>
 */ 
public class SeedScrtyUtil {
	// 반드시 16자리로 맞춤
	private static String key = Const.seedKey;

	/**
	 * <PRE>
	 * 1. MethodName : encryptText
	 * 2. ClassName  : SeedScrtyUtil
	 * 3. Comment   : 암호화
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 4. 7. 오전 11:57:55
	 * </PRE>
	 *   @return String
	 *   @param orgnStr
	 *   @return
	 */
	public static String encryptText(String orgnStr) {
		
		KISA_SEED_ECB seed = new KISA_SEED_ECB();
		String encryptStr = "";
		
		try{
			encryptStr = Base64Utils.base64Encode(seed.SEED_ECB_Encrypt(key.getBytes(), orgnStr.getBytes(), 0, orgnStr.getBytes().length));
		}
		catch(Exception e){
			e.printStackTrace();
		}	
		
		return encryptStr;
		
	}
	
	/**
	 * <PRE>
	 * 1. MethodName : decryptText
	 * 2. ClassName  : SeedScrtyUtil
	 * 3. Comment   : 복호화
	 * 4. 작성자    : SooHyun.Seo
	 * 5. 작성일    : 2017. 4. 7. 오전 11:58:01
	 * </PRE>
	 *   @return String
	 *   @param encryptStr
	 *   @return
	 */
	public static String decryptText(String encryptStr){
		
		KISA_SEED_ECB seed = new KISA_SEED_ECB();
		String decryptStr = "";
		
		try{
			if(!encryptStr.equals("")){
				byte[] encryptbytes = Base64Utils.base64Decode(encryptStr);
				decryptStr = new String(seed.SEED_ECB_Decrypt(key.getBytes(), encryptbytes, 0, encryptbytes.length));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return decryptStr;
	}
	
	
	
	
	/**
	 * <PRE>
	 * 1. MethodName : encryptText
	 * 2. ClassName  : SeedScrtyUtil
	 * 3. Comment   : 암호화
	 * 4. 작성자    : 박재현
	 * 5. 작성일    : 2014. 10. 17. 오전 9:41:16
	 * </PRE>
	 *   @return String
	 *   @param orgnStr
	 *   @return
	 */
//	public static String encryptText(String orgnStr) {
//		
//		SeedCipher seed = new SeedCipher();
//		String encryptStr = "";
//		
//		try{
//		
//			encryptStr = Base64Utils.base64Encode(seed.encrypt(orgnStr, key.getBytes(), "UTF-8"));
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}	
//		
//		return encryptStr;
//		
//	}
	
	
	/**
	 * <PRE>
	 * 1. MethodName : decryptText
	 * 2. ClassName  : SeedScrtyUtil
	 * 3. Comment   : 복호화
	 * 4. 작성자    : 박재현
	 * 5. 작성일    : 2014. 10. 17. 오전 9:41:22
	 * </PRE>
	 *   @return String
	 *   @param encryptStr
	 *   @return
	 */
//	public static String decryptText(String encryptStr){		
//		
//		SeedCipher seed = new SeedCipher();
//		String decryptStr = "";
//		
//		try{
//			byte[] encryptbytes = Base64Utils.base64Decode(encryptStr);		
//			decryptStr = seed.decryptAsString(encryptbytes, key.getBytes(), "UTF-8");
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return decryptStr;		
//	}

}
