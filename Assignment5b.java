// John Lester & Stephanie Gan
// CST 338-30_FA16
// Write a Java program (GUI Cards) (M5) PHASE 2
/* NOTE: Be sure card images from https://myetudes.org/access/content/user/jece75856/images.zip
are in the /images subfolder. */

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Assignment5b
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
   public static String[] suitRanks = {"C", "D", "S", "H"};

   /*static Card generateRandomCard()
   {
      String suit = "";
      char val;

      int suitSelector, valSelector;

      // get random suit and value
      suitSelector = (int) (Math.random() * 4);
      valSelector = (int) (Math.random() * 13);

      // pick suit
      suit = GUICard.turnIntIntoCardSuit(suitSelector);
      val = GUICard.turnIntIntoVal(valSelector);

      return new Card(val, suit);
   }*/

   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;

      // establish main frame in which program will run
      CardTable myCardTable 
      = new CardTable("CS 1B CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // calling deck class to create deck
      Deck myDeck = new Deck(1);
      //shuffling the deck
      myDeck.shuffle();
      
      //creating hands for a human and for a computer
      Hand compHand = new Hand(); // the evil hand
      Hand humanHand = new Hand(); // the better hand
      
      // both hands taking cards
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
    	  humanHand.takeCard(myDeck.dealCard());
    	  // creating human labels
    	  humanLabels[i] = new JLabel(GUICard.getIcon(humanHand.inspectCard(i)));
    	  
    	  compHand.takeCard(myDeck.dealCard());
    	  // creating computer labels
    	  computerLabels[i] = new JLabel(GUICard.getIcon(compHand.inspectCard(i)));
      }
      
      // show everything to the user
      myCardTable.setVisible(true);


      // ADD LABELS TO PANELS -----------------------------------------
      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
         myCardTable.pnlHumanHand.add(humanLabels[k]);

      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
         myCardTable.pnlComputerHand.add(computerLabels[k]);

      // and two random cards in the play region (simulating a computer/hum ply)
     // for(k = 0; k < NUM_PLAYERS; k++)
        // playedCardLabels[k] = new JLabel(GUICard.getIcon(
              // generateRandomCard()));

      playLabelText[0] = new JLabel("Computer", 0);
      playLabelText[1] = new JLabel("You", 0);
      playedCardLabels[0] = new JLabel(GUICard.getIcon(humanHand.inspectCard(2)));
      playedCardLabels[1] = new JLabel(GUICard.getIcon(compHand.inspectCard(2)));
      
      //Adding cards to the play area panel.
      for(k = 0; k < NUM_PLAYERS; k++)
      {
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
         //myCardTable.pnlPlayArea.add(playLabelText[k]);
      }
      //Adding lables to the play area panel under the cards.
      myCardTable.pnlPlayArea.add(playLabelText[0]);
      myCardTable.pnlPlayArea.add(playLabelText[1]);

      // show everything to the user
      myCardTable.setVisible(true);
   }

}

class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games

   private int numCardsPerHand;
   private int numPlayers;

   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      super(title);

      if(numCardsPerHand <= MAX_CARDS_PER_HAND && numPlayers <= MAX_PLAYERS)
      {
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = numPlayers;

         pnlComputerHand = new JPanel();
         pnlPlayArea = new JPanel(new GridLayout(2, 2));
         pnlHumanHand = new JPanel();

         setLayout(new BorderLayout(20, 10));
         add(pnlComputerHand, BorderLayout.NORTH );
         add(pnlPlayArea, BorderLayout.CENTER);
         add(pnlHumanHand, BorderLayout.SOUTH);

         pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
         pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
         pnlHumanHand.setBorder(new TitledBorder("Your Hand"));        
      }      
   }

   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   public int getNumplayers()
   {
      return numPlayers;
   }
}


class GUICard
{ 
   private static Icon[][] iconCards = new ImageIcon[14][4]; 
   private static Icon iconBack;
   static boolean iconsLoaded = false;
   private static String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X"};
   private static String[] suits = {"C", "D", "S", "H"};
   
   static Card.Suit turnIntIntoSuit(int suitSelector) 
   {
      if(suitSelector >= 0 && suitSelector <= 3)
         return Card.suitRanks[suitSelector];
      return Card.suitRanks[0]; //returns default suit
   }

   static char turnIntIntoVal(int valSelector)
   {
      if(valSelector >= 0 && valSelector <= 13)
         return Card.valueRanks[valSelector];
      return Card.valueRanks[12];//returning default value A.
   }

