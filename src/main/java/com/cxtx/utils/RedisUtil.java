package com.cxtx.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-5-27 下午1:15:45 类说明
 * 封装的对redis的操作
 * 本版本的redis服务器部署在139.196.40.33上
 * 客户端连接需要口令sjtuist
 * 在获得连接后一定要释放一定要释放一定要释放
 * 否则会造成内存泄漏
 */
public class RedisUtil {
	// private static String ADDR = "192.168.1.110";
	// private static String ADDR = "202.120.40.73";
	// private static String ADDR = "10.173.14.116"; "139.196.40.33"; sjtuist
	private static String ADDR ="localhost";
	private static int PORT = 6379;
	// private static int PORT = 36040;6379;
	private static String AUTH = "ycc";

	// 可用连接实例的最大数目，默认值为8
	private static int MAX_ACTIVE = 2048;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException;
	private static int MAX_WAIT = 10000;
	private static int TIMEOUT = 10000;

	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Reis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
//			config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
//			config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取redis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

}
