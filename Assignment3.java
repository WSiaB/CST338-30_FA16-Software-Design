// Stephanie Gan & John Lester
// CST 338-30_FA16
// Deck of Cards (M3)

import java.util.Scanner;
import java.util.Random;

public class Assign3Main {
   
   private static Scanner userInput = new Scanner(System.in);
   
   public static void main(String[] args) {
   //begin phase 1 testing
   System.out.println("Phase 1 Testing");
   
   //instantiating 3 cards
   Card phase1sample1 = new Card('4', Card.Suit.HEARTS);
   Card phase1sample2 = new Card('Q', Card.Suit.SPADES);
   Card phase1sample3 = new Card('J', Card.Suit.CLUBS);
   
   //printing the cards
   System.out.println("Current Cards for phase 1");
   System.out.println(phase1sample1.toString());
   System.out.println(phase1sample2.toString());
   System.out.println(phase1sample3.toString());
   
   //setting the cards' values manually
   phase1sample1.set('B', Card.Suit.CLUBS);
   phase1sample3.set('T', Card.Suit.HEARTS);
   
   //reprinting them
   System.out.println("Re-set Cards for phase 1");
   System.out.println(phase1sample1.toString());
   System.out.println(phase1sample2.toString());
   System.out.println(phase1sample3.toString());
   
   //begin phase 2 testing
   System.out.println("\n\nPhase 2 Testing");
   
   //create cards to use for phase 2 testing
   Card phase2sample1 = new Card('5', Card.Suit.CLUBS);
   Card phase2sample2 = new Card('K', Card.Suit.CLUBS);
   Card phase2sample3 = new Card('2', Card.Suit.HEARTS);
   Card phase2sample4 = new Card('9', Card.Suit.DIAMONDS);
   
   //creating a hand
   Hand phase2Hand = new Hand();
   
   //taking cards for phase 2 hand
   phase2Hand.takeCard(phase2sample1);
   phase2Hand.takeCard(phase2sample2);
   phase2Hand.takeCard(phase2sample3);
   phase2Hand.takeCard(phase2sample4);
   
   //displaying with toString()
   System.out.println(phase2Hand.toString());
   
   //inspecting cards
   System.out.println("Inspecting a couple of cards; one in hand and one non-valid:");
   System.out.println(phase2Hand.inspectCard(0));
   System.out.println(phase2Hand.inspectCard(6));
   
   //playing cards
   for (int i = 0; i < 4; i++) {
     System.out.println("Playing " + phase2Hand.playCard().toString());
   }
   
   //display current hand
   System.out.println("Current hand: " + phase2Hand.toString());
   
   //begin phase 3 testing
   System.out.println("\n\nPhase 3 Testing");
   
   //declaring a deck with 2 packs of card
   Deck testingDeck = new Deck(2);
   
   testingDeck.init(2);
   
   //reading off the cards
   for (int i = 0; i < 52 *2; i++) {
     System.out.print(testingDeck.inspectCard(i).toString() + " ");
   }
   
   //re-initializing the deck
   testingDeck.init(2);
   
   //shuffling the deck
   testingDeck.shuffle();
   
   //reading off the cards
   System.out.println("\n\nThe shuffled deck: ");
   for (int i = 0; i < 52 *2; i++) {
     System.out.print(testingDeck.inspectCard(i) + " ");
   }
   
   //begin phase 4 testing
   System.out.println("\n\nPhase 4 Testing");
     int input = 0;
     
     do {
        //prompting the user for input with bounds checking
        System.out.print("Enter the number of players (1-10): ");
        input = userInput.nextInt();
     } while (input > 10 || input < 1);
     
     //initialize a deck
     Deck userDeck = new Deck();
     
     //create an array of hands based on user input
     Hand[] currHands = new Hand[input];
     
     for (int i = 0; i < input; i++) {
       currHands[i] = new Hand();
     }
     
     //dealing cards
     for (int i = 0; i < 52; i++) {
       currHands[i%input].takeCard(userDeck.dealCard());
     }
     
     //printing hands
     for (int i = 0; i< input; i++) {
       System.out.print(currHands[i].toString());
     }
     
     //shuffling
     
     System.out.print("\n\n Now doing shuffled Hands\n\n");
     
     for (int i = 0; i < input; i++) {
       currHands[i].resetHand();
     }
     
     Deck userDeckShuffled = new Deck(1);
     userDeckShuffled.init(1);
     userDeckShuffled.shuffle();
     
     //dealing cards again
     for (int i = 0; i < 52; i++) {
       currHands[i%input].takeCard(userDeckShuffled.dealCard());
       
     }
     
     //printing new hands
     for (int i = 0; i< input; i++) {
       System.out.print(currHands[i].toString());
     }
     
   }
}

