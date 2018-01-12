package org.tc.osgi.bundle.logic.ltl.metamodel;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;

/**
 * Ltl.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public abstract class AbstractLtl extends AbstractFormule {

    /**
     * @return AbstractLtl
     * @see org.tc.osgi.bundle.logic.m3.AbstractFormule#evaluate()
     */
    @Override
    public abstract AbstractLtl evaluate();

}
