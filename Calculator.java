import java.util.Scanner;

public class Calculator
{
	// result of equation
	int result;
	// for outputting postfix to user
	String postfix = "";
	boolean validation = false;
	
	public Calculator(String newInfix)
	{
		// make sure they pick operator, parenthesis, or integer
		validation = check(newInfix);
		// if they did, next.. make sure the parenthesis are matching
		if(validation)
			validation = isMatching(newInfix);
		// if THAT is true then go ahead and evaluate the postfix
		if(validation == true)
		// conversion returns the postfix string
		evaluatePostfix(conversionToPostfix(newInfix));
	}
	private <E> String conversionToPostfix(String newInfix)
	{
		Stack<Character> characterStack = new ArrayStack<>();
		
		for(int i = 0; i < newInfix.length(); i++)
		{
			char currentChar = newInfix.charAt(i);
			while (currentChar >= '0' && currentChar <= '9')
			{
				postfix += currentChar;
				// if next character is within length, make that new current
				// else current = space
				if(i + 1 < newInfix.length())
					currentChar = newInfix.charAt(++i);
				
				else 
					currentChar = ' ';
				// if character is not a digit, add space to postfix
				if(!(currentChar >= '0' && currentChar <= '9'))
					postfix += ' ';
			}
			if (currentChar == '+' || currentChar == '-' || currentChar == '*'
					|| currentChar == '/' || currentChar == '%' || currentChar == '^')
			{
				while(!characterStack.isEmpty() && !isOpeningParenthesis(characterStack.top())
						&& hasHigherPrec(characterStack.top(), currentChar))
				{
					postfix += characterStack.top() + " ";
					characterStack.pop();
				}
				characterStack.push(currentChar);
			}
			else if (isOpeningParenthesis(currentChar))
			{
				characterStack.push(currentChar);
			}
			else if (isClosingParenthesis(currentChar))
			{
				while(!characterStack.isEmpty() && !isOpeningParenthesis(characterStack.top()))
				{
					postfix += characterStack.top() + " ";
					characterStack.pop();
				}
				characterStack.pop();
			}
		}
		// pop all remaining operators on the stack
		while(!characterStack.isEmpty())
		{
			postfix += characterStack.top() + " ";
			characterStack.pop();
		}
		return postfix;
	}
	
	private <E> void evaluatePostfix(String newPostfix)
	{
		Stack<Integer> integerStack = new ArrayStack<>();
		
		//loop through entire length of postfix expression
		for(int i = 0; i < newPostfix.length(); i++)
		{
			String temp = "";
			
			//store current character
			char currentChar = newPostfix.charAt(i);
			// while character is 1-9
			while(currentChar >= '0' && currentChar <= '9')
			{
				temp += currentChar;
				
				// if the character next to it (right side)
				//store next character in postfix expression into currentChar
				if(i + 1 < newPostfix.length())
					currentChar = newPostfix.charAt(++i);
				else 
					currentChar = ' ';
				
				//if current character is NOT 1-9
				//note: current character changed from beginning. so it might be *
				if(!(currentChar >= '0' && currentChar <= '9'))
				{
					int tempInt = Integer.parseInt(temp);
					integerStack.push(tempInt);
				}
			}
		
			if(currentChar == '+' || currentChar == '-' || currentChar == '*'
					|| currentChar == '/' || currentChar == '%' || currentChar == '^')
			{
				if(currentChar == '+')
				{
					result = integerStack.pop() + integerStack.pop();
					integerStack.push(result);
				}
				else if (currentChar == '-')
				{
					int secondNum = integerStack.pop();
					result = integerStack.pop() - secondNum;
					integerStack.push(result);
				}
				else if(currentChar == '*')
				{
					result = integerStack.pop() * integerStack.pop();
					integerStack.push(result);
				}
				else if(currentChar == '/')
				{
					int secondDivision = integerStack.pop();
					result = integerStack.pop() / secondDivision;
					integerStack.push(result);
				}
				else if(currentChar == '%')
				{
					int secondMod = integerStack.pop();
					result = integerStack.pop() % secondMod;
					integerStack.push(result);
				}
				else if(currentChar == '^')
				{
					result = power(integerStack.pop(), integerStack.pop());
					integerStack.push(result);
				}
			}
		}
	}
	public void print()
	{
		if(validation == true)
		{
			System.out.println("Postfix expression: " + postfix);
			System.out.println("Answer: " + result);
			System.out.println();
		}
		else
			System.out.println("This is an invalid expression");
	}
	private boolean isOpeningParenthesis(Character newTop)
	{
		if(newTop == '(')
			return true;
		else
			return false;
	}
	private boolean isClosingParenthesis(Character newTop)
	{
		if(newTop == ')')
			return true;
		else
			return false;
	}
	// does top of stack have higher or equal precedence than current character?
	private boolean hasHigherPrec(Character newTop, Character newCurrentChar)
	{
		boolean higherPrec = false;
		if(newCurrentChar == '*' || newCurrentChar == '%' || newCurrentChar == '/')
		{
			if(newTop == '+' || newTop == '-')
				higherPrec = false;
			
			else if(newTop == '/' || newTop == '%' || newTop == '*' || newTop == '^')
				higherPrec = true;
		}
		else if(newCurrentChar == '+' || newCurrentChar == '-')
		{
			higherPrec = true;
		}
		else if(newCurrentChar == '^')
		{
			higherPrec = false;
		}
		return higherPrec;
	}
	
	private int power(int power, int base)
	{
		int product = base;
		if(power == 0)
			return 1;
		if(power == 1)
			return base;
		for(int j = 1; j < power; j++)
			product *= base;
		return product;
	}
	
	private boolean check(String expression)
	{
		for(int i = 0; i < expression.length(); i++)
		{
			// if it's not a valid input character, return false.
			char var = expression.charAt(i);
			if(!(var == '+' || var == '-' || var == '*'
					|| var == '/' || var == '%' || var == '^'  
					|| var == '(' || var == ')'|| var >= '0' && var <= '9' || var == ' '))
				return false;
		}
		return true;
	}
	// make sure parenthesis match
	private boolean isMatching(String input)
	{
		MatchingDelimiters delimiters = new MatchingDelimiters();
		return delimiters.isMatched(input);
	}
}
