package com.vport.open.datashare.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.curator.framework.recipes.locks.Lease;
import org.junit.Test;

import com.vport.open.datashare.constants.CommonConstants;
import com.vport.open.datashare.demo.lock.Lock;
import com.vport.open.datashare.exceptions.IMIRpcException;
import com.vport.open.datashare.router.DataSharingRouter;
import com.vport.open.datashare.vo.StoreDataParams;

/**
 * 
 * @ClassName: ConcurrencyStoreTester
 * @Description: 模拟业务系统并发请求数据上链存储类
 * @author S.J.
 * @date 2017年11月30日 下午6:45:45
 *
 */
public class ConcurrencyStoreTester {
	
	private static void storeData(final TestHander hander) {

		final List<String> logList = new ArrayList<String>();

		// 模拟业务并发请求数
		int count = 5;

		Executor extractExecutor = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			final int j = i;
			extractExecutor.execute(new Runnable() {

				public void run() {
					String log = null;
					Lease lock = null;
					try {
						StoreDataParams params = new StoreDataParams();
						params.setData(hander.rawData);
						params.setTypeCode(hander.typeCode); // 数据模版编号
						params.setSubjectCid(hander.subjectCid); // 存储数据所属用户身份证号
						params.setSubjectName(hander.subjectName); // 存储数据所属用户身份证姓名
						params.setIssuerVportId(CommonConstants.imiVportId); // 第三方业务系统数字身份号
						params.setIsAppend(0); // 0:添加数据，1:更新数据

						// 加锁
						lock = Lock.getLock(hander.LOCK_DATASHARE_STOREDATA);

						long start = System.currentTimeMillis();
						
						String transactionHash = DataSharingRouter.storeData(params);

						long end = System.currentTimeMillis();
						
						log = "===【存储】【SUCCESS】[" + j + "]: transactionHash=[" + transactionHash + "]" + ", 耗时=[" + (end - start) + "]";
						logList.add(log);
						System.out.println(log);
					} catch (IMIRpcException e) {
						log = "===【存储】【ERROR】[" + j + "]: " + e.getMessage();
						logList.add(log);
						System.out.println(log);
					}  catch (Exception e) {
						log = "===【存储】【ERROR】[" + j + "]: " + e.getMessage();
						logList.add(log);
						System.out.println(log);
					} finally {
						// 解锁
						if (lock != null) {
							Lock.unLock(lock);
						}
					}

				}
			});
		}

		// 存储日志
		hander.saveLogs(logList, "datashare-sdk-concurrency-store-test.log");
	}

//	@Test
	public void tester() {

		final TestHander hander = TestHander.INSTANCE;
		hander.loadConfig();

		storeData(hander);
		
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
	
	public static void main(String[] args) {

		new ConcurrencyStoreTester().tester();
	}

}
