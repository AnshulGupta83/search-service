package com.anshulgupta.assignment.controller;


import java.io.IOException;
import java.util.List;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anshulgupta.assignment.json.SearchCount;
import com.anshulgupta.assignment.json.SearchResult;
import com.anshulgupta.assignment.json.SearchText;
import com.anshulgupta.assignment.services.SearchService;

@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST,consumes="application/json",produces="application/json")
	public SearchCount search(@RequestBody SearchText searchText) throws IOException {
		
		return searchService.searchCount(searchText);
	}

	
	
	/**
	 * @param map
	 *            = All Words in map
	 * @param n
	 *            = How many top elements you want to print? If n=1 it will print highest occurrence word. If n=2 it
	 *            will print top 2 highest occurrence words.
	 * @throws IOException 
	 * @returns list of String
	 */
	@RequestMapping(value = "/top/{topValue}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<SearchResult> findMaxOccurance(@PathVariable int topValue) throws IOException {
		return searchService.listSearchResult(topValue);
	}
 

}
