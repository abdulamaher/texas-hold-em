/* Simplified and User Friendly Texas Hold'Em Poker Game 
 A card game for ICS3U Summative Project 
 Created by Abdula Maher 
 For Mr. Jay // ICS3U 
 On Saturday, December 17, 2016 */ 

import java.awt.*; 
import hsa.Console; 
import javax.imageio.*; 
import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random; 
 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
 
import java.awt.Graphics; 
import java.awt.Color; 
import java.awt.Window; 
 
import javax.swing.*; 
import javax.swing.JFrame; 
import javax.swing.JButton; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
import javax.swing.JTextField; 
import javax.swing.JLabel;

public class ma_Summative extends JPanel
{
	// Global Random Object
	static Random rand = new Random ();
	
	// Stores the Username
	static String playerName; 
	  
	// Arrays to hold all the cards
	static int [] player = new int [2];
    static int [] compA = new int [2];
    static int [] compB = new int [2];
    static int [] compC = new int [2];
    static int [] communityCards = new int [5];
        
    // Accumulates the value of bets placed
    static int playerBet = 0;
    static int compABet = 0;
    static int compBBet = 0;
    static int compCBet = 0;
    static int totalBets = 0;
    
    // JFrame and JPanel for graphics
    static JFrame jF;
    static JPanel jP;
    static JButton raise, call, bet;
    
    // Allow for different actions depending on turn
    static boolean firstTurn = true, secondTurn = false, thirdTurn = false, lastTurn = false;
    
    // Initialization of complete deck
	static int [] deck = makeDeck ();
	
	// Initialization of Images
	static JLabel [] labelDeck = loadCardImages (deck);
	static ImageIcon backCard = loadBackCard ();
    static JLabel [] compCards = loadCompCards ();

    public ma_Summative ()
    {
    	// Making J Panel
    	jP = new JPanel ();
    	jP.setLayout(null);

    	// Call the TitleScreen Method
    	TitleScreen ();
    }
    
    // main method
    public static void main (String [] args)
    {   	
    	// Displays the J Frame
    	JFrame ();
    }

    // JFrame method
    public static void JFrame ()
    {
    	jF = new JFrame ();
    	
    	ma_Summative r = new ma_Summative ();
    	
    	// Set the size of the J Frame
    	jF.setSize(692, 548);
    	
    	// Set the title of the J Frame
    	jF.setTitle("Texas Hold'Em");
    	
    	// Makes the J Frame visible
    	jF.setVisible(true);
    	
        jF.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); 
        
