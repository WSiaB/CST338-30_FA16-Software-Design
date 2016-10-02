/* Author: John Lester
 * Class: CST338-30_FA16
 * Assignment: (M1) Write a Java program: String Manipulation
 * 
 * This program will prompt the user for their first and last name, then return
 * it with its length. It will then return it as all caps and all lower case.
 * The program will then state the min and max number of hours expected for the 
 * class, then prompt the user for the number of hours spent this week on the 
 * class.
 */

import java.util.Scanner;
import java.text.DecimalFormat;

public class Assignment1
{
   public static void main(String[] args)
   {
      Scanner textInput = new Scanner(System.in);
      System.out.println("Please capitalize your entries.");
      System.out.print("Enter your first name (Xxx): ");
      String firstName = textInput.nextLine();
      System.out.print("Enter your last name (Xxx): ");
      String lastName = textInput.nextLine();
      String fullName = firstName + " " + lastName;
      System.out.println(fullName + " is " + fullName.length() + " characters.");
      System.out.println("In all caps: " + fullName.toUpperCase());
      System.out.println("In lower case: " + fullName.toLowerCase());
      
      System.out.println();
      int MIN_HOURS = 12, MAX_HOURS=20;
      System.out.print("Students should spend between " + MIN_HOURS);
      System.out.println(" and " + MAX_HOURS + " hours per week on this class");
      System.out.print("How many hours have you spent this week (x.xxx)?: ");
      double timeSpent = textInput.nextDouble();
      DecimalFormat pattern0dot0 = new DecimalFormat("0.0");
      System.out.print("You have spent " + pattern0dot0.format(timeSpent));
      System.out.println("hrs this week.");
      System.out.print("This is " + (int)(timeSpent/MIN_HOURS*100) + "% of the ");
      System.out.print("minimum and " + (int)(timeSpent/MAX_HOURS*100) + "% of ");
      System.out.println("the maximum.");
   }
}

/*********************************** OUTPUT ************************************

Please capitalize your entries.
Enter your first name (Xxx): John
Enter your last name (Xxx): Lester
John Lester is 11 characters.
In all caps: JOHN LESTER
In lower case: john lester

Students should spend between 12 and 20 hours per week on this class
How many hours have you spent this week (x.xxx)?: 1.567
You have spent 1.6hrs this week.
This is 13% of the minimum and 7% of the maximum.


Please capitalize your entries.
Enter your first name (Xxx): Tom
Enter your last name (Xxx): LeGris
Tom LeGris is 10 characters.
In all caps: TOM LEGRIS
In lower case: tom legris

Students should spend between 12 and 20 hours per week on this class
How many hours have you spent this week (x.xxx)?: 24
You have spent 24.0hrs this week.
This is 200% of the minimum and 120% of the maximum.

*******************************************************************************/
