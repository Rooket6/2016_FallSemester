package anagrams;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Anagrams {
	
	/*
	 * Question: https://www.glassdoor.com/Interview/software-engineer-intern-interview-questions-SRCH_KO0,24.htm
	 * Check if two words are anagrams of each other
	 * 
	 *  Anagrams from: http://www.recordholders.org/en/news/news058.html
	 * 'sarcocarcinomata'='carcinosarcomata'
	 *	'dactylopteridae'='pterodactylidae'
	 *	'autoradiographic'='radioautographic'
	 *	'parallel of latitude'='parallel of altitude'
	 *	'pterylographical'='petrographically'
	 *	'nonconversational'='nonconservational'
	 *	'pathophysiological'='physiopathological'
	 *	'vaudeville theatre'='vaudeville theater'
	 *	'chlorobromomethane'='bromochloromethane'
	 *	'photomicrographic'='microphotographic'
	 *	'trifluorochloromethane'='chlorotrifluoromethane'
	 *	'telephotographic'='phototelegraphic'
	 *	'trichloronitromethane'='nitrotrichloromethane'
	 *	'misconfiguration'='configurationism'
	 *	'pathophysiologic'='physiopathologic'
	 *	'photomicrography'='microphotography'
	 *	'hypophysectomise'='hypophysectomies'
	 *	'nephrolithotomies'='lithonephrotomies'
	 */
	
	public static void main(String[] args) {
		
		String wordOne = "nephrolithotomies";
		String wordTwo = "lithonephrotomies";
		
		System.out.println("Anagram value of " + wordOne + " and " + wordTwo + " is " + areAnagramsSecondTry(wordOne, wordTwo));

	}
	
	// Solution: Check if every letter from word one exists in word two
	// Concerns: This solution will supply false-positives.
	// O(n^2). Labeled loop, weird continue.
	// Better way to implement loops in this situation?
	public static boolean areAnagramsFirstTry(String wordOne, String wordTwo) {
		
		int stepCount = 0; // For runtime analysis
		
		wordOne = wordOne.toLowerCase();
		wordTwo = wordTwo.toLowerCase();
		
		if (wordOne.length() != wordTwo.length()) {
			return false;
		}
		
		firstWordLoop:
		for (int i = 0; i < wordOne.length(); i++) {
			for (int j = 0; j < wordTwo.length(); j++) {
				stepCount++;
				if (wordOne.substring(i, i + 1).equals(wordTwo.substring(j, j + 1)))
						continue firstWordLoop;
			}
			return false;
		}

		return true;
	
	}
	
	// Count character occurrences in words. Compare the counts.
	// O(n)
		// Assuming HashMap's get/put time is constant. (Which it should be when there is no collisions BUT HOW??),
		// O(n) time inserting values into map,
		// O(n) time comparing the count reports
	public static boolean areAnagramsSecondTry(String wordOne, String wordTwo) {

		wordOne = wordOne.toLowerCase();
		wordTwo = wordTwo.toLowerCase();
		
		if (wordOne.length() != wordTwo.length())
			return false;
		
		// Stores a map containing the count of each char instance in each word.
		Map<Character, MutableInt> wordOneCharReport = countUniqueCharsInWord(wordOne);
		Map<Character, MutableInt> wordTwoCharReport = countUniqueCharsInWord(wordTwo);
		
		return wordOneCharReport.equals(wordTwoCharReport);
		
	}
	
	
	// Counts how many instances there are of each char in a word and puts the result in a map.
	public static Map<Character, MutableInt> countUniqueCharsInWord(String word) {
		
		Map<Character, MutableInt> wordMap = new HashMap<Character, MutableInt>(word.length());
		
		MutableInt count;
		for (int i = 0; i < word.length(); i++) {
			
			count = wordMap.get(word.charAt(i));
			if (count == null) {
				wordMap.put(word.charAt(i), new MutableInt(1));
			} else {
				count.incrementAndGet();
			}
			
		}
		
		return wordMap;
		
	}
	
	// Credits to "gregory" on StackOverflow for this method of incrementing map values efficiently: http://stackoverflow.com/a/107987
	@SuppressWarnings("serial")
	public static class MutableInt extends AtomicInteger {
		
		public MutableInt() {
			super();
		}
		
		public MutableInt(int initialValue) {
			super(initialValue);
		}
		
		@Override
		public int hashCode() {
			return get();
		}

		@Override
		public boolean equals(Object o) {
			
			MutableInt that = (MutableInt) o;
			
			return this.get() == that.get();
			
		}
		
	}

}
