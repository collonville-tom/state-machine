package org.tc.osgi.bundle.ts.destkit.utils.exception;

import org.tc.osgi.bundle.ts.utils.exception.TSFileNotFoundException;

public class FiniteStateMachineNotFoundFileException extends TSFileNotFoundException {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -4967833494293465716L;

    /**
     * FiniteStateMachineNotFoundFileException constructor.
     * @param msg String
     */
    public FiniteStateMachineNotFoundFileException(final String msg) {
        super(msg);
    }

    /**
     * FiniteStateMachineNotFoundFileException constructor.
     * @param msg String
     * @param t Throwable
     */
    public FiniteStateMachineNotFoundFileException(final String msg, final Throwable t) {
        super(msg, t);
    }
}