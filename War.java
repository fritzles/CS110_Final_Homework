import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class War extends JFrame {

  private JButton startGame = new JButton("Start Game");
  private JButton dealCard = new JButton("Deal Card");
  private JButton exit = new JButton("Quit");

  private JPanel frame = new JPanel(new BorderLayout());
  private JPanel playingField = new JPanel(new GridLayout(1,2,15,15));
  private JPanel buttons = new JPanel(new GridLayout(1,2,40,20));
  private JPanel information = new JPanel(new GridLayout(1,3,40,20));


  private WarDeck deck;

  private JLabel playerCard, playerDeck, computerCard, computerDeck, winner;

  private Card player, computer, playerTemp, computerTemp;

  private	ImageIcon cardback = new ImageIcon("b1fv.png");

  private	JLabel cardBack = new JLabel(cardback);
  private	JLabel cardBack2 = new JLabel(cardback);


  public War() { // the constructor

  	super("Fritz Card Games - War");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(800, 600));
    
    buttons.add(startGame);
    startGame.addActionListener(new StartGame());

    buttons.add(exit);
    exit.addActionListener(new ExitHandler());

    frame.add(buttons, BorderLayout.SOUTH);


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


			//create button and deck
			dealCard.addActionListener(new TurnTopCard());
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

			information.add(computerDeck);
			information.add(winner);
			information.add(playerDeck);

			

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

			information.remove(winner);
			playingField.remove(playerCard);
			playingField.remove(computerCard);

			

			winner = new JLabel("");

			if(deck.cardsRemainingComputer() == 0){
				System.out.println("computer wins game.");
			}else if(deck.cardsRemainingPlayer() == 0){
				System.out.println("player wins game.");
			}else{
			
				player = deck.dealCardPlayer();
				computer = deck.dealCardComputer();
				playerCard = displayCardFace(player);
				computerCard = displayCardFace(computer);



				deck.enqueueCardPile(player, computer);

				if(deck.compareTo(player,computer) == 1){
					winner = new JLabel("Player Wins");
					//System.out.println(player + ":" + computer);
					playingField.add(computerCard);
					playingField.add(playerCard);

					deck.enqueueCardPlayer(deck.dequeueCardPile());


				}else if(deck.compareTo(player, computer) == -1){
					winner = new JLabel("Computer Wins");

					playingField.add(computerCard);
					playingField.add(playerCard);

					//System.out.println(player + ":" + computer);
					deck.enqueueCardComputer(deck.dequeueCardPile());
				}else if(deck.compareTo(player,computer) == 0){
					winner = new JLabel("WAR");

					playingField.add(computerCard);
					playingField.add(playerCard);

					//System.out.println(player + ":" + computer);
					deck.enqueueCardPile(deck.dealCardPlayer(), deck.dealCardComputer());
					if(deck.cardsRemainingComputer() == 0){
						System.out.println("computer wins game.");
					}else if(deck.cardsRemainingPlayer() == 0){
						System.out.println("player wins game.");
					}
				}

				information.remove(computerDeck);
				information.remove(winner);
				information.remove(playerDeck);

				playerDeck = new JLabel("Player Deck:" + deck.cardsRemainingPlayer());
				computerDeck = new JLabel("Computer Deck:" + deck.cardsRemainingComputer());

				information.add(computerDeck);
				information.add(winner);
				information.add(playerDeck);



				pack();

				
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
