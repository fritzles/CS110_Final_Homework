import java.util.Random;
public class WarDeck 
{
   final int CARDS_IN_DECK = 52;

   QueueReferenceBased deck;

   QueueReferenceBased deckComputer;
   QueueReferenceBased deckPlayer;
   QueueReferenceBased cardPile = new QueueReferenceBased();

   Card [] deckTemp;
   int ctPlayer, ctComputer, ct;

   public WarDeck()
   {

      deck = new QueueReferenceBased();
      freshDeck();
      shuffle();

      split();

   }

   public void freshDeck()
   {
      ct = 0;
      deckTemp = new Card[CARDS_IN_DECK];
      for (int r = Card.ACE; r<=Card.KING;r++)
      {
         for (int s=Card.SPADES;s<=Card.CLUBS;s++)
         {
            deckTemp[ct]=new Card(r,s);
            ct++;
         }
      }
     
   
   }

   /**
   Deck Maintainance during game
   */
   public Card dealCardComputer()
   {
      ctComputer--;
      return (Card)deckComputer.dequeue();
   }

   public void enqueueCardPlayer(QueueReferenceBased cards)
   {
      while(!cards.isEmpty()){
         deckPlayer.enqueue(cards.dequeue());
         ctPlayer ++;
      }
   }

   public Card dealCardPlayer()
   {
      ctPlayer--;
      return (Card)deckPlayer.dequeue();
   }

   public void enqueueCardComputer(QueueReferenceBased cards)
   {
      while(!cards.isEmpty()){
         deckComputer.enqueue(cards.dequeue());
         ctComputer ++;
      }
   }

   public void enqueueCardPile(Card... cards){
      for(Card card: cards){
         cardPile.enqueue(card);
      }
   }

   public QueueReferenceBased dequeueCardPile(){
      return cardPile;
   }

   /**
   Deck checks
   */
   public int cardsRemainingPlayer()
   {  
      return ctPlayer;
   }
   public int cardsRemainingComputer()
   {  
      return ctComputer;
   }

   /**

   */
   public void shuffle()
   {
      int randNum;
      Card temp;
      Random r = new Random();
      for (int i = 0; i < ct; i++)
      {
         randNum = r.nextInt(ct);
         temp = deckTemp[i];
         deckTemp[i]=deckTemp[randNum];
         deckTemp[randNum]=temp;
      }
   }
   public boolean isEmpty()
   {
      return ((cardsRemainingPlayer() == 0) || (cardsRemainingComputer() == 0));
   }

   public void split(){

      deckPlayer = new QueueReferenceBased();
      deckComputer = new QueueReferenceBased();

      int splitter = 0;
      for(Card card: deckTemp){
         

         if (splitter == 0){
            deckComputer.enqueue(card);
            ctComputer ++;
            splitter++;
         }else{
            deckPlayer.enqueue(card);
            ctPlayer++;
            splitter = 0;
         }
      }
   }

   public int compareTo(Card card1, Card card2){
      if(card1.getRank() > card2.getRank()){
         return 1;
      }else if(card1.getRank() == card2.getRank()){
         return 0;
      }else {
         return -1;
      }
   }

   public static void main(String [] args) 
   {
      WarDeck deck = new WarDeck();

      while(deck.cardsRemainingPlayer() != 0){
         System.out.println(deck.compareTo(deck.dealCardPlayer(), deck.dealCardComputer()));
      }
   }
}