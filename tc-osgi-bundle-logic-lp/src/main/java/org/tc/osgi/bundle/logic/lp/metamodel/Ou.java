package org.tc.osgi.bundle.logic.lp.metamodel;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;

/**
 * Ou.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class Ou extends Lp {

    /**
     * AbstractFormule first.
     */
    private AbstractFormule first, second;

    /**
     * Ou constructor.
     * @param first AbstractFormule
     * @param second AbstractFormule
     */
    public Ou(final AbstractFormule first, final AbstractFormule second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return AbstractFormule
     * @see org.tc.osgi.bundle.logic.lp.metamodel.Lp#evaluate()
     */
    @Override
    public AbstractFormule evaluate() {
        if ((first.evaluate().getClass() == T.class) || (second.evaluate().getClass() == T.class)) {
            return new T();
        } else {
            return new F();
        }
    }

    /**
     * getFirst.
     * @return AbstractFormule
     */
    public AbstractFormule getFirst() {
        return first;
    }

    /**
     * getSecond.
     * @return AbstractFormule
     */
    public AbstractFormule getSecond() {
        return second;
    }

    /**
     * setFirst.
     * @param first AbstractFormule
     */
    public void setFirst(final AbstractFormule first) {
        this.first = first;
    }

    /**
     * setSecond.
     * @param second AbstractFormule
     */
    public void setSecond(final AbstractFormule second) {
        this.second = second;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.logic.m3.AbstractFormule#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("(");
        buff.append(first.toString());
        buff.append(" ou ");
        buff.append(second.toString());
        buff.append(")");
        return buff.toString();
    }

}