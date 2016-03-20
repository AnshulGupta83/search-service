package com.anshulgupta.assignment.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anshulgupta.assignment.json.SearchText;

@RestController("search-service")
public class SearchController {

	
	public Map<String, Integer> readFile() throws IOException {
		
		File file = new File("/Anshul/Personal/Workspace/SpringRestfullAsst/Paragraph.txt");
		BufferedReader bufferedReader = null;
		bufferedReader = new BufferedReader(new FileReader(file));
		String inputLine = null;
		Map<String, Integer> searchMap = new HashMap<>();

		try {
			while ((inputLine = bufferedReader.readLine()) != null) {
				String[] words = inputLine.split("[ \n\t\r.,;:!?(){}]");

				for (int counter = 0; counter < words.length; counter++) {
					String key = words[counter].toLowerCase(); // remove
																// .toLowerCase
																// for Case
																// Sensitive
																// result.
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
			Set<Map.Entry<String, Integer>> entrySet = searchMap.entrySet();
			System.out.println("Words" + "\t\t" + "# of Occurances");
			for (Map.Entry<String, Integer> entry : entrySet) {
				System.out.println(entry.getKey() + "\t\t" + entry.getValue());
			}

		}

		catch (IOException error) {
			System.out.println("Invalid File");
		} finally {
			bufferedReader.close();
		}

		return searchMap;
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST,consumes="application/json",produces="application/json")
	public List<WordComparable> search(@RequestBody SearchText searchText) throws IOException {
		Map<String, Integer> searchMap = readFile();
		List<WordComparable> wordList = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : searchMap.entrySet()){
			for(int i = 0 ; i < searchText.getSearchText().length; i++){
				if(searchText.getSearchText()[i].equalsIgnoreCase(entry.getKey())){
					wordList.add(new WordComparable(entry.getKey(), entry.getValue()));
				}			
			}
		}
			
		return wordList;
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
	@RequestMapping(value = "/searchTop/{topValue}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<String> findMaxOccurance(@PathVariable int topValue) throws IOException {
		Map<String, Integer> searchMap = readFile();
		List<WordComparable> wordList = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : searchMap.entrySet())
			wordList.add(new WordComparable(entry.getKey(), entry.getValue()));
 
		Collections.sort(wordList);
		List<String> list = new ArrayList<>();
		for (WordComparable wordComparable : wordList.subList(0, topValue))
			list.add(wordComparable.wordFromFile + ":" + wordComparable.numberOfOccurrence);
		return list;
	}

}
 
class WordComparable implements Comparable<WordComparable> {
	public String wordFromFile;
	public int numberOfOccurrence;
 
	public WordComparable(String wordFromFile, int numberOfOccurrence) {
		super();
		this.wordFromFile = wordFromFile;
		this.numberOfOccurrence = numberOfOccurrence;
	}
 
	@Override
	public int compareTo(WordComparable arg0) {
		int value = Integer.compare(arg0.numberOfOccurrence, this.numberOfOccurrence);
		return value != 0 ? value : wordFromFile.compareTo(arg0.wordFromFile);
	}


}
