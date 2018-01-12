package org.tc.osgi.bundle.ts.destkit.exec.exception;

/**
 * ExecInitExeception.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class ExecInitExeception extends Exception {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = 6511784493460797347L;

    /**
     * ExecInitExeception constructor.
     * @param msg String
     */
    public ExecInitExeception(final String msg) {
        super(msg);
    }

    /**
     * ExecInitExeception constructor.
     * @param msg String
     * @param _t Throwable
     */
    public ExecInitExeception(final String msg, final Throwable _t) {
        super(msg, _t);
    }

}
