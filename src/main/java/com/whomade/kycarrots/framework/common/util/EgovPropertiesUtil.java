package com.whomade.kycarrots.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

@Slf4j
@Component
public class EgovPropertiesUtil {

	private static Properties properties = new Properties();

	static {
		try (BufferedInputStream bis = new BufferedInputStream(EgovPropertiesUtil.class.getClassLoader().getResourceAsStream("globals.properties"))) {
			if (bis != null) {
				properties.load(bis);
			} else {
				log.error("globals.properties 파일을 찾을 수 없습니다.");
			}
		} catch (IOException e) {
			log.error("Unable to load properties file: globals.properties", e);
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static String getPathProperty(String key) {
		String value = getProperty(key);
		return System.getProperty("user.dir") + "/src/main/resources/" + value;
	}

	/**
	 * 주어진 파일에서 인자로 주어진 문자열을 Key 값으로 하는 프로퍼티 값을 반환한다
	 * @param fileName 프로퍼티 파일 이름
	 * @param key 프로퍼티 키 값
	 * @return String 프로퍼티 값
	 */
	public static String getProperty(String fileName, String key) {
		try (FileInputStream fis = new FileInputStream(fileName);
			 BufferedInputStream bis = new BufferedInputStream(fis)) {
			Properties props = new Properties();
			props.load(bis);
			return props.getProperty(key);
		} catch (FileNotFoundException fne) {
			return "EXCEPTION(FNFE) OCCURRED";
		} catch (IOException ioe) {
			return "EXCEPTION(IOE) OCCURRED";
		}
	}
}
