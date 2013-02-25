package Card;

import java.util.Random;


/** An abstract Deck class
 * @author Pyck Nicolas
 */
public class CardDeck extends CardStack {

	/**
	 * Default constructor.
	 */
	public CardDeck()
	{
		super();
		for (Card.Suit suit : Card.Suit.values())
		{
			for (Card.Rank rank : Card.Rank.values())
			{
				Card card = new Card(suit, rank);
				push(card);
			}
		}
	}
	
	/**
	 * Shuffles the deck.
	 */
	public void shuffle()
	{
		int index;
		Random random = new Random();
		for (int i=0; i<stack.size(); i++)
		{
			index = random.nextInt(stack.size() - i) + i;
			stack.set(index, stack.set(i, stack.get(index)));
		}
	}
}
