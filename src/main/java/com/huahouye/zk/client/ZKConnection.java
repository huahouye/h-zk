package com.huahouye.zk.client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 官方提供的 ZooKeeper API 主要集中在 ZooKeeper 类里面，ZooKeeper 提供了多种链接 ZooKeeper
 * 集群的构造方法，并提供了诸多方法：<br>
 * connect − 连接到 ZooKeeper 集群<br>
 * create - 创建 znode<br>
 * exists − 检查 znode 是否存在以及她的信息<br>
 * getData − 获取某个 znode 的数据<br>
 * setData − 设置 znode 节点的数据<br>
 * getChildren − 获取指定 znode 节点下的说有可用子节点<br>
 * delete − 删除 znode 节点以及她的所有子节点<br>
 * close − 关闭一个连接<br>
 * 
 * @author huahouye@gmail.com
 *
 */
public class ZKConnection {
	// declare zookeeper instance to access ZooKeeper ensemble
	private ZooKeeper zoo;
	final CountDownLatch connectedSignal = new CountDownLatch(1);

	// Method to connect zookeeper ensemble.
	public ZooKeeper connect(String host) throws IOException, InterruptedException {
		zoo = new ZooKeeper(host, 5000, new Watcher() {
			// zookeeper 的连接状态通过实现 Watcher 接口的对象回调方法返回
			public void process(WatchedEvent we) {
				if (we.getState() == KeeperState.SyncConnected) {
					// 执行 countDown() 释放锁，让 connectedSignal.await() 可以往后执行
					connectedSignal.countDown();
				}
			}
		});

		// 等待触发 connectedSignal.countDown() 释放锁后再往后执行
		connectedSignal.await();
		return zoo;
	}

	// Method to disconnect from zookeeper server
	public void close() throws InterruptedException {
		zoo.close();
	}
}