   static public Icon getIcon(Card card)
   {
      loadCardIcons(); // will not load twice, so no worries.
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   private static int suitAsInt(Card card)
   {
      Card.Suit cardsSuit = card.getSuit();
      for(int k = 0; k <= 3; k++)
      {
         if(suits[k].equals(card.getSuit().name().substring(0, 1)) )
            return k;
      }
      return 0;//return default suit clubs.
   }

   private static int valueAsInt(Card card)
   {
      char cardsValue = card.getValue(); 

      if(cardsValue == 'A')
         return 0;
      if(cardsValue == 'X')
         return 13;
      for(int k = 0; k <= 11; k++)
      {
         if(Card.valueRanks[k] == cardsValue) 
            return k + 1;
      }
      return 0;//return default value A.
   }

   static public Icon getBackCardIcon()
   {
      return iconBack;
   }

   static void loadCardIcons()
   {
      if(!iconsLoaded)
      {
         for(int j = 0; j < 4; j++)
         {
            for(int k = 0; k <= 13; k++)
            {
               iconCards[k][j] = new ImageIcon("src/images/" + turnIntIntoCardValue(k) 
                     + turnIntIntoCardSuit(j) + ".gif");
            }        
         }
         iconBack = new ImageIcon("src/images/BK.gif");
         iconsLoaded = true;
      }
   }

   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      // an idea for a helper method (do it differently if you wish)
      String returnValue = null;
      String[] compValue = {"A", "2", "3", "4", "5", "6", "7", "8", "9", 
            "T", "J", "Q", "K", "X"};
      if(k >=0 && k <= 13)
      {
         returnValue = compValue[k];
      }else{
         System.out.println("returning defalut value A");
         return compValue[0];//returns default value "A".
      }
      return returnValue; 
   }

   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      // an idea for another helper method (do it differently if you wish)
      String returnSuit = null;
      String[] compSuit = {"C", "D", "H", "S"};
      if(j >=0 && j <= 3)
      {
         returnSuit = compSuit[j];
      }else{
         System.out.println("returning defalut suit C");
         return compSuit[0];//returns default value "A".
      }
      return returnSuit; 
   }

}

// class Card from Assignment 3
class Card {
   //create values for value and suit
   public enum Suit {
      SPADES, DIAMONDS, HEARTS, CLUBS
   }
   private char value;
   private Suit suit;
   private boolean errorFlag;
   public static char[] valueRanks = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X'};

   //default constructor
   public Card() {
      this('A', Suit.SPADES);
   }

   //constructor to set the card's value and suit
   public Card(char value, Suit suit) {
      set(value, suit);
   }
   
   public Card(char value, String suit) {
	   Suit tempSuit;
	   
	   if(suit == "C") {
		   tempSuit = Suit.CLUBS;
	   }
	   
	   else if(suit == "S") {
			tempSuit = Suit.SPADES;
	   }
	   
	   else if(suit == "H") {
		   tempSuit = Suit.HEARTS;
	   }
	   
	   else {
		   tempSuit = Suit.DIAMONDS;
	   }
	   
	   set(value, tempSuit);
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


   // sort member methods
   public int compareTo(Card other)
   {
      if (this.value == other.value)
         return ( getSuitRank(this.suit) - getSuitRank(other.suit) );

      return (
            getValueRank(this.value)
            - getValueRank(other.value)
            );
   }

   public static void setRankingOrder(
         char[] valueOrderArr, Suit[] suitOrdeArr,
         int numValsInOrderingArray )
   {
      int k;

      // expects valueOrderArr[] to contain only cards used per pack,
      // including jokers, needed to define order for the game environment

      if (numValsInOrderingArray < 0 || numValsInOrderingArray > 13)
         return;

      Card.numValsInOrderingArray = numValsInOrderingArray;

      for (k = 0; k < numValsInOrderingArray; k++)
         Card.valueRanks[k] = valueOrderArr[k];

      for (k = 0; k < 4; k++)
         Card.suitRanks[k] = suitOrdeArr[k];
   }

   public static int getSuitRank(Suit st)
   {
      int k;

      for (k = 0; k < 4; k++)
         if (suitRanks[k] == st)
            return k;

      // should not happen
      return 0;
   }

   public  static int getValueRank(char val)
   {
      int k;

      for (k = 0; k < numValsInOrderingArray; k++)
         if (valueRanks[k] == val)
            return k;

      // should not happen
      return 0;
   }

   public static void arraySort(Card[] array, int arraySize)
   {
      for (int k = 0; k < arraySize; k++)
         if (!floatLargestToTop(array, arraySize - 1 - k))
            return;
   }

   private static boolean floatLargestToTop(Card[] array, int top)
   {
      boolean changed = false;
      Card temp;

      for (int k = 0; k < top; k++)
         if (array[k].compareTo(array[k+1]) > 0)
         {
            temp = array[k];
            array[k] = array[k+1];
            array[k+1] = temp;
            changed = true;
         };
         return changed;
   }


}

// class Hand from Assignment 3
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

// class Deck from Assignment 3
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
