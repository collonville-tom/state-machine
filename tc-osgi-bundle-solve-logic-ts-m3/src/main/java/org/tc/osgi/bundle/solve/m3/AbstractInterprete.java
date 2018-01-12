package org.tc.osgi.bundle.solve.m3;

import org.tc.osgi.bundle.logic.m3.AbstractFormule;

/**
 * Interprete.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public abstract class AbstractInterprete {

    /**
     * String stringformule.
     */
    private String formule;

    /**
     * AbstractInterprete constructor.
     * @param _formule String
     */
    public AbstractInterprete(final String _formule) {
        super();
        formule = _formule;
    }

    /**
     * getStringformule.
     * @return String
     */
    public String getStringformule() {
        return formule;
    }

    /**
     * setStringformule.
     * @param stringformule String
     */
    public void setStringformule(final String stringformule) {
        formule = stringformule;
    }

    /**
     * text2Formule.
     * @param satisfyFonction Satisfy
     * @return AbstractFormule
     */
    public abstract AbstractFormule text2Formule(AbstractSatisfy satisfyFonction);
}
