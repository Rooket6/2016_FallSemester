package sandbox;

import java.util.Scanner;

public class Sandbox {
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		while (true) {
			System.out.println(Integer.parseInt(in.next()) >> 1);
		}
				
	}

}
