package org.tc.osgi.bundle.ts.m3;

import java.util.Set;

/**
 * ITransition.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public interface ITransition {

    /**
     * getProperty.
     * @return Set<IProperty>
     */
    public abstract Set<IProperty> getProperty();

    /**
     * getSourceState.
     * @return IState
     */
    public abstract IState getSourceState();

    /**
     * getTargetState.
     * @return IState
     */
    public abstract IState getTargetState();

}
