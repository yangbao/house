package com.mooc.house.biz.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mooc.house.common.model.House;
import com.mooc.house.common.page.PageParams;

import redis.clients.jedis.Jedis;

/**
 * 热门房产, 计数器 放在redis
 * 
 * @author u6035457
 *
 */
@Service
public class RecommendService {

	private static final String HOT_HOUSE_KEY = "hot_house";

	private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

	@Autowired
	private HouseService houseService;

	public void increase(Long id) {
		try {
			Jedis jedis = new Jedis("127.0.0.1"); // 默认端口6379
			jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id + "");// 让该ID的HOT_HOUSE_KEY元素
														// 增加1.
			// 移除有序集 key 中，指定排名(rank)区间内的所有成员
			jedis.zremrangeByRank(HOT_HOUSE_KEY, 0, -11);// 0代表第一个元素,-11代表最后一个元素，保留热度由低到高末尾10个房产
			jedis.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public List<Long> getHot() {
		try {
			Jedis jedis = new Jedis("127.0.0.1");
			Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1); // 取出所有, 高到低  zrange是第到高
			jedis.close();
			// Java 8 中的 Stream 是对集合（Collection）对象功能的增强
			// 在java8中， stream().map()可以让你转化一个对象成其他的对象。
			// Long::parseLong 类名::方法名 是简略的Lambda表达式
			// 收集结果collect,当你处理完流时，通常只是想查看一下结果，而不是将他们聚合为一个值
			List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
			return ids;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);// 有同学反应在未安装redis时会报500,在这里做下兼容,
			return Lists.newArrayList();
		}

	}

	public List<House> getHotHouse(Integer size) {

		House query = new House();
		List<Long> list = getHot();
		list = list.subList(0, Math.min(list.size(), size));
		if (list.isEmpty()) {
			return Lists.newArrayList();
		}
		query.setIds(list); //这回打乱排序, 需要处理
		final List<Long> order = list; //必须是final的
		List<House> houses = houseService.queryAndSetImg(query, PageParams.build(size, 1));
		//guava 的排序方法, 传入一个排序的方法, 类似compareable
		Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
			return order.indexOf(hs.getId());
		});
		return houseSort.sortedCopy(houses);
	}

	public List<House> getLastest() {
		House query = new House();
		query.setSort("create_time");
		List<House> houses = houseService.queryAndSetImg(query, new PageParams(8, 1));
		return houses;
	}
}
