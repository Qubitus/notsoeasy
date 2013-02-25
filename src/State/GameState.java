package State;

import java.util.Stack;

import Card.Card;
import Card.CardDeck;
import Card.CardStack;
import Rules.StackRules;

/**
 * A game state implementation class.
 * 
 * @author Pyck Nicolas
 * 
 */
public class GameState {

	private CardStack[] playStacks;
	private CardStack[] spareStacks;

	/**
	 * Default constructor.
	 * 
	 * @param deck
	 *            The CardDeck from which Cards will be drawn.
	 */
	public GameState(CardDeck deck) {
		initPlayStacks(deck);
		initSpareStacks(deck);
	}

	/**
	 * Constructor for a GameState that will be identical to the specified
	 * GameState.
	 * 
	 * @param oldState
	 *            The base GameState to be cloned.
	 */
	private GameState(GameState oldState) {
		// Deep copy the playstacks
		playStacks = new CardStack[oldState.playStacks.length];
		for (int i = 0; i < oldState.playStacks.length; i++) {
			this.playStacks[i] = oldState.playStacks[i].clone();
		}

		// Deep copy the sparestacks
		spareStacks = new CardStack[oldState.spareStacks.length];
		for (int i = 0; i < oldState.spareStacks.length; i++) {
			spareStacks[i] = oldState.spareStacks[i].clone();
		}
	}

	/**
	 * Initializes the play CardStacks.
	 * 
	 * @param deck
	 *            CardDeck object from which to draw Cards.
	 */
	private void initPlayStacks(CardDeck deck) {
		CardStack[] playStacks = new CardStack[8];

		for (int i = 0; i < playStacks.length; i++) {
			playStacks[i] = new CardStack(StackRules.PLAY_RULES);
			playStacks[i].push(deck.pop(6));

			// Flip the first 3 cards of stacks 2, 4 & 6
			if (i % 2 != 0) {
				Stack<Card> cards = playStacks[i].getCards();
				for (int j = 0; j < 3; j++) {
					cards.get(j).flip();
				}
			}
		}

		this.playStacks = playStacks;
	}

	/**
	 * Initializes the spare CardStacks.
	 * 
	 * @param deck
	 *            CardDeck object from which to draw Cards.
	 */
	private void initSpareStacks(CardDeck deck) {
		CardStack[] spareStacks = new CardStack[4];

		for (int i = 0; i < spareStacks.length; i++) {
			spareStacks[i] = new CardStack(StackRules.SPARE_RULES);
			spareStacks[i] = deck.pop(1);
		}

		this.spareStacks = spareStacks;
	}

	/**
	 * Deep copy clone.
	 * 
	 * @return GameState object identical to the original GameState.
	 */
	public GameState clone() {
		return new GameState(this);
	}

	/**
	 * Determines whether the GameState is considered completed.
	 * 
	 * @return <code>true</code> is all CardStacks are completed, otherwise
	 *         <code>false</code>.
	 */
	public boolean isCompleted() {
		for (CardStack stack : playStacks) {
			if (!stack.isCompleted())
				return false;
		}

		for (CardStack stack : spareStacks) {
			if (!stack.isCompleted())
				return false;
		}

		return true;
	}

	/**
	 * Determines whether the GameState is considered playable.
	 * 
	 * @return <code>true</code> if all CardStacks are still playable,
	 *         <code>false</code> if any CardStack is unplayable.
	 */
	public boolean isPlayable() {
		for (int i = 0; i < playStacks.length; i += 2) {
			if (!playStacks[i].isPlayable())
				return false;
		}
		return true;
	}

	/**
	 * @return A CardStack array containing all play CardStacks.
	 */
	public CardStack[] getPlayStacks() {
		return playStacks;
	}

	/**
	 * @return A CardStack array containing all spare CardStacks.
	 */
	public CardStack[] getSpareStacks() {
		return spareStacks;
	}

}
