package findNonRepeatedInt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import anagrams.Anagrams.MutableInt;

public class FindNonRepeatedInt {

	/*
	 * Software Development Engineering Intern at Amazon.com was asked...	Jun 23, 2012
	 * You are given an array with n positive integers where all values in the array are repeated except for one.
	 * Return the one that is not repeated.
	 */
	
	public static void main(String[] args) {
		
		int[] arr = {1, 10, 5, 2, 2, 60, 5, 10, 1};
		
		System.out.println(firstAttempt(arr));

	}
	
	// I first ask myself, can I do better than O(n)
		// I don't think so, because I have to at least look at every number in the array once
	// I need to be able to identify which int is not represented
		// I think this means I must store the count of all of the integers until I find out there is more than one occurrence
		// HashSet?????
	
	// Solution: Loop through array, adding all non-repeated ints to a set if they don't already exist.
		// If they do exist, then remove them from the set because there is more than one occurrence
		// Return the first entry in set or -1
		// This can produce the wrong result if there are more than one odd occurrences of the same value
	// This is O(n)
		// O(n) loop that performs two operations in constant time = O(n)
	public static int firstAttempt(int[] arr) {
		
		// A set containing all of the integers that have not been repeated (so far [thus the suspects part])
		Set<Integer> nonRepeatedSuspects = new HashSet<Integer>();
		for (int n : arr) {
			
			// If the integer is already in the set, then remove it
			if (nonRepeatedSuspects.remove(n))
				continue;
			
			nonRepeatedSuspects.add(n);
			
		}
		
		for (int n : nonRepeatedSuspects)
			return n;
		
		// This will never happen assuming preconditions have been met
		return -1;
		
	}
	
	// Solution: Modified version of my anagrams algorithm. Using "MutableInt" from "Anagrams"
		// Count all occurrences of every integer
		// Return the integer with exactly one occurrence or -1
	// O(n)
		// O(n) loop that does at most two operations of constant time
		// O(n) loop
		// O(n) + O(n) = O(n)
	public static int secondAttempt(int[] arr) {
		
		Map<Integer, MutableInt> integerCountReport = new HashMap<Integer, MutableInt>();
		
		MutableInt count;
		for (int n : arr) {
			
			count = integerCountReport.get(n);
			if (count == null) {
				integerCountReport.put(n, new MutableInt(1));
			} else {
				count.incrementAndGet();
			}
			
		}
		
		for (Map.Entry<Integer, MutableInt> integerCountEntry : integerCountReport.entrySet()) {
			
			// If there is exactly one occurrence of an int, return its key
			if (integerCountEntry.getValue().get() == 1)
				return integerCountEntry.getKey();
			
		}
		
		// This will never happen assuming preconditions have been met
		return -1;
		
	}

}
