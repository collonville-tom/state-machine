package org.tc.osgi.bundle.ts.destkit.exec;

import org.tc.osgi.bundle.ts.destkit.exec.exception.ExecInitExeception;
import org.tc.osgi.bundle.ts.destkit.metamodel.Alphabet;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Transition;
import org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.interf.collection.IPredicate;


/**
 * FsmOpSem.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class FsmOpSem {

    /**
     * State currentState.
     */
    private State currentState;
    /**
     * FiniteStateMachine fsm.
     */
    private final FiniteStateMachine fsm;
    /**
     * Event receiveEvent.
     */
    private Event receiveEvent;
    /**
     * Transition transitionAccepted.
     */
    private Transition transitionAccepted;

    /**
     * FsmOpSem constructor.
     * @param fsm FiniteStateMachine
     * @throws ExecInitExeception
     */
    public FsmOpSem(final FiniteStateMachine fsm) throws ExecInitExeception {
        this.fsm = fsm;
        if (fsm.getInitialStateSet().size() != 1) {
            throw new ExecInitExeception("Plusieurs etats initiaux trouvés");
        }
        currentState = (State) fsm.getInitialStateSet().toArray()[0];
        LoggerServiceProxy.getInstance().getLogger(FsmOpSem.class).debug("Current State: " + currentState.getName());
    }

    /**
     * hasNext.
     * @return boolean
     */
    public boolean hasNext() {
        if (fsm.getPossibleEvents(currentState).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * next.
     * @return State
     */
    public State next() {
        currentState = transitionAccepted.getOutput();
        LoggerServiceProxy.getInstance().getLogger(FsmOpSem.class).debug("Current State: " + currentState.getName());
        return currentState;
    }

    /**
     * possibleEventInCurrentState.
     * @return Alphabet
     */
    public Alphabet possibleEventInCurrentState() {
        return currentState.getPossibleEvents();
    }

    /**
     * update.
     * @param receiveEvent Event
     * @return boolean
     */
    public boolean update(final Event receiveEvent) {

        if (fsm.getPossibleEvents(currentState).containsEvent(receiveEvent)) {
            this.receiveEvent = receiveEvent;
            transitionAccepted = CollectionUtilsServiceProxy.getInstance().extract(fsm.getTransitionFunction(), new IPredicate<Transition>() {

                @Override
                public boolean evaluate(final Transition e1) {
                    if (e1.getInput().equals(currentState) && e1.getEvent().equals(FsmOpSem.this.receiveEvent)) {
                        return true;
                    }
                    return false;
                };
            });
            return true;
        }
        LoggerServiceProxy.getInstance().getLogger(FsmOpSem.class).debug("CurrentStateNotChange");
        return false;
    }

}
