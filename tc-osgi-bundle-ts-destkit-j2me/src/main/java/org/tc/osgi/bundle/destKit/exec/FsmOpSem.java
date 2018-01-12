package destKit.exec;

import destKit.exec.exception.ExecInitExeception;
import destKit.metamodel.Alphabet;
import destKit.metamodel.FiniteStateMachine;
import destKit.metamodel.core.Event;
import destKit.metamodel.core.State;
import destKit.metamodel.core.Transition;
import destKit.utils.Collections;
import destKit.utils.Predicate;

public class FsmOpSem {

	private FiniteStateMachine fsm;
	private State currentState;
	private Transition transitionAccepted;
	private Event receiveEvent;

	public FsmOpSem(FiniteStateMachine fsm) throws ExecInitExeception {
		this.fsm = fsm;
		if (fsm.getInitialStateSet().size() != 1)
			throw new ExecInitExeception("Plusieurs etats initiaux trouvés");
		this.currentState = (State) fsm.getInitialStateSet().toArray()[0];
	}

	public boolean hasNext() {
		if (fsm.getPossibleEvents(currentState).size() == 0)
			return false;
		return true;
	}

	public boolean update(Event receiveEvent) {
		System.out.println(receiveEvent.toString());
		if (fsm.getPossibleEvents(currentState).containsEvent(receiveEvent)) {
			this.receiveEvent = receiveEvent;
			this.transitionAccepted = (Transition) Collections.detect(fsm
					.getTransitionFunction(), new Predicate() {

				public boolean evaluate(Object e1) {
					if (((Transition) e1).getInput().equals(currentState)
							&& ((Transition) e1).getEvent().equals(
									FsmOpSem.this.receiveEvent))
						return true;
					return false;
				};
			});
			return true;
		}
		System.out.println("CurrentStateNotChange");
		return false;
	}

	public State next() {
		this.currentState = this.transitionAccepted.getOutput();
		System.out.println(currentState.getName());
		return this.currentState;
	}

	public Alphabet possibleEventInCurrentState() {
		return currentState.getPossibleEvents();
	}

}
