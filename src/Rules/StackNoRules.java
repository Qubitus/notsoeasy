package Rules;

import Card.Card;
import Card.CardStack;

public class StackNoRules implements StackRules {

	@Override
	public boolean validMove(Card source, CardStack target) {
		return true;
	}

	@Override
	public boolean validMove(CardStack source, CardStack target) {
		return true;
	}

	@Override
	public boolean isCompleted(CardStack stack) {
		return true;
	}

	@Override
	public boolean isPlayable(CardStack stack) {
		return true;
	}

}
