package org.tc.osgi.bundle.logic.lp.metamodel;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;

/**
 * Et.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class Et extends Lp {

    /**
     * AbstractFormule first.
     */
    private AbstractFormule first, second;

    /**
     * Et constructor.
     * @param first AbstractFormule
     * @param second AbstractFormule
     */
    public Et(final AbstractFormule first, final AbstractFormule second) {
        this.first = first;
        this.second = second;
    }

    /**
     * @return AbstractFormule
     * @see org.tc.osgi.bundle.logic.lp.metamodel.Lp#evaluate()
     */
    @Override
    public AbstractFormule evaluate() {
        if ((first.evaluate().getClass() == T.class) && (second.evaluate().getClass() == T.class)) {
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
     * @param first Lp
     */
    public void setFirst(final Lp first) {
        this.first = first;
    }

    /**
     * setSecond.
     * @param second Lp
     */
    public void setSecond(final Lp second) {
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
        buff.append(" et ");
        buff.append(second.toString());
        buff.append(")");
        return buff.toString();
    }

}