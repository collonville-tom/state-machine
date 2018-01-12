package destKit.metamodel.core;

/**
 * @author Collonvillé
 */
public class Transition {

	private Event event;
	private State input;
	private State output;

	public Transition() {
	}

	public Transition(State input, Event event, State output) {
		this.input = input;
		this.event = event;
		this.output = output;
	}

	/**
	 * @return Returns the event.
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event
	 *            The event to set.
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return Returns the input.
	 */
	public State getInput() {
		return input;
	}

	/**
	 * @param input
	 *            The input to set.
	 */
	public void setInput(State input) {
		this.input = input;
	}

	/**
	 * @return Returns the output.
	 */
	public State getOutput() {
		return output;
	}

	/**
	 * @param output
	 *            The output to set.
	 */
	public void setOutput(State output) {
		this.output = output;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer("Transition: ");
		buff.append("\nInput:\n");
		buff.append(this.input.toString());
		buff.append("\nOutput:\n");
		buff.append(this.output.toString());
		buff.append("\nEvent:\n");
		buff.append(this.event.toString());
		return buff.toString();
	}

	public Transition clone() {
		Transition clone = new Transition(this.input.clone(), event.clone(),
				output.clone());
		return clone;
	}

	public boolean equals(Object transition) {
		if (transition == this)
			return true;
		if (transition instanceof Transition) {
			Transition t = (Transition) transition;
			if (t.event.equals(this.event) && t.input.equals(this.input)
					&& t.output.equals(this.output))
				return true;
			return false;
		}
		return false;
	}
}
