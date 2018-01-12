package destKit.metamodel.core;

import destKit.metamodel.Alphabet;
import destKit.utils.Iterator;

/**
 * @author Collonvillé
 */
public class State {

	public static final boolean NOTINITIAL = false;
	public static final boolean INITIAL = true;
	public static final boolean MARKED = true;
	public static final boolean NOTMARKED = false;

	private Alphabet possibleEvents = new Alphabet();
	/**
	 */
	private int id = 0;
	/**
	 */
	private boolean initial;
	/**
	 */
	private boolean marked;
	/**
	 */
	private String name;

	public State() {
	}

	public State(String name, boolean initial, boolean marked) {
		this.name = name;
		this.initial = initial;
		this.marked = marked;
	}

	public State(String name, boolean marked) {
		this(name, false, marked);
	}

	public State(String name) {
		this(name, false);
	}

	/**
	 * @return Returns the initial.
	 */
	public boolean isInitial() {
		return initial;
	}

	/**
	 * @param initial
	 *            The initial to set.
	 */
	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	/**
	 * @return Returns the marked.
	 */
	public boolean isMarked() {
		return marked;
	}

	/**
	 * @param marked
	 *            The marked to set.
	 */
	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("ID:");
		buff.append(this.id);
		buff.append(", State: ");
		buff.append(this.name);
		buff.append(", initial: ");
		buff.append(this.initial);
		buff.append(", marked: ");
		buff.append(this.marked);
		return buff.toString();
	}

	public State clone() {
		State clone = new State(name, initial, marked);
		Iterator it = this.possibleEvents.iterator();
		for (; it.hasNext();) {
			clone.getPossibleEvents().addEvent(((Event) it.next()).clone());
		}
		return clone;
	}

	public boolean equals(Object s) {
		if (s == this)
			return true;
		if (s instanceof State) {
			State state = (State) s;
			if (state.getName().equals(this.name)
					&& state.isInitial() == this.initial
					&& state.isMarked() == this.marked)
				return true;
			return false;
		}
		return false;
	}

	/**
	 * @return Returns the possibleEvents.
	 */
	public Alphabet getPossibleEvents() {
		return possibleEvents;
	}

	/**
	 * @param possibleEvents
	 *            The possibleEvents to set.
	 */
	public void setPossibleEvents(Alphabet possibleEvents) {
		this.possibleEvents = possibleEvents;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

}
