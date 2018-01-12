package org.tc.osgi.bundle.ts.destkit.metamodel;

import java.util.HashSet;

import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Transition;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;

/**
 * TransitionsSet.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class TransitionsSet extends HashSet<Transition> implements Cloneable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = 7599324140847652846L;

    /**
     * TransitionsSet constructor.
     */
    public TransitionsSet() {
    }

    /**
     * addTransition.
     * @param e Transition
     * @return boolean
     */
    public boolean addTransition(final Transition e) {
        if (!containsTransition(e)) {
            return super.add(e);
        }
        return false;

    }

    /**
     * @return TransitionsSet
     * @see java.util.HashSet#clone()
     */
    @Override
    public TransitionsSet clone() {
        final TransitionsSet clone = new TransitionsSet();
        for (final Transition t : this) {
            clone.addTransition(t.clone());
        }
        return clone;
    }

    /**
     * containsTransition.
     * @param e Transition
     * @return boolean
     */
    public boolean containsTransition(final Transition e) {
        final Transition t = e;
        if (Collections.getInstance().extract(this, new IPredicate<Transition>() {

            @Override
            public boolean evaluate(final Transition e1) {
                if (e1.equals(t)) {
                    return true;
                }
                return false;
            }
        }) != null) {
            return true;
        }
        return false;
    }

    /**
     * getTransitionsWhere.
     * @param state State
     * @return TransitionsSet
     */
    public TransitionsSet getTransitionsWhere(final State state) {
        final State s = state;
        return (TransitionsSet) Collections.getInstance().select(this, new IPredicate<Transition>() {

            @Override
            public boolean evaluate(final Transition e) {
                if (e.getInput().equals(s) || e.getOutput().equals(s)) {
                    return true;
                }
                return false;
            }

        });
    }

    /**
     * removeTransition.
     * @param e Transition
     * @return boolean
     */
    public boolean removeTransition(final Transition e) {
        final Transition s = e;
        final Transition transition = Collections.getInstance().extract(this, new IPredicate<Transition>() {

            @Override
            public boolean evaluate(final Transition e1) {
                if (e1.equals(s)) {
                    return true;
                }
                return false;
            }
        });
        if (transition != null) {
            transition.getInput().getPossibleEvents().removeEvent(transition.getEvent());
            return super.remove(transition);
        }
        return false;

    }

    /**
     * @return String
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("\nTransitions:");
        for (final Transition t : this) {
            buff.append("\n\n");
            buff.append(t.toString());
        }
        return buff.toString();
    }
}
