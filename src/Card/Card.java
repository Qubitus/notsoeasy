package Card;

import java.awt.Color;

/**
 * A game card implementation class.
 * 
 * @author Pyck Nicolas
 */
public class Card implements Comparable<Card> {

	/**
	 * Card suit enumeration
	 * 
	 * @author Pyck Nicolas
	 */
	public enum Suit {

		Spades(Color.BLACK), Clubs(Color.BLACK), Hearts(Color.RED), Diamonds(
				Color.RED);

		private final Color color;

		private Suit(Color color) {
			this.color = color;
		}

		public Color getColor() {
			return color;
		}
	};

	/**
	 * Card rank enumeration
	 * 
	 * @author Pyck Nicolas
	 */
	public enum Rank {

		Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King;
	};

	/**
	 * Card facing enumeration
	 * 
	 * @author Pyck Nicolas
	 * 
	 */
	public enum Facing {
		FaceDown, FaceUp;
	};

	protected Suit suit;
	protected Rank rank;
	protected Facing facing;

	/**
	 * Card constructor with default <code>Facing.FaceUp</code> facing.
	 * 
	 * @param suit
	 *            Suit of the new Card object
	 * @param rank
	 *            Rank of the new Card object
	 */
	public Card(Suit suit, Rank rank) {
		this(suit, rank, Facing.FaceUp);
	}

	/**
	 * Card constructor.
	 * 
	 * @param suit
	 *            Suit of the new Card object
	 * @param rank
	 *            Rank of the new Card object
	 * @param facing
	 *            Facing of the new Card object
	 */
	public Card(Suit suit, Rank rank, Facing facing) {
		this.suit = suit;
		this.rank = rank;
		this.facing = facing;
	}

	/**
	 * @return Rank of the Card.
	 */
	public Rank getRank() {
		return rank;
	}

	/**
	 * @return Suit of the Card.
	 */
	public Suit getSuit() {
		return suit;
	}

	/**
	 * @return Color of the card, can be either <CODE>Color.RED</CODE> or
	 *         <CODE>Color.BLACK</CODE>.
	 */
	public Color getColor() {
		return suit.getColor();
	}

	/**
	 * @return Facing of the Card.
	 */
	public Facing getFacing() {
		return facing;
	}

	/**
	 * @return True if the card has a facing equal to
	 *         <code>Facing.FaceDown</code>, false if the facing equals
	 *         <code>Facing.FaceUp</code>.
	 */
	public boolean isFacingDown() {
		return facing == Facing.FaceDown;
	}

	/**
	 * Flips the card over.
	 */
	public void flip() {
		switch (facing) {
		case FaceDown:
			facing = Facing.FaceUp;
			break;
		case FaceUp:
			facing = Facing.FaceDown;
			break;
		}
	}

	@Override
	/**
	 * @param target Target Card with which the current Card is to be compared.
	 * @return <code>-1</code> if the current Card is positioned in front of the target Card.
	 * 		   <code>0</code> if the current Card equals the position of the target Card.
	 * 		   <code>1</code> if the current Card is positioned behind of the target Card.
	 */
	public int compareTo(Card target) {
		if (suit == target.suit) {
			return rank.compareTo(target.rank);
		} else
			return suit.compareTo(target.suit);
	}

	@Override
	/**
	 * @param target Target Card with which the current Card is to be compared.
	 * @return <code>true</code> if both Card objects are equal.
	 * 		   <code>false</code> if both Card objects are not equal
	 */
	public boolean equals(Object target) {
		if (target instanceof Card)
			return compareTo((Card) target) == 0;
		else
			return false;
	}

	@Override
	/**
	 * @return String representation of the Card.
	 */
	public String toString() {
		String text;
		if (facing == Facing.FaceDown)
			text = "Cover";
		else
			text = rank.toString() + " of " + suit.toString();
		return text;
	}

	/**
	 * Deep copy clone method.
	 * 
	 * @return Card object identical to the original Card.
	 */
	public Card clone() {
		Card newCard = new Card(suit, rank, facing);

		return newCard;
	}
}
