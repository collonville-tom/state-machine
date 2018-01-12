package org.tc.osgi.bundle.solve.transformation;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.kripke.metamodel.Kripke;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;

/**
 * Kripke2Fsm.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class Kripke2Fsm {

    /**
     * transform.
     * @param k Kripke
     * @return FiniteStateMachine
     */
    public FiniteStateMachine transform(final Kripke k) {
        final FiniteStateMachine fsm = new FiniteStateMachine("k2" + k.getName());
        fsm.addState(new org.tc.osgi.bundle.ts.destkit.metamodel.core.State("i", org.tc.osgi.bundle.ts.destkit.metamodel.core.State.INITIAL, org.tc.osgi.bundle.ts.destkit.metamodel.core.State.MARKED));
        for (final org.tc.osgi.bundle.ts.kripke.metamodel.core.State s : k.getCloneStatesSet().getInitialStateSet()) {
            fsm.addState(new org.tc.osgi.bundle.ts.destkit.metamodel.core.State(s.getName(), org.tc.osgi.bundle.ts.destkit.metamodel.core.State.NOTINITIAL,
                org.tc.osgi.bundle.ts.destkit.metamodel.core.State.MARKED));
            for (final PropAtom atom : s.getPropAtomSet()) {
                fsm.addTransition("i", atom.getName(), s.getName());
            }
        }

        for (final org.tc.osgi.bundle.ts.kripke.metamodel.core.State s : k.getCloneStatesSet().getUnInitialStateSet()) {
            fsm.addState(new org.tc.osgi.bundle.ts.destkit.metamodel.core.State(s.getName(), org.tc.osgi.bundle.ts.destkit.metamodel.core.State.NOTINITIAL,
                org.tc.osgi.bundle.ts.destkit.metamodel.core.State.MARKED));
        }
        for (final org.tc.osgi.bundle.ts.kripke.metamodel.core.Transition t : k.getTransitionFunction()) {
            for (final PropAtom atom : t.getOutput().getPropAtomSet()) {
                fsm.addTransition(t.getInput().getName(), atom.getName(), t.getOutput().getName());
            }
        }
        return fsm;

    }

}
