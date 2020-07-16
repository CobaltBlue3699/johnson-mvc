package com.johnson.core.mvc.helper;

import java.util.Properties;

import com.johnson.core.mvc.ConfigConstant;
import com.johnson.core.utils.PropsUtils;

/**
 * 屬性文件助手類 該類用於獲取配置文件中的配置, 其中JSP和靜態資源設置了默認路徑
 */
public final class ConfigHelper {

	private static final Properties CONFIG_PROPS = PropsUtils.loadProps(ConfigConstant.CONFIG_FILE);

	public static String getJdbcDriver() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
	}

	public static String getJdbcUrl() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}

	public static String getJdbcUsername() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}

	public static String getJdbcPassword() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}

	public static String getAppBasePackage() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}

	/**
	 * 獲取應用 前端 路徑
	 */
	public static String getAppFrontEndPath() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_FRONT_END_PATH, "/dist/");
	}

	/**
	 * 獲取應用靜態資源路徑
	 */
	public static String getAppAssetPath() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/assets/");
	}
	
	/**
	 * 獲取應用暫存資源檔案路徑
	 */
	public static String getAppTempPath() {
		return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/temp/");
	}

	/**
	 * 根據屬性名獲取 String 類型的屬性值
	 */
	public static String getString(String key) {
		return PropsUtils.getString(CONFIG_PROPS, key);
	}

	/**
	 * 根據屬性名獲取 int 類型的屬性值
	 */
	public static int getInt(String key) {
		return PropsUtils.getInt(CONFIG_PROPS, key);
	}

	/**
	 * 根據屬性名獲取 boolean 類型的屬性值
	 */
	public static boolean getBoolean(String key) {
		return PropsUtils.getBoolean(CONFIG_PROPS, key);
	}
}
