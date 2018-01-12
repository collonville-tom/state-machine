package org.tc.osgi.bundle.ts.destkit.metamodel.core;

import org.tc.osgi.bundle.ts.m3.IProperty;

/**
 * Event.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class Event implements Cloneable, IProperty {

    /**
     * boolean CONTROLLABLE.
     */
    public static final boolean CONTROLLABLE = true;
    /**
     * boolean OBSERVABLE.
     */
    public static final boolean OBSERVABLE = true;
    /**
     * boolean UNCONTROLLABLE.
     */
    public static final boolean UNCONTROLLABLE = false;
    /**
     * boolean UNOBSERVABLE.
     */
    public static final boolean UNOBSERVABLE = false;

    /**
     */
    private boolean controllable;

    /**
     */
    private String name;
    /**
     */
    private boolean observable;

    /**
     * Event constructor.
     */
    public Event() {
    }

    /**
     * Event constructor.
     * @param name String
     */
    public Event(final String name) {
        this(name, true, true);
    }

    /**
     * Event constructor.
     * @param name String
     * @param controllable boolean
     * @param observable boolean
     */
    public Event(final String name, final boolean controllable, final boolean observable) {
        this.name = name;
        this.controllable = controllable;
        this.observable = observable;
    }

    /**
     * @return Event
     * @see java.lang.Object#clone()
     */
    @Override
    public Event clone() {
        final Event clone = new Event(name, controllable, observable);
        return clone;
    }

    /**
     * @param event Object
     * @return boolean
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object event) {
        if (event == this) {
            return true;
        }
        if (event instanceof Event) {
            final Event e = (Event) event;
            if (e.name.equals(name) && (e.controllable == controllable) && (e.observable == observable)) {
                return true;
            }
            return false;
        }
        return false;

    }

    /**
     * @return String
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return boolean
     */
    public boolean isControllable() {
        return controllable;
    }

    /**
     * @return boolean
     */
    public boolean isObservable() {
        return observable;
    }

    /**
     * @param controllable boolean
     */
    public void setControllable(final boolean controllable) {
        this.controllable = controllable;
    }

    /**
     * @param name String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param observable boolean
     */
    public void setObservable(final boolean observable) {
        this.observable = observable;
    }

    /**
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("Event: ");
        buff.append(name);
        buff.append(", controllable: ");
        buff.append(controllable);
        buff.append(", observable: ");
        buff.append(observable);
        return buff.toString();
    }
}
