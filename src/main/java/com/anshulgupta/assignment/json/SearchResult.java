package com.anshulgupta.assignment.json;

public class SearchResult {
	
	String name;

	Integer count;
	
	public SearchResult(String name , Integer count){
		this.name = name;
		this.count = count;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


}
