package org.tc.osgi.bundle.solve.transformation;

import junit.framework.Assert;

import org.junit.Test;
import org.tc.osgi.bundle.ts.kripke.metamodel.Kripke;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.State;

/**
 * LpSatisfyOnIStateAsKripkeTest.java.
 * @author Collonville Thomas
 * @version 0.0.1
 * @req SRS_BUNDLE_TRANSFORMATION_M3_030
 * @track SRS_BUNDLE_TRANSFORMATION_M3_010, SRS_BUNDLE_TRANSFORMATION_M3_020
 */
public class LpSatisfyOnIStateAsKripkeTest {

    @Test
    public void testKS() {
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

        final LpSatisfyOnIState sk = new LpSatisfyOnIState(k, state0);
        final String formule = "et(not(_p_),_q_)";
        Assert.assertEquals("F", sk.evaluate(formule));
    }
}
