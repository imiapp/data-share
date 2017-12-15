package com.vport.open.datashare.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vport.open.datashare.constants.CommonConstants;
import com.vport.open.datashare.constants.IMIConfiguration;
import com.vport.open.datashare.constants.KSConstant;
import com.vport.open.datashare.utils.TimestampUtils;

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
	// keystore解密密码
	public final String imiKsPass = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
	
	/**
	 * 测试上链数据及相关接口参数
	 */
	// 上链原始数据
	public String rawData = "{\"claim\":{\"type\":\"中华人民共和国居民身份证\",\"assertion\":{\"name\":\"向南\",\"sex\":\"男\",\"ethnicity\":\"汉族\",\"address\":\"北京朝阳酒仙桥\",\"cin\":\"43072119801024008x\",\"doi\":\"2015-02-15\",\"doe\":\"2035-02-15\",\"authority\":\"北京市公安局\",\"image\":{\"type\":\"image/Jpeg\",\"data\":\"/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEwKDcpLDAxNDQ0HyWY4dh6GilyRfQd2f/9k=\"}},\"recipient\":{\"vportId\":\"0x3f182ebdbfec6af397f59ab1d125ff3e90dc6318\"}}}";
	// 数据模版类型编号
	public String typeCode = "10100000004";
	// 上链数据所属用户身份证号
	public String subjectCid = "43072119801024008x";
	// 上链数据所属用户身份证姓名
	public String subjectName = "向南";
	// 查询上链数据条件：截止时间
	public String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	// 查询上链数据条件：数据条数
	public int num = 10;
	
	/**
	 * 测试链上交易、块信息查询接口参数
	 */
	// 交易Hash值
	public String transactionHash = "0xc60ec052a6fe245ff9a361755814080c8196aecbdc0e218da064b8739ece71fe";
	// 块Hash值
	public String blockHash = "0x5caf54c240d5dc397fb4f9a46ef11976b85170f96cd4a516d9a98152600c1945";
	// 块编号
	public long blockNumber = 8665;
	
	public static final TestHander INSTANCE = new TestHander();

	private TestHander() {
		loadConfig();
		
		JSONObject rawDataJson = JSON.parseObject(rawData);
		JSONObject issuer = new JSONObject();
        issuer.put("name", CommonConstants.imiName);
        issuer.put("hashed", false);
        issuer.put("publicKey", KSConstant.publicKey);
        issuer.put("vportId", CommonConstants.imiVportId);

        rawDataJson.put("issuer", issuer);
        rawDataJson.put("type", "CitizenIdentityClaim");
        rawDataJson.put("iat", TimestampUtils.currentTimeNanos());
        rawDataJson.put("charset", "utf-8");
        rawDataJson.put("@context", "http://www.blockcerts.org/schema/1.2/context.json");
        
        rawData = rawDataJson.toJSONString();
	}
	
	/**
	 * @Title: loadConfig
	 * @Description: 加载SDK配置信息
	 */
	private void loadConfig() {
		try {
			IMIConfiguration.initConfigPath(imiConfigPath, imiKsPath, imiKsPass);
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
