import java.util.Scanner;

public class CalculatorDriver
{
	static String userInput;
	public static void main(String[] args)
	{
		System.out.println("Author: Kathleen Hang");
		System.out.println("Project 2: Calculator");
		System.out.println();
		
		System.out.println("Please input an infix expression: ");
		Scanner inputScanner = new Scanner(System.in);
		userInput = inputScanner.nextLine();
		
		if(!userInput.equals("0"))
		{
			Calculator myCalc = new Calculator(userInput);
			myCalc.print();
		}
		else
			System.out.println("User entered sentinel. [ Program End ]");
		
		while(!userInput.equals("0"))
		{
			System.out.println("Please input an infix expression: ");
			inputScanner = new Scanner(System.in);
			userInput = inputScanner.nextLine();
			
			if(!userInput.equals("0"))
			{
				Calculator myCalc2 = new Calculator(userInput);
				myCalc2.print();
			}
			else
				System.out.println("User entered sentinel. [ Program End ]");
		}
	}
}
