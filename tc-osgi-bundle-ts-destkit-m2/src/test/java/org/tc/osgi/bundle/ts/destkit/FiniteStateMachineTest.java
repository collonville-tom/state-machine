package org.tc.osgi.bundle.ts.destkit;

import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.bundle.ts.destkit.exec.FsmOpSem;
import org.tc.osgi.bundle.ts.destkit.exec.exception.ExecInitExeception;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;


/**
 * FiniteStateMachineTest.java.
 * @req STD_BUNDLE_DESTKIT_M2_010
 * @track SRS_BUNDLE_DESTKIT_M2_010, SRS_BUNDLE_DESTKIT_M2_020, SRS_BUNDLE_DESTKIT_M2_030, SRS_BUNDLE_DESTKIT_M2_040, SRS_BUNDLE_DESTKIT_M2_050
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class FiniteStateMachineTest {

    /**
     * test.
     */
    @Test
    public void test() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final Event a = new Event("a");
        final Event b = new Event("b");

        final State x = new State("x", State.INITIAL, State.NOTMARKED);
        final State y = new State("y", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsmab = new FiniteStateMachine("ab");

        fsmab.addEvent(a);
        fsmab.addEvent(b);
        fsmab.addState(x);
        fsmab.addState(y);
        fsmab.addTransition("x", "a", "y");
        fsmab.addTransition("y", "b", "x");

        try {
            final FsmOpSem sem = new FsmOpSem(fsmab);
            sem.update(a);
            Assert.assertEquals(y, sem.next());
            sem.update(b);
            Assert.assertEquals(x, sem.next());
        } catch (final ExecInitExeception e) {
            Assert.fail();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }

    }

}