class Card {
   //create values for value and suit
   public enum Suit {
      SPADES, DIAMONDS, HEARTS, CLUBS
   }
   private char value;
   private Suit suit;
   private boolean errorFlag;

   //default constructor
   public Card() {
      this('A', Suit.SPADES);
   }

   //constructor to set the card's value and suit
   public Card(char value, Suit suit) {
      set(value, suit);
   }

   public boolean set(char value, Suit suit) {
      //calling method to check validity
      boolean validityCheck = isValid(value, suit);

      //if the values are valid, set the value and suit
      if (validityCheck == true) {
         this.value = Character.toUpperCase(value);
         this.suit = suit;

         //set error flag to false
         this.errorFlag = false;
      }
      else {
         //if the validity check comes back false, set errorflag to true
         this.errorFlag = true;
      }

      return validityCheck;
   }

   private boolean isValid(char value, Suit suit) {
      //making a character array to check validity
      char[] vals = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
       
      //initializing boolean
      boolean check = false;

      //checking the value against the character array vals
      for (char c : vals) {
         if (Character.toUpperCase(value) == c) {
            check = true;
         }
      }

      return check;       
   }

   public String toString() {
      //initialize string
      String display = "";
      //if the errorflag is true, set display as "invalid"
      if (this.getErrorFlag() == true) {
         display = "Invalid card.";
      }
      else {
         display = this.value + " of " + this.suit;
      }
      return display;
   }

   //creating the accessors
   public char getValue() {
      return value;
   }

   public Suit getSuit() {
      return suit;
   }

   public boolean getErrorFlag() {
      return errorFlag;
   }

   //checking the current instance of value and suit against the values of the passed in card
   public boolean equals(Card card) {
      boolean checkCard = false;
      if (this.getSuit() == card.getSuit()) {
         if (this.getValue() == card.getValue()) {
            checkCard = true;
         }
      }
      return checkCard;
   }
}

class Hand {
    //defining a max number of cards here to avoid a huge array by default
    public int MAX_CARDS = 50;
   
    //making the array to hold the player's current hand
    private Card[] myCards;
    private int numCards;
   
    //default constructor
    public Hand() {
        this.myCards = new Card[MAX_CARDS];
        numCards = 0;
    }
   
    public void resetHand() {
        //setting numCards back to zer0
        myCards = new Card[MAX_CARDS];
        numCards = 0;
    }
   
    public boolean takeCard(Card card) {
        boolean testing = false;
       
        //adds a card to the myCards array
        if (numCards < MAX_CARDS) {
            myCards[numCards] = new Card(card.getValue(), card.getSuit());
            //incrementing the counter of cards in the hand
            numCards++;
            testing = true;
        }
       
        else {
            testing = false;
        }
        return testing;
    }
   
    public Card playCard() {
      //returns and removes the card in the top occupied position of the array.
      
        Card cardPlayed = new Card(myCards[numCards - 1].getValue(), myCards[numCards - 1].getSuit());
       
        //removing the card by setting it to null
        myCards[numCards - 1] = null;
        //decrement counter
        numCards--;
        
        return cardPlayed;
    }
    
    //a stringizer that the client can use prior to displaying the entire hand
    public String toString() {
      String displayString = "";
      
      if (numCards > 0) {
          displayString += "Hand - ";
         for (int i = 0; i < numCards; i++) {
            displayString += myCards[i].toString() + ", ";
         }
          displayString += "\n\n";
      }
      
      return displayString;
    }
    
    //accessor for numCards
    public int getNumCards() {
      return numCards;
    }
    
    //Card inspectCard(int k) - Accessor for an individual card.  Returns a card with errorFlag = true if k is bad
    public Card inspectCard(int k) {
      //make a boolean errorFlag
      boolean errorFlag;
      
      //if the card in position k in hand exists, return the card
      if (myCards[k] != null) {
         errorFlag = false;
         return myCards[k];
      }
      
      //if the card in position k is null, set errorFlag to true
      else {
         errorFlag = true;
         return myCards[k];
      }
    }
}

//An object of type Deck represents a deck of playing cards
class Deck {
   public static int MAX_CARDS = 6*52;
   private static Card[] masterPack; // An array of cards.
   private static boolean masterMade = false;
   
