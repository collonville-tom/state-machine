package org.tc.osgi.bundle.ts.destkit.metamodel;

import java.util.HashSet;
import java.util.Set;

import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Transition;
import org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.ts.m3.core.IProperty;
import org.tc.osgi.bundle.ts.m3.core.IState;
import org.tc.osgi.bundle.ts.m3.core.ITransition;
import org.tc.osgi.bundle.ts.m3.core.ITs;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitor;


/**
 * FiniteStateMachine.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class FiniteStateMachine implements Cloneable, ITs {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -3652189270903133152L;
    /**
     * Alphabet alphabet.
     */
    private Alphabet alphabet = new Alphabet();
    /**
     * String name.
     */
    private String name;
    /**
     * StatesSet statesSet.
     */
    private StatesSet statesSet = new StatesSet();
    /**
     * TransitionsSet transitionFunction.
     */
    private TransitionsSet transitionFunction = new TransitionsSet();

    /**
     * FiniteStateMachine constructor.
     */
    public FiniteStateMachine() {
    }

    /**
     * FiniteStateMachine constructor.
     * @param name String
     */
    public FiniteStateMachine(final String name) {
        this.name = name;
    }

    /**
     * @param visitor AbstractVisitor
     * @see org.tc.osgi.bundle.utils.pattern.visitor.IVisitable#accept(org.tc.osgi.bundle.utils.pattern.visitor.AbstractVisitor)
     */
    @Override
    public void accept(final IVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * addEvent.
     * @param e Event
     */
    public void addEvent(final Event e) {
        if (!this.contains(e)) {
            alphabet.addEvent(e.clone());
        }
    }

    /**
     * addState.
     * @param s State
     */
    public void addState(final State s) {
        if (!this.contains(s)) {
            final State state = s.clone();
            final State sValid = new State(state.getName(), state.isInitial(), state.isMarked());
            for (final Event e : state.getPossibleEvents()) {
                if (this.getEvent(e) != null) {
                    sValid.getPossibleEvents().addEvent(this.getEvent(e));
                } else {
                    addEvent(e);
                }
                sValid.getPossibleEvents().addEvent(this.getEvent(e));
            }
            statesSet.addState(sValid);
        }
    }

    /**
     * addTransition.
     * @param input String
     * @param event String
     * @param output String
     */
    public void addTransition(final String input, final String event, final String output) {
        Event e = this.getEvent(event);
        State iState = this.getState(input);
        State oState = this.getState(output);
        if (e == null) {
            e = new Event(event);
            addEvent(e);
        }
        if (iState == null) {
            iState = new State(input);
            addState(iState);
        }
        if (oState == null) {
            oState = new State(output);
            addState(oState);
        }
        final Transition t = new Transition(iState, e, oState);
        if (!this.contains(t)) {
            transitionFunction.addTransition(t);
            iState.getPossibleEvents().addEvent(e);
        }
    }

    /**
     * addTransition.
     * @param t Transition
     */
    public void addTransition(final Transition t) {
        final Transition tc = t.clone();
        if (!this.contains(tc)) {
            if (!this.contains(tc.getEvent())) {
                alphabet.addEvent(tc.getEvent());
            } else {
                tc.setEvent(this.getEvent(tc.getEvent()));
            }

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
            tc.getInput().getPossibleEvents().addEvent(tc.getEvent());
        }
    }

    /**
     * @return FiniteStateMachine
     * @see java.lang.Object#clone()
     */
    @Override
    public FiniteStateMachine clone() {
        final FiniteStateMachine clone = new FiniteStateMachine(name);

        for (final Event e : alphabet) {
            if (!clone.contains(e)) {
                clone.addEvent(e);
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

    /**
     * contains.
     * @param e Event
     * @return boolean
     */
    public boolean contains(final Event e) {
        if (this.getEvent(e) != null) {
            return true;
        }
        return false;
    }

    /**
     * contains.
     * @param s State
     * @return boolean
     */
    public boolean contains(final State s) {
        if (this.getState(s) != null) {
            return true;
        }
        return false;
    }

    /**
     * contains.
     * @param t Transition
     * @return boolean
     */
    public boolean contains(final Transition t) {
        if (this.getTransition(t) != null) {
            return true;
        }
        return false;
    }

    /**
     * @return Alphabet
     */
    public Alphabet getAlphabet() {
        return alphabet;
    }

    /**
     * getCloneAlphabet.
     * @return Alphabet
     */
    public Alphabet getCloneAlphabet() {
        return alphabet.clone();
    }

    /**
     * getCloneState.
     * @param state int
     * @return State
     */
    public State getCloneState(final int state) {
        return this.getState(state).clone();
    }

    /**
     * getCloneState.
     * @param state
     * @return State
     */
    public State getCloneState(final String state) {
        return this.getState(state).clone();
    }

    /**
     * getCloneStatesSet.
     * @return StatesSet
     */
    public StatesSet getCloneStatesSet() {
        return statesSet.clone();
    }

    /**
     * getCloneTransitionFunction.
     * @return TransitionsSet
     */
    public TransitionsSet getCloneTransitionFunction() {
        return transitionFunction.clone();
    }

    /**
     * getControllableAlphabet.
     * @return Alphabet
     */
    public Alphabet getControllableAlphabet() {
        return getAlphabet().getControllableAlphabet().clone();
    }

    /**
     * getEvent.
     * @param event Event
     * @return Event
     */
    private Event getEvent(final Event event) {
        final Event s = event;
        return CollectionUtilsServiceProxy.getInstance().extract(alphabet, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e) {
                if (e.equals(s)) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * getEvent.
     * @param event String
     * @return Event
     */
    private Event getEvent(final String event) {
        final Event s = new Event(event);
        return CollectionUtilsServiceProxy.getInstance().extract(alphabet, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e) {
                if (e.getName().equals(s.getName())) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * getInitialStateSet.
     * @return StatesSet
     */
    public StatesSet getInitialStateSet() {
        return getStatesSet().getInitialStateSet().clone();
    }

    /**
     * getMarkedStateSet.
     * @return StatesSet
     */
    public StatesSet getMarkedStateSet() {
        return getStatesSet().getMarkedStateSet().clone();

    }

    /**
     * @return  String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * getObservableAlphabet.
     * @return Alphabet
     */
    public Alphabet getObservableAlphabet() {
        return getAlphabet().getObservableAlphabet().clone();
    }

    /**
     * getPossibleEvents.
     * @param state State
     * @return Alphabet
     */
    public Alphabet getPossibleEvents(final State state) {
        return getStatesSet().getPossibleEvents(state).clone();
    }

    // PARTIE ALPHABET

    /**
     * @return Set<IProperty>
     * @see org.tc.osgi.bundle.ts.m3.core.ITs#getProperty()
     */
    @Override
    public Set<IProperty> getProperty() {
        final HashSet<IProperty> set = new HashSet<IProperty>();
        for (final Event e : alphabet) {
            set.add(e.clone());
        }
        return set;
    }

    /**
     * getState.
     * @param state state
     * @return State
     */
    private State getState(final int state) {
        return CollectionUtilsServiceProxy.getInstance().extract(statesSet, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                if (e.getId() == state) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * getState.
     * @param state State
     * @return State
     */
    private State getState(final State state) {
        final State s = state;
        return CollectionUtilsServiceProxy.getInstance().extract(statesSet, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                if (e.equals(s)) {
                    return true;
                }
                return false;
            }

        });
    }

    /**
     * getState.
     * @param state State
     * @return State
     */
    private State getState(final String state) {
        final State s = new State(state);
        return CollectionUtilsServiceProxy.getInstance().extract(statesSet, new IPredicate<State>() {

            @Override
            public boolean evaluate(final State e) {
                if (e.getName().equals(s.getName())) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * @return Set<IState>
     * @see org.tc.osgi.bundle.ts.m3.core.ITs#getStates()
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
     * @return  StatesSet
     */
    public StatesSet getStatesSet() {
        return statesSet;
    }

    /**
     * getTransition.
     * @param input String
     * @param event String
     * @param output String
     * @return Transition
     */
    private Transition getTransition(final String input, final String event, final String output) {
        final Transition t = new Transition(this.getState(input), this.getEvent(event), this.getState(output));
        return this.getTransition(t);
    }

    /**
     * getTransition.
     * @param s Transition
     * @return Transition
     */
    private Transition getTransition(final Transition s) {
        final Transition t = s;
        return CollectionUtilsServiceProxy.getInstance().extract(transitionFunction, new IPredicate<Transition>() {

            @Override
            public boolean evaluate(final Transition e) {
                if (e.equals(t)) {
                    return true;
                }
                return false;
            }

        });
    }

    // PARTIE STATES

    /**
     * @return  TransitionsSet
     */
    public TransitionsSet getTransitionFunction() {
        return transitionFunction;
    }

    /**
     * @return Set<ITransition>
     * @see org.tc.osgi.bundle.ts.m3.core.ITs#getTransitions()
     */
    @Override
    public Set<ITransition> getTransitions() {
        final HashSet<ITransition> transitions = new HashSet<ITransition>();
        for (final Transition t : getCloneTransitionFunction()) {
            transitions.add(t);
        }
        return transitions;
    }

    /**
     * getTransitionsWhere.
     * @param state State
     * @return TransitionsSet
     */
    public TransitionsSet getTransitionsWhere(final State state) {
        return transitionFunction.getTransitionsWhere(state).clone();
    }

    /**
     * getUncontrollableAlphabet.
     * @return Alphabet
     */
    public Alphabet getUncontrollableAlphabet() {
        return getAlphabet().getUncontrollableAlphabet().clone();
    }

    /**
     * getUnInitialStateSet.
     * @return StatesSet
     */
    public StatesSet getUnInitialStateSet() {
        return getStatesSet().getUnInitialStateSet().clone();
    }

    /**
     * getUnMarkedStateSet.
     * @return StatesSet
     */
    public StatesSet getUnMarkedStateSet() {
        return getStatesSet().getUnMarkedStateSet().clone();

    }

    /**
     * getUnobservableAlphabet.
     * @return Alphabet
     */
    public Alphabet getUnobservableAlphabet() {
        return getAlphabet().getUnobservableAlphabet().clone();
    }

    /**
     * removeState.
     * @param state State
     */
    public void removeState(final State state) {
        final State s = this.getState(state);
        transitionFunction.removeAll(transitionFunction.getTransitionsWhere(s));
        statesSet.removeState(s);
    }

    /**
     * removeState.
     * @param name String
     */
    public void removeState(final String name) {
        final State s = this.getState(name);
        transitionFunction.removeAll(transitionFunction.getTransitionsWhere(s));
        statesSet.removeState(s);

    }

    /**
     * removeTransition.
     * @param input State
     * @param event Event
     * @param output State
     */
    public void removeTransition(final State input, final Event event, final State output) {
        transitionFunction.removeTransition(this.getTransition(new Transition(input, event, output)));
    }

    // PARTIE TRANSITIONS
    /**
     * removeTransition.
     * @param input String
     * @param event String
     * @param output String
     */
    public void removeTransition(final String input, final String event, final String output) {
        transitionFunction.removeTransition(this.getTransition(input, event, output));
    }

    /**
     * removeTransition.
     * @param t Transition
     */
    public void removeTransition(final Transition t) {
        transitionFunction.removeTransition(this.getTransition(t));
    }

    /**
     * @param alphabet  Alphabet
     */
    public void setAlphabet(final Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * @param name  String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param statesSet  StatesSet
     */
    public void setStatesSet(final StatesSet statesSet) {
        this.statesSet = statesSet;
    }

    /**
     * @param TransitionsSet
     */
    public void setTransitionFunction(final TransitionsSet transitionFunction) {
        this.transitionFunction = transitionFunction;
    }

    /**
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("FSM: ");
        buff.append(alphabet.toString());
        buff.append("\n");
        buff.append(statesSet.toString());
        buff.append("\n");
        buff.append(transitionFunction.toString());
        return buff.toString();
    }
}
