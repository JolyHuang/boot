package com.sharingif.cube.spring.boot.core;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sharingif.cube.core.util.Charset;

/**
 * 加载cube系统文件
 * 2017年5月6日 下午10:48:33
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public final class CubeConfigure {
	
	private static final Logger logger = LoggerFactory.getLogger(CubeConfigure.class);
	
	public static final String DEFAULT_ENCODING;
	public static final String  APP_COMPONENTSCAN_BASEPACKAGES;
	
	static{
		ResourceBundle resourceBundle = null;
		try {
			resourceBundle = ResourceBundle.getBundle("config.app.CubeConfigure");
		} catch (Exception e) {
			logger.error("config.app.CubeConfigure file not found");
			throw e;
		}
		
		if(resourceBundle == null) {
			DEFAULT_ENCODING = Charset.UTF8.toString();
		} else {
			DEFAULT_ENCODING = resourceBundle.getString("app.properties.default.encoding");
		}
		
		try {
			APP_COMPONENTSCAN_BASEPACKAGES = resourceBundle.getString("app.componentscan.basepackages");
		} catch (Exception e) {
			logger.error("Property app.componentscan.basepackages was not found in file config.app.CubeConfigure");
			throw e;
		}
		
	}
	
}
