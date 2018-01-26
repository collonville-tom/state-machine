package org.tc.osgi.bundle.ts.destkit.metamodel.core;

import java.util.HashSet;
import java.util.Set;

import org.tc.osgi.bundle.ts.destkit.metamodel.Alphabet;
import org.tc.osgi.bundle.ts.m3.core.IProperty;
import org.tc.osgi.bundle.ts.m3.core.IState;

/**
 * State.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class State implements Cloneable, IState {

    /**
     * boolean INITIAL.
     */
    public static final boolean INITIAL = true;
    /**
     * boolean MARKED.
     */
    public static final boolean MARKED = true;
    /**
     * boolean NOTINITIAL.
     */
    public static final boolean NOTINITIAL = false;
    /**
     * boolean NOTMARKED.
     */
    public static final boolean NOTMARKED = false;
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
    /**
     * Alphabet possibleEvents.
     */
    private Alphabet possibleEvents = new Alphabet();

    /**
     * State constructor.
     */
    public State() {
    }

    /**
     * State constructor.
     * @param name String
     */
    public State(final String name) {
        this(name, false);
    }

    /**
     * State constructor.
     * @param name String
     * @param marked boolean
     */
    public State(final String name, final boolean marked) {
        this(name, false, marked);
    }

    /**
     * State constructor.
     * @param name String
     * @param initial boolean
     * @param marked boolean
     */
    public State(final String name, final boolean initial, final boolean marked) {
        this.name = name;
        this.initial = initial;
        this.marked = marked;
    }

    /**
     * @return State
     * @see java.lang.Object#clone()
     */
    @Override
    public State clone() {
        final State clone = new State(name, initial, marked);
        for (final Event e : possibleEvents) {
            clone.getPossibleEvents().addEvent(e.clone());
        }
        return clone;
    }

    /**
     * @param s Object
     * @return boolean
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object s) {
        if (s == this) {
            return true;
        }
        if (s instanceof State) {
            final State state = (State) s;
            if (state.getName().equals(name) && (state.isInitial() == initial) && (state.isMarked() == marked)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * @return  int
     */
    public int getId() {
        return id;
    }

    /**
     * @return  String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return  Alphabet
     */
    public Alphabet getPossibleEvents() {
        return possibleEvents;
    }

    /**
     * @return Set<IProperty>
     * @see org.tc.osgi.bundle.ts.m3.core.IState#getProperty()
     */
    @Override
    public Set<IProperty> getProperty() {
        final HashSet<IProperty> set = new HashSet<IProperty>();
        for (final Event e : possibleEvents) {
            set.add(e.clone());
        }
        return set;
    }

    /**
     * @return  boolean
     */
    public boolean isInitial() {
        return initial;
    }

    /**
     * @return  boolean
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * @param id  int
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * @param initial  boolean
     */
    public void setInitial(final boolean initial) {
        this.initial = initial;
    }

    /**
     * @param marked  boolean
     */
    public void setMarked(final boolean marked) {
        this.marked = marked;
    }

    /**
     * @param name  String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param possibleEvents  Alphabet
     */
    public void setPossibleEvents(final Alphabet possibleEvents) {
        this.possibleEvents = possibleEvents;
    }

    /**
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("ID:");
        buff.append(id);
        buff.append(", State: ");
        buff.append(name);
        buff.append(", initial: ");
        buff.append(initial);
        buff.append(", marked: ");
        buff.append(marked);
        return buff.toString();
    }

}
