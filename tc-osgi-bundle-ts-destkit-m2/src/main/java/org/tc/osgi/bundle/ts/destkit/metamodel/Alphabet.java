package org.tc.osgi.bundle.ts.destkit.metamodel;

import java.util.Collection;
import java.util.HashSet;

import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;
import org.tc.osgi.bundle.utils.interf.collection.ITransformer;


/**
 * Alphabet.java.
 * @author Collonville Thomas
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class Alphabet extends HashSet<Event> implements Cloneable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = 4844674289821693766L;

    /**
     * Alphabet constructor.
     */
    public Alphabet() {
    }

    /**
     * addEvent.
     * @param e Event
     * @return boolean
     */
    public boolean addEvent(final Event e) {
        if (!containsEvent(e)) {
            return super.add(e);
        }
        return false;

    }

    /**
     * @return Alphabet
     * @see java.util.HashSet#clone()
     */
    @Override
    public Alphabet clone() {
        return (Alphabet) CollectionUtilsServiceProxy.getInstance().collect(this, new ITransformer<Event>() {

            @Override
            public void evaluate(final Collection<Event> c, final Event e) {
                ((Alphabet) c).addEvent(e.clone());
            }
        });
    }

    /**
     * containsEvent.
     * @param e Event
     * @return boolean
     */
    public boolean containsEvent(final Event e) {
        final Event event = e;
        if (CollectionUtilsServiceProxy.getInstance().extract(this, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e1) {
                if (e1.equals(event)) {
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
     * getControllableAlphabet.
     * @return Alphabet
     */
    public Alphabet getControllableAlphabet() {
        return (Alphabet) CollectionUtilsServiceProxy.getInstance().select(this, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e) {
                return e.isControllable();
            }

        });
    }

    /**
     * getObservableAlphabet.
     * @return Alphabet
     */
    public Alphabet getObservableAlphabet() {
        return (Alphabet) CollectionUtilsServiceProxy.getInstance().select(this, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e) {
                return e.isObservable();
            }

        });
    }

    /**
     * getUncontrollableAlphabet.
     * @return Alphabet
     */
    public Alphabet getUncontrollableAlphabet() {
        return (Alphabet) CollectionUtilsServiceProxy.getInstance().reject(this, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e) {
                return e.isControllable();
            }

        });
    }

    /**
     * getUnobservableAlphabet.
     * @return Alphabet
     */
    public Alphabet getUnobservableAlphabet() {
        return (Alphabet) CollectionUtilsServiceProxy.getInstance().reject(this, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e) {
                return e.isObservable();
            }

        });
    }

    /**
     * removeEvent.
     * @param e Event
     * @return boolean
     */
    public boolean removeEvent(final Event e) {
        final Event s = e;
        final Event event = CollectionUtilsServiceProxy.getInstance().extract(this, new IPredicate<Event>() {

            @Override
            public boolean evaluate(final Event e1) {
                if (e1.equals(s)) {
                    return true;
                }
                return false;
            }
        });
        if (event != null) {
            return super.remove(event);
        }
        return false;

    }

    /**
     * @return String
     * @see java.util.AbstractCollection#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("\nAlphabet:");
        for (final Event e : this) {
            buff.append("\n");
            buff.append(e.toString());
        }
        return buff.toString();
    }
}
