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
	// keystore解密密码
	public final String imiKsPass = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
	
	/**
	 * 测试上链数据及相关接口参数
	 */
	// 上链原始数据
	public String rawData = "{\"idCard\":\"43072119801024008x\",\"gender\":\"男\",\"idCardStartTime\":\"2015-02-15\",\"idCardEndTime\":\"2035-02-15\",\"idCardName\":\"向南\",\"idCardAddr\":\"北京朝阳酒仙桥\",\"idCardOrganization\":\"北京市公安局\",\"idCardNation\":\"汉族\",\"idCardHeadImg\":\"data:image/Jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAB+AGYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD36iiigAoorI8ReI7Hw1pj3l6/tHGPvO3oBQG5rMwVSzEADqSa5zxB440Xw7DunuVmlOMQwsGb6+wrxTxR4/1fxFM6tM1vZkYFvE3H4nv/AC9q4uUSbQ7dxxSuaqn3PeoPjHo7yqJbW5WNv4gAdtdvpWv6XrUCzWF5FKD/AA7sMD6Edc18mxXBUdTmr1rfSwSrLDK8ci8q6MVI+hFA3BH1tRXjvg74qTRzRWGukPExwLsnBX03e3v9Pc17BHIksayRsGRhkEd6Zk00OooooEFFFFABRRRQBU1PUINK0y4vrhtsUCF2P0r5t8T+IrzxJqT3l1J8vSOMH5UX0H+Nej/GbXzb2dto0MgDTHzZRnnaOn68/hXk2l2T6ncBVH7tTg+9RN2OilHqRQ2zzDaiZz3rRk0K6lslPlHKjiu20vQIbcLwMiukiskKAbRj0rHnZvyo8NbTLiHiWJgfpTBCUbGOle43OhWtyCHjHPfFcV4l8Hm0jNxaD5e4q4yfUlxRw4PYV6h8NfHMthcQ6LqEu60kbbDI7f6s9h9Ow/L6eaGLawGOe9OTG/B6itU7mckrWZ9ZgggEHIPQ0VzfgXWTrXhe2mkcNPGPLk55yPWukpnM9AooooAKKKhu5vs9nPNkApGzAn2FAHzf8UNVOqeNL0q6PHCRDGU9B1/HOR+FXPCFsIrRX9cVw1/dvd6rNMxy80zPjHGSc/1r0/Src2djEiryFArCd+p2wtbQ6K3BBFbNsuVzXLql4fniucf7Jwa0tPvblRtmcE+1ZqxbNxlwar3UIuIGjf7p61HNdMIyVPNZbzXUr83TRoT0GKpO5LPO/GGjzaVe/aIh/ozE5I7GuXN0DICD1617Hq2njUNNe2lbzUYdW9a8Vv7KXT72S3kBBQ4HvWkZdCHHqezfBbVy02oaY7oFIE0a/wATHofwHH517BXzV8LtUOn+MrElkWObMLluwI/xAr6VrU5prUKKKKCQrC8aSGLwXq7g4K2zHNbtc947GfAutDOP9Fega3PlnTYxcazaof74/mK9maB2jxHwccV4/onGvWp/2gf1r26yw+3NYVDrpo5i90W7u4du5vOWTeGLdR6e1bun2s0UKLI7My4GWOSa6DyEZc1VkZVuAi9ahbWNGSSwnyciuR1bT9TubpGgmkWMHBUOVAGetdsuAuG60eTG/NCSQrGFZwXaSSK5Yx5+Uk5xXH/EDRwbc38agOpAY+oJr1BlCxEVw/j6UR+HLkE4LYA+uaa3BrQ858MTCDXtPkLBVW4jJJOMDcM19aqwdAynKsMg+1fHenOVnRh1BBBr69sDnT7Y+sS/yFdCOWp0LFFFFMyCuU+JF59j8CakdobzUEPPbccZrq680+NU7ReFbVAxAkuMEA9cDNDHFXZ4FaXP2XUYJyOEcZ+mea9p0i7Se0imX7rqCK8NkJEgr1DwVfi40hYi3zRHaR6d6wqbXOqEtbHercEx4FZ07zLch1QMc+vQUNK6QMyDJArOh1x0fDxksODwaxTub8rZuNeXDlV8hSPXJzU0ckkZw386yk8QKoz5LAf7pqeK9a7beEdV7ZHWnew3Fo1TKCleafEy822cVuD9984+ld7JLsQ57V494/vxe69Hbo2REnPsSc/4VVPVmc3oYtouNp9a+rfCd0974U0yeV90jwKWPvivli3UBE9K+jfha7P4Ftd38Mkij6Bq6Fuc9T4bnZ0UUVRgFeM/HPUPm06wEgIUGVo88g5wD+Wa9mr5i+KWtDVvGl2UcNHBiFcDHSky6a1OLmYFx+lafhrW30fUjk5hmYBx6e9Y5+Y89qSBTJdRgcksOPxqJK8TaPxHu1peLND8rZzTWtGaTeg+b1rLsVeO2jZOeOa1rfUAvEmQa49jsi2TR20rcyevSrqsIo/QCq51OAL1JP0rPub2WdiIgQlJu4733KniXxJBpVk7M+ZCPlX1NePefJe38t3KcySEljW546ZjqMaMScA/zrn7X5FA9TXVRWlzlqN81jXUbYYvfmvcvgzdiTRr+2abc6ThljJztUjrjsM5/WvCZXISEf7NenfBzUvJ8QyWzOqrcw456llOQB+daLciaXKe60UUVZzGL4s1mPQPDF9qDjPlxEKPViOBXyRcXDXEzysSXclmLHJJ+ter/FvxmmtTpothITbwPmZlB+Zu3PQivLjZkcniobOulQm1cqopbHoa0dOtAdStsj5ScH61FHEEwOuKu2ztHIjDqCDUOWh2ww3u3e56hZoVi29qtrao7Ybmquk3CXVjHIDlyOa0kB3A1yyRm7xdhBp8CdEA98UjxIiHFWi2eKqXkgjjOTQkFzzDxjD5upZI4Ga5d8Rygeldb4imE18QozjOa5a6iy+4etdMHZWKnR925JcyELFj+7W74M1ptJ1+xutwURSjcSM/KeD+hrnpuYkPoKZaTmKYHPfNXc5GujPs+KVJ4UljOUcAg0VwXw18Ux6l4cjtpZFElqoQA8fL2oquZHO6bueCiMIxNNf94xB6CrE4AGQMc1ARgNjuazZ9NGmoqyKzAA5qWF+aieq09y0CZUA1DJnJRV2d/wCF71490R+6CK76GLzEDeteJ6FrM9pKsh/eBuoJ7V7Lod8buxhlC7Q65A9Kho82rJSd0WpIdilvSud1S5yr5OEUEk1098xEZFcB4tneCwZUOAzBT9KLCpR5mcjLJ58jynqzGqU0eeKga8kiuxEPumrrjnNXE9CylG3YoPGdmyqUkTxn2rVI3bs9qYsau2D0xWpyTop7FrQtfm0qN/KkZGIwcdxRVUWsX9wUUGf1WR//2Q=\"}";
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

	private TestHander() {}
	
	/**
	 * @Title: loadConfig
	 * @Description: 加载SDK配置信息
	 */
	public void loadConfig() {
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