        // Does not allow the J Frame to be resized
        jF.setResizable (false); 
        jF.add (r);
    }
    
    // TitleScreen method
    public void TitleScreen ()
    {
    	// Load the background
        ImageIcon backgroundImg = loadBackground ();
    	
        // Creating fonts for title screen
    	Font impact = new Font ("Impact", Font.PLAIN, 48), arial = new Font ("Arial", Font.PLAIN, 15);
    	
    	// J Button to go to next screen
    	JButton play = new JButton ("Play");
    	
    	// J Label to display title
    	JLabel label1, label2, label3, background;
    	
    	// J Text Field to get username
    	JTextField textField = new JTextField (10);
    	
    	// Set background to J Label and set position
    	background = new JLabel (backgroundImg);
    	background.setBounds(0, 0, 692, 548);
    	
    	// Set title message
    	label1 = new JLabel ("Welcome to Texas Hold'Em");
    	label1.setBounds (85, 25, 1000, 100);
    	
    	label2 = new JLabel ("Poker");
    	label2.setBounds(280, 75, 1000, 100);
    	
    	label1.setFont(impact);
    	label2.setFont(impact);
    	
    	// Prompt user to enter username
    	label3 = new JLabel ("Enter your username and press 'Play'");
    	label3.setBounds(235, 200, 500, 50);
    	
    	label3.setFont (arial);
    	
    	textField.setBounds(277, 250, 125, 25);
    	
    	// Set the position of play J Button
    	play.setBounds(297, 300, 80, 30);
    	
    	// Add all components to display on J Panel
    	jP.add(label1);
    	jP.add(label2);
    	jP.add(label3);
    	
    	jP.add(play);
    	
    	jP.add(textField);

    	jP.add(background);
    	
    	// Action to be performed when username is entered
    	textField.addActionListener (new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{
    			String playerNameS = textField.getText();
    			System.out.print(playerNameS + " is playing\n\n");

    			playerName = playerNameS;
    			
    			// Call GameScreen method
    			GameScreen ();
    		}
    	});
    	
    	play.addActionListener(new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{
    			String playerNameS = textField.getText();
    			System.out.print(playerNameS + " is playing\n\n");

    			playerName = playerNameS;
    			
    			// Call GameScreen method
    			GameScreen ();
    		}
    	});
    	
    	// Add the J Panel to J Frame
    	jF.add(jP);
    }

    // GameScreen method
    public void GameScreen ()
    {
    	// J Labels for players
    	JLabel player = new JLabel (playerName), 
    			compA = new JLabel ("Computer 1"), 
    			compB = new JLabel ("Computer 2"), 
    			compC = new JLabel ("Computer 3");
    	
    	// Removes all components from title screen
    	jP.removeAll();
    	
    	// Set the position of components
    	player.setBounds (282, 382, 100, 100);
    	compA.setBounds (15, 255, 100, 100);
    	compB.setBounds(282, 82, 100, 100);
    	compC.setBounds (480, 255, 100, 100);
    	
    	// Show remaining deck facing down
    	showBackCard (backCard, 765, 400);
    	
    	// Call dealDeck method
    	deck = dealDeck (deck, labelDeck);
    	
    	// Call firstBet method
    	firstBet ();

    	// Add all components to J Panel
    	jP.add (player);
    	jP.add (compA);
    	jP.add (compB);
    	jP.add (compC);
    	
    	// Repaint and update the J Panel
    	jP.repaint();
    }
    
    // firstBet method
    public void firstBet ()
    {
    	// Creation of new font
    	Font arial = new Font ("Arial", Font.PLAIN, 15);
    	
    	// New Text Field and Label to get user to enter bet
    	JTextField textField = new JTextField (10);
    	JLabel label = new JLabel ("How much would you like to bet? ($)");
    	
    	// New Button and position of Button
    	bet = new JButton ("Bet");
    	bet.setBounds(500, 460, 80, 30);
    	
    	// Set position of Text Field
    	textField.setBounds(350, 463, 125, 25);
    	
    	// Set position of Label
    	label.setBounds(70, 450, 520, 50);
    	label.setFont(arial);
    	
    	// Add all components to J Panel
    	jP.add(textField);
    	jP.add(label);
    	jP.add(bet);
    	
    	// Action to be performed when bet is entered 
    	textField.addActionListener (new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{
    			// Read value of bet
    			String playerBetS = textField.getText();
    			
    			// Convert String bet to integer
    			playerBet = Integer.parseInt(playerBetS);
    			    			
    			// Remove the Text Field and Button
    			jP.remove(textField);
    			jP.remove(label);
    			jP.remove(bet);
    			
    			// Call the bet method
    			bet (playerBet);
    			
    			// Print the bet user has places
    			System.out.println ("\n" + playerName + " has bet $" + playerBet);
    			
    			// Call compTurnA method
    			compTurnA ();
    		}
    	});
    	
    	// Action to be performed when bet is pressed 
    	bet.addActionListener (new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{
    			// Read value of bet
    			String playerBetS = textField.getText();
    			
    			// Convert String bet to integer
    			playerBet = Integer.parseInt(playerBetS);
    			    			
    			// Remove the Text Field and Button
    			jP.remove(textField);
    			jP.remove(label);
    			jP.remove(bet);
    			
    			// Call the bet method
    			bet (playerBet);
    			
    			// Print the bet user has places
    			System.out.println ("\n" + playerName + " has bet $" + playerBet);
    			
    			// Call compTurnA method
    			compTurnA ();
    		}
    	});
    }
    
    // bet method
    public static void bet (int bet)
    {
    	// Update totalBets
    	totalBets = bet;
    	
    	// Repaint and update J Panel
		jP.repaint();
    }
    
    // call method
    public static int call (int prevBet, int user)
    {
    	System.out.print(" called\n");
    	
    	// Match the previous bet placed
    	user = prevBet;
    	
    	// Return the amount
    	return user;
    }
    
    // raise method
    public static int raise (int bet)
    {
    	System.out.print(" raised by $" + bet);
    	
    	// Update the value of totalBets
    	totalBets += bet;
    	
    	// return the value of bet
    	return bet;
    }
    
    // playerTurn method
    public void playerTurn ()
    {   
    	// Print the total amount of bets
		System.out.print("\nTotal Amount = $" + totalBets + "\n");

		// If it is not the last turn
		if (!lastTurn)
			// Print the action of player
			System.out.print("\n" + playerName + " has ");
    	
		// Show Buttons for choice
    	callButton ();
    	raiseButton ();
    }
    
    // compTurnA method
    public void compTurnA ()
    {
    	// Print the amount of totalBets
		System.out.print("\nTotal Amount = $" + totalBets + "\n");

		// Show compA's action
    	System.out.print("\nComputer 1 has ");
    	
    	// Make compA call and match previous bet
    	compABet = call (playerBet, compABet);
    	
    	// Update amount of totalBets
    	totalBets += compABet;
    	
    	// Call compTurnB method
    	compTurnB ();
    }
    
    // compTurnB method
    public void compTurnB ()
    {
    	// Print the amount of totalBets
		System.out.print("\nTotal Amount = $" + totalBets + "\n");

		// Show compB's action
    	System.out.print("\nComputer 2 has ");
    	
    	// Make compA call and match previous bet
    	compBBet = call (compABet, compBBet);
    	
    	// Update amount of totalBets
    	totalBets += compBBet;
    	
    	// Call compTurnB method
    	compTurnC ();
    }
    
    // compTurnC method
    public void compTurnC ()
    {
    	// Print the amount of totalBets
		System.out.print("\nTotal Amount = $" + totalBets + "\n");

		// Show compB's action
    	System.out.print("\nComputer 3 has ");
    	
    	// Make compA call and match previous bet
    	compCBet = call (compBBet, compCBet);
    	
    	// Update amount of totalBets
    	totalBets += compBBet;
    	
    	// If it is the first turn
    	if (firstTurn)
    	{
    		// Call the dealFlop method and deal first 3 community cards
    		deck = dealFlop (deck, labelDeck);
    		
    		// Update turns
    		firstTurn = false;
    		secondTurn = true;
    		
    		// Call playerTurn method
    		playerTurn ();
    	}
    	
    	// If it is the second turn
    	else if (secondTurn)
    	{
    		// Call dealTurn method and deal 4th community card
    		deck = dealTurn (deck, labelDeck);
    		
    		// Update turns
    		secondTurn = false;
    		thirdTurn = true;
    		
    		// Call playerTurn method
    		playerTurn ();
    	}
    	
    	// If it is the third turn
    	else if (thirdTurn)
    	{
    		// Call dealRiver and deal last community card
    		deck = dealRiver (deck, labelDeck);
    		
    		// Update turns
    		thirdTurn = false;
    		lastTurn = true;
    		
    		// Call the playerTurn method
    		playerTurn ();
    	}
    	
    	// If it is the last turn
    	else if (lastTurn)
    	{
    		// Call the playerTurn method
        	playerTurn ();
        	
        	// Call the printResults method
        	printResults();
    	}
    }
    
    // printResults method
    public static void printResults ()
    {
    	// Declaration of Strings to get the result of players
    	String playerResult = getResults (player, communityCards),
     		   compAResult = getResults (compA, communityCards),
     		   compBResult = getResults (compB, communityCards),
     		   compCResult = getResults (compC, communityCards);
     	
    	// Remove the backward facing computer cards
    	for (int x = 0; x < 6; x++)
    		jP.remove(compCards [x]);
    	    	
    	// Set position for cards
     	int posX = 75;
     	int posY = 450;
     	
     	System.out.println("\nComputer 1's Cards");
     	
     	for (int x = 0; x < 2; x++)
     	{	
     		// Print Computer 1's cards
     		System.out.println(getRank (compA [x]) + " of " + getSuit (compA [x]));
     		
     		// Show Computer 1 's cards
     		showCard (deck, labelDeck, compA [x], posX, posY);
     		
     		// Update position
     		posY -= 100;
     	}
     	
     	// Set position for Computer 2's cards
     	posX = 650;
     	posY = 100;
     	
     	System.out.println ("\nComputer 2's Cards");
     	
     	for (int x = 0; x < 2; x++)
     	{	
     		// Print Computer 2's cards
     		System.out.println(getRank (compB [x]) + " of " + getSuit (compB [x]));
     		
     		// Show Computer 2's cards
     		showCard (deck, labelDeck, compB [x], posX, posY);
     		
     		// Update position
     		posX -= 100;
     	}
     	
     	// Set position for Computer 3's cards
     	posX = 1000;
     	posY = 450;
     	
     	System.out.println ("\nComputer 3's Cards");
     	
     	for (int x = 0; x < 2; x++)
     	{	
     		// Print Computer 3's cards
     		System.out.println(getRank (compC [x]) + " of " + getSuit (compC [x]));
     		
     		// Show Computer 3's cards
     		showCard (deck, labelDeck, compC [x], posX, posY);
     		
     		// Update position
     		posY -= 100;
     	}
     	
     	// Print the results
     	System.out.print("\n\n" + playerName +"\n" + playerResult + 
     			"\n\nComputer 1\n" + compAResult + "\n\nComputer 2\n" + compBResult + "\n\nComputer 3\n" + compCResult + 
     			"\n\n" + getWinner (player, compA, compB, compC, communityCards) + " won. \n\nPrize is $" + totalBets);
     	
     	// Repaint and update J Panel
    	jP.repaint();
    }
    
    // loadCompCards method
    public static JLabel [] loadCompCards ()
    {
    	// New J Label array for backwards facing card
    	JLabel [] users = new JLabel [6];
    	
    	for (int x = 0; x < 6; x++)
    		users [x] = new JLabel (backCard);
    	
    	// Return array
    	return users;
    }
    
    // makeDeck method
    public static int [] makeDeck ()
    {
    	// Declaration of new array for deck
    	int [] deck = new int [52];
    	
    	// Assign value to deck
    	for (int x = 0; x < deck.length; x++)
    		deck [x] = x;
    	
    	// Return the deck
    	return deck;
    }
    
    // loadCardImages method
    public static JLabel [] loadCardImages (int [] deck)
    {
    	// ImageIcon array to hold card images
    	ImageIcon [] imageDeck = new ImageIcon [52];
    	
    	// Assign cards to array
    	for (int x = 0; x < 52; x++)
    		imageDeck [x] = new ImageIcon ("cards/" + (x + 1) + ".gif");
    	
    	// New J Label array to cards can be displayed
		JLabel labelDeck [] = new JLabel [52];
    	
		// Assign images to J Label
    	for (int x = 0; x < deck.length; x++)
    		labelDeck [x] = new JLabel (imageDeck [deck[x]]);

    	// Return J Label array
    	return labelDeck;
    }
    
    // loadBackCard method
    public static ImageIcon loadBackCard ()
    {
    	// Load back card image
    	ImageIcon backCard = new ImageIcon ("cards/53.jpg");
    	
    	// Return back card image
    	return backCard;
    }
    
    // loadBackground method
    public static ImageIcon loadBackground ()
    {
    	// Load background
    	ImageIcon background = new ImageIcon ("cards/Background.jpg");
    	
    	// Return background
    	return background;
    }
    
    // shuffleDeck method
    public static int [] shuffleDeck (int [] deck)
    {
    	// Shuffle the deck 3 times
    	for (int x = 0; x < deck.length * 3; x++)
    	{
    		// Produce 2 new random numbers
    		int y = rand.nextInt (deck.length);
    		int z = rand.nextInt (deck.length);
    		
    		// If 2 random values are the same
    		while (y == z)
    		{
    			// Get new value
    			z = rand.nextInt(deck.length);
    		}
    		
    		int temp = deck [y];
    		
    		// Swap position of cards
    		deck [y] = deck [z];
    		deck [z] = temp;
    	}
    	
    	// Return the shuffled deck
    	return deck;
    }

    // combineDeck method
    public static int [] combineDeck (int [] handA, int [] handB)
    {
    	// New array to hold both arrays
    	int [] newDeck = new int [handA.length + handB.length];
    	
    	// Copy values from both arrays
    	System.arraycopy (handA, 0, newDeck, 0, handA.length);
    	System.arraycopy(handB, 0, newDeck, handA.length, handB.length);
    	
    	// Return new and larger array
    	return newDeck;
    }
    
    // dealDeck method
    public static int [] dealDeck (int [] deck, JLabel [] labelDeck)
    {
    	// Set position of player's cards
    	int posX = 650, posY = 700;
    	
    	// Shuffle the deck
    	deck = shuffleDeck (deck);
    	
    	System.out.print (playerName + "'s Cards\n");
    	
    	// Deal cards to user
    	for (int x = 0; x < 2; x++)
    	{
    		// Get first 2 cards from the shuffled deck
    		player [x] = deck [x];
    		
    		// Print the player's cards
    		System.out.println(getRank (player [x]) + " of " + getSuit (player [x]));
    		
    		// Show the player's cards
    		showCard (deck, labelDeck, player [x], posX, posY);
    		
    		// Update position
    		posX -= 100;
    	}
    	
    	// Set position for Computer 1's cards
    	posX = 75;
    	posY = 450;
    	
    	// Deal cards to first A.I
    	for (int x = 0; x < 2; x++)
    	{
    		// Get 3rd and 4th card from shuffled deck
    		compA [x] = deck [x + 2];
    		
    		// Show the backside of card
    		compCards [x].setBounds (10, 10, posX, posY);
    		jP.add(compCards [x]);
    		
    		// Update position
    		posY -= 100;
    	}
    	
    	// Set position for Computer 2's cards
    	posX = 650;
    	posY = 100;
    	
    	// Deal cards to second A.I
    	for (int x = 0; x < 2; x++)
    	{
    		// Get 5th and 6th card from shuffled deck
    		compB [x] = deck [x + 4];
    		
    		// Show backside of card
    		compCards [x + 2].setBounds (10, 10, posX, posY);
    		jP.add(compCards [x + 2]);
    		
    		// Update position
    		posX -= 100;
    	}
    	
    	// Set position for Computer 3's cards
    	posX = 1000;
    	posY = 450;
    	
    	// Deal cards to last A.I
    	for (int x = 0; x < 2; x++)
    	{
    		// Get 7th and 8th card from the shuffled deck
    		compC [x] = deck [x + 6];
    		
    		// Show backside of card
    		compCards [x + 4].setBounds (10, 10, posX, posY);
    		jP.add(compCards [x + 4]);
    		
    		// Update positon
    		posY -= 100;
    	}
    	
    	// Return the shuffled deck
    	return deck;
    }

    // dealFlop method
    public static int [] dealFlop (int [] deck, JLabel [] labelDeck)
    {
    	// Set position of flop cards
    	int posX = 550, posY = 400;
    	
    	System.out.println("\nThe Flop");
    	
    	for (int x = 0; x < 3; x++)
    	{
    		// Get the 9th and 10th cards from the shuffled deck
    		communityCards [x] = deck [x + 8];
    		
    		// Print the flop cards
    		System.out.println(getRank (communityCards [x]) + " of " + getSuit (communityCards [x]));
    		
    		// Show the flop cards
    		showCard (deck, labelDeck, communityCards [x], posX, posY);
    		
    		// Update position
    		posX -= 50;
    	}
    	
    	// Return the deck
    	return deck;
    }
    
    // dealTurn method
    public static int [] dealTurn (int [] deck, JLabel [] labelDeck)
    {
    	// Set position for turn card
    	int posX = 400, posY = 400;
    	
    	System.out.println("\nThe Turn");
    	
    	// Get the 11th card from the deck 
    	communityCards [3] = deck [11];
    	
    	// Print the flop card
    	System.out.println(getRank (communityCards [3]) + " of " + getSuit (communityCards [3]));
    	
    	// Show the flop card
    	showCard (deck, labelDeck, communityCards [3], posX, posY);
    	
    	// Return the deck
    	return deck;
    }
    
    // dealRiver method
    public static int [] dealRiver (int [] deck, JLabel [] labelDeck)
    {
    	// Set the position for river cards
    	int posX = 350, posY = 400;
    	
    	System.out.println("\nThe River");
    	
    	// Get the 12th card from the shuffled deck
    	communityCards [4] = deck [12];
    	
    	// Print the river card
    	System.out.println(getRank (communityCards [4]) + " of " + getSuit (communityCards [4]));
    	
    	// Show the river card
    	showCard (deck, labelDeck, communityCards [4], posX, posY);
    	
    	// Return the deck
    	return deck;
    }
    
    // getRank method
    public static String getRank (int card)
    {
    	String rank = " ";
    	
    	// Determine the rank of the card
    	if (card == 0 || card == 13 || card == 26 || card == 39)
    		rank = "2";
    	
    	else if (card == 1 || card == 14 || card == 27 || card == 40)
    		rank = "3";
    	
    	else if (card == 2 || card == 15 || card == 28 || card == 41)
    		rank = "4";
    	
    	else if (card == 3 || card == 16 || card == 29 || card == 42)
    		rank = "5";
    	
    	else if (card == 4 || card == 17 || card == 30 || card == 43)
    		rank = "6";
    	
    	else if (card == 5 || card == 18 || card == 31 || card == 44)
    		rank = "7";
    	
    	else if (card == 6 || card == 19 || card == 32 || card == 45)
    		rank = "8";
    	
    	else if (card == 7 || card == 20 || card == 33 || card == 46)
    		rank = "9";
    	
    	else if (card == 8 || card == 21 || card == 34 || card == 47)
    		rank = "10";
    	
    	else if (card == 9 || card == 22 || card == 35 || card == 48)
    		rank = "Jack";
    	
    	else if (card == 10 || card == 23 || card == 36 || card == 49)
    		rank = "Queen";
    	
    	else if (card == 11 || card == 24 || card == 37 || card == 50)
    		rank = "King";
    	
    	else if (card == 12|| card == 25 || card == 38 || card == 51)
    		rank = "Ace";
    	
    	// Return the rank
    	return rank;
    }

    // getRankInt method
    public static int getRankInt (int card)
    {
        int rank = 0;
        	
       	// Determine the rank of the card in integers
       	if (card == 0 || card == 13 || card == 26 || card == 39)
        	rank = 2;
        	
       	else if (card == 1 || card == 14 || card == 27 || card == 40)
        	rank = 3;
        	
       	else if (card == 2 || card == 15 || card == 28 || card == 41)
       		rank = 4;
        	
       	else if (card == 3 || card == 16 || card == 29 || card == 42)
       		rank = 5;
       	
       	else if (card == 4 || card == 17 || card == 30 || card == 43)
       		rank = 6;
        	
       	else if (card == 5 || card == 18 || card == 31 || card == 44)
       		rank = 7;
        	
        else if (card == 6 || card == 19 || card == 32 || card == 45)
       		rank = 8;
        	
       	else if (card == 7 || card == 20 || card == 33 || card == 46)
       		rank = 9;
        	
        else if (card == 8 || card == 21 || card == 34 || card == 47)        		
       		rank = 10;
        	
       	else if (card == 9 || card == 22 || card == 35 || card == 48)
       		rank = 11;
        	
       	else if (card == 10 || card == 23 || card == 36 || card == 49)
       		rank = 12;
        	
       	else if (card == 11 || card == 24 || card == 37 || card == 50)
       		rank = 13;
        	
       	else if (card == 12|| card == 25 || card == 38 || card == 51)
       		rank = 14;
        	
       	// Return the rank in integer
        return rank;
    }
    
    // getSUit method
    public static String getSuit (int card)
    {
    	String suit = " ";
    	
    	// Determine the suit
    	if (card >= 0 && card < 13)
    		suit = "Spades";
    	
    	else if (card > 12 && card < 26)
    		suit = "Hearts";
    	
    	else if (card > 25 && card < 39)
    		suit = "Clubs";
    	
    	else if (card > 38 && card < 52)
    		suit = "Diamonds";
    	
    	// Return the suit of the card
    	return suit;
    }
    
    // royalFLush method
    public static boolean royalFlush (int [] newHand)
    {
    	// Return true if cards are a Royal Flush
    	return sameSuit (newHand, 5) && highestOrder (newHand);
    }
    
    // straightFlush method
    public static boolean straightFlush (int [] newHand)
    {
    	// Return true if cards are a Straight Flush
    	return sameSuit (newHand, 5) && order (newHand);
    }
    
    // fourOfKind method
    public static boolean fourOfKind (int [] newHand)
    {
    	// Return true if cards are 4 of a Kind
    	return sameRank (newHand, 4);
    }
    
    // fullHouse method
    public static boolean fullHouse (int [] newHand)
    {
    	// Return true if cards are a Full House
    	return sameRank (newHand, 3) && sameRank (newHand, 2);
    }
    
    // flush method
    public static boolean flush (int [] newHand)
    {
    	// Return true if cards are a Flush
    	return sameSuit (newHand, 5);
    }
    
    // straight method
    public static boolean straight (int [] newHand)
    {
    	// Return true if cards are a Straight
    	return order (newHand);
    }
    
    // threeOfKind method
    public static boolean threeOfKind (int [] newHand)
    {
    	// Return true if cards are 3 of a Kind
    	return sameRank (newHand, 3);
    }
    
    // twoPair method
    public static boolean twoPair (int [] newHand)
    {
    	// Return true if cards are 2 Pairs
    	return twoPairs (newHand);
    }
    
    // pair method
    public static boolean pair (int [] newHand)
    {
    	// Return true if cards are a pair
    	return sameRank (newHand, 2);
    }
   
    // getResults method
    public static String getResults (int [] hand, int [] commCards)
    {	
    	String result = " ";
    	
    	// Combine player and community cards
    	int [] newHand = combineDeck (hand, commCards);
    	
    	// Check for the hand and update result
    	if (royalFlush (newHand))
    		result = "Royal FLush";
    	
    	else if (straightFlush (newHand))
    		result = "Straight Flush";
    	
    	else if (fourOfKind (newHand))
    		result = "Four of a Kind";
    	
    	else if (fullHouse (newHand))
    		result = "Full House";
    	
    	else if (flush (newHand))
    		result = "Flush";
    	
    	else if (straight (newHand))
    		result = "Straight";
    	
    	else if (threeOfKind (newHand))
    		result = "Three of a Kind";
    	
    	else if (twoPairs (newHand))
    		result = "Two Pairs";
    	
    	else if (pair (newHand))
    		result = "Pair";
    	
    	else
    		result = "High Card";
    	
    	// Return the result of the hand
    	return result;
    }
    
    // getWinner method
    public static String getWinner (int [] player, int [] compA, int [] compB, int [] compC, int [] commCards)
    {
    	String winner = " ";
    	
    	// Combine deck of players and community card
    	int [] newCompA = combineDeck (compA, commCards);
    	
    	int [] newCompB = combineDeck (compB, commCards);
    	
    	int [] newCompC = combineDeck (compC, commCards);
    	
    	int [] newPlayer = combineDeck (player, commCards);
    	
    	// Determine Royal Flush
    	if (royalFlush (newCompA))
    		winner = "Computer 1";
    	
    	else if (royalFlush (newCompB))
    		winner = "Computer 2";
    	
    	else if (royalFlush (newCompC))
    		winner = "Computer 3";
    	
    	else if (royalFlush (newPlayer))
    		winner = playerName;
  
    	// Determine Straight Flush
    	else if (straightFlush (newCompA) && ! straightFlush (newCompB) && ! straightFlush (newCompC) && ! straightFlush (newPlayer))
    		winner = "Computer 1";
    	
    	else if (straightFlush (newCompB) && ! straightFlush (newCompA) && ! straightFlush (newCompC) && ! straightFlush (newPlayer))
    		winner = "Computer 2";
    	
    	else if (straightFlush (newCompC) && ! straightFlush (newCompB) && ! straightFlush (newCompA) && ! straightFlush (newPlayer))
    		winner = "Computer 3";
    	
    	else if (straightFlush (newPlayer) && ! straightFlush (newCompB) && ! straightFlush (newCompC) && ! straightFlush (newCompA))
    		winner = playerName;
    	
    	// Determine Four of a Kind
    	else if (fourOfKind (newCompA) && ! fourOfKind (newCompB) && ! fourOfKind (newCompC) && ! fourOfKind (newPlayer))
    		winner = "Computer 1";
    	
    	else if (fourOfKind (newCompB) && ! fourOfKind (newCompA) && ! fourOfKind (newCompC) && ! fourOfKind (newPlayer))
    		winner = "Computer 2";
    	
    	else if (fourOfKind (newCompC) && ! fourOfKind (newCompB) && ! fourOfKind (newCompA) && ! fourOfKind (newPlayer))
    		winner = "Computer 3";
    	
    	else if (fourOfKind (newPlayer) && ! fourOfKind (newCompB) && ! fourOfKind (newCompC) && ! fourOfKind (newCompA))
    		winner = playerName;
    	
    	else if ((fourOfKind (newCompA) && (fourOfKind (newCompB) || fourOfKind (newCompC) || fourOfKind (newPlayer))) 
    			|| (fourOfKind (newCompB) && (fourOfKind (newCompA) || fourOfKind (newCompC) || fourOfKind (newPlayer)))
    			|| (fourOfKind (newCompC) && (fourOfKind (newCompB) || fourOfKind (newCompA) || fourOfKind (newPlayer)))
    			|| (fourOfKind (newPlayer) && (fourOfKind (newCompB) || fourOfKind (newCompC) || fourOfKind (newCompA))))
    	{
    		winner = highCard (newCompA, newCompB, newCompC, newPlayer);
    		
    		if (winner.equals ("Computer 1"))
    			winner = "Computer 1";
    		
    		else if (winner.equals ("Computer 2"))
    			winner = "Computer 2";
    		
    		else if (winner.equals ("Computer 3"))
    			winner = "Computer 3";
    		
    		else if (winner.equals (playerName))
    			winner = playerName;
    	}
    	
    	// Determine Full House
    	else if (fullHouse (newCompA) && ! fullHouse (newCompB) && ! fullHouse (newCompC) && ! fullHouse (newPlayer))
    		winner = "Computer 1";
    	
    	else if (fullHouse (newCompB) && ! fullHouse (newCompA) && ! fullHouse (newCompC) && ! fullHouse (newPlayer))
    		winner = "Computer 2";
    	
    	else if (fullHouse (newCompC) && ! fullHouse (newCompB) && ! fullHouse (newCompA) && ! fullHouse (newPlayer))
    		winner = "Computer 3";
    	
    	else if (fullHouse (newPlayer) && ! fullHouse (newCompB) && ! fullHouse (newCompC) && ! fullHouse (newCompA))
    		winner = playerName;
    	
    	else if ((fullHouse (newCompA) && (fullHouse (newCompB) || fullHouse (newCompC) || fullHouse (newPlayer))) 
    			|| (fullHouse (newCompB) && (fullHouse (newCompA) || fullHouse (newCompC) || fullHouse (newPlayer)))
    			|| (fullHouse (newCompC) && (fullHouse (newCompB) || fullHouse (newCompA) || fullHouse (newPlayer)))
    			|| (fullHouse (newPlayer) && (fullHouse (newCompB) || fullHouse (newCompC) || fullHouse (newCompA))))
    	{
    		winner = highCard (newCompA, newCompB, newCompC, newPlayer);
    		
    		if (winner.equals ("Computer 1"))
    			winner = "Computer 1";
    		
    		else if (winner.equals ("Computer 2"))
    			winner = "Computer 2";
    		
    		else if (winner.equals ("Computer 3"))
    			winner = "Computer 3";
    		
    		else if (winner.equals (playerName))
    			winner = playerName;
    	}
    	
    	// Determine Flush
    	else if (flush (newCompA) && ! flush (newCompB) && ! flush (newCompC) && ! flush (newPlayer))
    		winner = "Computer 1";
    	
    	else if (flush (newCompB) && ! flush (newCompA) && ! flush (newCompC) && ! flush (newPlayer))
    		winner = "Computer 2";
    	
    	else if (flush (newCompC) && ! flush (newCompB) && ! flush (newCompA) && ! flush (newPlayer))
    		winner = "Computer 3";
    	
    	else if (flush (newPlayer) && ! flush (newCompB) && ! flush (newCompC) && ! flush (newCompA))
    		winner = playerName;
    	
    	// Determine Straight
    	else if (straight (newCompA) && ! straight (newCompB) && ! straight (newCompC) && ! straight (newPlayer))
    		winner = "Computer 1";
    	
    	else if (straight (newCompB) && ! straight (newCompA) && ! straight (newCompC) && ! straight (newPlayer))
    		winner = "Computer 2";
    	
    	else if (straight (newCompC) && ! straight (newCompB) && ! straight (newCompA) && ! straight (newPlayer))
    		winner = "Computer 3";
    	
    	else if (straight (newPlayer) && ! straight (newCompB) && ! straight (newCompC) && ! straight (newCompA))
    		winner = playerName;
    	
    	else if ((straight (newCompA) && (straight (newCompB) || straight (newCompC) || straight (newPlayer))) 
    			|| (straight (newCompB) && (straight (newCompA) || straight (newCompC) || straight (newPlayer)))
    			|| (straight (newCompC) && (straight (newCompB) || straight (newCompA) || straight (newPlayer)))
    			|| (straight (newPlayer) && (straight (newCompB) || straight (newCompC) || straight (newCompA))))
    	{
    		winner = highCard (newCompA, newCompB, newCompC, newPlayer);
    		
    		if (winner.equals ("Computer 1"))
    			winner = "Computer 1";
    		
    		else if (winner.equals ("Computer 2"))
    			winner = "Computer 2";
    		
    		else if (winner.equals ("Computer 3"))
    			winner = "Computer 3";
    		
    		else if (winner.equals (playerName))
    			winner = playerName;
    	}
    	
    	// Determine Three of a Kind
    	else if (threeOfKind (newCompA) && ! threeOfKind (newCompB) && ! threeOfKind (newCompC) && ! threeOfKind (newPlayer))
    		winner = "Computer 1";
    	
    	else if (threeOfKind (newCompB) && ! threeOfKind (newCompA) && ! threeOfKind (newCompC) && ! threeOfKind (newPlayer))
    		winner = "Computer 2";
    	
    	else if (threeOfKind (newCompC) && ! threeOfKind (newCompB) && ! threeOfKind (newCompA) && ! threeOfKind (newPlayer))
    		winner = "Computer 3";
    	
    	else if (threeOfKind (newPlayer) && ! threeOfKind (newCompB) && ! threeOfKind (newCompC) && ! threeOfKind (newCompA))
    		winner = playerName;
    	
    	else if ((threeOfKind (newCompA) && (threeOfKind (newCompB) || threeOfKind (newCompC) || threeOfKind (newPlayer))) 
    			|| (threeOfKind (newCompB) && (threeOfKind (newCompA) || threeOfKind (newCompC) || threeOfKind (newPlayer)))
    			|| (threeOfKind (newCompC) && (threeOfKind (newCompB) || threeOfKind (newCompA) || threeOfKind (newPlayer)))
    			|| (threeOfKind (newPlayer) && (threeOfKind (newCompB) || threeOfKind (newCompC) || threeOfKind (newCompA))))
    	{
    		winner = highCard (newCompA, newCompB, newCompC, newPlayer);
    		
    		if (winner.equals ("Computer 1"))
    			winner = "Computer 1";
    		
    		else if (winner.equals ("Computer 2"))
    			winner = "Computer 2";
    		
    		else if (winner.equals ("Computer 3"))
    			winner = "Computer 3";
    		
    		else if (winner.equals (playerName))
    			winner = playerName;
    	}
    	
    	// Determine Two Pair
    	else if (twoPair (newCompA) && ! twoPair (newCompB) && ! twoPair (newCompC) && ! twoPair (newPlayer))
    		winner = "Computer 1";
    	
    	else if (twoPair (newCompB) && ! twoPair (newCompA) && ! twoPair (newCompC) && ! twoPair (newPlayer))
    		winner = "Computer 2";
    	
    	else if (twoPair (newCompC) && ! twoPair (newCompB) && ! twoPair (newCompA) && ! twoPair (newPlayer))
    		winner = "Computer 3";
    	
    	else if (twoPair (newPlayer) && ! twoPair (newCompB) && ! twoPair (newCompC) && ! twoPair (newCompA))
    		winner = playerName;
    	
    	else if ((twoPair (newCompA) && (twoPair (newCompB) || twoPair (newCompC) || twoPair (newPlayer))) 
    			|| (twoPair (newCompB) && (twoPair (newCompA) || twoPair (newCompC) || twoPair (newPlayer)))
    			|| (twoPair (newCompC) && (twoPair (newCompB) || twoPair (newCompA) || twoPair (newPlayer)))
    			|| (twoPair (newPlayer) && (twoPair (newCompB) || twoPair (newCompC) || twoPair (newCompA))))
    	{
    		winner = highCard (newCompA, newCompB, newCompC, newPlayer);
    		
    		if (winner.equals ("Computer 1"))
    			winner = "Computer 1";
    		
    		else if (winner.equals ("Computer 2"))
    			winner = "Computer 2";
    		
    		else if (winner.equals ("Computer 3"))
    			winner = "Computer 3";
    		
    		else if (winner.equals (playerName))
    			winner = playerName;
    	}
    	
    	// Determine Pair
    	else if (pair (newCompA) && ! pair (newCompB) && ! pair (newCompC) && ! pair (newPlayer))
    		winner = "Computer 1";
    	
    	else if (pair (newCompB) && ! pair (newCompA) && ! pair (newCompC) && ! pair (newPlayer))
    		winner = "Computer 2";
    	
    	else if (pair (newCompC) && ! pair (newCompB) && ! pair (newCompA) && ! pair (newPlayer))
    		winner = "Computer 3";
    	
    	else if (pair (newPlayer) && ! pair (newCompB) && ! pair (newCompC) && ! pair (newCompA))
    		winner = playerName;
    	
    	else if ((pair (newCompA) && (pair (newCompB) || pair (newCompC) || pair (newPlayer))) 
    			|| (pair (newCompB) && (pair (newCompA) || pair (newCompC) || pair (newPlayer)))
    			|| (pair (newCompC) && (pair (newCompB) || pair (newCompA) || pair (newPlayer)))
    			|| (pair (newPlayer) && (pair (newCompB) || pair (newCompC) || pair (newCompA))))
    	{
    		winner = highCard (newCompA, newCompB, newCompC, newPlayer);
    	}
    	
    	// Return the winner
    	return winner;
    }
    
    // sameSuit method
    public static boolean sameSuit (int [] hand, int numOfCards)
    {
    	int checkSpades = 0, checkHearts = 0, checkClubs = 0, checkDiamonds = 0;
    	boolean sameSuitAll = false;
    	
    	// Check if hand consists of the same suit
    	for (int x = 0; x < hand.length; x++)
    	{
    		if (hand [x] >= 0 && hand [x] < 13)
    			checkSpades++;
    		
    		else if (hand [x] > 12 && hand [x] < 26)
    			checkHearts++;
    		
    		else if (hand [x] > 25 && hand [x] < 39)
    			checkClubs++;
    		
    		else if (hand [x] > 38 && hand [x] < 52)
    			checkDiamonds++;
    	}
    	
    	// If all cards have the same suits
    	if (checkSpades >= numOfCards || checkHearts >= numOfCards || checkClubs >= numOfCards || checkDiamonds >= numOfCards)
    		sameSuitAll = true;
    	
    	// Return true or false
    	return sameSuitAll;
    }
    
    // sameRank method
    public static boolean sameRank (int [] hand, int numOfCards)
    {
    	int check2 = 0, check3 = 0, check4 = 0, check5 = 0, check6 = 0, check7 = 0, check8 = 0, check9 = 0, check10 = 0, checkJack = 0, checkQueen = 0, checkKing = 0, checkAce = 0;
    	
    	boolean sameRank = false;
    	
    	// Check if the cards have the same rank
    	for (int x = 0; x < hand.length; x++)
    	{
    		if (hand [x] == 0 || hand [x] == 13 || hand [x] == 26 || hand [x] == 39)
        		check2 ++;
        	
        	else if (hand [x] == 1 || hand [x] == 14 || hand [x] == 27 || hand [x] == 40)
        		check3 ++;
        	
        	else if (hand [x] == 2 || hand [x] == 15 || hand [x] == 28 || hand [x] == 41)
        		check4 ++;
        	
        	else if (hand [x] == 3 || hand [x] == 16 || hand [x] == 29 || hand [x] == 42)
        		check5 ++;
        	
        	else if (hand [x] == 4 || hand [x] == 17 || hand [x] == 30 || hand [x] == 43)
        		check6 ++;
        	
        	else if (hand [x] == 5 || hand [x] == 18 || hand [x] == 31 || hand [x] == 44)
        		check7 ++;
        	
        	else if (hand [x] == 6 || hand [x] == 19 || hand [x] == 32 || hand [x] == 45)
        		check8 ++;
        	
        	else if (hand [x] == 7 || hand [x] == 20 || hand [x] == 33 || hand [x] == 46)
        		check9 ++;
        	
        	else if (hand [x] == 8 || hand [x] == 21 || hand [x] == 34 || hand [x] == 47)
        		check10 ++;
        	
        	else if (hand [x] == 9 || hand [x] == 22 || hand [x] == 35 || hand [x] == 48)
        		checkJack ++;
        	
        	else if (hand [x] == 10 || hand [x] == 23 || hand [x] == 36 || hand [x] == 49)
        		checkQueen ++;
        	
        	else if (hand [x] == 11 || hand [x] == 24 || hand [x] == 37 || hand [x] == 50)
        		checkKing ++;
        	
        	else if (hand [x] == 12|| hand [x] == 25 || hand [x] == 38 || hand [x] == 51)
        		checkAce ++;
    	}
    	
    	if (check2 == numOfCards || check3 == numOfCards || check4 == numOfCards || check5 == numOfCards || check6 == numOfCards || check7 == numOfCards || check8 == numOfCards || check9 == numOfCards ||  check10 == numOfCards || checkJack == numOfCards || checkQueen == numOfCards || checkKing == numOfCards || checkAce == numOfCards)
    		sameRank = true;
    	
    	// Return true or false
    	return sameRank;
    }
    
    // highestOrder method
    public static boolean highestOrder (int [] hand)
    {   
    	int checkOrder = 0;
    	boolean highestOrder = false;
    	
    	// Check if hand is in highest order
    	Arrays.sort(hand);
    	
    	for (int x = 2; x < hand.length; x++)
    	{
    		for (int y = 0; y < hand.length; y++)
    		{
    			if (hand [x] == 8 + y|| hand [x] == 21 + y|| hand [x] == 34 + y|| hand [x] == 47 + y)
    				checkOrder++;	
    		}
    	}
    	
    	if (checkOrder >= 5)
    		highestOrder = true;
    	
    	// Return true or false
    	return highestOrder;
    }
    
    // order method
    public static boolean order (int [] hand)
    {
    	int checkOrder = 0, currentElement, prevElement;
    	boolean order = false;
    	
    	int [] newHand = new int [hand.length];
    	
    	// Get the integer value of card rank
    	for (int x = 0; x < newHand.length; x++)
    	{
    		newHand [x] = getRankInt (hand [x]);
    	}
    	
    	// Sort the hand
    	Arrays.sort(newHand);
    	
    	// Check if hand is in order
    	for (int x = 1; x < newHand.length; x++)
    	{
    		currentElement = getRankInt (newHand [x]);
    		prevElement = getRankInt (newHand [x - 1]);
    		
    		if (currentElement - prevElement == 1)
    			checkOrder ++;
    	}
    	
    	if (checkOrder >= 5)
    		order = true;
    	
    	// Return true or false
    	return order;
    }
     
    // twoPairs method
    public static boolean twoPairs (int [] hand)
    {
    	int check2 = 0, check3 = 0, check4 = 0, check5 = 0, check6 = 0, check7 = 0, check8 = 0, check9 = 0, check10 = 0, checkJack = 0, checkQueen = 0, checkKing = 0, checkAce = 0;
    	
    	boolean twoPairs = false;
    	
    	// Check for 2 pairs of same cards
    	for (int x = 0; x < hand.length; x++)
    	{
    		if (hand [x] == 0 || hand [x] == 13 || hand [x] == 26 || hand [x] == 39)
        		check2 ++;
        	
        	else if (hand [x] == 1 || hand [x] == 14 || hand [x] == 27 || hand [x] == 40)
        		check3 ++;
        	
        	else if (hand [x] == 2 || hand [x] == 15 || hand [x] == 28 || hand [x] == 41)
        		check4 ++;
        	
        	else if (hand [x] == 3 || hand [x] == 16 || hand [x] == 29 || hand [x] == 42)
        		check5 ++;
        	
        	else if (hand [x] == 4 || hand [x] == 17 || hand [x] == 30 || hand [x] == 43)
        		check6 ++;
        	
        	else if (hand [x] == 5 || hand [x] == 18 || hand [x] == 31 || hand [x] == 44)
        		check7 ++;
        	
        	else if (hand [x] == 6 || hand [x] == 19 || hand [x] == 32 || hand [x] == 45)
        		check8 ++;
        	
        	else if (hand [x] == 7 || hand [x] == 20 || hand [x] == 33 || hand [x] == 46)
        		check9 ++;
        	
        	else if (hand [x] == 8 || hand [x] == 21 || hand [x] == 34 || hand [x] == 47)
        		check10 ++;
        	
        	else if (hand [x] == 9 || hand [x] == 22 || hand [x] == 35 || hand [x] == 48)
        		checkJack ++;
        	
        	else if (hand [x] == 10 || hand [x] == 23 || hand [x] == 36 || hand [x] == 49)
        		checkQueen ++;
        	
        	else if (hand [x] == 11 || hand [x] == 24 || hand [x] == 37 || hand [x] == 50)
        		checkKing ++;
        	
        	else if (hand [x] == 12|| hand [x] == 25 || hand [x] == 38 || hand [x] == 51)
        		checkAce ++;
    	}
    	
    	if ((check2 == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (check3 == 2 && (check2 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (check4 == 2 && (check2 == 2 || check3 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (check5 == 2 && (check2 == 2 || check3 == 2 || check4 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (check6 == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check2 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (check7 == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check2 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (check8 == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check2 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (check9 == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check2 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2))  || (check10 == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check2 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (checkJack == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || check2 == 2 || checkQueen == 2 || checkKing == 2 || checkAce == 2)) || (checkQueen == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || check2 == 2 || checkKing == 2 || checkAce == 2))  || (checkKing == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || check2 == 2 || checkAce == 2))  || (checkAce == 2 && (check3 == 2 || check4 == 2 || check5 == 2 || check6 == 2 || check7 == 2 || check8 == 2 || check9 == 2 ||  check10 == 2 || checkJack == 2 || checkQueen == 2 || checkKing == 2 || check2 == 2)))
    		twoPairs = true;
    	
    	// Return true or false
    	return twoPairs;
    }
    
    // highCard method
    public static String highCard (int [] compA, int [] compB, int [] compC, int [] player)
    {
    	int highCardA = 0, secondHighA = 0, thirdHighA = 0, highCardB = 0, secondHighB = 0, 
    		thirdHighB = 0, highCardC = 0, secondHighC = 0, thirdHighC = 0, highCardP = 0, secondHighP = 0, thirdHighP = 0;
    	
    	String highestCard = " ";
    	
    	// Check for the highest Card
    	for (int x = 0; x < player.length; x++)
    	{
    		if (getRankInt (compA [x]) > highCardA)
    		{
    			thirdHighA = secondHighA;
    			secondHighA = highCardA;
    			highCardA = getRankInt (compA [x]); 
    		}
    		
    		if (getRankInt (compB [x]) > highCardB)
    		{
    			thirdHighB = secondHighB;
    			secondHighB = highCardB;
    			highCardB = getRankInt (compB [x]); 
    		}
    		
    		if (getRankInt (compC [x]) > highCardC)
    		{
    			thirdHighC = secondHighC;
    			secondHighC = highCardC;
    			highCardC = getRankInt (compC [x]); 
    		}
    		
    		if (getRankInt (player [x]) > highCardP)
    		{
    			thirdHighP = secondHighP;
    			secondHighP = highCardP;
    			highCardP = getRankInt (player [x]); 
    		}
    	}
    	
    	// Determine which player has the highest card
    	if (highCardA > highCardB && highCardA > highCardC && highCardA > highCardP)
    		highestCard = "Computer 1";
    	
    	else if (highCardB > highCardA && highCardB > highCardC && highCardB > highCardP)
    		highestCard = "Computer 2";
    	
    	else if (highCardC > highCardA && highCardC > highCardB && highCardC > highCardP)
    		highestCard = "Computer 3";
    	
    	else if (highCardP > highCardA && highCardP > highCardB && highCardP > highCardC)
    		highestCard = playerName;
    	
    	// If there is a tie
    	else if ((highCardA == highCardB || highCardA == highCardC || highCardA == highCardP) 
    			|| (highCardB == highCardA || highCardB == highCardC || highCardB == highCardP) 
    			|| (highCardC == highCardB || highCardC == highCardA || highCardC == highCardP)
    			|| (highCardP == highCardB || highCardP == highCardC || highCardP == highCardA))
    	{
        	if (secondHighA > secondHighB && secondHighA > secondHighC && secondHighA > secondHighP)
        		highestCard = "Computer 1";
        	
        	else if (secondHighB > secondHighA && secondHighB > secondHighC && secondHighB > secondHighP)
        		highestCard = "Computer 2";
        	
        	else if (secondHighC > secondHighA && secondHighC > secondHighB && secondHighC > secondHighP)
        		highestCard = "Computer 3";
        	
        	else if (secondHighP > secondHighA && secondHighP > secondHighB && secondHighP > secondHighC)
        		highestCard = playerName;
        	
        	// If there is another tie
        	else if (secondHighA == secondHighB || secondHighA == secondHighC || secondHighA == secondHighP)
        	{
            	if (thirdHighA > thirdHighB && thirdHighA > thirdHighC && thirdHighA > thirdHighP)
            		highestCard = "Computer 1";
            	
            	else if (thirdHighB > thirdHighA && thirdHighB > thirdHighC && thirdHighB > thirdHighP)
            		highestCard = "Computer 2";
            	
            	else if (thirdHighC > thirdHighA && thirdHighC > thirdHighB && thirdHighC > thirdHighP)
            		highestCard = "Computer 3";
            	
            	else if (thirdHighP > thirdHighA && thirdHighP > thirdHighB && thirdHighP > thirdHighC)
            		highestCard = playerName;
        	}
    	}
    	
    	// Return player with the highest card
    	return highestCard;
    }
    
    // callButton method
    public void callButton ()
    {
    	// Make new J Button
    	call = new JButton ("Call");
    	call.setBounds(197, 460, 80, 30);
    	
    	// Action when call is pressed
    	call.addActionListener (new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{
    			// Call the call method and update totalBets
    			totalBets += call (playerBet, compCBet);
    		    			
    			// Call compATurn method
    			compTurnA ();
    			
    			// Repaint and update the J Panel
    			jP.repaint();
    		}
    	});
    	
    	// Add the Button to the J Panel
    	jP.add(call);
    }
    
    // raiseButton method
    public void raiseButton ()
    {
    	// New Font
    	Font arial = new Font ("Arial", Font.PLAIN, 15);
    	
    	// Text Field and message to prompt user to enter amount 
    	JTextField textField = new JTextField (10);
    	JLabel label = new JLabel ("How much would you like to raise? (Balance = $" + (10 - playerBet) + ")");
    	
    	// New Button 
    	raise = new JButton ("Raise");
    	raise.setBounds(350, 460, 80, 30);
    	
    	JButton raise2 = new JButton ("Raise");
    	
    	// Action when raise if pressed
    	raise.addActionListener(new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{    		
    			// Prompt user to enter amount
    			jP.add(textField);
    			jP.add(label);
    			
    			// Set position of TExt Field
    	    	textField.setBounds(350, 463, 125, 25);
    	    	
    	    	label.setBounds(15, 450, 500, 50);
    	    	label.setFont(arial);
    			
    	    	// Remove the 2 Buttons
    			jP.remove(call);
    			jP.remove(raise);
    			
    			// Add a 2nd raise Button
    			jP.add(raise2);
    			raise2.setBounds(500, 460, 80, 30);
    			
    			// Repaint and update the J Panel
    			jP.repaint();
    		}
    	});
    	
    	// Add the raise Button to the J Panel
    	jP.add(raise);
    	
    	// Action performed when amount is entered
    	textField.addActionListener (new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{
    			// Read value of raise amount
    			String playerRaiseS = textField.getText();
    			
    			// Convert the String into an integer value
    			playerBet += Integer.parseInt(playerRaiseS);
    			    			
    			// Remove the prompt components
    			jP.remove (label);
    			jP.remove(textField);
    			jP.remove(raise2);
    			
    			// Update totalBets by calling the raise method
    			totalBets += raise (playerBet);
    			
    			// Call the compTurnA method
    			compTurnA ();
    			
    			// Repaint and update the J Panel
    			jP.repaint();
    		}
    	});
    	
    	raise2.addActionListener (new ActionListener ()
    	{
    		public void actionPerformed (ActionEvent e)
    		{
    			// Read value of raise amount
    			String playerRaiseS = textField.getText();
    			
    			// Convert the String into an integer value
    			playerBet += Integer.parseInt(playerRaiseS);
    			    			
    			// Remove the prompt components
    			jP.remove (label);
    			jP.remove(textField);
    			jP.remove(raise2);
    			
    			// Update totalBets by calling the raise method
    			totalBets += raise (playerBet);
    			
    			// Call the compTurnA method
    			compTurnA ();
    			
    			// Repaint and update the J Panel
    			jP.repaint();
    		}
    	});
    	
    	// Repaint and update J Panel
    	jP.repaint ();
    }
    
    // showCard method
    public static void showCard (int [] deck, JLabel [] labelDeck, int card, int posX, int posY)
    {
    	// Set the position of the card
    	labelDeck [card].setBounds(10, 10, posX, posY);
    	
    	// Add the Label to the J Panel
    	jP.add(labelDeck [card]);
    }
       
    // showBackCard method
    public static void showBackCard (ImageIcon cardImage, int posX, int posY)
    {
    	// Make new Label with image
    	JLabel backCard = new JLabel (cardImage);
    	
    	// Set the position of the image
    	backCard.setBounds(10, 10, posX, posY);
    	
    	// Add the image to the J Panel
    	jP.add(backCard);
    }
}