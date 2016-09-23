package anagrams;

import java.util.HashMap;
import java.util.Map;

public class Anagrams {

	public static void main(String[] args) {
		
		String wordOne = "sarcocbarcinomata";
		String wordTwo = "carcinosarcomatab";
		
		System.out.println("Anagram value of " + wordOne + " and " + wordTwo + " is " + areAnagramsSecondTry(wordOne, wordTwo));

	}
	
	// Solution: Check if every letter from word one exists in word two
	// Concerns: This solution will supply false-positives.
	// This is close to n squared time at worst. Labeled loop, weird continue.
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
	
	
	// Counts how many intances there are of each char in a word and puts the result in a map.
	public static Map<Character, MutableInt> countUniqueCharsInWord(String word) {
		
		Map<Character, MutableInt> wordMap = new HashMap<Character, MutableInt>(word.length());
		
		MutableInt count;
		for (int i = 0; i < word.length(); i++) {
			
			count = wordMap.get(word.charAt(i));
			if (count == null) {
				wordMap.put(word.charAt(i), new MutableInt());
			} else {
				count.increment();
			}
			
		}
		
		return wordMap;
		
	}
	
	// Credits to "gregory" on StackOverflow for this method of incrementing map values efficiently: http://stackoverflow.com/a/107987
	public static class MutableInt {
		
		private int value = 1;
		
		@Override
		public int hashCode() {
			return getValue();
		}

		@Override
		public boolean equals(Object o) {
			
			MutableInt that = (MutableInt) o;
			
			return this.getValue() == that.getValue();
			
		}
		
		public void increment() {
			value++;
		}
		
		public int getValue() {
			return value;
		}
		
	}

}
