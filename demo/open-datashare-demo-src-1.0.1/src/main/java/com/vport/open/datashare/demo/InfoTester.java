package com.vport.open.datashare.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.vport.open.datashare.constants.CommonConstants;
import com.vport.open.datashare.exceptions.IMIRpcException;
import com.vport.open.datashare.router.DataSharingRouter;
import com.vport.open.datashare.vo.BlockInfoVO;
import com.vport.open.datashare.vo.GetDataParams;
import com.vport.open.datashare.vo.GetDataVO;
import com.vport.open.datashare.vo.QueryBlockInfoParams;
import com.vport.open.datashare.vo.QueryTransactionInfoParams;
import com.vport.open.datashare.vo.TransactionInfoVO;
import com.vport.open.datashare.vo.VerifyDataParams;
import com.vport.open.datashare.vo.VerifyDataVO;

/**
 * 
 * @ClassName: QueryTester
 * @Description: 数据查询及验证测试类
 * @author S.J.
 * @date 2017年11月30日 下午6:45:17
 *
 */
public class InfoTester {

	private static void queryTransactionInfo(final TestHander hander) {
		String log = null;
		List<String> logList = new ArrayList<String>();
		
		try {
			// 通过交易Hash值查询交易信息
			QueryTransactionInfoParams params = new QueryTransactionInfoParams();
			params.setTransactionHash(hander.transactionHash);
			
			TransactionInfoVO info = DataSharingRouter.queryTransactionInfo(params);
			
			log = "===【交易信息查询】TransactionInfoVO=[" + JSON.toJSONString(info) + "]";
			logList.add(log);
			System.out.println(log);
		} catch (IMIRpcException e) {
			log = "===【交易信息查询】【ERROR】: " + e.getMessage();
			logList.add(log);
			System.out.println(log);
		} catch (Exception e) {
			log = "===【交易信息查询】【ERROR】: " + e.getMessage();
			logList.add(log);
			System.out.println(log);
		}
		
		//存储日志
		hander.saveLogs(logList, "datashare-sdk-queryTransactionInfo-test.log");
	}
	
	private static void queryBlockInfo(final TestHander hander) {
		String log = null;
		List<String> logList = new ArrayList<String>();
		
		try {
			// 方式一：通过块Hash值查询块信息
			QueryBlockInfoParams params = new QueryBlockInfoParams();
			params.setType("blockhash");
			params.setValue(hander.blockHash);
			
			BlockInfoVO info = DataSharingRouter.queryBlockInfo(params);
			
			log = "===【块信息查询-blockhash】TransactionInfoVO=[" + JSON.toJSONString(info) + "]";
			logList.add(log);
			System.out.println(log);
			
			// 方式二：通过块编号查询块信息
			params = new QueryBlockInfoParams();
			params.setType("blocknumber");
			params.setValue(String.valueOf(hander.blockNumber));
			
			info = DataSharingRouter.queryBlockInfo(params);
			
			log = "===【块信息查询-blocknumber】TransactionInfoVO=[" + JSON.toJSONString(info) + "]";
			logList.add(log);
			System.out.println(log);
			
			// 方式三：通过交易Hash值查询块信息
			params = new QueryBlockInfoParams();
			params.setType("transactionhash");
			params.setValue(hander.transactionHash);
			
			info = DataSharingRouter.queryBlockInfo(params);
			
			log = "===【块信息查询-transactionhash】TransactionInfoVO=[" + JSON.toJSONString(info) + "]";
			logList.add(log);
			System.out.println(log);
			
		} catch (IMIRpcException e) {
			log = "===【交易信息查询】【ERROR】: " + e.getMessage();
			logList.add(log);
			System.out.println(log);
		} catch (Exception e) {
			log = "===【交易信息查询】【ERROR】: " + e.getMessage();
			logList.add(log);
			System.out.println(log);
		}
		
		//存储日志
		hander.saveLogs(logList, "datashare-sdk-queryBlockInfo-test.log");
	}
	
//	@Test
	public void tester() {

		final TestHander hander = TestHander.INSTANCE;

		queryTransactionInfo(hander);
		queryBlockInfo(hander);
	}
	
	public static void main(String[] args) {

		new InfoTester().tester();
	}
}
