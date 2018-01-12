package org.tc.osgi.bundle.logic.lp.metamodel;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;

/**
 * Lp.java.
 * @author thomas collonville
 * @version 0.0.1
 */
public abstract class Lp extends AbstractFormule {

    /**
     * @return AbstractFormule
     * @see org.tc.osgi.bundle.logic.m3.AbstractFormule#evaluate()
     */
    @Override
    public abstract AbstractFormule evaluate();

}
