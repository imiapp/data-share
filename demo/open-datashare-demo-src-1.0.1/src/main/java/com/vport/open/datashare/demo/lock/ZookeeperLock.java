package com.vport.open.datashare.demo.lock;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;


/**
 * 获取锁的方式
 * ClassName: ZookeeperLock 
 * @Description: TODO
 * @author yue.li3
 * @date 2016年3月24日
 */
public class ZookeeperLock {
	
	/**
	 * 多信号量锁
	 * @param zkPath
	 * @param count
	 * @return
	 */
	public static InterProcessSemaphoreV2 getSharedSemaphore(String zkPath, int count){
		return new InterProcessSemaphoreV2(ZookeeperClient.getClient(), zkPath, count);
	}
	
	/**
	 * 生成一个只有一个许可的信号量
	 * @param zkPath
	 * @return
	 */
	public static InterProcessSemaphoreV2 getSharedSemaphore(String zkPath){
		return getSharedSemaphore(zkPath, 1);
	}
	
	/**
	 * 单一锁
	 * @param zkPath
	 * @return
	 */
	public static InterProcessMutex getInterProcessMutex(String zkPath){
		return new InterProcessMutex(ZookeeperClient.getClient(), zkPath);
	}
	
}
