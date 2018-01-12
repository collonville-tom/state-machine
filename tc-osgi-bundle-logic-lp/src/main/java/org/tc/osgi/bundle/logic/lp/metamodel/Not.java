package org.tc.osgi.bundle.logic.lp.metamodel;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;

/**
 * Not.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class Not extends Lp {

    /**
     * AbstractFormule lp.
     */
    private AbstractFormule lp;

    /**
     * Not constructor.
     * @param lp AbstractFormule
     */
    public Not(final AbstractFormule lp) {
        this.lp = lp;
    }

    /**
     * @return AbstractFormule
     * @see org.tc.osgi.bundle.logic.lp.metamodel.Lp#evaluate()
     */
    @Override
    public AbstractFormule evaluate() {
        if (lp.evaluate().getClass() == T.class) {
            return new F();
        } else {
            return new T();
        }
    }

    /**
     * getFormule.
     * @return AbstractFormule
     */
    public AbstractFormule getFormule() {
        return lp;
    }

    /**
     * setFormule.
     * @param lp AbstractFormule
     */
    public void setFormule(final AbstractFormule lp) {
        this.lp = lp;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.logic.m3.AbstractFormule#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("Not(");
        buff.append(lp.toString());
        buff.append(")");
        return buff.toString();
    }

}