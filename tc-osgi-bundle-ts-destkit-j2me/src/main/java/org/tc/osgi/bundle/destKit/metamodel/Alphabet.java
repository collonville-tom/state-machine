package destKit.metamodel;

import destKit.metamodel.core.Event;
import destKit.utils.Collection;
import destKit.utils.Collections;
import destKit.utils.HashSet;
import destKit.utils.Iterator;
import destKit.utils.Predicate;
import destKit.utils.Transformer;

public class Alphabet extends HashSet {

	public Alphabet() {
	}

	public Alphabet clone() {
		return (Alphabet) Collections.collect(this, new Transformer() {

			public Collection evaluate(Collection c, Object e) {
				((Alphabet) c).addEvent(((Event) e).clone());
				return c;
			}
		});
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("\nAlphabet:");
		Iterator it = this.iterator();
		for (; it.hasNext();) {
			buff.append("\n");
			buff.append(it.next().toString());
		}
		return buff.toString();
	}

	public Alphabet getUncontrollableAlphabet() {
		return (Alphabet) Collections.reject(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((Event) e).isControllable();
			}
		});
	}

	public Alphabet getControllableAlphabet() {
		return (Alphabet) Collections.select(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((Event) e).isControllable();
			}
		});
	}

	public Alphabet getUnobservableAlphabet() {
		return (Alphabet) Collections.reject(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((Event) e).isObservable();
			}

		});
	}

	public Alphabet getObservableAlphabet() {
		return (Alphabet) Collections.select(this, new Predicate() {

			public boolean evaluate(Object e) {
				return ((Event) e).isObservable();
			}

		});
	}

	public boolean addEvent(Event e) {
		if (!this.containsEvent(e))
			return super.add(e);
		return false;

	}

	public boolean removeEvent(Event e) {
		final Event s = e;
		Event event = (Event) Collections.detect(this, new Predicate() {

			public boolean evaluate(Object e1) {
				if (((Event) e1).equals(s))
					return true;
				return false;
			}
		});
		if (event != null)
			return super.remove(event);
		return false;

	}

	public boolean containsEvent(Event e) {
		final Event event = e;
		if (Collections.detect(this, new Predicate() {

			public boolean evaluate(Object e1) {
				if (((Event) e1).equals(event))
					return true;
				return false;
			}
		}) != null)
			return true;
		return false;
	}

	public Event getEvent(String name) {
		Iterator it = this.iterator();
		for (; it.hasNext();) {
			Event e = (Event) it.next();
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}

}
