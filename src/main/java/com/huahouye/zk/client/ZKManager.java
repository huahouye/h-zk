package com.huahouye.zk.client;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

/**
 * 维护 Znode 的接口
 * @author huahouye@gmail.com
 *
 */
public interface ZKManager {

	/**
	 * 创建 Znode，保存一些数据
	 * @param path
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void create(String path, byte[] data)
			throws KeeperException, InterruptedException;

	/**
	 * 获取 Znode 的状态
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public Stat getZnodeStats(String path) throws KeeperException, InterruptedException;

	/**
	 * 获取 Znode 的数据
	 * @param path
	 * @param watchFlag
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public Object getZnodeData(String path, boolean watchFlag)
			throws KeeperException, InterruptedException;

	/**
	 * 更新 Znode 的数据
	 * @param path
	 * @param data
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void update(String path, byte[] data)
			throws KeeperException, InterruptedException;

	/**
	 * 获取 Znode 的孩子节点
	 * @param path
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public List<String> getZnodeChildren(String path)
			throws KeeperException, InterruptedException;

	/**
	 * 删除 Znode
	 * @param path
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void delete(String path) throws KeeperException, InterruptedException;

}
