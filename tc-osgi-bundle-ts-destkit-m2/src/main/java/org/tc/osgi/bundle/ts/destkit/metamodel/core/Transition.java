package org.tc.osgi.bundle.ts.destkit.metamodel.core;

import java.util.HashSet;
import java.util.Set;

import org.tc.osgi.bundle.ts.m3.IProperty;
import org.tc.osgi.bundle.ts.m3.IState;
import org.tc.osgi.bundle.ts.m3.ITransition;

/**
 * Transition.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class Transition implements Cloneable, ITransition {

    /**
     * Event event.
     */
    private Event event;
    /**
     * State input.
     */
    private State input;
    /**
     * State output.
     */
    private State output;

    /**
     * Transition constructor.
     */
    public Transition() {
    }

    /**
     * Transition constructor.
     * @param input State
     * @param event Event
     * @param output State
     */
    public Transition(final State input, final Event event, final State output) {
        this.input = input;
        this.event = event;
        this.output = output;
    }

    /**
     * @return Transition
     * @see java.lang.Object#clone()
     */
    @Override
    public Transition clone() {
        final Transition clone = new Transition(input.clone(), event.clone(), output.clone());
        return clone;
    }

    /**
     * @param transition Object
     * @return boolean
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object transition) {
        if (transition == this) {
            return true;
        }
        if (transition instanceof Transition) {
            final Transition t = (Transition) transition;
            if (t.event.equals(event) && t.input.equals(input) && t.output.equals(output)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * @return Event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * @return State
     */
    public State getInput() {
        return input;
    }

    /**
     * @return State
     */
    public State getOutput() {
        return output;
    }

    /**
     * @return Set<IProperty>
     * @see org.tc.osgi.bundle.ts.m3.ITransition#getProperty()
     */
    @Override
    public Set<IProperty> getProperty() {
        final HashSet<IProperty> set = new HashSet<IProperty>();
        set.add(event.clone());
        return set;
    }

    /**
     * @return IState
     * @see org.tc.osgi.bundle.ts.m3.ITransition#getSourceState()
     */
    @Override
    public IState getSourceState() {
        return input.clone();
    }

    /**
     * @return IState
     * @see org.tc.osgi.bundle.ts.m3.ITransition#getTargetState()
     */
    @Override
    public IState getTargetState() {
        return output.clone();
    }

    /**
     * @param event Event
     */
    public void setEvent(final Event event) {
        this.event = event;
    }

    /**
     * @param input State
     */
    public void setInput(final State input) {
        this.input = input;
    }

    /**
     * @param output State
     */
    public void setOutput(final State output) {
        this.output = output;
    }

    /**
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("Transition: ");
        buff.append("\nInput:\n");
        buff.append(input.toString());
        buff.append("\nOutput:\n");
        buff.append(output.toString());
        buff.append("\nEvent:\n");
        buff.append(event.toString());
        return buff.toString();
    }
}
