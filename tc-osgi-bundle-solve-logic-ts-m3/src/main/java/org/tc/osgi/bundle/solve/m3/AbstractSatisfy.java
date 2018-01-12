package org.tc.osgi.bundle.solve.m3;

import java.util.HashSet;
import java.util.Set;

import org.tc.osgi.bundle.logic.m3.IPa;
import org.tc.osgi.bundle.ts.m3.ITs;

/**
 * AbstractSatisfy.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public abstract class AbstractSatisfy {

    /**
     * Set<IPa> propertySet.
     */
    private Set<IPa> propertySet = new HashSet<IPa>();
    /**
     * ITs transitionStructure.
     */
    private ITs transitionStructure;

    /**
     * AbstractSatisfy constructor.
     * @param ts ITs
     */
    public AbstractSatisfy(final ITs ts) {
        super();
        transitionStructure = ts;
    }

    /**
     * evaluate.
     * @param form String
     * @return String
     */
    public abstract String evaluate(String form);

    /**
     * getPropertySet.
     * @return Set<IPa>
     */
    public Set<IPa> getPropertySet() {
        return propertySet;
    }

    /**
     * getTransitionStructure.
     * @return ITs
     */
    public ITs getTransitionStructure() {
        return transitionStructure;
    }

    /**
     * setPropertySet.
     * @param propertySet Set<IPa>
     */
    public void setPropertySet(final Set<IPa> propertySet) {
        this.propertySet = propertySet;
    }

    /**
     * setTransitionStructure.
     * @param transitionStructure ITs
     */
    public void setTransitionStructure(final ITs transitionStructure) {
        this.transitionStructure = transitionStructure;
    }

    /**
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("Satisfy(").append(transitionStructure.getName()).append(" for {");
        for (final IPa pa : propertySet) {
            buff.append(pa.getName()).append(", ");
        }
        buff.append("})");
        return buff.toString();
    }

}
