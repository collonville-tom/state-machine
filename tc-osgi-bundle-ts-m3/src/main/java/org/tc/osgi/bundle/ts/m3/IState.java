package org.tc.osgi.bundle.ts.m3;

import java.util.Set;

/**
 * IState.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public interface IState {

    /**
     * getName.
     * @return String
     */
    public abstract String getName();

    /**
     * getProperty.
     * @return Set<IProperty>
     */
    public abstract Set<IProperty> getProperty();
}
