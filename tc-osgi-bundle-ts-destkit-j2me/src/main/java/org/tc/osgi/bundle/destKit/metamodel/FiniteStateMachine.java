package destKit.metamodel;

import destKit.metamodel.core.Event;
import destKit.metamodel.core.State;
import destKit.metamodel.core.Transition;
import destKit.utils.Collections;
import destKit.utils.Iterator;
import destKit.utils.Predicate;
import destKit.visiteur.Visitor;

/**
 * @author Collonvillé
 */
public class FiniteStateMachine {

	private StatesSet statesSet = new StatesSet();
	private Alphabet alphabet = new Alphabet();
	private TransitionsSet transitionFunction = new TransitionsSet();
	private String name;

	public FiniteStateMachine() {
	}

	public FiniteStateMachine(String name) {
		this.name = name;
	}

	public void visit(Visitor visitor) {
		visitor.visit(this);
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("FSM: ");
		buff.append(this.alphabet.toString());
		buff.append("\n");
		buff.append(this.statesSet.toString());
		buff.append("\n");
		buff.append(this.transitionFunction.toString());
		return buff.toString();
	}

	public FiniteStateMachine clone() {
		FiniteStateMachine clone = new FiniteStateMachine(this.name);
		Iterator it1 = this.alphabet.iterator();
		for (; it1.hasNext();) {
			Event e = (Event) it1.next();
			if (!clone.contains(e))
				clone.addEvent(e);
		}

		Iterator it2 = this.statesSet.iterator();
		for (; it2.hasNext();) {
			State s = (State) it2.next();
			if (!clone.contains(s))
				clone.addState(s);
		}

		Iterator it3 = this.transitionFunction.iterator();
		for (; it3.hasNext();) {
			Transition t = (Transition) it3.next();
			if (!clone.contains(t))
				clone.addTransition(t);
		}

		return clone;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Alphabet getCloneAlphabet() {
		return alphabet.clone();
	}

	public StatesSet getCloneStatesSet() {
		return statesSet.clone();
	}

	public TransitionsSet getCloneTransitionFunction() {
		return transitionFunction.clone();
	}

	/**
	 * @return Returns the alphabet.
	 */
	public Alphabet getAlphabet() {
		return alphabet;
	}

	/**
	 * @param alphabet
	 *            The alphabet to set.
	 */
	public void setAlphabet(Alphabet alphabet) {
		this.alphabet = alphabet;
	}

	/**
	 * @return Returns the statesSet.
	 */
	public StatesSet getStatesSet() {
		return statesSet;
	}

	/**
	 * @param statesSet
	 *            The statesSet to set.
	 */
	public void setStatesSet(StatesSet statesSet) {
		this.statesSet = statesSet;
	}

	/**
	 * @return Returns the transitionFunction.
	 */
	public TransitionsSet getTransitionFunction() {
		return transitionFunction;
	}

	/**
	 * @param transitionFunction
	 *            The transitionFunction to set.
	 */
	public void setTransitionFunction(TransitionsSet transitionFunction) {
		this.transitionFunction = transitionFunction;
	}

	// PARTIE TRANSITIONS
	public void removeTransition(String input, String event, String output) {
		this.transitionFunction.removeTransition(this.getTransition(input,
				event, output));
	}

	public void removeTransition(State input, Event event, State output) {
		this.transitionFunction.removeTransition(this
				.getTransition(new Transition(input, event, output)));
	}

	public void removeTransition(Transition t) {
		this.transitionFunction.removeTransition(this.getTransition(t));
	}

	public void addTransition(String input, String event, String output) {
		Event e = this.getEvent(event);
		State iState = this.getState(input);
		State oState = this.getState(output);
		if (e == null) {
			e = new Event(event);
			this.addEvent(e);
		}
		if (iState == null) {
			iState = new State(input);
			this.addState(iState);
		}
		if (oState == null) {
			oState = new State(output);
			this.addState(oState);
		}
		Transition t = new Transition(iState, e, oState);
		if (!this.contains(t)) {
			this.transitionFunction.addTransition(t);
			iState.getPossibleEvents().addEvent(e);
		}
	}

	public void addTransition(Transition t) {
		Transition tc = t.clone();
		if (!this.contains(tc)) {
			if (!this.contains(tc.getEvent()))
				this.alphabet.addEvent(tc.getEvent());
			else
				tc.setEvent(this.getEvent(tc.getEvent()));

			if (!this.contains(tc.getInput()))
				this.statesSet.addState(tc.getInput());
			else
				tc.setInput(this.getState(tc.getInput()));

			if (!this.contains(tc.getOutput()))
				this.statesSet.addState(tc.getOutput());
			else
				tc.setOutput(this.getState(tc.getOutput()));
			this.transitionFunction.addTransition(tc);
			tc.getInput().getPossibleEvents().addEvent(tc.getEvent());
		}
	}

	public boolean contains(Transition t) {
		if (this.getTransition(t) != null)
			return true;
		return false;
	}

	private Transition getTransition(Transition s) {
		final Transition t = s;
		return (Transition) Collections.detect(this.transitionFunction,
				new Predicate() {

					public boolean evaluate(Object e) {
						if (((Transition) e).equals(t))
							return true;
						return false;
					}

				});
	}

	private Transition getTransition(String input, String event, String output) {
		final Transition t = new Transition(this.getState(input), this
				.getEvent(event), this.getState(output));
		return this.getTransition(t);
	}

	public TransitionsSet getTransitionsWhere(State state) {
		return this.transitionFunction.getTransitionsWhere(state).clone();

	}

	// PARTIE ALPHABET

	public void addEvent(Event e) {
		if (!this.contains(e))
			this.alphabet.addEvent(e.clone());
	}

	public boolean contains(Event e) {
		if (this.getEvent(e) != null)
			return true;
		return false;
	}

	private Event getEvent(Event event) {
		final Event s = event;
		return (Event) Collections.detect(this.alphabet, new Predicate() {

			public boolean evaluate(Object e) {
				if (((Event) e).equals(s))
					return true;
				return false;
			}
		});
	}

	private Event getEvent(String event) {
		final Event s = new Event(event);
		return (Event) Collections.detect(this.alphabet, new Predicate() {

			public boolean evaluate(Object e) {
				if (((Event) e).getName().equals(s.getName()))
					return true;
				return false;
			}
		});
	}

	public Alphabet getUncontrollableAlphabet() {
		return this.getAlphabet().getUncontrollableAlphabet().clone();

	}

	public Alphabet getControllableAlphabet() {
		return this.getAlphabet().getControllableAlphabet().clone();

	}

	public Alphabet getUnobservableAlphabet() {
		return this.getAlphabet().getUnobservableAlphabet().clone();

	}

	public Alphabet getObservableAlphabet() {
		return this.getAlphabet().getObservableAlphabet().clone();

	}

	// PARTIE STATES

	public void removeState(String name) {
		State s = this.getState(name);
		this.transitionFunction.removeAll(this.transitionFunction
				.getTransitionsWhere(s));
		this.statesSet.removeState(s);

	}

	public void removeState(State state) {
		State s = this.getState(state);
		this.transitionFunction.removeAll(this.transitionFunction
				.getTransitionsWhere(s));
		this.statesSet.removeState(s);
	}

	public void addState(State s) {
		if (!this.contains(s)) {
			State state = s.clone();
			State sValid = new State(state.getName(), state.isInitial(), state
					.isMarked());
			Iterator it = state.getPossibleEvents().iterator();
			for (; it.hasNext();) {
				Event e = (Event) it.next();
				if (this.getEvent(e) != null)
					sValid.getPossibleEvents().addEvent(this.getEvent(e));
				else
					this.addEvent(e);
				sValid.getPossibleEvents().addEvent(this.getEvent(e));
			}
			this.statesSet.addState(sValid);
		}
	}

	public boolean contains(State s) {
		if (this.getState(s) != null)
			return true;
		return false;
	}

	private State getState(State state) {
		final State s = state;
		return (State) Collections.detect(this.statesSet, new Predicate() {

			public boolean evaluate(Object e) {
				if (((State) e).equals(s))
					return true;
				return false;
			}

		});
	}

	private State getState(String state) {
		final State s = new State(state);
		return (State) Collections.detect(this.statesSet, new Predicate() {

			public boolean evaluate(Object e) {
				if (((State) e).getName().equals(s.getName()))
					return true;
				return false;
			}
		});
	}

	private State getState(final int state) {
		return (State) Collections.detect(this.statesSet, new Predicate() {

			public boolean evaluate(Object e) {
				if (((State) e).getId() == state)
					return true;
				return false;
			}
		});
	}

	public State getCloneState(String state) {
		return this.getState(state).clone();
	}

	public State getCloneState(int state) {
		return this.getState(state).clone();
	}

	public StatesSet getMarkedStateSet() {
		return this.getStatesSet().getMarkedStateSet().clone();

	}

	public StatesSet getInitialStateSet() {
		return this.getStatesSet().getInitialStateSet().clone();
	}

	public StatesSet getUnMarkedStateSet() {
		return this.getStatesSet().getUnMarkedStateSet().clone();

	}

	public StatesSet getUnInitialStateSet() {
		return this.getStatesSet().getUnInitialStateSet().clone();
	}

	public Alphabet getPossibleEvents(State state) {
		return this.getStatesSet().getPossibleEvents(state).clone();
	}
}
