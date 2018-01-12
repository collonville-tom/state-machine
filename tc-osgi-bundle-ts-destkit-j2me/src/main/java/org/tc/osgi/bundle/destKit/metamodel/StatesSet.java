package destKit.metamodel;

import destKit.metamodel.core.State;
import destKit.utils.Collection;
import destKit.utils.Collections;
import destKit.utils.HashSet;
import destKit.utils.Iterator;
import destKit.utils.Predicate;
import destKit.utils.Transformer;

public class StatesSet extends HashSet {

	private int stateIndexValue = 0;

	public StatesSet() {
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("\nStates:");

		for (Iterator it2 = this.iterator(); it2.hasNext();) {
			buff.append("\n");
			buff.append(it2.next().toString());
		}

		return buff.toString();
	}

	public StatesSet getMarkedStateSet() {
		return (StatesSet) Collections.select(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((State) e).isMarked();
			}

		});

	}

	public StatesSet getInitialStateSet() {
		return (StatesSet) Collections.select(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((State) e).isInitial();
			}

		});
	}

	public StatesSet getUnMarkedStateSet() {
		return (StatesSet) Collections.reject(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((State) e).isMarked();
			}

		});

	}

	public StatesSet getUnInitialStateSet() {
		return (StatesSet) Collections.reject(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((State) e).isInitial();
			}

		});
	}

	public StatesSet clone() {
		return (StatesSet) Collections.collect(this, new Transformer() {

			public Collection evaluate(Collection c, Object e) {
				((StatesSet) c).addState(((State) e).clone());
				return c;
			}
		});
	}

	public Alphabet getPossibleEvents(State state) {
		final State s = state;
		return (Alphabet) ((State) Collections.detect(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((State) e).equals(s);
			}

		})).getPossibleEvents();
	}

	public boolean addState(State e) {
		if (!this.containsState(e)) {
			e.setId(stateIndexValue++);
			return super.add(e);
		}
		return false;

	}

	public boolean add(State e) {
		if (this.stateIndexValue < e.getId())
			this.stateIndexValue = e.getId();
		return super.add(e);
	}

	public boolean removeState(State e) {
		final State s = e;
		State state = (State) Collections.detect(this, new Predicate() {

			public boolean evaluate(Object e1) {
				if (((State) e1).equals(s))
					return true;
				return false;
			}
		});
		if (state != null)
			return super.remove(state);
		return false;

	}

	public boolean containsState(State e) {
		final State state = e;
		if (Collections.detect(this, new Predicate() {

			public boolean evaluate(Object e1) {
				if (((State) e1).equals(state))
					return true;
				return false;
			}
		}) != null)
			return true;
		return false;
	}

}
