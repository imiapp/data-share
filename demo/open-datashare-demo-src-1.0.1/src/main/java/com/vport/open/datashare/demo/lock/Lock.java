package com.vport.open.datashare.demo.lock;

import java.io.IOException;

import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;

/**
 * 获取锁
 * ClassName: Lock 
 * @Description: TODO
 * @author yue.li3
 * @date 2016年3月24日
 */
public class Lock {
	
	public static Lease getLock(){
		InterProcessSemaphoreV2 process = ZookeeperLock.getSharedSemaphore("/lock_datashare");
		Lease lock=null ;
		try {
			lock = process.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lock;
	}
	
	public static Lease getLock(String LockName){
		InterProcessSemaphoreV2 process = ZookeeperLock.getSharedSemaphore(LockName);
		Lease lock=null ;
		try {
			lock = process.acquire();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lock;
	}
	
	public static void unLock(Lease lock){
		try {
			lock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
