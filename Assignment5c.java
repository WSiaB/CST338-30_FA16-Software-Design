// John Lester & Stephanie Gan
// CST 338-30_FA16
// Write a Java program (GUI Cards) (M5) PHASE 3
/* NOTE: Be sure card images from https://myetudes.org/access/content/user/jece75856/images.zip
are in the /images subfolder. */

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Assignment5c
{
   static int CARDS_PER_HAND = 7;
   static int PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[PLAYERS];

   static Card generateRandomCard()
   {
      Card.Suit suit;
      char value;
      int suitSelector, valueSelector;

      suitSelector = (int) (Math.random() * 4);
      valueSelector = (int) (Math.random() * 13);
      suit = turnIntIntoSuit(suitSelector);
      value = turnIntIntoVal(valueSelector);
      return new Card(value, suit);
   }

   static Card.Suit turnIntIntoSuit(int suitSelector) 
   {
      if(suitSelector >= 0 && suitSelector <= 3)
         return Card.suitRanks[suitSelector];
      return Card.suitRanks[0]; //returns default
   }

   static char turnIntIntoVal(int valueSelector)
   {
      if(valueSelector >= 0 && valueSelector <= 13)
         return Card.valueRanks[valueSelector];
      return Card.valueRanks[12]; //returns default
   }

   public static void main(String[] args)
   {
      int count;
      Icon tempIcon;
      
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            PLAYERS, CARDS_PER_HAND);

      highCardGame.deal();

      // establish main frame in which program will run
      CardTable myCardTable = new CardTable("CardTable", CARDS_PER_HAND, PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS ----------------------------------------------------
      for(count=0; count<CARDS_PER_HAND; count++)
         humanLabels[count] = new JLabel(GUICard.getIcon(highCardGame.getHand(1).inspectCard(count)));
      for(count=0; count<CARDS_PER_HAND; count++)
         computerLabels[count] = new JLabel(GUICard.getBackCardIcon());
  
      // ADD LABELS TO PANELS -----------------------------------------
      for(count=0; count<CARDS_PER_HAND; count++)
         myCardTable.pnlHumanHand.add(humanLabels[count]);
      for(count=0; count<CARDS_PER_HAND; count++)
         myCardTable.pnlComputerHand.add(computerLabels[count]);
      
      // and two random cards in the play region (simulating a computer/hum ply)
      for(count=0; count<PLAYERS; count++)
         playedCardLabels[count] = new JLabel(GUICard.getIcon(generateRandomCard()));
      playLabelText[0] = new JLabel("Computer", 0);
      playLabelText[1] = new JLabel("Player", 0);

      // show everything to the user
      myCardTable.setVisible(true);
   }
}

class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;
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
}

class GUICard
{ 
   private static Icon[][] iconCards = new ImageIcon[14][4]; 
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   static public Icon getIcon(Card card)
   {
      loadCardIcons();
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   private static int suitAsInt(Card card)
   {
      Card.Suit cardsSuit = card.getSuit();
      for(int value=0; value<=13; value++)
      {
         if(Card.suitRanks[value] == cardsSuit) 
            return value;
      }
      return 0; // returns default
   }

   private static int valueAsInt(Card card)
   {
      char cardsValue = card.getValue(); 
      if(cardsValue == 'A')
         return 0;
      if(cardsValue == 'X')
         return 13;
      for(int suit=0; suit<=11; suit++)
      {
         if(Card.valueRanks[suit] == cardsValue) 
            return suit++;
      }
      return 0; //returns default
   }

   static public Icon getBackCardIcon()
   {
      return iconBack;
   }

   static void loadCardIcons()
   {
      if(!iconsLoaded)
      {
         for(int suit=0; suit<4; suit++)
         {
            for(int value = 0; value<=13; value++)
            {
               iconCards[value][suit] = new ImageIcon("images/" + turnIntIntoCardValue(value) + turnIntIntoCardSuit(suit) + ".gif");
            }        
         }
         iconBack = new ImageIcon("images/BK.gif");
         iconsLoaded = true;
      }
   }

   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int value)
   {
      // an idea for a helper method (do it differently if you wish)
      String returnValue = null;
      String[] compValue = {"A", "2", "3", "4", "5", "6", "7", "8", "9", 
            "T", "J", "Q", "K", "X"};
      if(value>=0 && value<=13)
      {
         returnValue = compValue[value];
      } else {
         returnValue = compValue[0]; //returns default
      }
      return returnValue; 
   }

   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int suit)
   {
      // an idea for another helper method (do it differently if you wish)
      String returnSuit = null;
      String[] compSuit = {"C", "D", "H", "S"};
      if(suit>=0 && suit<=3)
      {
         returnSuit = compSuit[suit];
      } else {
         returnSuit = compSuit[0]; //returns default
      }
      return returnSuit; 
   }
}