   private Card[] cards; // Holds all the decks of cards
   private int topCard; // Keeps track of the top card
   private int numPacks; // Keeps track of the packs

   // default constructor
   public Deck() {
      this(1);
   }

   public Deck(int numPacks) {
      allocateMasterPack();
      this.cards=masterPack;
      init(numPacks);
   }

   public void init(int numPacks) {
      this.numPacks=numPacks;
      topCard = numPacks * 52;
      
      if((topCard)<=MAX_CARDS && numPacks>0) {
         cards = new Card[topCard];
         
         for (int i=0; i < this.getTopCard(); i++){
           cards[i] = masterPack[i%52];
         }
         
         //for(int init=0; init<cards.length; init++)
         //   cards[init] = new Card();
         //for(int a=0; a<numPacks; a++) {
         //   for(int b=52*a, c=0; b<52*a+52; b++, c++) {
         //      cards[b] = masterPack[c];
     }

   }

   //shuffle the deck into a random order.
   public void shuffle() {
      for (int i=0; i < numPacks * 52; i++) {
         double randomDouble = Math.random() * numPacks * 52;
         int rand = (int) randomDouble;
         Card temp=cards[i];
         cards[i]=cards[rand];
         cards[rand]=temp;
      }
   }

   // Removes the next card from the deck and returns it.
   public Card dealCard() {
      if (topCard==0) {
         return null;
      }
      else {
         Card tempCard = new Card(cards[topCard-1].getValue(), cards[topCard-1].getSuit());
         cards[topCard-1] = null;
         topCard--;
         return tempCard;
      }
   }

   // accessor for the int topCard
   public int getTopCard() {
      topCard = 52*numPacks;
      return topCard;
   }

   // Accessor for an individual card
   public Card inspectCard(int location) {
      if (topCard!=0 && location>=0 && location<topCard) {
         return cards[location];
      }
      else {
         return new Card('Z', Card.Suit.CLUBS); // generates error from cards
      }
   }

