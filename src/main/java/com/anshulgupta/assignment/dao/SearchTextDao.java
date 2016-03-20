package com.anshulgupta.assignment.dao;

import java.util.List;
import java.util.Map;

public interface SearchTextDao {

	public Integer searchCount(String name);
	
	public List<Map.Entry<String,Integer>> getSortedMapList();
	
}