// From Assignment 3 with additional methods
class Card {   
   //create values for value and suit
   public enum Suit {
      SPADES, DIAMONDS, HEARTS, CLUBS
   }
   private char value;
   private Suit suit;
   boolean errorFlag;

   public enum State {deleted, active}
   State state;

   // for sort.  
   public static char[] valueRanks = { '2', '3', '4', '5', '6', '7', '8', '9',
      'T', 'J', 'Q', 'K', 'A', 'X'};
   static Suit[] suitRanks = {Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS,
      Suit.SPADES};
   static int numValsInOrderingArray = 14;  // 'X' = Joker

   //default constructor
   public Card() {
      this('A', Suit.SPADES);
   }

   //constructor to set the card's value and suit
   public Card(char value, Suit suit) {
      set(value, suit);
   }

   // copy constructor
   public Card(Card card)
   {
      this(card.value, card.suit);
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
      char[] values = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
       
      //initializing boolean
      boolean check = false;

      //checking the value against the character array values
      for (char c : values) {
         if (Character.toUpperCase(value) == c) {
            check = true;
         }
      }
      return check;       
   }

   public String toString()
   {
      //initialize string
      String display = "";
      
      if (this.getErrorFlag() == true)
         display = "Invalid card.";
      else if (state == State.deleted)
         display = "Card deleted.";
      else if (value == 'X')
         display = "Joker";
      else
         display = String.valueOf(value) + " of " + String.valueOf(suit);

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

   //check value and suit against values passed in
   public boolean equals(Card card) {
      boolean checkCard = false;
      if (this.getSuit() == card.getSuit()) {
         if (this.getValue() == card.getValue()) {
            checkCard = true;
         }
      }
      return checkCard;
   }

   // sort method
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
      return 0;
   }

   public  static int getValueRank(char val)
   {
      int k;
      for (k = 0; k < numValsInOrderingArray; k++)
         if (valueRanks[k] == val)
            return k;
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

class Hand {
   //defining a max number of cards here to avoid a huge array by default
   public int MAX_CARDS = 100;
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

   void sort()
   {
      Card.arraySort(myCards, numCards);
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
         
         for (int i=0; i < this.getTopCard(); i++) {
           cards[i] = masterPack[i%52];
         }
      }
   }

public int getNumCards()
   {
      return topCard;
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

   public boolean removeCard(Card card)
   {
      boolean foundOne;

      foundOne = false;
      for (int count=0; count<topCard; count++)
      {
         while (cards[count].equals(card))
         {
            cards[count] = cards[topCard-1];
            topCard--;
            foundOne = true;
            if (count>=topCard )
               break;
         }
      }
      return foundOne;
   }

   public boolean addCard(Card card)
   {
      if (cardCount(card)>=numPacks)
         return false;
      else
         cards[topCard++]=card;
      return true;
   }

   public int cardCount(Card card)
   {
      int retValue = 0;
      for (int count=0; count<topCard; count++)
      {
         if (inspectCard(count).equals(card))
            retValue++;
      }
      return retValue;
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

class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;            // # standard 52-card packs per deck
   // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
   // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
   // in the game.  e.g. pinochle does not
   // use cards 2-8 of any suit

   public CardGameFramework( int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
         int numPlayers, int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if  (numCardsPerHand < 1 ||
            numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
            / numPlayers )
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard( unusedCardsPerPack[k] );

      // add jokers
      for (k = 0; k < numPacks; k++)
         for ( j = 0; j < numJokersPerPack; j++)
            deck.addCard( new Card('X', Card.Suit.values()[j]) );

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard( deck.dealCard() );
            else
            {
               enoughCards = false;
               break;
            }
      }

      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }
}
