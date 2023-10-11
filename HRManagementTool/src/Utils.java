/*public class Utils
 * This class contains the methods used in validating user input (integer, double, and string inputs)
 * 
 * Facilitates the rest of the program to have methods manage user inputs
 */

import java.util.Scanner;
public class Utils {

	

/*Method obtainIntegerInputLowHigh
 * This method is used to obtain a user input of an integer within a low and high bound
 * 
 * Parameters:
 * 			prompMessage :	String message that is asked of the user so that they can input based on the message
 * 			low :	int variable that represents the low bound for the user inputted integer
 * 			high :	int variable that represents the high bound for the user inputted integer
 * 
 * Return Values:
 * 			choice :	int variable that contains the user inputted integer from the return of the obtainIntegerInput method
 * 
 * Local Variables Used:
 * 			choice : 	int variable that contains the user inputted integer from the return of the obtainIntegerInput method
 * 			valid :		boolean variable that is used for looping through the user inputted respoinses as a way to ensure it meets the range of low and high bound
 */
	public static int obtainIntegerInputLowHigh(String prompMessage, int low, int high) {
		int choice = 0; 																	//initalizes the choice variable as 0 so that further in the looping it will change 
		boolean valid;																		//initalizes the valid variable as false for the purpose of looping
		valid = false;
		
		while (!valid){ 																	// begins loop with valid variable as condition

		choice = obtainIntegerInput(prompMessage); 											// calls obtainIntegerInput method with prompMessage parameter
																							//stores return of method in choice variable

		if ((choice < low) || (choice > high)){												// checks if the choice variable is in between low and high, and if it isn't
			System.out.println("Invalid Choice Entered. Value must be between "+low+", and "+high+". Please try again.");						// there will be an error message that shows
		}
		else 																				//If the integer choice is allowed in the range of high and low
			valid = true;																	//changes the value of valid to stop the loop
	}
		return choice;																		//after the choice has been verified in the loop to be allowed, it is returned
	}

	/*Method obtainIntegerInputLow
 * This method is used to obtain a user input of an integer within a low bound
 * 
 * Parameters:
 * 			prompMessage :	String message that is asked of the user so that they can input based on the message
 * 			low :	int variable that represents the low bound for the user inputted integer
 * 
 * Return Values:
 * 			choice :	int variable that contains the user inputted integer from the return of the obtainIntegerInput method
 * 
 * Local Variables Used:
 * 			choice : 	int variable that contains the user inputted integer from the return of the obtainIntegerInput method
 * 			valid :		boolean variable that is used for looping through the user inputted respoinses as a way to ensure it meets the range of low and high bound
 */
	public static int obtainIntegerInputLow(String prompMessage, int low) {
		int choice = 0; 																	//initalizes the choice variable as 0 so that further in the looping it will change 
		boolean valid;																		//initalizes the valid variable as false for the purpose of looping
		valid = false;
		
		while (!valid){ 																	// begins loop with valid variable as condition

		choice = obtainIntegerInput(prompMessage); 											// calls obtainIntegerInput method with prompMessage parameter
																							//stores return of method in choice variable

		if (choice < low){																								// checks if the choice variable is less than low
			System.out.println("Invalid Choice Entered. Integer must be greater/equal to "+low+"!");						// there will be an error message that shows
		}
		else 																				//If the integer choice is allowed in the range of low bound
			valid = true;																	//changes the value of valid to stop the loop
	}
		return choice;																		//after the choice has been verified in the loop to be allowed, it is returned
	}
	
	


	public static boolean isInteger(String inputString) {
		try {
			Integer.parseInt(inputString);
			return true;
	}
	catch (NumberFormatException e) {
		return false;
	}
	}
	public static int obtainIntegerInput(String prompMessage) {
		String temp = "";
		Scanner userInput = new Scanner(System.in);
		
		boolean validInput = false;
		
		while (!validInput)
		{	
			System.out.println(prompMessage);
			temp = userInput.nextLine();
			
			if (isInteger(temp))
				validInput = true;
			else
				System.out.println("You must enter an integer. Try again!");
			
			}
		return Integer.parseInt(temp);
	
	
	
}
	
	public static boolean isDouble(String inputString) {
	
	try {
		Double.parseDouble(inputString);
		return true;
}
catch (NumberFormatException e) {
	return false;
}
	}

public static double obtainDoubleInput(String prompMessage) {
	String temp = "";
	Scanner userInput = new Scanner(System.in);
	
	boolean validInput = false;
	
	while (!validInput)
	{	
		System.out.println(prompMessage);
		temp = userInput.nextLine();
		
		if (isDouble(temp))
			validInput = true;
		else
			System.out.println("You must enter a decimal. Try again!");
		
		}
	return (double) Double.parseDouble(temp);
}

public static double roundDouble(double value, int decimalPlaces) {
	if (decimalPlaces<0)
		throw new IllegalArgumentException();
	return Math.round(value*Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
}

public static String obtainInput(String prompMessage){
	String temp = "";
	Scanner userInput = new Scanner(System.in);
	System.out.println(prompMessage);
	temp = userInput.nextLine();
	return temp;
}

	/*Method obtainInputSpecificAnswers
 * This method is used to obtain a user input and check if is valid
 * 
 * Parameters:
 * 			prompMessage :	String message that is asked of the user so that they can input based on the message
 * 			answer1 :	String variable representing a valid entry
 * 			answer2 : String variable representing a valid entry
 * Return Values:
 * 			temp :	String input that has been validated
 * 
 * Local Variables Used:
 * 			valid :		boolean variable that is used for looping through the user inputted respoinses as a way to ensure it meets the valid criteria
 */
public static String obtainInputSpecificAnswers(String prompMessage, String answer1, String answer2){
	String temp = ""; // init variable temp that will contain user input
	boolean valid;  // set boolean valid to false, for looping
	valid = false;
	while(!valid){ //loop start
		temp = obtainInput(prompMessage);//obtain user input
		if (temp.equals(answer1) || temp.equals(answer2)) // checks if it is valid
			valid = true; // if the criteria is met, then breaks out of loop
		else{ // if not valid, prints error message and redoes loop
			System.out.println("*****Input must be either "+ answer1 + " or "+answer2);
			System.out.println("Employee Type must be specified as Employee or Manager. ***Case Sensitive. Try again!\n");
	
			}	}
	return temp; // returns valid temp
}

}