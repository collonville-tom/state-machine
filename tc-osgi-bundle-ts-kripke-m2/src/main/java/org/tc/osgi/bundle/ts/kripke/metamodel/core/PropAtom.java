package org.tc.osgi.bundle.ts.kripke.metamodel.core;

import org.tc.osgi.bundle.ts.m3.IProperty;

/**
 * PropAtom.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class PropAtom implements Cloneable, IProperty {

    /**
     * String name.
     */
    private String name;

    /**
     * PropAtom constructor.
     */
    public PropAtom() {
    }

    /**
     * PropAtom constructor.
     * @param name String
     */
    public PropAtom(final String name) {
        this.name = name;

    }

    /**
     * @return PropAtom
     * @see java.lang.Object#clone()
     */
    @Override
    public PropAtom clone() {
        final PropAtom clone = new PropAtom(name);
        return clone;
    }

    /**
     * @param propAtom Object
     * @return boolean
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object propAtom) {
        if (propAtom == this) {
            return true;
        }
        if (propAtom instanceof PropAtom) {
            final PropAtom e = (PropAtom) propAtom;
            if (e.name.equals(name)) {
                return true;
            }
            return false;
        }
        return false;

    }

    /**
     * @return String
     * @see org.tc.osgi.bundle.ts.m3.IProperty#getName()
     */
    @Override
    public String getName() {
        return name;
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("PropAtom: ");
        buff.append(name);
        return buff.toString();
    }
}