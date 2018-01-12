package destKit.metamodel;

import destKit.metamodel.core.State;
import destKit.metamodel.core.Transition;
import destKit.utils.Collections;
import destKit.utils.HashSet;
import destKit.utils.Iterator;
import destKit.utils.Predicate;

public class TransitionsSet extends HashSet {

	public TransitionsSet() {
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("\nTransitions:");
		Iterator it = this.iterator();
		for (; it.hasNext();) {
			buff.append("\n\n");
			buff.append(it.next().toString());
		}
		return buff.toString();
	}

	public TransitionsSet getTransitionsWhere(State state) {
		final State s = state;
		return (TransitionsSet) Collections.select(this, new Predicate() {

			public boolean evaluate(Object e) {
				if (((Transition) e).getInput().equals(s)
						|| ((Transition) e).getOutput().equals(s))
					return true;
				return false;
			}

		});
	}

	public TransitionsSet clone() {
		TransitionsSet clone = new TransitionsSet();
		Iterator it = this.iterator();
		for (; it.hasNext();) {
			clone.addTransition(((Transition) it.next()).clone());
		}
		return clone;
	}

	public boolean addTransition(Transition e) {
		if (!this.containsTransition(e))
			return super.add(e);
		return false;

	}

	public boolean containsTransition(Transition e) {
		final Transition t = e;
		if (Collections.detect(this, new Predicate() {

			public boolean evaluate(Object e1) {
				if (((Transition) e1).equals(t))
					return true;
				return false;
			}
		}) != null)
			return true;
		return false;
	}

	public boolean removeTransition(Transition e) {
		final Transition s = e;
		Transition transition = (Transition) Collections.detect(this,
				new Predicate() {

					public boolean evaluate(Object e1) {
						if (((Transition) e1).equals(s))
							return true;
						return false;
					}
				});
		if (transition != null) {
			transition.getInput().getPossibleEvents().removeEvent(
					transition.getEvent());
			return super.remove(transition);
		}
		return false;

	}
}
