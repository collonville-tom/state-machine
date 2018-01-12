package org.tc.osgi.bundle.ts.kripke.metamodel;

import java.util.HashSet;
import java.util.Set;

import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.State;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.Transition;
import org.tc.osgi.bundle.ts.m3.IProperty;
import org.tc.osgi.bundle.ts.m3.IState;
import org.tc.osgi.bundle.ts.m3.ITransition;
import org.tc.osgi.bundle.ts.m3.ITs;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;
import org.tc.osgi.bundle.utils.pattern.visitor.IVisitor;

public class Kripke implements Cloneable, ITs {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -8954479258866073362L;
    private String name;
    private PropAtomSet propAtomSet = new PropAtomSet();
    private StatesSet statesSet = new StatesSet();
    private TransitionsSet transitionFunction = new TransitionsSet();

    public Kripke() {
    }

    public Kripke(final String name) {
        this.name = name;
    }

    @Override
    public void accept(final IVisitor visitor) {
        visitor.visit(this);
    }

    public void addPropAtom(final PropAtom e) {
        if (!this.contains(e)) {
            propAtomSet.addPropAtom(e.clone());
        }
    }

    public void addPropAtomTo(final State s, final PropAtom e) {
        final State state = s.clone();
        final PropAtom pa = e.clone();
        if (!this.contains(pa)) {
            addPropAtom(pa);
        }
        if (!this.contains(state)) {
            state.getPropAtomSet().add(pa);
            addState(state);
        } else {
            this.getState(state).getPropAtomSet().add(pa);
        }
    }

    public void addPropAtomTo(final String s, final PropAtom e) {
        State state = this.getState(s);
        final PropAtom pa = e.clone();
        if (!this.contains(pa)) {
            addPropAtom(pa);
        }
        if (state == null) {
            state = new State(s);
            state.getPropAtomSet().add(e);
            addState(state);
        } else {
            state.getPropAtomSet().add(e);
        }
    }

    public void addState(final State s) {
        if (!this.contains(s)) {
            final State state = s.clone();
            final State sValid = new State(state.getName(), state.isInitial());
            for (final PropAtom e : state.getPropAtomSet()) {
                if (this.getPropAtom(e) != null) {
                    sValid.getPropAtomSet().addPropAtom(this.getPropAtom(e));
                } else {
                    addPropAtom(e);
                }
                sValid.getPropAtomSet().addPropAtom(this.getPropAtom(e));
            }
            statesSet.addState(sValid);
        }
    }

    public void addTransition(final String input, final String output) {
        State iState = this.getState(input);
        State oState = this.getState(output);
        if (iState == null) {
            iState = new State(input);
            addState(iState);
        }
        if (oState == null) {
            oState = new State(output);
            addState(oState);
        }
        final Transition t = new Transition(iState, oState);
        if (!this.contains(t)) {
            transitionFunction.addTransition(t);
        }
    }

    public void addTransition(final Transition t) {
        final Transition tc = t.clone();
        if (!this.contains(tc)) {
            if (!this.contains(tc.getInput())) {
                statesSet.addState(tc.getInput());
            } else {
                tc.setInput(this.getState(tc.getInput()));
            }

            if (!this.contains(tc.getOutput())) {
                statesSet.addState(tc.getOutput());
            } else {
                tc.setOutput(this.getState(tc.getOutput()));
            }
            transitionFunction.addTransition(tc);
        }
    }

    @Override
    public Kripke clone() {
        final Kripke clone = new Kripke(name);

        for (final PropAtom e : propAtomSet) {
            if (!clone.contains(e)) {
                clone.addPropAtom(e);
            }
        }

        for (final State s : statesSet) {
            if (!clone.contains(s)) {
                clone.addState(s);
            }
        }

        for (final Transition t : transitionFunction) {
            if (!clone.contains(t)) {
                clone.addTransition(t);
            }
        }

        return clone;
    }

    public boolean contains(final PropAtom e) {
        if (this.getPropAtom(e) != null) {
            return true;
        }
        return false;
    }

    public boolean contains(final State s) {
        if (this.getState(s) != null) {
            return true;
        }
        return false;
    }

    public boolean contains(final Transition t) {
        if (this.getTransition(t) != null) {
            return true;
        }
        return false;
    }

    public PropAtomSet getClonePropAtomSet() {
        return propAtomSet.clone();
    }

    public State getCloneState(final int state) {
        return this.getState(state).clone();
    }

    public State getCloneState(final String state) {
        return this.getState(state).clone();
    }

    public StatesSet getCloneStatesSet() {
        return statesSet.clone();
    }

    public TransitionsSet getCloneTransitionFunction() {
        return transitionFunction.clone();
    }

    public StatesSet getInitialStateSet() {
        return getStatesSet().getInitialStateSet().clone();
    }

    /**
     * @return  Returns the name.
     */
    @Override
    public String getName() {
        return name;
    }

    private PropAtom getPropAtom(final PropAtom propAtom) {
        final PropAtom s = propAtom;
        return Collections.getInstance().extract(propAtomSet, new IPredicate<PropAtom>() {

            @Override
            public boolean evaluate(final PropAtom e) {
                if (e.equals(s)) {
                    return true;
                }
                return false;
            }
        });
    }

