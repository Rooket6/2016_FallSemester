package addingStrings;

// This problem seems too simple.
public class AddingStrings {

	/* 
	 * https://www.glassdoor.com/Interview/software-engineer-intern-interview-questions-SRCH_KO0,24.htm
	 * Software Engineer Intern at Facebook was asked...	Oct 1, 2013
	 * Given two strings representing integer numbers ("123" , "30") return a string representing the sum of the two numbers ("153")
	 */
	public static void main(String[] args) {

		String[] intStrings = {"123", "30"};
		
		System.out.print("The value of ");
		
		long sum = 0;
		for (int i = 0; i < intStrings.length; i++) {
			
			// Only add " + " before the number if it is not the first number
			if (i > 0) {
				System.out.print(" + ");
			}
			
			System.out.print(intStrings[i]);
			sum += Integer.parseInt(intStrings[i]);
			
		}

		System.out.println(", is: " + sum);
		
	}

}
