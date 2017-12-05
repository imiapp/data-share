package com.vport.open.datashare.demo;

import java.util.ArrayList;
import java.util.List;

import com.vport.open.datashare.constants.CommonConstants;
import com.vport.open.datashare.exceptions.IMIRpcException;
import com.vport.open.datashare.router.DataSharingRouter;
import com.vport.open.datashare.vo.GetDataParams;
import com.vport.open.datashare.vo.GetDataVO;
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
public class QueryTester {

	private static void queryData(final TestHander hander) {
		String log = null;
		final List<String> logList = new ArrayList<String>();

		// 通过存储条件查询多数据集合
		GetDataParams params = new GetDataParams();
		params.setTypeCode(hander.typeCode); // 数据模版编号
		params.setSubjectCid(hander.subjectCid); // 存储数据所属用户身份证号
		params.setSubjectName(hander.subjectName); // 存储数据所属用户身份证姓名
		params.setIssuerVportId(CommonConstants.imiVportId); // 第三方业务系统数字身份号(来自配置文件)
		params.setEndTime(hander.endTime);
		params.setNum(hander.num);

		List<GetDataVO> dataList = null;
		try {
			dataList = DataSharingRouter.getData(params);
		} catch (IMIRpcException e) {
			log = "===【查询】【ERROR】: " + e.getMessage();
			logList.add(log);
			System.out.println(log);
		}
		
		if (null != dataList && !dataList.isEmpty()) {
			for (int i = 0; i < dataList.size(); i++) {
				
				GetDataVO getVo = dataList.get(i);
				
				try {
					// 通过交易Hash值查询验证数据
					VerifyDataParams verifyParams = new VerifyDataParams();
					verifyParams.setTransactionHash(getVo.getTransactionHash());
					
					VerifyDataVO verifyVo = DataSharingRouter.verifyData(verifyParams);
					
					boolean verified = getVo.getData().equals(verifyVo.getData());
					
					log = "===【验证】[" + i + "]: verified=[" + verified + "], GetData=[" + getVo.getData() + "], VerifyData=[" + verifyVo.getData() + "]";
					logList.add(log);
					System.out.println(log);
				} catch (IMIRpcException e) {
					log = "===【验证】【ERROR】[" + i + "]: " + e.getMessage();
					logList.add(log);
					System.out.println(log);
				}
			}
		}

		//存储日志
		hander.saveLogs(logList, "datashare-sdk-get&verify-test.log");
	}
	
	public static void main(String[] args) {

		final TestHander hander = TestHander.INSTANCE;
		hander.loadConfig();

		queryData(hander);
	}
}
