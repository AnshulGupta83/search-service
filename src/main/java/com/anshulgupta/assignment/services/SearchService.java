package com.anshulgupta.assignment.services;

import java.util.List;

import com.anshulgupta.assignment.json.SearchCount;
import com.anshulgupta.assignment.json.SearchResult;
import com.anshulgupta.assignment.json.SearchText;

public interface SearchService {
	
	public SearchCount searchCount(SearchText searchText);
	
	public List<SearchResult> listSearchResult(Integer topCount);
	
}