    private PropAtom getPropAtom(final String propAtom) {
        final PropAtom s = new PropAtom(propAtom);
        return Collections.getInstance().extract(propAtomSet, new IPredicate<PropAtom>() {

            @Override
            public boolean evaluate(final PropAtom e) {
                if (e.getName().equals(s.getName())) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * @return  Returns the propAtomSet.
     */
    public PropAtomSet getPropAtomSet() {
        return propAtomSet;
    }

    public PropAtomSet getPropAtomSet(final State state) {
        return getStatesSet().getPropAtomSet(state).clone();
    }

    @Override
    public Set<IProperty> getProperty() {
        final HashSet<IProperty> set = new HashSet<IProperty>();
        for (final PropAtom e : propAtomSet) {
            set.add(e.clone());
        }
        return set;
    }

    // PARTIE PropAtomSet

    private State getState(final int state) {
        return Collections.getInstance().extract(statesSet, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                if (e.getId() == state) {
                    return true;
                }
                return false;
            }
        });
    }

    private State getState(final State state) {
        final State s = state;
        return Collections.getInstance().extract(statesSet, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                if (e.equals(s)) {
                    return true;
                }
                return false;
            }

        });
    }

    private State getState(final String state) {
        final State s = new State(state);
        return Collections.getInstance().extract(statesSet, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                if (e.getName().equals(s.getName())) {
                    return true;
                }
                return false;
            }
        });
    }

    /*
     * public void addPropAtom(String e) { PropAtom pa=new PropAtom(e);
     * if(!this.contains(pa)) this.propAtomSet.addPropAtom(pa); }
     */

    @Override
    public Set<IState> getStates() {
        final HashSet<IState> states = new HashSet<IState>();
        for (final State s : getCloneStatesSet()) {
            states.add(s);
        }
        return states;
    }

    /**
     * @return  Returns the statesSet.
     */
    public StatesSet getStatesSet() {
        return statesSet;
    }

    /*
     * public void addPropAtomTo(String s,String pa) { State
     * state=this.getState(s); PropAtom e=this.getPropAtom(pa); if(e==null) { e=
     * new PropAtom(pa); this.addPropAtom(e); } if(state==null) { state= new
     * State(s); state.getPropAtomSet().add(e); this.addState(state); } else
     * state.getPropAtomSet().add(e); }
     */

    private Transition getTransition(final String input, final String output) {
        final Transition t = new Transition(this.getState(input), this.getState(output));
        return this.getTransition(t);
    }

    private Transition getTransition(final Transition s) {
        final Transition t = s;
        return Collections.getInstance().extract(transitionFunction, new IPredicate<Transition>() {

            @Override
            public boolean evaluate(final Transition e) {
                if (e.equals(t)) {
                    return true;
                }
                return false;
            }

        });
    }

    /**
     * @return  Returns the transitionFunction.
     */
    public TransitionsSet getTransitionFunction() {
        return transitionFunction;
    }

    // PARTIE STATES

    @Override
    public Set<ITransition> getTransitions() {
        final HashSet<ITransition> transitions = new HashSet<ITransition>();
        for (final Transition t : getCloneTransitionFunction()) {
            transitions.add(t);
        }
        return transitions;
    }

    public TransitionsSet getTransitionsWhere(final State state) {
        return transitionFunction.getTransitionsWhere(state).clone();
    }

    public StatesSet getUnInitialStateSet() {
        return getStatesSet().getUnInitialStateSet().clone();
    }

    public void removePropAtom(final String name) {
        final PropAtom pa = this.getPropAtom(name);
        propAtomSet.removePropAtom(pa);
    }

    public void removePropAtomTo(final String state, final String name) {
        final PropAtom pa = this.getPropAtom(name);
        final State s = this.getState(state);
        s.getPropAtomSet().removePropAtom(pa);
    }

    public void removeState(final State state) {
        final State s = this.getState(state);
        transitionFunction.removeAll(transitionFunction.getTransitionsWhere(s));
        statesSet.removeState(s);
    }

    public void removeState(final String name) {
        final State s = this.getState(name);
        transitionFunction.removeAll(transitionFunction.getTransitionsWhere(s));
        statesSet.removeState(s);

    }

    public void removeTransition(final State input, final State output) {
        transitionFunction.removeTransition(this.getTransition(new Transition(input, output)));
    }

    // PARTIE TRANSITIONS
    public void removeTransition(final String input, final String output) {
        transitionFunction.removeTransition(this.getTransition(input, output));
    }

    public void removeTransition(final Transition t) {
        transitionFunction.removeTransition(this.getTransition(t));
    }

    /**
     * @param name  The name to set.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param propAtomSet  The propAtomSet to set.
     */
    public void setPropAtomSet(final PropAtomSet propAtomSet) {
        this.propAtomSet = propAtomSet;
    }

    /**
     * @param statesSet  The statesSet to set.
     */
    public void setStatesSet(final StatesSet statesSet) {
        this.statesSet = statesSet;
    }

    /**
     * @param transitionFunction  The transitionFunction to set.
     */
    public void setTransitionFunction(final TransitionsSet transitionFunction) {
        this.transitionFunction = transitionFunction;
    }

    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("Kripke: ");
        buff.append(propAtomSet.toString());
        buff.append("\n");
        buff.append(statesSet.toString());
        buff.append("\n");
        buff.append(transitionFunction.toString());
        return buff.toString();
    }

}
