package org.tc.osgi.bundle.logic.ltl.metamodel;

/**
 * Next.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class Next extends AbstractLtl {

    /**
     * AbstractLtl formule.
     */
    private final AbstractLtl formule;

    /**
     * Next constructor.
     * @param formule AbstractLtl
     */
    public Next(final AbstractLtl formule) {
        this.formule = formule;
    }

    /**
     * @return AbstractLtl
     * @see org.tc.osgi.bundle.logic.ltl.metamodel.AbstractLtl#evaluate()
     */
    @Override
    public AbstractLtl evaluate() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.logic.m3.AbstractFormule#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

}
