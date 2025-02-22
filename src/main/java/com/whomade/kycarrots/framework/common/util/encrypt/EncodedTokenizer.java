package com.whomade.kycarrots.framework.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.whomade.kycarrots.entity.member.OpUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class EncodedTokenizer {


	// 추후에 키값은 고정값이 아닌 기준 유동적으로 변경. 
	private AES256Cipher aes256;
	private String key = "cashcukCashcukApplication";
	private String email;
	private String token;
	private String code;
        


    public String getToken(OpUserVO member) throws DecoderException{
		try {
			aes256 = new AES256Cipher(key);
			URLCodec codec = new URLCodec();
			
			String encodedemail = codec.encode(aes256.aesEncode(member.getEmail()));
			String role = "<ROLE_NONE>";
			//Long roleid = member.getRoleid();
			Long roleid = 3L;
			if( roleid == 1L ) role = "<ROLE_ADMN>";
			else if( roleid == 2L ) role = "<ROLE_BIZ>"; 
			else if( roleid == 3L ) role = "<ROLE_USER>"; 
			else if( roleid == 4L ) role = "<ROLE_NONE>"; 

			String tokensource = codec.encode(aes256.aesEncode(member.getPassword()));
		    token = codec.encode(aes256.aesEncode(encodedemail+role+tokensource));
		}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			log.warn("getToken Exception");
			return null;
		}
		return token;
	}
    
    public String encode(String rare){
    	AES256Cipher aes256;
		try {
			aes256 = new AES256Cipher(key);
			URLCodec codec = new URLCodec();
			code = codec.encode(aes256.aesEncode(rare));
		}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			log.warn("encode Exception");
			return null;
		}
		return code;
	}
    
    public String decode(String encodedStr){
    	AES256Cipher aes256;
    	String decodedStr;
		try {
			aes256 = new AES256Cipher(key);
			URLCodec codec = new URLCodec();
			decodedStr = aes256.aesDecode(codec.decode(encodedStr));
		}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			log.warn("decode Exception");
			return null;
		}
		return decodedStr;
	}

    public boolean matches(String raw, String encodedStr){
    	return false;
   	}
    
    public static void main(String[] args) throws DecoderException {
    	AES256Cipher aes256;
    	String decodedStr = null;
    	String encPwd = "OrSD96XRfYtjmv2G%2BqJnGQ%3D%3D";
    	
		try {
			aes256 = new AES256Cipher("cashcukCashcukApplication");
			URLCodec codec = new URLCodec();
			decodedStr = aes256.aesDecode(codec.decode(encPwd));
		}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.warn("decode Exception");
		}
		
		
		System.out.println(decodedStr);
	}
}