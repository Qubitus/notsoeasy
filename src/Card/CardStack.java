package Card;

import java.util.Stack;

import Rules.StackRules;

/**
 * A card stack implementation class.
 * 
 * @author Pyck Nicolas
 */
public class CardStack {

	protected Stack<Card> stack;
	protected StackRules rules;

	/**
	 * Default constructor with <code>StackRules.NO_RULES</code> as stack rules.
	 */
	public CardStack() {
		this(StackRules.NO_RULES);
	}

	/**
	 * Constructor with specified StackRules.
	 * 
	 * @param rules
	 *            StackRules to be applied to the stack.
	 */
	public CardStack(StackRules rules) {
		super();

		this.stack = new Stack<Card>();
		this.rules = rules;
	}

	/**
	 * Deep copy clone method.
	 * 
	 * @return CardStack object identical to the original CardStack.
	 */
	public CardStack clone() {
		CardStack cs = new CardStack(this.rules);

		for (Card card : stack) {
			cs.push(card.clone());
		}

		return cs;
	}

	/**
	 * @return The number of Card objects contained within the CardStack.
	 */
	public int getSize() {
		return stack.size();
	}

	/**
	 * @return The collection of Card objects contained within the CardStack.
	 */
	public Stack<Card> getCards() {
		return stack;
	}

	/**
	 * @return The index of the first Card object that has a
	 *         <code>Facing.FaceUp</code> facing.
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
	 * @return The top Card object, null if the CardStack size equals 0.
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
	 *            The position of the Card object to be returned.
	 * @return The Card object located the specified index.
	 */
	public Card elementAt(int index) {
		return stack.get(index);
	}

	/**
	 * Pushes a Card upon on the stack.
	 * 
	 * @param card
	 *            The Card to be pushed onto the CardStack.
	 */
	public void push(Card card) {
		stack.push(card);
	}

	/**
	 * Pushes a CardStack on the stack.
	 * 
	 * @param cs
	 *            The CardStack to be pushed onto the CardStack.
	 */
	public void push(CardStack cs) {
		while (!cs.isEmpty()) {
			push(cs.stack.remove(0));
		}
	}

	/**
	 * Try and push a Card on the stack.
	 * 
	 * @param card
	 *            The Card to be pushed onto the stack
	 * @return <code>true</code> if the push was successful, <code>false</code>
	 *         if the push failed.
	 */
	public boolean tryPush(Card card) {
		if (rules.validMove(card, this)) {
			push(card);
			return true;
		} else
			return false;
	}

	/**
	 * Try and push a CardStack on the stack.
	 * 
	 * @param cs
	 *            The CardStack to be pushed onto the stack.
	 * @return <code>true</code> if the push was successful, <code>false</code>
	 *         if the push failed.
	 */
	public boolean tryPush(CardStack cs) {
		if (rules.validMove(cs, this)) {
			while (!cs.isEmpty())
				push(cs);
			return true;
		} else
			return false;
	}

	/**
	 * Pops a Card from the stack.
	 * 
	 * @return The top Card of the CardStack.
	 */
	public Card pop() {
		Card c = stack.pop();

		return c;
	}

	/**
	 * Pops several cards from the stack into a new CardStack.
	 * 
	 * @param n
	 *            Number of Cards to be popped.
	 * @return A new CardStack containing the Cards popped.
	 */
	public CardStack pop(int n) {
		CardStack cs = null;
		if (n > 0 && n <= cardCount()) {
			cs = new CardStack(rules);

			for (int i = n; i > 0 && !isEmpty(); i--) {
				cs.push(pop());
			}

			cs.reverse();
		}

		return cs;
	}

	/**
	 * Pops several Cards from the stack into a returned CardStack until the
	 * specified Card is reached.
	 * 
	 * @param card
	 *            Last Card to be popped.
	 * @return A stack containing the Cards popped.
	 */
	public CardStack pop(Card card) {
		int index = stack.indexOf(card);

		return pop(stack.size() - index);
	}

	/**
	 * @return The number of Cards contained within the stack.
	 */
	public int cardCount() {
		return stack.size();
	}

	/**
	 * Determines whether or not the stack contains a specified Card.
	 * 
	 * @param c
	 *            Card to check.
	 * @return <code>true</code> if the stack contains the card,
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(Card c) {
		return stack.contains(c);
	}

	/**
	 * Determines if the CardStack holds no Cards.
	 * 
	 * @return <code>true</code> if the stack is empty, otherwise
	 *         <code>false</code>.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Determines if the CardStack is considered completed.
	 * 
	 * @return <code>true</code> if the stack is completed according to the
	 *         stack's StackRules, otherwise <code>false</code>.
	 */
	public boolean isCompleted() {
		return rules.isCompleted(this);
	}

	/**
	 * Determines if the CardStack is considered playable.
	 * 
	 * @return <code>true</code> if the stack is playable according to the
	 *         stack's StackRules, otherwise <code>false</code>.
	 */
	public boolean isPlayable() {
		return rules.isPlayable(this);
	}

	/**
	 * Determines if the CardStack contains any Cards that have a
	 * <code>Facing.FaceDown</code> facing.
	 * 
	 * @return <code>true</code> if the stack contains any covered Cards,
	 *         otherwise <code>false</code>.
	 */
	public boolean containsCovered() {
		for (Card card : stack) {
			if (card.getFacing() == Card.Facing.FaceDown)
				return true;
		}
		return false;
	}

	/**
	 * Reverses the Cards contained within the CardStack
	 */
	public void reverse() {
		CardStack newStack = new CardStack(rules);

		while (!this.isEmpty())
			newStack.push(pop());

		stack = newStack.stack;
	}

	@Override
	/**
	 * @return String representation of the CardStack.
	 */
	public String toString() {
		return stack.toString();
	}

}
