//Stephanie Gan & John Lester
//CST 338-30_FA16
//Casino with Methods and a Class

import java.util.Scanner;

public class Assign2 {
	static Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		//prompt the user for their bet
		int bet = getBet();
		TripleString currPull = new TripleString();
		
		//initialize the variable to hold the sum of the user's winnings
		int sum = 0;
		
		//initialize i to keep track of round
		int i = 0;
		
		do {
			
			//if the bet is not zero, proceed
			System.out.println("Thank you for your bet.");
				
			//print a confirmation
			System.out.println("You entered " + bet + ".");
			System.out.println("... ... ...!");
					
			//pull
			currPull = pull();
				
			//get pay multiplier
			int payoutMultiplier = getPayMultiplier(currPull);
				
			//calculate winnings
			int winnings = payoutMultiplier * bet;
				
			//add winnings to the sum variable
			sum += winnings;
				
			//save the current pull's winnings into an array
			currPull.saveWinnings(winnings);
				
			//display results
			display(currPull, winnings);
			
			//increment i
			i++;
				
			//prompt the user for another bet
			bet = getBet();
			//end of while loop
		} while (i < currPull.MAX_PULLS - 1 && bet != 0);
		
		//print list of individual winnings
		System.out.println("Your individual winnings were: ");
		System.out.println(TripleString.displayWinnings());
		
		//print total winnings
		System.out.print("Your total winnings: $" + sum);
		System.out.println("\nGoodbye!");
		
		//close the scanner object
		userInput.close();
	}
	
	//method to receive input from user
	private static int getBet() {
		int bet = 0;
		//entering bet for the first time
		System.out.print("Enter your bet (range 1-100) or 0 to quit: ");
		bet = userInput.nextInt();
		
		//check to see if the value is within bounds
		while (bet > 100 || bet < 0) {
			System.out.println("You entered " + bet + ".");
			System.out.print("That won't work! Please place a bet between 1 and 100: ");
			bet = userInput.nextInt();
		}
		
		return bet;
	}
	
	private static String randString() {
		String word = "";
		//set a random number as rand
		double rand = Math.random() * 1000;
		
		//determine which string gets returned as a result of rand
		if (0 <= rand && rand < 125) {
			//space appears 12.5% of the time
			word = "SPACE";
		}
		
		else if (125 <= rand && rand < 250) {
			//7 appears 12.5% of the time
			word = "7";
		}
		
		else if (250 <= rand && rand < 500) {
			//cherries appears 25% of the time
			word = "CHERRIES";
		}
		
		else {
			//bar appears 50% of the time
			word = "BAR";
		}
		
		return word;
	}
	
	public static TripleString pull() {
		TripleString outcomes = new TripleString();
		//get 3 random outcomes
		String randWord1 = randString();
		String randWord2 = randString();
		String randWord3 = randString();
		
		//assign them to TripleString
		outcomes.setString1(randWord1);
		outcomes.setString2(randWord2);
		outcomes.setString3(randWord3);
		return outcomes;
	}
	
	static int getPayMultiplier (TripleString thePull) {
		int multiplier;
		
		//if the outcome is all cherries, the pay multiplier is 30
		if (thePull.getString1() == "CHERRIES" && thePull.getString2() == "CHERRIES" && thePull.getString3() == "CHERRIES") {
			multiplier = 30;
		}
		//if the outcome is all bars, the pay multiplier is 50
		else if (thePull.getString1() == "BAR" && thePull.getString2() == "BAR" && thePull.getString3() == "BAR") {
			multiplier = 50;
		}
		//if the outcome is all 7's, the pay multiplier is 100
		else if (thePull.getString1() == "7" && thePull.getString2() == "7" && thePull.getString3() == "7") {
			multiplier = 100;
		}
		//if the outcome is cherries, cherries, not cherries, the multiplier is 15
		else if (thePull.getString1() == "CHERRIES" && thePull.getString2() == "CHERRIES" && thePull.getString3() != "CHERRIES") {
			multiplier = 15;
		}
		//if the outcome is cherries, not cherries, any, the multiplier is 5
		else if (thePull.getString1() == "CHERRIES" && thePull.getString2() != "CHERRIES") {
			multiplier = 5;
		}
		//otherwise, there is no payout
		else {
			multiplier = 0;
		}
		return multiplier;
	}
	
	static void display (TripleString thePull, int winnings) {
		//display the outcome
		System.out.println(thePull.getString1() + " " + thePull.getString2() + " " + thePull.getString3());
		
		//if the user won anything, display their earnings
		if (winnings > 0) {
			System.out.println("Congrats! You won $" + winnings + ".\n");
		}
		else {
			System.out.println("Sorry, you lost.\n");
		}
	}
}

