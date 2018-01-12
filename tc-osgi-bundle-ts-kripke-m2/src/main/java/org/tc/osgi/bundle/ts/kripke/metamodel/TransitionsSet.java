package org.tc.osgi.bundle.ts.kripke.metamodel;

import java.util.HashSet;

import org.tc.osgi.bundle.ts.kripke.metamodel.core.State;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.Transition;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;

@SuppressWarnings("serial")
public class TransitionsSet extends HashSet<Transition> implements Cloneable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -6200575301589598996L;

    public TransitionsSet() {
    }

    public boolean addTransition(final Transition e) {
        if (!containsTransition(e)) {
            return super.add(e);
        }
        return false;

    }

    @Override
    public TransitionsSet clone() {
        final TransitionsSet clone = new TransitionsSet();
        for (final Transition t : this) {
            clone.addTransition(t.clone());
        }
        return clone;
    }

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
            return super.remove(transition);
        }
        return false;

    }

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
