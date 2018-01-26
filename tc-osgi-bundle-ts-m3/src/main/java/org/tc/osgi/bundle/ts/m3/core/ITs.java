package org.tc.osgi.bundle.ts.m3.core;

import java.io.Serializable;
import java.util.Set;

import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitable;



/**
 * ITs.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public interface ITs extends IVisitable, Serializable {

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

    /**
     * getStates.
     * @return Set<IState>
     */
    public abstract Set<IState> getStates();

    /**
     * getTransitions.
     * @return Set<ITransition>
     */
    public abstract Set<ITransition> getTransitions();
}
