package Card;

import java.util.Stack;

import Rules.StackRules;

/**
 * A Card Stack implementation
 * 
 * @author Pyck Nicolas
 * @version Version 1.0
 */
public class CardStack {

	public enum Direction {
		None,
		North,
		East,
		South,
		West;
	}

	protected Stack<Card>	stack;
	protected StackRules	rules;
	protected Direction		direction;

	/**
	 * Default constructor with StackRules.NO_RULES as stack rules
	 */
	public CardStack() {
		this(StackRules.NO_RULES, Direction.None);
	}

	/**
	 * Constructor
	 * 
	 * @param rules
	 *            StackRules to be applied to the stack
	 * @param direction
	 *            Spread Direction
	 */
	public CardStack(StackRules rules, Direction direction) {
		super();

		this.stack = new Stack<Card>();
		this.rules = rules;
		
		this.direction = direction;
	}

	/**
	 * Deep copy clone method
	 * 
	 * @return CardStack object identical to the original
	 */
	public CardStack clone() {
		CardStack cs = new CardStack(this.rules, this.direction);

		for (Card card : stack) {
			cs.push(card.clone());
		}

		return cs;
	}

	/**
	 * @return The number of Card object contained within the CardStack
	 */
	public int getSize() {
		return stack.size();
	}

	/**
	 * @return The collection of Card objects contained within the CardStack
	 */
	public Stack<Card> getCards() {
		return stack;
	}

	/**
	 * @return The index of the first Card object that has a FaceUp facing
	 */
	public int firstFaceUp() {
		for (int i = 0; i < stack.size(); i++) {
			Card card = stack.get(i);
			if (card.getFacing() == Card.Facing.FaceUp)
				return i;
		}
		return -1;
	}

	/**
	 * @return The top Card object, null if the CardStack size equals 0
	 */
	public Card top() {
		int size = stack.size();
		if (size > 0)
			return stack.get(size - 1);
		else
			return null;
	}

	/**
	 * @param index
	 *            The position of the Card object to be returned
	 * @return The Card object at the specified position
	 */
	public Card elementAt(int index) {
		return stack.get(index);
	}

	/**
	 * Pushes a Card upon on the stack
	 * 
	 * @param card
	 *            The Card to be pushed on the stack
	 */
	public void push(Card card) {
		stack.push(card);
	}

	/**
	 * Pushes a CardStack on the stack
	 * 
	 * @param cs
	 *            The CardStack to be pushed on the stack
	 */
	public void push(CardStack cs) {
		while (!cs.isEmpty()) {
			push(cs.stack.remove(0));
		}
	}

	/**
	 * Try and push a Card on the stack
	 * 
	 * @param card
	 *            The Card to be pushed on the stack
	 * @return <code>true</code> if the push was successful, <code>false</code>
	 *         if the push failed
	 */
	public boolean tryPush(Card card) {
		if (rules.validMove(card, this)) {
			push(card);
			return true;
		}
		else
			return false;
	}

	/**
	 * Try and push a CardStack on the stack
	 * 
	 * @param cs
	 *            The CardStack to be pushed on the stack
	 * @return <code>true</code> if the push was successful, <code>false</code>
	 *         if the push failed
	 */
	public boolean tryPush(CardStack cs) {
		if (rules.validMove(cs, this)) {
			while (!cs.isEmpty())
				push(cs);
			return true;
		}
		else
			return false;
	}

	/**
	 * Pops a card from the stack.
	 * 
	 * @return The card on top of the stack.
	 */
	public Card pop() {
		Card c = stack.pop();

		return c;
	}

	/**
	 * Pops several cards from the stack into a new CardStack.
	 * 
	 * @param n
	 *            Number of cards to be popped.
	 * @return A new CardStack containing the cards popped.
	 */
	public CardStack pop(int n) {
		CardStack cs = null;
		if (n > 0 && n <= cardCount()) {
			cs = new CardStack(rules, direction);

			for (int i = n; i > 0 && !isEmpty(); i--) {
				cs.push(pop());
			}

			cs.reverse();
		}

		return cs;
	}

	/**
	 * Pops several cards from the stack into a returned stack until the
	 * specified card is reached.
	 * 
	 * @param card
	 *            Last card to be popped.
	 * @return A stack containing the cards popped.
	 */
	public CardStack pop(Card card) {
		int index = stack.indexOf(card);

		return pop(stack.size() - index);
	}

	/**
	 * @return The number of cards contained on the stack.
	 */
	public int cardCount() {
		return stack.size();
	}

	/**
	 * @param c
	 *            Card to check.
	 * @return <CODE>true</CODE> if the stack contains the card,
	 *         <CODE>false</CODE> otherwise.
	 */
	public boolean contains(Card c) {
		return stack.contains(c);
	}

	/**
	 * Method to determine if the CardStack holds no Cards
	 * 
	 * @return <code>true</code> if the stack is empty, otherwise
	 *         <code>false</code>
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Method to determine if the CardStack is considered completed
	 * 
	 * @return <code>true</code> if the stack is completed according to the
	 *         stack rules, otherwise <code>false</code>
	 */
	public boolean isCompleted() {
		return rules.isCompleted(this);
	}

	/**
	 * Method to determine if the CardStack is considered playable
	 * 
	 * @return <code>true</code> if the stack is playable according to the stack
	 *         rules, otherwise <code>false</code>
	 */
	public boolean isPlayable() {
		return rules.isPlayable(this);
	}

	/**
	 * Determines if the CardStack contains any Cards that are faced down
	 * 
	 * @return <code>true</code> if the stack contains covered cards, otherwise
	 *         <code>false</code>
	 */
	public boolean containsCovered() {
		for (Card card : stack) {
			if (card.getFacing() == Card.Facing.FaceDown)
				return true;
		}
		return false;
	}

	/**
	 * @return the constants corresponding to the spreading direction of the
	 *         stack of cards.
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @param sd
	 *            Constant corresponding to the spreading direction of the stack
	 *            of cards.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Reverses the cards contained within the CardStack
	 */
	public void reverse() {
		CardStack newStack = new CardStack(rules, direction);

		while (!this.isEmpty())
			newStack.push(pop());

		stack = newStack.stack;
	}

	/**
	 * @return String representation of the CardStack.
	 */
	public String toString() {
		return stack.toString();
	}

}