class TripleString {
	//initializing the 3 strings for this class
	private String string1, string2, string3;
	
	//setting the maximum length of strings
	public static final int MAX_LEN = 20;
	
	//setting the maximum amount of pulls
	public static final int MAX_PULLS = 40;
		
	//initializing the array to keep track of winnings
	private static int[] pullWinnings = new int[MAX_PULLS];
	
	//setting a variable to keep track of which spot we are on in the array
	private static int numPulls;
	
	//initializing the constructor
	public TripleString() {
		string1 = "";
		string2 = "";
		string3 = "";
	}
	
	//creating validString()
	private boolean validString(String check) {
		//if the argument is within bounds of MAX_LEN and is not null, return true
		if (check.length() <= MAX_LEN && check != null) {
			return true;
		}
		//if the argument is not valid, return false
		else {
			return false;
		}
	}
	
	//setting up the mutators
	public boolean setString1 (String first) {
		//set string to argument if valid and return true
		if (validString(first) == true) {
			string1 = first;
			return true;
		}
		//return false if unable to set string equal to argument
		else {
			return false;
		}
	}
	
	public boolean setString2 (String second) {
		//set string to argument if valid and return true
		if (validString(second) == true) {
			string2 = second;
			return true;
		}
		//return false if unable to set string equal to argument
		else {
			return false;
		}
	}
	
	public boolean setString3 (String third) {
		//set string to argument if valid and return true
		if (validString(third) == true) {
			string3 = third;
			return true;
		}
		//return false if unable to set string equal to argument
		else {
			return false;
		}
	}
	
	//setting up the accessors
	public String getString1() {
		return string1;
	}
	
	public String getString2() {
		return string2;
	}
	
	public String getString3() {
		return string3;
	}
	
	//tying all of the strings together into one string
	public String toString() {
		String temp = string1 + " " + string2 + " " + string3;
		return temp;
	}
	
	//saving winnings into an array
	public boolean saveWinnings(int winnings) {
		pullWinnings[numPulls] = winnings;
		numPulls++;
		return true;
	}
	
	//setting winnings into a string for display
	public static String displayWinnings() {
		String winnings = "";
		for (int i = 0; i <= numPulls; i++) {
			winnings += pullWinnings[i] + " ";
		}
		return winnings;
	}
}



