package Rules;
import Card.Card;
import Card.CardStack;


public interface StackRules {

	static final StackNoRules NO_RULES = new StackNoRules();
	static final StackPlayRules PLAY_RULES = new StackPlayRules();
	static final StackSpareRules SPARE_RULES = new StackSpareRules();
	
	boolean validMove(Card source, CardStack target);
	
	boolean validMove(CardStack source, CardStack target);
	
	boolean isCompleted(CardStack stack);
	
	boolean isPlayable(CardStack stack);
}
