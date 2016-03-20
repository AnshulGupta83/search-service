package com.anshulgupta.assignment.daoimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.anshulgupta.assignment.dao.SearchTextDao;

@Component
public class SearchTextInFileDao implements SearchTextDao {

	private Map<String, Integer> searchMap;

	@Override
	public Integer searchCount(String name) {
		// TODO Auto-generated method stub

		return searchMap.get(name);
	}

	@PostConstruct
	public void readFile() throws IOException {

		String inputLine = null;
		Resource resource = new ClassPathResource("Paragraph.txt");
		InputStream resourceInputStream = resource.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceInputStream));

		searchMap = new HashMap<String, Integer>();
		try {
			while ((inputLine = bufferedReader.readLine()) != null) {
				String[] words = inputLine.split("[ \n\t\r.,;:!?(){}]");
				for (int counter = 0; counter < words.length; counter++) {
					String key = words[counter].toLowerCase().trim();
					if (key.length() > 0) {
						if (searchMap.get(key) == null) {
							searchMap.put(key, 1);
						} else {
							int value = searchMap.get(key).intValue();
							value++;
							searchMap.put(key, value);
						}
					}
				}
			}
			
		} catch (IOException error) {
			System.out.println("Invalid File");
		} finally {
			bufferedReader.close();
		}
	}

	@Override
	public List<Map.Entry<String, Integer>> getSortedMapList() {
		// TODO Auto-generated method stub
		Set<Entry<String, Integer>> set = searchMap.entrySet();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		return list;
	}
}
