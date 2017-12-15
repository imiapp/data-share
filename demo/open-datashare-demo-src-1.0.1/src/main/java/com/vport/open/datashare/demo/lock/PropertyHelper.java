package com.vport.open.datashare.demo.lock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * ClassName: PropertyHelper
 * 
 * @Description: 加载zookeeper 分布式锁基本配置信息
 * @author yue.li3
 * @date 2016年3月23日
 */

public class PropertyHelper {

	private static final String ZOOKEEPER_FILE = "zookeeperlock.properties";

	private static Properties zookeeperConfig = null;

	static {
		if (zookeeperConfig == null) {
			loadZoopeeperConfig();
		}
	}

	private static void loadZoopeeperConfig() {

		try {
			InputStream inStream = PropertyHelper.class.getClassLoader().getResourceAsStream(ZOOKEEPER_FILE);

			zookeeperConfig = new Properties();
			zookeeperConfig.load(inStream);

		} catch (FileNotFoundException e) {
			// 文件不存在
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		
		String value = zookeeperConfig.getProperty(key);

		return StringUtils.isBlank(value) ? "" : value.trim();
	}

	public static String getDefaultValue(String key, String defaultValue) {

		String value = zookeeperConfig.getProperty(key);

		return StringUtils.isBlank(value) ? defaultValue : value.trim();
	}

}
