package org.tc.osgi.bundle.logic.m3;

/**
 * AbstractFormule.java.
 * @author thomas collonville
 * @version 0.0.1
 */
public abstract class AbstractFormule {

    /**
     * evaluate.
     * @return AbstractFormule
     */
    public abstract AbstractFormule evaluate();

    /**
     * result.
     * @return String
     */
    public String result() {
        return this.getClass().getSimpleName();
    }

    /**
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public abstract String toString();
}
