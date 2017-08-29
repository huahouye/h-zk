package com.huahouye.com.test;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import com.huahouye.zk.client.ZKManagerImpl;

public class ZKClientTest {

	private static ZKManagerImpl zkmanager = new ZKManagerImpl("localhost");
	// ZNode Path
	private String path = "/QN-GBZnode";
	byte[] data = "bbs.huahouye.com Client Data".getBytes();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() throws KeeperException, InterruptedException {
		System.out.println("testCreate()");
		zkmanager.create(path, data);
		Stat stat = zkmanager.getZnodeStats(path);
		assertNotNull(stat);
		zkmanager.delete(path);
	}

	@Test
	public void testZnodeStat() throws KeeperException, InterruptedException {
		System.out.println("testZnodeStat()");
		zkmanager.create(path, data);
		Stat stat = zkmanager.getZnodeStats(path);
		assertNotNull(stat);
		assertNotNull(stat.getVersion());
		zkmanager.delete(path);
	}

	@Test
	public void testGetZnodeData() throws KeeperException, InterruptedException {
		System.out.println("testGetZnodeData()");
		zkmanager.create(path, data);
		String data = (String) zkmanager.getZnodeData(path, false);
		assertNotNull(data);
		zkmanager.delete(path);
	}

	@Test
	public void testUpdate() throws KeeperException, InterruptedException {
		System.out.println("testUpdate()");
		zkmanager.create(path, data);
		String data = "www.huahouye.com Update Data";
		byte[] dataBytes = data.getBytes();
		zkmanager.update(path, dataBytes);
		String retriveData = (String) zkmanager.getZnodeData(path, false);
		assertNotNull(retriveData);
		zkmanager.delete(path);
	}

	@Test
	public void testGetZnodeChildren() throws KeeperException, InterruptedException {
		System.out.println("testGetZnodeChildren()");
		zkmanager.create(path, data);
		String childPath = "/QN-GBZnode/child1";
		String childData = "child1";
		byte[] childDateBytes = childData.getBytes();
		zkmanager.create(childPath, childDateBytes);
		List<String> children = zkmanager.getZnodeChildren(path);
		assertNotNull(children);
		zkmanager.delete(childPath);
		zkmanager.delete(path);
	}
	
	@Test
	public void testDelete() throws KeeperException, InterruptedException {
		zkmanager.create(path, data);
		zkmanager.delete(path);
		Stat stat = zkmanager.getZnodeStats(path);
		assertNotNull(stat);
	}

}
