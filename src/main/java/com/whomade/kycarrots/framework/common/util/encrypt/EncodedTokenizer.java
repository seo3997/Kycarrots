package com.whomade.kycarrots.framework.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.whomade.kycarrots.entity.member.OpUserVO;
import com.whomade.kycarrots.framework.common.object.DataMap;
import com.whomade.kycarrots.service.member.OpUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
//import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class EncodedTokenizer {
	@Autowired private  OpUserService opUserService;

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
			
			String encodedemail = codec.encode(aes256.aesEncode(member.getUserId()));
			String role = "<ROLE_PUB>";
			String  memberCode = member.getMemberCode();
			if( memberCode.equals("ROLE_ADMIN")) role = "<ROLE_ADMIN>";
			else if( memberCode.equals("ROLE_PUB") ) role = "<ROLE_PUB>";
			else if(  memberCode.equals("ROLE_PROJ") ) role = "<ROLE_PROJ>";
			else if(  memberCode.equals("ROLE_SELL") ) role = "<ROLE_SELL>";

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

	public OpUserVO getMember(String token){
		String role = "ROLE_SELL";
		if( token.isEmpty()) return null;
//    	if( url==null ) return null;
		try {

			AES256Cipher aes256 = new AES256Cipher(key);
			URLCodec codec = new URLCodec();
			String token1st = aes256.aesDecode(codec.decode(token));
			if ( token1st.contains("<ROLE_ADMIN>") ){
				email = aes256.aesDecode(codec.decode(token1st.split("<ROLE_ADMIN>")[0]));
				role = "ROLE_ADMIN";
			}else if ( token1st.contains("<ROLE_PUB>") ){
				email = aes256.aesDecode(codec.decode(token1st.split("<ROLE_PUB>")[0]));
				role = "ROLE_PUB";
			}else if ( token1st.contains("<ROLE_PROJ>") ){
				email = aes256.aesDecode(codec.decode(token1st.split("<ROLE_PROJ>")[0]));
				role = "ROLE_PROJ";
			}else
				email = aes256.aesDecode(codec.decode(token1st.split("<ROLE_SELL>")[0]));

		}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| DecoderException e) {
			e.printStackTrace();
			return null;
		}

		DataMap param = new DataMap();
		param.put("userId", email);
		OpUserVO mem = opUserService.seelectUser(param);
		return mem;
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