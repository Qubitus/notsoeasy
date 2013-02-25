package State;

import java.util.Stack;

import Card.Card;
import Card.CardDeck;
import Card.CardStack;
import Rules.StackRules;

public class GameState {

	private CardStack[]	playStacks;
	private CardStack[]	spareStacks;

	public GameState(CardDeck deck) {
		playStacks = initPlayStacks(deck);
		spareStacks = initSpareStacks(deck);
	}

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

	private CardStack[] initPlayStacks(CardDeck deck) {
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

		return playStacks;
	}

	private CardStack[] initSpareStacks(CardDeck deck) {
		CardStack[] spareStacks = new CardStack[4];

		for (int i = 0; i < spareStacks.length; i++) {
			spareStacks[i] = new CardStack(StackRules.SPARE_RULES);
			spareStacks[i] = deck.pop(1);
		}

		return spareStacks;
	}

	public GameState clone() {
		return new GameState(this);
	}
	
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

	public boolean isPlayable() {
		for (int i = 0; i < playStacks.length; i += 2) {
			if (!playStacks[i].isPlayable())
				return false;
		}
		return true;
	}
	
	public CardStack[] getPlayStacks() {
		return playStacks;
	}

	public CardStack[] getSpareStacks() {
		return spareStacks;
	}

}
