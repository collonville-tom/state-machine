package org.tc.osgi.bundle.ts.m3.utils.exception;

/**
 * TSNotFoundFileException.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class TSFileNotFoundException extends Exception {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = 8029448135152541739L;

    /**
     * TSNotFoundFileException constructor.
     * @param name
     */
    public TSFileNotFoundException(final String msg) {
        super(msg);
    }

    /**
     * TSNotFoundFileException constructor.
     * @param name
     * @param t
     */
    public TSFileNotFoundException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
