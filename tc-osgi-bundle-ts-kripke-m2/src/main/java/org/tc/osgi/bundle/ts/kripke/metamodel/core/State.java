package org.tc.osgi.bundle.ts.kripke.metamodel.core;

import java.util.HashSet;
import java.util.Set;

import org.tc.osgi.bundle.ts.kripke.metamodel.PropAtomSet;
import org.tc.osgi.bundle.ts.m3.IProperty;
import org.tc.osgi.bundle.ts.m3.IState;

/**
 * State.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class State implements Cloneable, IState {

    /**
     * boolean INITIAL.
     */
    public static final boolean INITIAL = true;
    /**
     * boolean NOTINITIAL.
     */
    public static final boolean NOTINITIAL = false;

    /**
     * int id.
     */
    private int id = 0;
    /**
     * boolean initial.
     */
    private boolean initial;
    /**
     * String name.
     */
    private String name;
    /**
     * PropAtomSet propAtomSet.
     */
    private PropAtomSet propAtomSet = new PropAtomSet();

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
     * @param initial boolean
     */
    public State(final String name, final boolean initial) {
        this.name = name;
        this.initial = initial;
    }

    /**
     * @return State
     * @see java.lang.Object#clone()
     */
    @Override
    public State clone() {
        final State clone = new State(name, initial);
        for (final PropAtom e : propAtomSet) {
            if (e != null) {
                clone.getPropAtomSet().addPropAtom(e.clone());
            }
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
            if (state.getName().equals(name) && (state.isInitial() == initial)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * getId.
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.ts.m3.IState#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * getPropAtomSet.
     * @return PropAtomSet
     */
    public PropAtomSet getPropAtomSet() {
        return propAtomSet;
    }

    /**
     * @return HashSet<IProperty>
     * @see org.tc.osgi.bundle.ts.m3.IState#getProperty()
     */
    @Override
    public Set<IProperty> getProperty() {
        final HashSet<IProperty> set = new HashSet<IProperty>();
        for (final PropAtom e : getPropAtomSet()) {
            set.add(e.clone());
        }
        return set;
    }

    /**
     * isInitial.
     * @return boolean
     */
    public boolean isInitial() {
        return initial;
    }

    /**
     * setId.
     * @param id int
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * setInitial.
     * @param initial boolean
     */
    public void setInitial(final boolean initial) {
        this.initial = initial;
    }

    /**
     * setName.
     * @param name String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * setPropAtomSet.
     * @param propAtomSet PropAtomSet
     */
    public void setPropAtomSet(final PropAtomSet propAtomSet) {
        this.propAtomSet = propAtomSet;
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
        return buff.toString();
    }

}
