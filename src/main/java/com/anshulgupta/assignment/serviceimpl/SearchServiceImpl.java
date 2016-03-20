package com.anshulgupta.assignment.serviceimpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anshulgupta.assignment.dao.SearchTextDao;
import com.anshulgupta.assignment.json.SearchCount;
import com.anshulgupta.assignment.json.SearchResult;
import com.anshulgupta.assignment.json.SearchText;
import com.anshulgupta.assignment.services.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	@Autowired
	private SearchTextDao searchTextDao;
	
	@Override
	public SearchCount searchCount(SearchText searchText) {
		// TODO Auto-generated method stub
		SearchCount searchCount = new SearchCount();
		for( String name : searchText.getSearchText()){
			Integer count = searchTextDao.searchCount(name.toLowerCase());
			searchCount.getCounts().add(new SearchResult(name, count));
		}
		return searchCount;
	}

	@Override
	public List<SearchResult> listSearchResult(Integer topCount) {
		// TODO Auto-generated method stub
		List<SearchResult> listOfTopCount = new LinkedList<SearchResult>();
		List<Map.Entry<String, Integer>> mapList = searchTextDao.getSortedMapList();
		for( int i = 0 ; i < topCount ; i++ ){
			Map.Entry<String, Integer> entry = mapList.get(i);
			listOfTopCount.add(new SearchResult(entry.getKey(), entry.getValue()));
		}
		return listOfTopCount;
	}

	
}
