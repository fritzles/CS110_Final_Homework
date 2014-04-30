import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
//import java.awt.Color.ChartColor;



public class War extends JFrame {

  private JButton startGame = new JButton("Start Game");
  private JButton dealCard = new JButton("Deal Card");
  private JButton exit = new JButton("Quit");

  private JPanel frame = new JPanel(new BorderLayout(30,30));
  private JPanel playingField = new JPanel(new GridLayout(1,2,15,15));
  private JPanel buttons = new JPanel(new GridLayout(1,2,40,20));
  private JPanel information = new JPanel(new GridLayout(1,3,40,20));

  private Color dark_green = new Color(39, 92, 25);
  private Color maple = new Color(51, 25, 0);
  private Color gold = new Color(204,204,0);

  private WarDeck deck;

  private ActionListener turnTopCard = new TurnTopCard();
  private ActionListener exitHandler = new ExitHandler();

  private Border raisedbevel = BorderFactory.createRaisedBevelBorder();

  private JLabel playerCard, playerDeck, computerCard, computerDeck, winner;

  private Card player, computer, playerTemp, computerTemp;

  private	ImageIcon cardback = new ImageIcon("b1fv.png");
  private 	ImageIcon noDeck = new ImageIcon("done.png");

  private	JLabel cardBack = new JLabel(cardback);
  private	JLabel cardBack2 = new JLabel(cardback);
  private 	JLabel noDeckLeft = new JLabel(noDeck);

  private 	JLabel welcomeMessage = new JLabel("Welcome to War!");
  private 	JLabel welcomeContinued = new JLabel("Press 'Start Game' to begin.");


  public War() { // the constructor

  	super("Fritz Card Games - War");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(700, 550));
	setResizable(false);


    buttons.add(startGame);
    startGame.addActionListener(new StartGame());

    buttons.add(exit);
    exit.addActionListener(exitHandler);

    frame.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    frame.setBackground(maple);
    information.setBackground(maple);
    buttons.setBackground(maple);
    playingField.setBackground(dark_green);

    startGame.setBackground(dark_green);
    exit.setBackground(Color.RED);
    dealCard.setBackground(dark_green);

    startGame.setForeground(Color.WHITE);
    exit.setForeground(Color.WHITE);
    dealCard.setForeground(Color.WHITE);

    startGame.setBorder(raisedbevel);
    exit.setBorder(raisedbevel);
    dealCard.setBorder(raisedbevel);
    playingField.setBorder(raisedbevel);

    welcomeMessage.setForeground(Color.WHITE);
    welcomeContinued.setForeground(Color.WHITE); 

    information.add(welcomeMessage,SwingConstants.CENTER);
    playingField.add(welcomeContinued,SwingConstants.CENTER);

    frame.add(playingField, BorderLayout.CENTER);
    frame.add(cardBack2, BorderLayout.EAST);
    frame.add(cardBack, BorderLayout.WEST);
    frame.add(buttons, BorderLayout.SOUTH);
    frame.add(information, BorderLayout.NORTH);

    this.getContentPane().add(frame);
    pack();

