package Rules;

import Card.Card;
import Card.CardStack;

/**
 * Rules interface with various method definitions involving a CardStack.
 * 
 * @author Pyck Nicolas
 * 
 */
public interface StackRules {

	static final StackNoRules NO_RULES = new StackNoRules();
	static final StackPlayRules PLAY_RULES = new StackPlayRules();
	static final StackSpareRules SPARE_RULES = new StackSpareRules();

	/**
	 * Determines whether the specified move is valid.
	 * 
	 * @param source
	 *            The source Card to be moved.
	 * @param target
	 *            The target CardStack that will receive the source Card.
	 * @return <code>true</code> if the specified move is valid,
	 *         <code>false</code> otherwise.
	 */
	boolean validMove(Card source, CardStack target);

	/**
	 * Determines whether the specified move is valid.
	 * 
	 * @param source
	 *            The source CardStack to be moved.
	 * @param target
	 *            The target CardStack that will receive the source CardStack.
	 * @return <code>true</code> if the specified move is valid,
	 *         <code>false</code> otherwise.
	 */
	boolean validMove(CardStack source, CardStack target);

	/**
	 * Determines whether the CardStack is considered completed.
	 * 
	 * @param stack
	 *            The CardStack to be evaluated.
	 * @return <code>true</code> if the CardStack is completed,
	 *         <code>false</code> otherwise.
	 */
	boolean isCompleted(CardStack stack);

	/**
	 * Determines whether the CardStack is considered playable.
	 * 
	 * @param stack
	 *            The CardStack to be evaluated.
	 * @return <code>true</code> if the CardStack is playable,
	 *         <code>false</code> otherwise.
	 */
	boolean isPlayable(CardStack stack);
}
