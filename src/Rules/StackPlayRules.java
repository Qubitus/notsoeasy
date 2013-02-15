package Rules;

import java.util.Stack;

import Card.Card;
import Card.CardStack;

public class StackPlayRules implements StackRules {

	public StackPlayRules() {
	}

	@Override
	public boolean validMove(Card source, CardStack target) {
		boolean valid = true;

		if (target.getSize() > 0) {
			Card targetCard = target.getCards().lastElement();

			valid &= source.getSuit() == targetCard.getSuit();
			valid &= (source.getRank().ordinal() + 1) == targetCard.getRank().ordinal();
		}
		else {
			valid &= source.getRank() == Card.Rank.King;
		}

		return valid;
	}

	@Override
	public boolean validMove(CardStack source, CardStack target) {
		Card sourceCard = source.getCards().firstElement();

		return validMove(sourceCard, target);
	}

	@Override
	public boolean isCompleted(CardStack stack) {
		if (stack.isEmpty())
			return true;
		else if (stack.getSize() < Card.Rank.values().length || stack.containsCovered())
			return false;
		else {
			Stack<Card> cards = stack.getCards();
			for (int i = 0; i < cards.size() - 1; i++) {
				if (cards.get(i).getSuit() != cards.get(i + 1).getSuit() && cards.get(i).compareTo(cards.get(i + 1)) != -1)
					return false;
			}
			return true;
		}

	}

	@Override
	public boolean isPlayable(CardStack stack) {
		if (stack.containsCovered()) {
			Stack<Card> cards = stack.getCards();
			for (int i = stack.getSize() - 1; i > 0; i--) {
				if (cards.get(i - 1).getFacing() == Card.Facing.FaceDown) {
					for (int j = i - 1; i >= 0; j--) {
						if (cards.get(i).getSuit() == cards.get(j).getSuit()
								&& cards.get(i).getRank().ordinal() + 1 == cards.get(j).getSuit().ordinal())
							return false;
					}

				}
			}
		}
		return true;
	}

}
