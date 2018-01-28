package org.tc.osgi.bundle.solve.transformation;

import junit.framework.Assert;

import org.junit.Test;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.visitor.Fsm2GraphvizVisitor;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;

/**
 * LpSatisfyOnIStateAsFSMTest.java.
 * 
 * @author Collonville Thomas
 * @version 0.0.1
 * @req SRS_BUNDLE_TRANSFORMATION_M3_020
 * @track SRS_BUNDLE_TRANSFORMATION_M3_010, SRS_BUNDLE_TRANSFORMATION_M3_020
 */
public class LpSatisfyOnIStateAsFSMTest {

	/**
	 * test.
	 */
	@Test
	public void test() {
		try {
		org.tc.osgi.bundle.ts.kripke.module.service.CollectionUtilsServiceProxy.getInstance()
				.setService(new CollectionUtilsServiceImpl());
		org.tc.osgi.bundle.solve.transformation.module.service.CollectionUtilsServiceProxy.getInstance()
				.setService(new CollectionUtilsServiceImpl());
		org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
		final State state0 = new State("i", State.INITIAL, State.MARKED);
		final State state1 = new State("S0", State.NOTINITIAL, State.MARKED);
		final State state2 = new State("S1", State.NOTINITIAL, State.MARKED);
		final State state3 = new State("S2", State.NOTINITIAL, State.MARKED);

		final FiniteStateMachine k = new FiniteStateMachine("test2");

		k.addState(state0);
		k.addState(state1);
		k.addState(state2);
		k.addState(state3);
		k.addTransition("i", "p", "S0");
		k.addTransition("i", "q", "S0");
		k.addTransition("i", "p", "S1");
		k.addTransition("S0", "p", "S1");
		k.addTransition("S1", "q", "S2");
		k.addTransition("S1", "p", "S0");
		k.addTransition("S1", "q", "S0");
		k.addTransition("S2", "p", "S0");
		k.addTransition("S2", "q", "S0");

		k.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));

		final LpSatisfyOnIState sk = new LpSatisfyOnIState(k, state0);
		final String formule = "et(not(_p_),_q_)";
		Assert.assertEquals("F", sk.evaluate(formule));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
