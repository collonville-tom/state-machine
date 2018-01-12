package destKit.metamodel.core;

/**
 * @author Collonvillé
 */
public class Event {

	public static final boolean UNCONTROLLABLE = false;
	public static final boolean CONTROLLABLE = true;
	public static final boolean UNOBSERVABLE = false;
	public static final boolean OBSERVABLE = true;

	public Event() {
	}

	/**
	 */
	private String name;
	/**
	 */
	private boolean controllable;
	/**
	 */
	private boolean observable;

	public Event(String name, boolean controllable, boolean observable) {
		this.name = name;
		this.controllable = controllable;
		this.observable = observable;
	}

	public Event(String name) {
		this(name, true, true);
	}

	/**
	 * @return Returns the controllable.
	 */
	public boolean isControllable() {
		return controllable;
	}

	/**
	 * @param controllable
	 *            The controllable to set.
	 */
	public void setControllable(boolean controllable) {
		this.controllable = controllable;
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

	/**
	 * @return Returns the observable.
	 */
	public boolean isObservable() {
		return observable;
	}

	/**
	 * @param observable
	 *            The observable to set.
	 */
	public void setObservable(boolean observable) {
		this.observable = observable;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("Event: ");
		buff.append(this.name);
		buff.append(", controllable: ");
		buff.append(this.controllable);
		buff.append(", observable: ");
		buff.append(this.observable);
		return buff.toString();
	}

	public Event clone() {
		Event clone = new Event(this.name, this.controllable, this.observable);
		return clone;
	}

	public boolean equals(Object event) {
		if (event == this)
			return true;
		if (event instanceof Event) {
			Event e = (Event) event;
			if (e.name.equals(this.name) && e.controllable == this.controllable
					&& e.observable == this.observable)
				return true;
			return false;
		}
		return false;

	}
}
