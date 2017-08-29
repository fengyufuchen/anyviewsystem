package com.anyview.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.anyview.redis.service.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientPool implements JedisClient {

	@Autowired
	private JedisPool jedisPool;

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisPool.getResource().set(key.getBytes(), value.getBytes());
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return new String(jedisPool.getResource().get(key.getBytes()));
	}

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		Jedis resource = jedisPool.getResource();

		Boolean exists = resource.exists(key.getBytes());

		resource.close();

		return exists;
	}

	@Override
	public Long expire(String key, int second) {
		// TODO Auto-generated method stub
		Jedis resource = jedisPool.getResource();
		Long expire = resource.expire(key.getBytes(), second);

		resource.close();
		return expire;

	}

	@Override
	public Long ttl(String key) {
		// TODO Auto-generated method stub
		Jedis resource = jedisPool.getResource();
		Long ttl = resource.ttl(key.getBytes());
		resource.close();
		return ttl;
	}

	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		Jedis resource = jedisPool.getResource();
		Long incr = resource.incr(key.getBytes());

		resource.close();
		return incr;
	}

	@Override
	public Long hset(String key, String field, String vaule) {
		// TODO Auto-generated method stub
		Jedis resource = jedisPool.getResource();
		Long hset = resource.hset(key.getBytes(), field.getBytes(), vaule.getBytes());
		resource.close();
		return hset;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		Jedis resource = jedisPool.getResource();
		byte[] hget = resource.hget(key.getBytes(), field.getBytes());

		resource.close();
		return new String(hget);
	}

	@Override
	public Long hdel(String key, String field) {
		// TODO Auto-generated method stub
		Jedis resource = jedisPool.getResource();
		Long hdel = resource.hdel(key.getBytes(), field.getBytes());

		resource.close();

		return hdel;
	}

}
