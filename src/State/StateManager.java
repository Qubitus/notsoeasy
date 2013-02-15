package State;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


import Card.CardDeck;

public class StateManager extends Observable {

	private int currentStateCounter;
	private GameState currentState;
	private List<GameState> states;

	public StateManager() {
		currentStateCounter = -1;
		
		states = new ArrayList<GameState>();
	}
	
	public void init() {
		states.clear();
		
		CardDeck newDeck = new CardDeck();
		newDeck.shuffle();
		
		GameState newState = new GameState(newDeck);
		states.add(newState);
		
		currentStateCounter = 0;
		
		setCurrentState();
	}
	
	public void addState() {
		if (currentStateCounter < (states.size() -1)) {
			states = states.subList(0, currentStateCounter + 1);
		}

		states.add(currentState.clone());
		currentStateCounter += 1;
		
		setChanged();
		notifyObservers();
	}

	public boolean canUndo() {
		return currentStateCounter > 0;
	}
	
	public void undo() {
		if (canUndo()) {
			currentStateCounter -= 1;
			setCurrentState();
		}
	}

	public boolean canRedo() {
		return currentStateCounter < (states.size() - 1);
	}
	
	public void redo() {
		if (canRedo()) {
			currentStateCounter += 1;
			setCurrentState();
		}
	}

	public void reset() {
		if (currentStateCounter > 0) {
			currentStateCounter = 0;
			setCurrentState();
		}
	}
	
	private void setCurrentState() {
		currentState = states.get(currentStateCounter).clone();
		
		setChanged();
		notifyObservers();
	}
	
	public GameState getCurrentState() {
		return currentState;
	}

}