    setVisible(true);
  }
  // add inner class event handler for each button here
  class ExitHandler implements ActionListener {

    public void actionPerformed(ActionEvent e) {

        System.exit(0);

    }
  }
  class StartGame implements ActionListener {

	  public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton)(e.getSource());

			information.remove(welcomeMessage);
    		playingField.remove(welcomeContinued);



			//create button and deck
			dealCard.addActionListener(turnTopCard);
			deck = new WarDeck();

			//change out buttons cause grid layout is fun!!! (not)
			buttons.remove(startGame);
			buttons.remove(exit);
			buttons.add(dealCard);
			buttons.add(exit);

			frame.add(playingField, BorderLayout.CENTER);
			frame.add(information, BorderLayout.NORTH);
			


			playerDeck = new JLabel("Player Deck:" + deck.cardsRemainingPlayer());
			computerDeck = new JLabel("Computer Deck:" + deck.cardsRemainingComputer());
			winner = new JLabel("Press Deal Card to Start!");

			playerDeck.setForeground(Color.WHITE);
			winner.setForeground(Color.WHITE);
			computerDeck.setForeground(Color.WHITE);

			information.add(playerDeck,SwingConstants.CENTER);
			information.add(winner,SwingConstants.CENTER);
			information.add(computerDeck,SwingConstants.CENTER);


			playerCard = new JLabel();
			computerCard = new JLabel();

			

    		frame.add(cardBack, BorderLayout.EAST);
    		frame.add(cardBack2, BorderLayout.WEST);

    		
			pack();


		}
	  }
  }
  class TurnTopCard implements ActionListener {

	  public void actionPerformed(ActionEvent e) {
	  //	while(deck.cardsRemainingComputer() != 0){
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton)(e.getSource());

			try{

			information.remove(winner);
			playingField.remove(playerCard);
			playingField.remove(computerCard);

			

			winner = new JLabel("");

			
				player = deck.dealCardPlayer();
				computer = deck.dealCardComputer();
				playerCard = displayCardFace(player);
				computerCard = displayCardFace(computer);



				deck.enqueueCardPile(player, computer);

				if(deck.compareTo(player,computer) == 1){
					winner = new JLabel("Player Wins!");
					playingField.add(computerCard);
					playingField.add(playerCard);
					winner.setForeground(Color.WHITE);


					deck.enqueueCardPlayer(deck.dequeueCardPile());


				}else if(deck.compareTo(player, computer) == -1){
					winner = new JLabel("Computer Wins!");
					winner.setForeground(Color.WHITE);


					playingField.add(computerCard);
					playingField.add(playerCard);

					deck.enqueueCardComputer(deck.dequeueCardPile());
				}else if(deck.compareTo(player,computer) == 0){
					winner = new JLabel("WAR!");
					winner.setForeground(Color.RED);

					playingField.add(computerCard);
					playingField.add(playerCard);

					//System.out.println(player + ":" + computer);
					deck.enqueueCardPile(deck.dealCardPlayer(), deck.dealCardComputer());

				}

				information.remove(computerDeck);
				information.remove(winner);
				information.remove(playerDeck);

				playerDeck = new JLabel("Player Deck:" + deck.cardsRemainingPlayer());
				computerDeck = new JLabel("Computer Deck:" + deck.cardsRemainingComputer());

				if(deck.cardsRemainingComputer() == 0){
					winner = new JLabel("Congratulations! You win!");
					winner.setForeground(Color.YELLOW);
						
					dealCard.removeActionListener( new TurnTopCard());

					frame.remove(cardBack2);
					frame.add(noDeckLeft, BorderLayout.WEST);
					dealCard.removeActionListener(turnTopCard);
					

				}else if(deck.cardsRemainingPlayer() == 0){
					winner = new JLabel("You lose.");
					winner.setForeground(Color.RED);
						
					dealCard.removeActionListener( new TurnTopCard());
					playingField.remove(playerCard);
					playingField.remove(computerCard);

					frame.remove(cardBack);
					frame.add(noDeckLeft, BorderLayout.EAST);
					dealCard.removeActionListener(turnTopCard);

				}

				playerDeck.setForeground(Color.WHITE);
				computerDeck.setForeground(Color.WHITE);


				information.add(playerDeck,SwingConstants.CENTER);
				information.add(winner,SwingConstants.CENTER);
				information.add(computerDeck,SwingConstants.CENTER);

			
				pack();
			}
			catch (QueueException qe){
				dealCard.removeActionListener(turnTopCard);
			}
		}
	  }

	  public JLabel displayCardFace(Card card){

	  	String ext = ".png";
	  	ImageIcon image = new ImageIcon(card.toString() + ext);
	  	JLabel label = new JLabel(image);

	  	return label;
	  }

  }
  public static void main(String[] args) {
	  new War();
  }
}