   // private method that will be called by the constructor
   private static void allocateMasterPack() {
      if (masterMade) {
         return;
      }
      else {
         masterPack=new Card[52];
         Card.Suit suit;

         for (int c=0; c<masterPack.length; c++) {
            masterPack[c] = new Card();
         }

         for (int s=0; s<4; s++) {
            if (s==0) {
               suit = Card.Suit.CLUBS;
            }
            else if (s==1) {
               suit = Card.Suit.DIAMONDS;
            }
            else if (s==2) {
               suit = Card.Suit.HEARTS;
            }
            else {
               suit = Card.Suit.SPADES;
            }

            masterPack[13*s].set('A', suit);
            int cardCount; //create card count
            char cardSuit; //create card suit
            for (cardSuit='2', cardCount=1; cardSuit<='9'; cardSuit++, cardCount++)
               masterPack[13*s+cardCount].set(cardSuit, suit);
            masterPack[13*s+9].set('T', suit);
            masterPack[13*s+10].set('J', suit);
            masterPack[13*s+11].set('Q', suit);
            masterPack[13*s+12].set('K', suit);
         }
         masterMade = true;
      }
   }
}
 /* SAMPLE RESULTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 Phase 1 Testing
Current Cards for phase 1
4 of HEARTS
Q of SPADES
J of CLUBS
Re-set Cards for phase 1
Invalid card.
Q of SPADES
T of HEARTS


Phase 2 Testing
Hand - 5 of CLUBS, K of CLUBS, 2 of HEARTS, 9 of DIAMONDS,


Inspecting a couple of cards; one in hand and one non-valid:
5 of CLUBS
null
Playing 9 of DIAMONDS
Playing 2 of HEARTS
Playing K of CLUBS
Playing 5 of CLUBS
Current hand:


Phase 3 Testing
A of CLUBS 2 of CLUBS 3 of CLUBS 4 of CLUBS 5 of CLUBS 6 of CLUBS 7 of CLUBS 8 of CLUBS 9 of CLUBS T of CLUBS J of CLUBS Q of CLUBS K of CLUBS A of DIAMONDS 2 of DIAMONDS 3 of DIAMONDS 4 of DIAMONDS 5 of DIAMONDS 6 of DIAMONDS 7 of DIAMONDS 8 of DIAMONDS 9 of DIAMONDS T of DIAMONDS J of DIAMONDS Q of DIAMONDS K of DIAMONDS A of HEARTS 2 of HEARTS 3 of HEARTS 4 of HEARTS 5 of HEARTS 6 of HEARTS 7 of HEARTS 8 of HEARTS 9 of HEARTS T of HEARTS J of HEARTS Q of HEARTS K of HEARTS A of SPADES 2 of SPADES 3 of SPADES 4 of SPADES 5 of SPADES 6 of SPADES 7 of SPADES 8 of SPADES 9 of SPADES T of SPADES J of SPADES Q of SPADES K of SPADES A of CLUBS 2 of CLUBS 3 of CLUBS 4 of CLUBS 5 of CLUBS 6 of CLUBS 7 of CLUBS 8 of CLUBS 9 of CLUBS T of CLUBS J of CLUBS Q of CLUBS K of CLUBS A of DIAMONDS 2 of DIAMONDS 3 of DIAMONDS 4 of DIAMONDS 5 of DIAMONDS 6 of DIAMONDS 7 of DIAMONDS 8 of DIAMONDS 9 of DIAMONDS T of DIAMONDS J of DIAMONDS Q of DIAMONDS K of DIAMONDS A of HEARTS 2 of HEARTS 3 of HEARTS 4 of HEARTS 5 of HEARTS 6 of HEARTS 7 of HEARTS 8 of HEARTS 9 of HEARTS T of HEARTS J of HEARTS Q of HEARTS K of HEARTS A of SPADES 2 of SPADES 3 of SPADES 4 of SPADES 5 of SPADES 6 of SPADES 7 of SPADES 8 of SPADES 9 of SPADES T of SPADES J of SPADES Q of SPADES K of SPADES

The shuffled deck:
2 of HEARTS A of CLUBS 3 of DIAMONDS 4 of CLUBS T of CLUBS T of DIAMONDS 8 of HEARTS 5 of HEARTS Q of SPADES A of DIAMONDS 3 of CLUBS 6 of CLUBS 2 of DIAMONDS 2 of SPADES 2 of CLUBS K of DIAMONDS T of SPADES J of DIAMONDS A of DIAMONDS 6 of HEARTS Q of DIAMONDS 9 of HEARTS Q of HEARTS K of HEARTS 8 of HEARTS 3 of DIAMONDS 4 of SPADES T of CLUBS 8 of CLUBS 3 of HEARTS A of HEARTS K of DIAMONDS 5 of DIAMONDS 8 of DIAMONDS 7 of HEARTS 4 of SPADES Q of CLUBS 4 of HEARTS 5 of SPADES 8 of DIAMONDS J of SPADES 8 of SPADES T of SPADES 5 of SPADES K of HEARTS 2 of CLUBS 3 of CLUBS 3 of HEARTS A of CLUBS T of HEARTS 7 of SPADES 6 of SPADES 7 of HEARTS 6 of CLUBS J of DIAMONDS 9 of SPADES A of HEARTS A of SPADES 4 of CLUBS Q of CLUBS Q of DIAMONDS K of CLUBS 5 of HEARTS 9 of HEARTS 8 of SPADES 6 of DIAMONDS J of CLUBS 5 of DIAMONDS K of CLUBS 9 of CLUBS J of HEARTS 4 of HEARTS 8 of CLUBS 7 of CLUBS 9 of SPADES 4 of DIAMONDS J of CLUBS J of HEARTS A of SPADES 6 of DIAMONDS K of SPADES 5 of CLUBS T of HEARTS 7 of CLUBS Q of HEARTS J of SPADES 2 of HEARTS 4 of DIAMONDS 9 of CLUBS 2 of SPADES 3 of SPADES 6 of SPADES 9 of DIAMONDS 7 of SPADES Q of SPADES 9 of DIAMONDS 5 of CLUBS 7 of DIAMONDS 7 of DIAMONDS 2 of DIAMONDS 3 of SPADES T of DIAMONDS K of SPADES 6 of HEARTS

Phase 4 Testing
Enter the number of players (1-10): 2
Hand - K of SPADES, J of SPADES, 9 of SPADES, 7 of SPADES, 5 of SPADES, 3 of SPADES, A of SPADES, Q of HEARTS, T of HEARTS, 8 of HEARTS, 6 of HEARTS, 4 of HEARTS, 2 of HEARTS, K of DIAMONDS, J of DIAMONDS, 9 of DIAMONDS, 7 of DIAMONDS, 5 of DIAMONDS, 3 of DIAMONDS, A of DIAMONDS, Q of CLUBS, T of CLUBS, 8 of CLUBS, 6 of CLUBS, 4 of CLUBS, 2 of CLUBS,

Hand - Q of SPADES, T of SPADES, 8 of SPADES, 6 of SPADES, 4 of SPADES, 2 of SPADES, K of HEARTS, J of HEARTS, 9 of HEARTS, 7 of HEARTS, 5 of HEARTS, 3 of HEARTS, A of HEARTS, Q of DIAMONDS, T of DIAMONDS, 8 of DIAMONDS, 6 of DIAMONDS, 4 of DIAMONDS, 2 of DIAMONDS, K of CLUBS, J of CLUBS, 9 of CLUBS, 7 of CLUBS, 5 of CLUBS, 3 of CLUBS, A of CLUBS,



 Now doing shuffled Hands

Hand - T of DIAMONDS, 5 of CLUBS, K of CLUBS, 5 of HEARTS, K of SPADES, T of SPADES, A of SPADES, J of HEARTS, 6 of SPADES, 6 of DIAMONDS, K of DIAMONDS, 3 of HEARTS, 6 of HEARTS, 9 of HEARTS, 2 of DIAMONDS, 8 of DIAMONDS, 3 of CLUBS, 9 of SPADES, 4 of CLUBS, 7 of SPADES, 8 of HEARTS, Q of DIAMONDS, K of HEARTS, 6 of CLUBS, 8 of SPADES, 4 of DIAMONDS,

Hand - T of HEARTS, J of SPADES, 2 of HEARTS, Q of SPADES, 2 of SPADES, 3 of SPADES, 2 of CLUBS, 8 of CLUBS, 9 of CLUBS, 7 of CLUBS, 5 of DIAMONDS, A of CLUBS, 9 of DIAMONDS, 4 of HEARTS, Q of CLUBS, Q of HEARTS, J of DIAMONDS, 7 of DIAMONDS, 7 of HEARTS, 4 of SPADES, J of CLUBS, 5 of SPADES, A of HEARTS, 3 of DIAMONDS, T of CLUBS, A of DIAMONDS,


C:\Users\John\Dropbox\College\CSUMB\CST338 Software Design\Assign3\src>java Assign3Main
Phase 1 Testing
Current Cards for phase 1
4 of HEARTS
Q of SPADES
J of CLUBS
Re-set Cards for phase 1
Invalid card.
Q of SPADES
T of HEARTS


Phase 2 Testing
Hand - 5 of CLUBS, K of CLUBS, 2 of HEARTS, 9 of DIAMONDS,


Inspecting a couple of cards; one in hand and one non-valid:
5 of CLUBS
null
Playing 9 of DIAMONDS
Playing 2 of HEARTS
Playing K of CLUBS
Playing 5 of CLUBS
Current hand:


Phase 3 Testing
A of CLUBS 2 of CLUBS 3 of CLUBS 4 of CLUBS 5 of CLUBS 6 of CLUBS 7 of CLUBS 8 of CLUBS 9 of CLUBS T of CLUBS J of CLUBS Q of CLUBS K of CLUBS A of DIAMONDS 2 of DIAMONDS 3 of DIAMONDS 4 of DIAMONDS 5 of DIAMONDS 6 of DIAMONDS 7 of DIAMONDS 8 of DIAMONDS 9 of DIAMONDS T of DIAMONDS J of DIAMONDS Q of DIAMONDS K of DIAMONDS A of HEARTS 2 of HEARTS 3 of HEARTS 4 of HEARTS 5 of HEARTS 6 of HEARTS 7 of HEARTS 8 of HEARTS 9 of HEARTS T of HEARTS J of HEARTS Q of HEARTS K of HEARTS A of SPADES 2 of SPADES 3 of SPADES 4 of SPADES 5 of SPADES 6 of SPADES 7 of SPADES 8 of SPADES 9 of SPADES T of SPADES J of SPADES Q of SPADES K of SPADES A of CLUBS 2 of CLUBS 3 of CLUBS 4 of CLUBS 5 of CLUBS 6 of CLUBS 7 of CLUBS 8 of CLUBS 9 of CLUBS T of CLUBS J of CLUBS Q of CLUBS K of CLUBS A of DIAMONDS 2 of DIAMONDS 3 of DIAMONDS 4 of DIAMONDS 5 of DIAMONDS 6 of DIAMONDS 7 of DIAMONDS 8 of DIAMONDS 9 of DIAMONDS T of DIAMONDS J of DIAMONDS Q of DIAMONDS K of DIAMONDS A of HEARTS 2 of HEARTS 3 of HEARTS 4 of HEARTS 5 of HEARTS 6 of HEARTS 7 of HEARTS 8 of HEARTS 9 of HEARTS T of HEARTS J of HEARTS Q of HEARTS K of HEARTS A of SPADES 2 of SPADES 3 of SPADES 4 of SPADES 5 of SPADES 6 of SPADES 7 of SPADES 8 of SPADES 9 of SPADES T of SPADES J of SPADES Q of SPADES K of SPADES

The shuffled deck:
Q of DIAMONDS Q of HEARTS T of CLUBS K of CLUBS 6 of HEARTS 5 of SPADES 8 of DIAMONDS 4 of DIAMONDS 3 of CLUBS 5 of DIAMONDS 6 of DIAMONDS 9 of HEARTS J of DIAMONDS J of HEARTS K of DIAMONDS A of SPADES 7 of CLUBS K of SPADES 2 of HEARTS T of HEARTS 5 of SPADES 3 of HEARTS Q of SPADES 2 of DIAMONDS 6 of SPADES 8 of HEARTS 2 of SPADES Q of HEARTS Q of DIAMONDS Q of SPADES 3 of SPADES 8 of SPADES 5 of CLUBS 2 of CLUBS T of HEARTS 7 of SPADES A of DIAMONDS 8 of HEARTS 8 of DIAMONDS 9 of CLUBS 5 of HEARTS J of CLUBS Q of CLUBS 3 of DIAMONDS T of DIAMONDS 9 of CLUBS 8 of CLUBS 4 of SPADES A of HEARTS 7 of CLUBS J of HEARTS 8 of SPADES 3 of HEARTS 9 of DIAMONDS 2 of HEARTS 6 of CLUBS 9 of HEARTS 5 of CLUBS 2 of CLUBS 4 of HEARTS J of CLUBS 6 of DIAMONDS 7 of DIAMONDS K of CLUBS 6 of HEARTS 3 of DIAMONDS K of SPADES K of HEARTS T of SPADES K of HEARTS 4 of SPADES 7 of HEARTS A of HEARTS 5 of DIAMONDS 3 of SPADES T of SPADES 4 of HEARTS 3 of CLUBS 9 of DIAMONDS 7 of SPADES A of SPADES 6 of CLUBS 4 of DIAMONDS K of DIAMONDS Q of CLUBS A of CLUBS T of DIAMONDS 5 of HEARTS T of CLUBS A of DIAMONDS 6 of SPADES 4 of CLUBS 2 of SPADES J of SPADES 2 of DIAMONDS J of DIAMONDS 9 of SPADES J of SPADES 4 of CLUBS 9 of SPADES 7 of DIAMONDS 8 of CLUBS 7 of HEARTS A of CLUBS

Phase 4 Testing
Enter the number of players (1-10): 3
Hand - K of SPADES, T of SPADES, 7 of SPADES, 4 of SPADES, A of SPADES, J of HEARTS, 8 of HEARTS, 5 of HEARTS, 2 of HEARTS, Q of DIAMONDS, 9 of DIAMONDS, 6 of DIAMONDS, 3 of DIAMONDS, K of CLUBS, T of CLUBS, 7 of CLUBS, 4 of CLUBS, A of CLUBS,

Hand - Q of SPADES, 9 of SPADES, 6 of SPADES, 3 of SPADES, K of HEARTS, T of HEARTS, 7 of HEARTS, 4 of HEARTS, A of HEARTS, J of DIAMONDS, 8 of DIAMONDS, 5 of DIAMONDS, 2 of DIAMONDS, Q of CLUBS, 9 of CLUBS, 6 of CLUBS, 3 of CLUBS,

Hand - J of SPADES, 8 of SPADES, 5 of SPADES, 2 of SPADES, Q of HEARTS, 9 of HEARTS, 6 of HEARTS, 3 of HEARTS, K of DIAMONDS, T of DIAMONDS, 7 of DIAMONDS, 4 of DIAMONDS, A of DIAMONDS, J of CLUBS, 8 of CLUBS, 5 of CLUBS, 2 of CLUBS,



 Now doing shuffled Hands

Hand - 6 of CLUBS, J of CLUBS, 6 of DIAMONDS, 7 of CLUBS, 5 of CLUBS, K of SPADES, Q of SPADES, 3 of SPADES, 3 of CLUBS, 2 of SPADES, 7 of SPADES, 9 of CLUBS, 5 of SPADES, 5 of DIAMONDS, 3 of HEARTS, 4 of SPADES, K of DIAMONDS, 9 of HEARTS,

Hand - 3 of DIAMONDS, 4 of CLUBS, 9 of DIAMONDS, A of CLUBS, 8 of SPADES, K of HEARTS, 7 of HEARTS, Q of HEARTS, T of DIAMONDS, A of HEARTS, 2 of HEARTS, J of HEARTS, A of SPADES, T of HEARTS, J of SPADES, T of SPADES, A of DIAMONDS,

Hand - 4 of HEARTS, 9 of SPADES, 6 of SPADES, K of CLUBS, J of DIAMONDS, 5 of HEARTS, 2 of CLUBS, 8 of DIAMONDS, 6 of HEARTS, Q of CLUBS, T of CLUBS, 7 of DIAMONDS, 8 of CLUBS, 8 of HEARTS, 2 of DIAMONDS, Q of DIAMONDS, 4 of DIAMONDS,


C:\Users\John\Dropbox\College\CSUMB\CST338 Software Design\Assign3\src>java Assign3Main
Phase 1 Testing
Current Cards for phase 1
4 of HEARTS
Q of SPADES
J of CLUBS
Re-set Cards for phase 1
Invalid card.
Q of SPADES
T of HEARTS


Phase 2 Testing
Hand - 5 of CLUBS, K of CLUBS, 2 of HEARTS, 9 of DIAMONDS,


Inspecting a couple of cards; one in hand and one non-valid:
5 of CLUBS
null
Playing 9 of DIAMONDS
Playing 2 of HEARTS
Playing K of CLUBS
Playing 5 of CLUBS
Current hand:


Phase 3 Testing
A of CLUBS 2 of CLUBS 3 of CLUBS 4 of CLUBS 5 of CLUBS 6 of CLUBS 7 of CLUBS 8 of CLUBS 9 of CLUBS T of CLUBS J of CLUBS Q of CLUBS K of CLUBS A of DIAMONDS 2 of DIAMONDS 3 of DIAMONDS 4 of DIAMONDS 5 of DIAMONDS 6 of DIAMONDS 7 of DIAMONDS 8 of DIAMONDS 9 of DIAMONDS T of DIAMONDS J of DIAMONDS Q of DIAMONDS K of DIAMONDS A of HEARTS 2 of HEARTS 3 of HEARTS 4 of HEARTS 5 of HEARTS 6 of HEARTS 7 of HEARTS 8 of HEARTS 9 of HEARTS T of HEARTS J of HEARTS Q of HEARTS K of HEARTS A of SPADES 2 of SPADES 3 of SPADES 4 of SPADES 5 of SPADES 6 of SPADES 7 of SPADES 8 of SPADES 9 of SPADES T of SPADES J of SPADES Q of SPADES K of SPADES A of CLUBS 2 of CLUBS 3 of CLUBS 4 of CLUBS 5 of CLUBS 6 of CLUBS 7 of CLUBS 8 of CLUBS 9 of CLUBS T of CLUBS J of CLUBS Q of CLUBS K of CLUBS A of DIAMONDS 2 of DIAMONDS 3 of DIAMONDS 4 of DIAMONDS 5 of DIAMONDS 6 of DIAMONDS 7 of DIAMONDS 8 of DIAMONDS 9 of DIAMONDS T of DIAMONDS J of DIAMONDS Q of DIAMONDS K of DIAMONDS A of HEARTS 2 of HEARTS 3 of HEARTS 4 of HEARTS 5 of HEARTS 6 of HEARTS 7 of HEARTS 8 of HEARTS 9 of HEARTS T of HEARTS J of HEARTS Q of HEARTS K of HEARTS A of SPADES 2 of SPADES 3 of SPADES 4 of SPADES 5 of SPADES 6 of SPADES 7 of SPADES 8 of SPADES 9 of SPADES T of SPADES J of SPADES Q of SPADES K of SPADES

The shuffled deck:
K of DIAMONDS Q of CLUBS A of HEARTS 3 of CLUBS 4 of HEARTS T of CLUBS 9 of HEARTS 2 of SPADES 4 of DIAMONDS 7 of SPADES 3 of HEARTS Q of CLUBS 8 of DIAMONDS 5 of HEARTS 7 of SPADES 5 of CLUBS 5 of HEARTS 7 of CLUBS 6 of DIAMONDS 2 of DIAMONDS 8 of CLUBS 9 of CLUBS 5 of DIAMONDS Q of HEARTS 9 of DIAMONDS 7 of HEARTS 2 of DIAMONDS Q of HEARTS J of HEARTS 3 of SPADES 7 of DIAMONDS T of CLUBS 4 of SPADES K of SPADES 5 of SPADES A of CLUBS 6 of HEARTS 5 of DIAMONDS 4 of HEARTS 6 of DIAMONDS 6 of CLUBS 5 of CLUBS 7 of DIAMONDS J of CLUBS 4 of SPADES 9 of SPADES Q of DIAMONDS 8 of CLUBS 6 of HEARTS T of DIAMONDS 2 of HEARTS A of HEARTS A of CLUBS 2 of SPADES J of DIAMONDS 4 of CLUBS K of HEARTS A of DIAMONDS 4 of DIAMONDS 2 of CLUBS J of CLUBS Q of SPADES 9 of HEARTS 6 of SPADES 7 of CLUBS K of DIAMONDS 3 of SPADES 5 of SPADES 3 of HEARTS T of HEARTS K of CLUBS T of HEARTS 2 of HEARTS 8 of SPADES 7 of HEARTS 9 of DIAMONDS 6 of SPADES A of SPADES 4 of CLUBS J of SPADES T of SPADES 8 of DIAMONDS J of HEARTS 8 of HEARTS Q of DIAMONDS K of CLUBS 6 of CLUBS A of DIAMONDS K of SPADES 8 of HEARTS K of HEARTS 3 of DIAMONDS Q of SPADES A of SPADES J of DIAMONDS T of SPADES 8 of SPADES 9 of SPADES J of SPADES 3 of DIAMONDS 9 of CLUBS 2 of CLUBS T of DIAMONDS 3 of CLUBS

Phase 4 Testing
Enter the number of players (1-10): 4
Hand - K of SPADES, 9 of SPADES, 5 of SPADES, A of SPADES, T of HEARTS, 6 of HEARTS, 2 of HEARTS, J of DIAMONDS, 7 of DIAMONDS, 3 of DIAMONDS, Q of CLUBS, 8 of CLUBS, 4 of CLUBS,

Hand - Q of SPADES, 8 of SPADES, 4 of SPADES, K of HEARTS, 9 of HEARTS, 5 of HEARTS, A of HEARTS, T of DIAMONDS, 6 of DIAMONDS, 2 of DIAMONDS, J of CLUBS, 7 of CLUBS, 3 of CLUBS,

Hand - J of SPADES, 7 of SPADES, 3 of SPADES, Q of HEARTS, 8 of HEARTS, 4 of HEARTS, K of DIAMONDS, 9 of DIAMONDS, 5 of DIAMONDS, A of DIAMONDS, T of CLUBS, 6 of CLUBS, 2 of CLUBS,

Hand - T of SPADES, 6 of SPADES, 2 of SPADES, J of HEARTS, 7 of HEARTS, 3 of HEARTS, Q of DIAMONDS, 8 of DIAMONDS, 4 of DIAMONDS, K of CLUBS, 9 of CLUBS, 5 of CLUBS, A of CLUBS,



 Now doing shuffled Hands

Hand - 9 of DIAMONDS, 8 of DIAMONDS, 4 of DIAMONDS, J of SPADES, Q of CLUBS, 7 of HEARTS, 5 of SPADES, 4 of HEARTS, 2 of SPADES, A of HEARTS, K of HEARTS, 8 of CLUBS, T of HEARTS,

Hand - 3 of DIAMONDS, A of DIAMONDS, 9 of SPADES, J of CLUBS, K of DIAMONDS, 6 of SPADES, 5 of HEARTS, 5 of CLUBS, 9 of CLUBS, Q of HEARTS, 8 of HEARTS, T of CLUBS, 6 of DIAMONDS,

Hand - J of DIAMONDS, 2 of DIAMONDS, 4 of CLUBS, 4 of SPADES, 9 of HEARTS, J of HEARTS, Q of SPADES, 3 of HEARTS, T of SPADES, 3 of SPADES, Q of DIAMONDS, 8 of SPADES, 2 of CLUBS,

Hand - 2 of HEARTS, T of DIAMONDS, 3 of CLUBS, A of SPADES, 7 of CLUBS, A of CLUBS, 6 of HEARTS, K of SPADES, 5 of DIAMONDS, K of CLUBS, 7 of SPADES, 6 of CLUBS, 7 of DIAMONDS,

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
