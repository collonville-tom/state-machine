package org.tc.osgi.bundle.ts.kripke.utils.exception;

import org.tc.osgi.bundle.ts.m3.utils.exception.TSFileNotFoundException;

/**
 * KripkeNotFoundFileException.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class KripkeNotFoundFileException extends TSFileNotFoundException {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -8600035427675815303L;

    /**
     * KripkeNotFoundFileException constructor.
     * @param msg String
     */
    public KripkeNotFoundFileException(final String msg) {
        super(msg);
    }

    /**
     * KripkeNotFoundFileException constructor.
     * @param msg String
     * @param t Throwable
     */
    public KripkeNotFoundFileException(final String msg, final Throwable t) {
        super(msg, t);
    }

}
