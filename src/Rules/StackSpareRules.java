package Rules;

import Card.Card;
import Card.CardStack;

/**
 * Rules implementation for a spare CardStack
 * 
 * @author Pyck Nicolas
 * 
 */
public class StackSpareRules implements StackRules {

	@Override
	public boolean validMove(Card source, CardStack target) {
		return false;
	}

	@Override
	public boolean validMove(CardStack source, CardStack target) {
		return false;
	}

	@Override
	public boolean isCompleted(CardStack stack) {
		if (stack.getSize() == 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean isPlayable(CardStack stack) {
		return true;
	}

}
