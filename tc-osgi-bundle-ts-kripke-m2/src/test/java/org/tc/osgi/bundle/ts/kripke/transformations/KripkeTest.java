package org.tc.osgi.bundle.ts.kripke.transformations;

import org.junit.Test;
import org.tc.osgi.bundle.ts.kripke.metamodel.Kripke;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.State;
import org.tc.osgi.bundle.ts.kripke.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;

/**
 * KripkeTest.java.
 * @req STD_BUNDLE_KRIPKE_M2_010
 * @track SRS_BUNDLE_KRIPKE_M2_010
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class KripkeTest {

    /**
     * test.
     */
    @Test
    public void test() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
        final State state0 = new State("0", State.INITIAL);
        final State state1 = new State("1", State.NOTINITIAL);
        final State state3 = new State("3", State.NOTINITIAL);

        final PropAtom pa1 = new PropAtom("e1");
        final PropAtom pa2 = new PropAtom("e2");
        final PropAtom pa3 = new PropAtom("e3");
        final PropAtom pa4 = new PropAtom("e4");
        final PropAtom pa5 = new PropAtom("toto");

        final Kripke k = new Kripke("test");

        k.addPropAtom(pa1);
        k.addPropAtom(pa2);
        k.addPropAtom(pa3);

        k.addState(state0);
        k.addState(state1);

        k.addPropAtomTo(state0, pa3);
        k.addPropAtomTo(state0, pa2);
        k.addPropAtomTo(state3, pa4);
        k.addPropAtomTo("2", pa5);

        k.addTransition("0", "1");
        k.addTransition("0", "2");
        k.addTransition("1", "2");
        k.addTransition("2", "3");
        k.addTransition("3", "0");
        k.addTransition("3", "3");

    }

}
