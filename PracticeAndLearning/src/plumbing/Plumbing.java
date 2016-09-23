package plumbing;

import java.util.Arrays;

public class Plumbing {

	/*A plumber working for a company as a contractor. His job is attend services and submit the bill to get his salary on
	 * basis of daily work including his service charge 500 rupees. For a typical plumbing work he need pipes with different
	 * lengths. But in market he will get new pipe with standard size 100m of cost 100 rupees.
	 * (no small or large sized pipes available) Now , he need 10 , 40 , 60, 70 lengths of pipes for a jobwork.
	 * 
	 * Generally company  gives him (4 pipes * 100 rupees) + 500rupees as service charge = 900 rupees.
	 * but plumber bought only 2 pipes cut as follows and get his job done...
	 * 1st pipe => 40+60
	 * 2nd pipe => 10+70 + extra left(20)
	 * By buying only 2 pipes he get his job done. remaining 2 pipes money saved.
	 *
	 * write an efficient algorithm to calculate minimum number of standard size pipes required for given number of different pipes lengths:
	 * 
	 * Input:
	 * arr[N] => lengths of pipes. (for simplicity, pipe size will be either smaller or equal to standard size)
	 * 
	 * Output:
	 * minimum statdard sized(100m) pipes required
	 * 
	 * constraint: you can only cut them can not join them back as follows
	 * say he need 10 95 95,
	 * with two pipes 100 100 = > 95+5 95+5 => 95 95 (5+5)// this is not accepted
	 * 
	 * EX:
	 * 
	 * Input:
	 * 20 30 50 60 80
	 * 
	 * Output:
	 * 3
	 * 
	 * Input:
	 * 10 10 10 15 20 35 55 60 70 75 75 80
	 * 
	 * Output:
	 * 7
	 */
	public static void main(String[] args) {
		
		int[] pipeLengths = {10, 10, 10, 15, 20, 35, 55, 60, 70, 75, 75, 80};
		int standardPipeLength = 100;
		
		Arrays.sort(pipeLengths); // This makes it so I only need to keep track of one cut pipe at a time
		
		int minPipesNeeded = 1;
		int currentPipeLength = standardPipeLength;
		for (int pipeLength : pipeLengths) {
			
			if (currentPipeLength >= pipeLength)
				currentPipeLength -= pipeLength;
			else {
				minPipesNeeded++;
				currentPipeLength = standardPipeLength - pipeLength;
			}
			
			
		}
		
		System.out.println(minPipesNeeded);		

	}

}
