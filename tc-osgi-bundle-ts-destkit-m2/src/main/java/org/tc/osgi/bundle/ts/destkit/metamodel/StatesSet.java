package org.tc.osgi.bundle.ts.destkit.metamodel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;
import org.tc.osgi.bundle.utils.collection.ITransformer;

/**
 * StatesSet.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class StatesSet extends HashSet<State> implements Cloneable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -3562333007504254426L;
    /**
     * int stateIndexValue.
     */
    private int stateIndexValue = 0;

    /**
     * StatesSet constructor.
     */
    public StatesSet() {
    }

    /**
     * @param e State
     * @return boolean
     * @see java.util.HashSet#add(java.lang.Object)
     */
    @Override
    public boolean add(final State e) {
        if (stateIndexValue < e.getId()) {
            stateIndexValue = e.getId();
        }
        return super.add(e);
    }

    /**
     * addState.
     * @param e State
     * @return boolean
     */
    public boolean addState(final State e) {
        if (!containsState(e)) {
            e.setId(stateIndexValue++);
            return super.add(e);
        }
        return false;

    }

    /**
     * @return StatesSet
     * @see java.util.HashSet#clone()
     */
    @Override
    public StatesSet clone() {
        return (StatesSet) Collections.getInstance().collect(this, new ITransformer<State>() {

            @Override
            public void evaluate(final Collection<State> c, final State e) {
                ((StatesSet) c).addState(e.clone());
            }
        });
    }

    /**
     * containsState.
     * @param e State
     * @return boolean
     */
    public boolean containsState(final State e) {
        final State state = e;
        if (Collections.getInstance().extract(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e1) {
                if (e1.equals(state)) {
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
     * getInitialStateSet.
     * @return StatesSet
     */
    public StatesSet getInitialStateSet() {
        return (StatesSet) Collections.getInstance().select(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.isInitial();
            }

        });
    }

    /**
     * getMarkedStateSet.
     * @return StatesSet
     */
    public StatesSet getMarkedStateSet() {
        return (StatesSet) Collections.getInstance().select(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.isMarked();
            }

        });

    }

    /**
     * getPossibleEvents.
     * @param state State
     * @return Alphabet
     */
    public Alphabet getPossibleEvents(final State state) {
        final State s = state;
        return Collections.getInstance().extract(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.equals(s);
            }

        }).getPossibleEvents();
    }

    /**
     * getUnInitialStateSet.
     * @return StatesSet
     */
    public StatesSet getUnInitialStateSet() {
        return (StatesSet) Collections.getInstance().reject(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.isInitial();
            }

        });
    }

    /**
     * getUnMarkedStateSet.
     * @return StatesSet
     */
    public StatesSet getUnMarkedStateSet() {
        return (StatesSet) Collections.getInstance().reject(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.isMarked();
            }

        });

    }

    /**
     * removeState.
     * @param e State
     * @return boolean
     */
    public boolean removeState(final State e) {
        final State s = e;
        final State state = Collections.getInstance().extract(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e1) {
                if (e1.equals(s)) {
                    return true;
                }
                return false;
            }
        });
        if (state != null) {
            return super.remove(state);
        }
        return false;

    }

    /**
     * @return String
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("\nStates:");

        for (final Iterator<State> it2 = iterator(); it2.hasNext();) {
            buff.append("\n");
            buff.append(it2.next().toString());
        }

        return buff.toString();
    }

}
