package sortedArraySum;

import java.util.Arrays;

/* 
 * After looking a the answers people are writing answers that have relatively high time complexities (O(n)+) for what seems like a simple lookup.
 * These people sound like they know what they are talking about (saying to modify 3SUM algorithm and talking about hash maps)
 * Am I interpreting the question wrong?
 */
public class SortedArraySum {

	/*
	 * https://www.glassdoor.com/Interview/software-engineer-intern-interview-questions-SRCH_KO0,24.htm
	 * Software Engineering Intern at Facebook was asked...	Mar 8, 2012
	 * Given a sorted array, write a program to decide if two elements sum up to a third.
	 */
	public static void main(String[] args) {
		
		int[] arr = {4, 7, 11, 15, 28, 55, 83};
		
		Arrays.sort(arr);
		
		System.out.println(firstAttempt(arr, 4, 5));

	}
	
	// My instinct is to do binary search after the index of the largest element O(log(n))
	// This is the code for that solution.
		// Elm = Element in array
	public static boolean firstAttempt(int[] arr, int someElmIndex, int otherElmIndex) {
		
		// Get the index of the largest of the two given elements
		int indexOflargestOfTwoElm = Math.max(someElmIndex, otherElmIndex);
		
		// Binary search
		int sum = arr[someElmIndex] + arr[otherElmIndex];
		int i = indexOflargestOfTwoElm + 1;
		int j = arr.length - 1;
		int middleIndex;
		while ((j - i) >= 0) {
			
			middleIndex = (int) Math.floor((j + i) / 2);
			
			if (arr[middleIndex] < sum) {
				i = middleIndex + 1;
			} else if (arr[middleIndex] > sum) {
				j = middleIndex - 1;
			} else if (arr[middleIndex] == sum){
				return true;
			}
			
		}
			
		return false;
	}

}
