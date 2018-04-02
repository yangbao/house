package com.mooc.house.common;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStreamInJava8 {

	public static void main(String[] args) {
		Set<String> idSet = new HashSet<String>();
		for(int i = 0; i< 10; i++){
			idSet.add("100"+i);
		}
		Stream<String> stream = idSet.stream();
//		stream.forEach(System.out::println);
		Stream<Long> mapStream = stream.map(Long::parseLong);;
//		mapStream.forEach(System.out::println);
		 List<Long> list = mapStream.collect(Collectors.toList());
//		 System.out.println(list);
	      //Java 8 中的 Stream 是对集合（Collection）对象功能的增强
	     // List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
	}
}
