package org.tc.osgi.bundle.ts.kripke.metamodel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.State;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;
import org.tc.osgi.bundle.utils.collection.ITransformer;

@SuppressWarnings("serial")
public class StatesSet extends HashSet<State> implements Cloneable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = 2729026922048189073L;
    private int stateIndexValue = 0;

    public StatesSet() {
    }

    @Override
    public boolean add(final State e) {
        if (stateIndexValue < e.getId()) {
            stateIndexValue = e.getId();
        }
        return super.add(e);
    }

    public boolean addState(final State e) {
        if (!containsState(e)) {
            e.setId(stateIndexValue++);
            return super.add(e);
        }
        return false;

    }

    @Override
    public StatesSet clone() {
        return (StatesSet) Collections.getInstance().collect(this, new ITransformer<State>() {

            @Override
            public void evaluate(final Collection<State> c, final State e) {
                ((StatesSet) c).addState(e.clone());
            }
        });
    }

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

    public StatesSet getInitialStateSet() {
        return (StatesSet) Collections.getInstance().select(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.isInitial();
            }

        });
    }

    public PropAtomSet getPropAtomSet(final State state) {
        final State s = state;
        return Collections.getInstance().extract(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.equals(s);
            }

        }).getPropAtomSet();
    }

    public StatesSet getStateSetWhere(final PropAtom pa) {
        final PropAtom propAtom = pa;
        return (StatesSet) Collections.getInstance().select(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.getPropAtomSet().containsPropAtom(propAtom);
            }

        });
    }

    public StatesSet getStateSetWhereNot(final PropAtom pa) {
        final PropAtom propAtom = pa;
        return (StatesSet) Collections.getInstance().reject(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.getPropAtomSet().containsPropAtom(propAtom);
            }

        });
    }

    public StatesSet getUnInitialStateSet() {
        return (StatesSet) Collections.getInstance().reject(this, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                return e.isInitial();
            }

        });
    }

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
