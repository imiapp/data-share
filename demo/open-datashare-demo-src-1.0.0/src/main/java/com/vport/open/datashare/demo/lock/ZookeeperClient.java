package com.vport.open.datashare.demo.lock;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 
 * ClassName: ZookeeperClient
 * 
 * @Description: 加载zookeeper cluster 信息
 * @author yue.li3
 * @date 2016年3月23日
 */
public class ZookeeperClient {
	// 存放zookeeper 集群
	private static final List<CuratorFramework> clusters = new ArrayList<CuratorFramework>();
	// 集群下标
	private static int clusterIndex = 0;

	// 初始化集群信息
	static {
		try {
			// “;”分隔多个zookeeper集群，“,”分隔集群中多个zookeeper节点
			String zookeeperClusters = PropertyHelper.getValue("zookeeper.clusters");

			if (StringUtils.isEmpty(zookeeperClusters)) {

				throw new Exception("没有获取到zookeeper 节点信息,请查看配置文件key=zookeeper.clusters是否存在");

			}

			for (String cluster : zookeeperClusters.split("\\;")) {

				cluster = cluster == null ? "" : cluster.trim();

				if (cluster.length() <= 0) {
					continue;
				}
				// 重连间隔时间
				int retrySleep = Integer
						.parseInt(PropertyHelper.getDefaultValue("zookeeper.connectRetry.sleepMs", "10000"));
				// 重连次数
				int retryTimes = Integer.parseInt(PropertyHelper.getDefaultValue("zookeeper.connectRetry.times", "3"));
				// 设置空间名称
				String namespace = PropertyHelper.getValue("zookeeper.namespace");
				// 设置session 的超时时间
				int sessionTimeoutMs = Integer
						.parseInt(PropertyHelper.getDefaultValue("zookeeper.sessionTimeoutMs", "15000"));
				// 链接超时时间
				int connectionTimeoutMs = Integer
						.parseInt(PropertyHelper.getDefaultValue("zookeeper.connectionTimeoutMs", "5000"));

				Builder builder = CuratorFrameworkFactory.builder();
				builder.connectString(cluster);
				builder.retryPolicy(new ExponentialBackoffRetry(retrySleep, retryTimes));
				builder.namespace(namespace);
				builder.sessionTimeoutMs(sessionTimeoutMs);
				builder.connectionTimeoutMs(connectionTimeoutMs);
				CuratorFramework client = builder.build();
				client.start();
				clusters.add(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private synchronized static void swtichClient() {
		clusterIndex++;
		if (clusterIndex >= clusters.size()) {
			clusterIndex = 0;
		}
	}

	public synchronized static CuratorFramework getClient() {
		CuratorFramework curatorFramework = clusters.get(clusterIndex);
		
		return curatorFramework;
	}

}
