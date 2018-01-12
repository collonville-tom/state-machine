package org.tc.osgi.bundle.logic.lp.metamodel;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;

/**
 * F.java.
 * Represente False
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class F extends Lp {

    /**
     * @return AbstractFormule
     * @see org.tc.osgi.bundle.logic.lp.metamodel.Lp#evaluate()
     */
    @Override
    public AbstractFormule evaluate() {
        return this;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.logic.m3.AbstractFormule#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("False");
        return buff.toString();
    }

}
