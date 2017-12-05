package com.vport.open.datashare.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.vport.open.datashare.constants.IMIConfiguration;

/**
 * 
 * @ClassName: TestHander
 * @Description: SDK测试数据处理类
 * @author S.J.
 * @date 2017年11月30日 下午6:44:40
 *
 */
public class TestHander {
	
	/**
	 * 数据共享平台接口SDK配置
	 */
	public final String LOCK_DATASHARE_STOREDATA = "/lock_datashare_storedata";
	
	/**
	 * 数据共享平台接口SDK配置
	 */
	// 接口参数配置文件
	public final String imiConfigPath = "imi/imi-config.properties";
	// 私钥存储keystore文件
	public final String imiKsPath = "imi/imi-ks";
	
	/**
	 * 测试上链数据及相关接口参数
	 */
	// 上链原始数据
	public String rawData = "{\"idCard\":\"43072119801024008x\",\"gender\":\"男\",\"idCardStartTime\":\"2015-02-15\",\"idCardEndTime\":\"2035-02-15\",\"idCardName\":\"向南\",\"idCardAddr\":\"北京朝阳酒仙桥\",\"idCardOrganization\":\"北京市公安局\",\"idCardNation\":\"汉族\",\"idCardHeadImg\":\"data: image/Jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEwKDcpLDAxNDQ0HyWY4dh6GilyRfQd2f/9k=...\"}";
	// 数据模版类型编号
	public String typeCode = "10001";
	// 上链数据所属用户身份证号
	public String subjectCid = "43072119801024008x";
	// 上链数据所属用户身份证姓名
	public String subjectName = "向南";
	// 查询上链数据条件：截止时间
	public String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	// 查询上链数据条件：数据条数
	public int num = 10;
	
	public static final TestHander INSTANCE = new TestHander();

	private TestHander() {}
	
	/**
	 * @Title: loadConfig
	 * @Description: 加载SDK配置信息
	 */
	public void loadConfig() {
		try {
			IMIConfiguration.initConfigPath(imiConfigPath, imiKsPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: saveLogs
	 * @Description: 测试日子信息输出到文件
	 * @param logList
	 * @param logName
	 */
	public void saveLogs(List<String> logList, String logName) {

		String logDir = "logs";
		
		FileWriter writer = null;
		try {
			File dir = new File(logDir);
			if (!dir.exists() || !dir.isDirectory()) {
				dir.mkdirs();
			}
			
			File file = new File(dir, logName);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			writer = new FileWriter(file);

			for (String log : logList) {
				writer.write(log + "\n");
			}
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
