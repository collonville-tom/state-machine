package org.tc.osgi.bundle.logic.lp.metamodel;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;
import org.tc.osgi.bundle.logic.m3.IPa;

/**
 * Pa.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class Pa extends Lp implements IPa {

    /**
     * boolean is.
     */
    private boolean is = false;
    /**
     * String name.
     */
    private String name;

    /**
     * Pa constructor.
     * @param name String
     */
    public Pa(final String name) {
        this.name = name;
    }

    /**
     * @return AbstractFormule
     * @see org.tc.osgi.bundle.logic.lp.metamodel.Lp#evaluate()
     */
    @Override
    public AbstractFormule evaluate() {
        if (is) {
            return new T();
        } else {
            return new F();
        }
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.logic.m3.IPa#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * isIs.
     * @return boolean
     */
    public boolean isIs() {
        return is;
    }

    /**
     * setIs.
     * @param is boolean
     */
    public void setIs(final boolean is) {
        this.is = is;
    }

    /**
     * setName.
     * @param name String
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.logic.m3.AbstractFormule#toString()
     */
    @Override
    public String toString() {
        return name;
    }

}