/* TEST RUN 

Enter your bet (range 1-100) or 0 to quit: 5
Thank you for your bet.
You entered 5.
... ... ...!
SPACE 7 SPACE
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 15
Thank you for your bet.
You entered 15.
... ... ...!
7 BAR BAR
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 50
Thank you for your bet.
You entered 50.
... ... ...!
BAR BAR BAR
Congrats! You won $2500.

Enter your bet (range 1-100) or 0 to quit: 55
Thank you for your bet.
You entered 55.
... ... ...!
BAR BAR CHERRIES
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 56
Thank you for your bet.
You entered 56.
... ... ...!
CHERRIES SPACE BAR
Congrats! You won $280.

Enter your bet (range 1-100) or 0 to quit: 32
Thank you for your bet.
You entered 32.
... ... ...!
7 BAR CHERRIES
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 12
Thank you for your bet.
You entered 12.
... ... ...!
CHERRIES CHERRIES BAR
Congrats! You won $180.

Enter your bet (range 1-100) or 0 to quit: 15
Thank you for your bet.
You entered 15.
... ... ...!
BAR BAR 7
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 14
Thank you for your bet.
You entered 14.
... ... ...!
BAR SPACE CHERRIES
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 89
Thank you for your bet.
You entered 89.
... ... ...!
CHERRIES CHERRIES BAR
Congrats! You won $1335.

Enter your bet (range 1-100) or 0 to quit: 101
You entered 101.
That won't work! Please place a bet between 1 and 100: 96
Thank you for your bet.
You entered 96.
... ... ...!
7 CHERRIES SPACE
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 95
Thank you for your bet.
You entered 95.
... ... ...!
BAR CHERRIES BAR
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 99
Thank you for your bet.
You entered 99.
... ... ...!
BAR BAR BAR
Congrats! You won $4950.

Enter your bet (range 1-100) or 0 to quit: 32
Thank you for your bet.
You entered 32.
... ... ...!
SPACE BAR SPACE
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 32
Thank you for your bet.
You entered 32.
... ... ...!
CHERRIES BAR BAR
Congrats! You won $160.

Enter your bet (range 1-100) or 0 to quit: 35
Thank you for your bet.
You entered 35.
... ... ...!
BAR BAR SPACE
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 45
Thank you for your bet.
You entered 45.
... ... ...!
CHERRIES BAR BAR
Congrats! You won $225.

Enter your bet (range 1-100) or 0 to quit: 16
Thank you for your bet.
You entered 16.
... ... ...!
CHERRIES BAR BAR
Congrats! You won $80.

Enter your bet (range 1-100) or 0 to quit: 28
Thank you for your bet.
You entered 28.
... ... ...!
BAR CHERRIES BAR
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 34
Thank you for your bet.
You entered 34.
... ... ...!
BAR BAR CHERRIES
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 59
Thank you for your bet.
You entered 59.
... ... ...!
CHERRIES BAR BAR
Congrats! You won $295.

Enter your bet (range 1-100) or 0 to quit: 65
Thank you for your bet.
You entered 65.
... ... ...!
BAR CHERRIES CHERRIES
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 44
Thank you for your bet.
You entered 44.
... ... ...!
BAR CHERRIES 7
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 87
Thank you for your bet.
You entered 87.
... ... ...!
SPACE BAR BAR
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 21
Thank you for your bet.
You entered 21.
... ... ...!
BAR BAR BAR
Congrats! You won $1050.

Enter your bet (range 1-100) or 0 to quit: 22
Thank you for your bet.
You entered 22.
... ... ...!
CHERRIES BAR BAR
Congrats! You won $110.

Enter your bet (range 1-100) or 0 to quit: 101
You entered 101.
That won't work! Please place a bet between 1 and 100: 105
You entered 105.
That won't work! Please place a bet between 1 and 100: 63
Thank you for your bet.
You entered 63.
... ... ...!
CHERRIES BAR BAR
Congrats! You won $315.

Enter your bet (range 1-100) or 0 to quit: 25
Thank you for your bet.
You entered 25.
... ... ...!
BAR BAR CHERRIES
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 45
Thank you for your bet.
You entered 45.
... ... ...!
BAR 7 BAR
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 99
Thank you for your bet.
You entered 99.
... ... ...!
BAR CHERRIES BAR
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 86
Thank you for your bet.
You entered 86.
... ... ...!
SPACE BAR 7
Sorry, you lost.

Enter your bet (range 1-100) or 0 to quit: 0
Your individual winnings were: 
0 0 2500 0 280 0 180 0 0 1335 0 0 4950 0 160 0 225 80 0 0 295 0 0 0 1050 110 315 0 0 0 0 0 
Your total winnings: $11480
Goodbye!
*/