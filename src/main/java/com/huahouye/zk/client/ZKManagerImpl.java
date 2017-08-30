package com.huahouye.zk.client;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 维护 Znode 的接口的实现
 * @author huahouye@gmail.com
 *
 */
public class ZKManagerImpl implements ZKManager {

	private static ZooKeeper zk;
	private static ZKConnection zkConnection;

	/**
	 * 初始化连接
	 */
	public ZKManagerImpl() {
		try {
			zkConnection = new ZKConnection();
			zk = zkConnection.connect("localhost");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ZKManagerImpl(String zkServer) {
		try {
			zkConnection = new ZKConnection();
			zk = zkConnection.connect(zkServer);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭 zookeeper 连接
	 */
	public void closeConnection() {
		try {
			zkConnection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void create(String path, byte[] data)
			throws KeeperException, InterruptedException {
		// path - znode
		// 的路径，如：/myapp1，/myapp2，/myapp1/mydata1，myapp2/mydata1/myanothersubdata
		// ACL - access control list，访问控制列表
		// createMode - znode 的类型
		zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public Stat getZnodeStats(String path) throws KeeperException, InterruptedException {
		Stat stat = zk.exists(path, true);
		if (stat != null) {
			System.out
					.println("Node exists and the node version is " + stat.getVersion());
		}
		else {
			System.out.println("Node does not exists");
		}
		return stat;
	}

	public Object getZnodeData(String path, boolean watchFlag)
			throws KeeperException, InterruptedException {
		try {
			Stat stat = getZnodeStats(path);
			byte[] b = null;
			if (stat != null) {
				if (watchFlag) {
					ZKWatcher watch = new ZKWatcher();
					b = zk.getData(path, watch, null);
					watch.wait();
				}
				else {
					b = zk.getData(path, null, null);
				}
				String data = new String(b, "UTF-8");
				System.out.println(data);
				return data;
			}
			else {
				System.out.println("Node does not exists");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void update(String path, byte[] data)
			throws KeeperException, InterruptedException {
		int version = zk.exists(path, true).getVersion();
		zk.setData(path, data, version);
	}

	public List<String> getZnodeChildren(String path)
			throws KeeperException, InterruptedException {
		Stat stat = getZnodeStats(path);
		List<String> children = null;

		if (stat != null) {
			children = zk.getChildren(path, false);
			for (int i = 0; i < children.size(); i++) {
				System.out.println(children.get(i));
			}
		}
		else {
			System.out.println("Node does not exists");
		}
		return children;
	}

	public void delete(String path) throws KeeperException, InterruptedException {
		int version = zk.exists(path, true).getVersion();
		zk.delete(path, version);
	}

	public boolean existe(String path) throws KeeperException, InterruptedException {
		Stat stat = zk.exists(path, true);
		if (stat != null) {
			System.out
					.println("Node exists and the node version is " + stat.getVersion());
			return true;
		}
		else {
			System.out.println("Node does not exists");
			return false;
		}
	}

}
