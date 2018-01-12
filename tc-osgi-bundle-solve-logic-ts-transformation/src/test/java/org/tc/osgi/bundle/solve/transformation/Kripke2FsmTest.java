package org.tc.osgi.bundle.solve.transformation;

import org.junit.Test;
import org.tc.osgi.bundle.ts.destkit.visitor.Fsm2GraphvizVisitor;
import org.tc.osgi.bundle.ts.kripke.metamodel.Kripke;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.State;

/**
 * Kripke2FsmTest.java.
 * @author Collonville Thomas
 * @version 0.0.1
 * @req SRS_BUNDLE_TRANSFORMATION_M3_010
 * @track SDD_BUNDLE_TRANSFORMATION_M3_020
 */
public class Kripke2FsmTest {

    /**
     * test.
     */
    @Test
    public void test() {
        final State state0 = new State("S0", State.INITIAL);
        final State state1 = new State("S1", State.INITIAL);
        final State state2 = new State("S2", State.NOTINITIAL);

        final PropAtom p = new PropAtom("p");
        final PropAtom q = new PropAtom("q");

        final Kripke k = new Kripke("test");

        k.addPropAtom(p);
        k.addPropAtom(q);

        k.addState(state0);
        k.addState(state1);
        k.addState(state2);

        k.addPropAtomTo(state0, p);
        k.addPropAtomTo(state0, q);
        k.addPropAtomTo(state1, p);
        k.addPropAtomTo(state2, q);

        k.addTransition("S0", "S1");
        k.addTransition("S1", "S0");
        k.addTransition("S1", "S2");
        k.addTransition("S2", "S0");

        final Kripke2Fsm transfo = new Kripke2Fsm();
        transfo.transform(k).accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));
    }

}
